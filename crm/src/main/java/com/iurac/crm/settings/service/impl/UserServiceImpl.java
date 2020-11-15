package com.iurac.crm.settings.service.impl;

import com.iurac.crm.exception.LoginException;
import com.iurac.crm.settings.dao.UserDao;
import com.iurac.crm.settings.domain.User;
import com.iurac.crm.settings.service.UserService;
import com.iurac.crm.utils.DateTimeUtil;
import com.iurac.crm.utils.SqlSessionUtil;
import sun.rmi.runtime.Log;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.service.impl
 * Author: IuRac
 * CreateTime: 2020-11-04 21:59
 * Description:
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    public User login(String loginAct, String loginPwd, String ip) throws LoginException{

        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        //验证密码
        if(user == null){
            throw new LoginException("账号密码错误,登录失败!");
        }

        //验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(currentTime.compareTo(expireTime) >= 0){
            throw new LoginException("该账号已失效!");
        }

        //验证锁定状态
        String lockState = user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("该账号已被锁定!");
        }

        //验证ip地址
        String allowIps = user.getAllowIps();
        if(allowIps != null && !allowIps.isEmpty()){
            if(!allowIps.contains(ip)){
                throw new LoginException("当前ip访问受限");
            }
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public boolean updatePwd(String id, String newPwd) {
        Map<String ,String> map = new HashMap<>();
        map.put("id",id);
        map.put("newPwd",newPwd);

        int result = userDao.updatePwd(map);

        if(result == 1){
            return true;
        }
        return false;
    }
}
