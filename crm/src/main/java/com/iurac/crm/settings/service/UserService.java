package com.iurac.crm.settings.service;

import com.iurac.crm.exception.LoginException;
import com.iurac.crm.settings.domain.User;

import java.util.List;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.service
 * Author: IuRac
 * CreateTime: 2020-11-04 21:58
 * Description:
 */
public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();

    boolean updatePwd(String id, String newPwd);
}
