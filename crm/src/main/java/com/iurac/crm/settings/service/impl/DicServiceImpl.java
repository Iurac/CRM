package com.iurac.crm.settings.service.impl;

import com.iurac.crm.settings.dao.DicTypeDao;
import com.iurac.crm.settings.dao.DicValueDao;
import com.iurac.crm.settings.domain.DicType;
import com.iurac.crm.settings.domain.DicValue;
import com.iurac.crm.settings.service.DicService;
import com.iurac.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.service.impl
 * Author: IuRac
 * CreateTime: 2020-11-12 13:22
 * Description:
 */
public class DicServiceImpl implements DicService {

    DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
    DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);


    @Override
    public Map<String, List<DicValue>> getAll() {

        List<DicType> dicTypeList = dicTypeDao.getAll();
        Map<String, List<DicValue>> map = new HashMap<>();

        for(DicType dicType:dicTypeList){
            String code = dicType.getCode();

            List<DicValue> dicValueList = dicValueDao.getByCode(code);

            map.put(code,dicValueList);
        }
        return map;
    }
}
