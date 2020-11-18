package com.iurac.crm.workbench.service;

import com.iurac.crm.workbench.domain.Contacts;

import java.util.List;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service
 * Author: IuRac
 * CreateTime: 2020-11-16 15:46
 * Description:
 */
public interface ContactsService {
    List<Contacts> getContactsListByName(String cname);
}
