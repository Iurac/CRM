package com.iurac.crm.settings.dao;

import com.iurac.crm.settings.domain.DicValue;

import java.util.List;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.dao
 * Author: IuRac
 * CreateTime: 2020-11-12 13:18
 * Description:
 */
public interface DicValueDao {
    List<DicValue> getByCode(String code);
}
