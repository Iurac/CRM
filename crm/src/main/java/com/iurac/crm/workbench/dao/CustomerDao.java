package com.iurac.crm.workbench.dao;

import com.iurac.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);
}
