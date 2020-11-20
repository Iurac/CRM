package com.iurac.crm.workbench.service.impl;

import com.iurac.crm.utils.SqlSessionUtil;
import com.iurac.crm.utils.UUIDUtil;
import com.iurac.crm.vo.EchartsVo;
import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.dao.CustomerDao;
import com.iurac.crm.workbench.dao.TranDao;
import com.iurac.crm.workbench.dao.TranHistoryDao;
import com.iurac.crm.workbench.domain.Customer;
import com.iurac.crm.workbench.domain.Tran;
import com.iurac.crm.workbench.domain.TranHistory;
import com.iurac.crm.workbench.service.TranService;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service.impl
 * Author: IuRac
 * CreateTime: 2020-11-16 13:14
 * Description:
 */
public class TranServiceImpl implements TranService {
    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public PaginationVo<Tran> transactionPageList(Map<String, Object> map) {
        PaginationVo<Tran> tranPaginationVo = new PaginationVo<>();

        int total = tranDao.getTotalByCondition(map);
        List<Tran> tranList = tranDao.getTransactionByCondition(map);

        tranPaginationVo.setDataList(tranList);
        tranPaginationVo.setTotal(total);

        return tranPaginationVo;
    }

    @Override
    public boolean save(Tran tran, String customerName) {
        boolean flag = true;
        Customer customer = customerDao.getCustomerByName(customerName);

        if(customer == null){
            customer = new Customer();
            String customerId = UUIDUtil.getUUID();
            customer.setId(customerId);
            customer.setName(customerName);
            customer.setOwner(tran.getOwner());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setDescription(tran.getDescription());
            customer.setCreateTime(tran.getCreateTime());
            customer.setCreateBy(tran.getCreateBy());

            int result1 = customerDao.save(customer);
            if(result1 != 1){
                flag = false;
            }
            tran.setCustomerId(customerId);

        }else {
            tran.setCustomerId(customer.getId());
        }
        int result2 = tranDao.save(tran);
        if(result2 != 1 ){
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setTranId(tran.getId());
        int result3 = tranHistoryDao.save(tranHistory);
        if(result3 != 1 ){
            flag = false;
        }
        return flag ;
    }

    @Override
    public Tran detail(String id) {
        Tran tran = tranDao.detail(id);

        return tran;
    }

    @Override
    public List<TranHistory> getTranHistoryListByTid(String tid) {
        List<TranHistory> tranHistoryList = tranHistoryDao.getTranHistoryListByTid(tid);

        return tranHistoryList;
    }

    @Override
    public boolean changeStage(Tran tran) {
        boolean flag =true;
        int count1 = tranDao.changeStage(tran);
        if(count1 != 1){
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setCreateBy(tran.getEditBy());
        tranHistory.setCreateTime(tran.getEditTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());

        int count2 = tranHistoryDao.save(tranHistory);
        if(count2 != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public EchartsVo getEcharts() {
        int total = tranDao.getTotal();
        List<Map<String ,Object>> dataList  = tranDao.getNumOfStage();

        EchartsVo echartsVo = new EchartsVo();
        echartsVo.setTotal(total);
        echartsVo.setDataList(dataList);

        return echartsVo;
    }
}
