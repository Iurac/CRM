package com.iurac.crm.workbench.service.impl;

import com.iurac.crm.settings.dao.UserDao;
import com.iurac.crm.settings.domain.User;
import com.iurac.crm.utils.DateTimeUtil;
import com.iurac.crm.utils.SqlSessionUtil;
import com.iurac.crm.utils.UUIDUtil;
import com.iurac.crm.vo.PaginationVo;
import com.iurac.crm.workbench.dao.*;
import com.iurac.crm.workbench.domain.*;
import com.iurac.crm.workbench.service.ClueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.service.impl
 * Author: IuRac
 * CreateTime: 2020-11-12 13:31
 * Description:
 */
public class ClueServiceImpl implements ClueService {
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao =  SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Clue clue) {
        int result = clueDao.save(clue);

        if(result == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public PaginationVo<Clue> cluePageList(Map<String, Object> map) {
        PaginationVo<Clue> cluePaginationVo = new PaginationVo<>();

        int total = clueDao.getTotalByCondition(map);
        List<Clue> clueList = clueDao.getClueByCondition(map);

        cluePaginationVo.setTotal(total);
        cluePaginationVo.setDataList(clueList);

        return cluePaginationVo;
    }

    @Override
    public Map<String, Object> getUserListAndClue(String id) {
        List<User> userList = userDao.getUserList();
        Clue clue = clueDao.getClueById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("uList",userList);
        map.put("c",clue);

        return map;
    }

    @Override
    public boolean update(Clue clue) {
        int result = clueDao.update(clue);

        if(result == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;

        int remarkCount = clueRemarkDao.getByCids(ids);
        int deleteRemarkCount = clueRemarkDao.deleteByCids(ids);
        if(remarkCount != deleteRemarkCount){
            flag = false;
        }

        int deleteCount = clueDao.deleteByIds(ids);
        if(deleteCount != ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Clue detail(String id) {
        Clue clue = clueDao.getDetail(id);

        return clue;
    }

    @Override
    public List<ClueRemark> getRemarkListByCid(String cid) {
        List<ClueRemark> clueRemarkList = clueRemarkDao.getRemarkListByCid(cid);

        return clueRemarkList;
    }

    @Override
    public boolean saveRemark(ClueRemark clueRemark) {
        int result = clueRemarkDao.saveRemark(clueRemark);

        if(result == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateRemark(ClueRemark clueRemark) {
        int result = clueRemarkDao.updateRemark(clueRemark);

        if(result == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteRemark(String id) {
        int result = clueRemarkDao.deleteRemark(id);

        if(result == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean unbund(String id) {
        int result = clueActivityRelationDao.unbund(id);

        if(result == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean bund(String cid, String[] aids) {
        boolean flag = true;

        for(String aid :aids){
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(aid);
            clueActivityRelation.setClueId(cid);

            int result = clueActivityRelationDao.bund(clueActivityRelation);

            if(result != 1){
                return false;
            }
        }
        return flag;
    }

    @Override
    public boolean convert(String cid, Tran tran, String createBy) {
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = true;

        Clue clue = clueDao.getClueById(cid);
        String company = clue.getCompany();

        Customer customer = customerDao.getCustomerByName(company);
        if(customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(company);
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());
            customer.setWebsite(clue.getWebsite());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setContactSummary(clue.getContactSummary());
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setAddress(clue.getAddress());
            int customerCount = customerDao.save(customer);
            if(customerCount!=1){
                flag = false;
            }
        }

        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setCustomerId(customer.getId());
        int contactsCount = contactsDao.save(contacts);
        if(contactsCount!=1){
            flag = false;
        }

        List<ClueRemark> clueRemarkList = clueRemarkDao.getRemarkListByCid(cid);
        for(ClueRemark clueRemark : clueRemarkList){
            String noteContent = clueRemark.getNoteContent();
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            int contactsRemarkCount = contactsRemarkDao.save(contactsRemark);
            if(contactsRemarkCount!=1){
                flag = false;
            }

            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            int customerRemarkCount = customerRemarkDao.save(customerRemark);
            if(customerRemarkCount!=1){
                flag = false;
            }
        }

        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByCid(cid);
        for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            String aid = clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(aid);
            contactsActivityRelation.setContactsId(contacts.getId());
            int contactsActivityRelationCount = contactsActivityRelationDao.save(contactsActivityRelation);
            if(contactsActivityRelationCount != 1) {
                flag = false;
            }
        }

        if(tran != null){
            tran.setContactsId(contacts.getId());
            tran.setCustomerId(customer.getId());
            tran.setOwner(clue.getOwner());
            tran.setSource(clue.getSource());
            tran.setDescription(clue.getDescription());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setContactSummary(clue.getContactSummary());
            int tranCount = tranDao.save(tran);
            if(tranCount != 1){
                flag = false;
            }

            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setTranId(tran.getId());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            int tranHistoryCount = tranHistoryDao.save(tranHistory);
            if(tranHistoryCount != 1){
                flag = false;
            }
        }

        for(ClueRemark clueRemark : clueRemarkList){
            int deleteClueRemarkCount = clueRemarkDao.deleteRemark(clueRemark.getId());
            if(deleteClueRemarkCount != 1){
                flag = false;
            }
        }

        for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            int deleteClueActivityRelationCount = clueActivityRelationDao.delete(clueActivityRelation.getId());
            if(deleteClueActivityRelationCount != 1){
                flag = false;
            }
        }

        int deleteClueCount = clueDao.delete(cid);
        if(deleteClueCount != 1){
            flag = false;
        }
        return flag;
    }
}
