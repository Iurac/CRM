package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    int save(Contacts contacts);

    List<Contacts> getContactsListByName(String cname);
}
