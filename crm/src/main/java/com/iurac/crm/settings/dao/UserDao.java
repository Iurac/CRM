package com.iurac.crm.settings.dao;

import com.iurac.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.dao
 * Author: IuRac
 * CreateTime: 2020-11-04 20:44
 * Description:
 */
public interface UserDao {

    public User login(Map<String,String> map);

    List<User> getUserList();

    int updatePwd(Map<String,String> map);
}
