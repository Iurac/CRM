package com.iurac.crm.workbench.service.impl;

import com.iurac.crm.utils.SqlSessionUtil;
import com.iurac.crm.workbench.dao.CustomerDao;
import com.iurac.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service.impl
 * Author: IuRac
 * CreateTime: 2020-11-16 16:41
 * Description:
 */
public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> customerList = customerDao.getCustomerName(name);

        return customerList;
    }

}
