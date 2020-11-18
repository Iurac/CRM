package com.iurac.crm.workbench.service.impl;

import com.iurac.crm.utils.SqlSessionUtil;
import com.iurac.crm.workbench.dao.ContactsDao;
import com.iurac.crm.workbench.domain.Activity;
import com.iurac.crm.workbench.domain.Contacts;
import com.iurac.crm.workbench.service.ContactsService;

import java.util.List;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service.impl
 * Author: IuRac
 * CreateTime: 2020-11-16 15:45
 * Description:
 */
public class ContactsServiceImpl implements ContactsService {
    ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);

    @Override
    public List<Contacts> getContactsListByName(String cname) {
        List<Contacts> contactsList = contactsDao.getContactsListByName(cname);

        return contactsList;
    }
}
