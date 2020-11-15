package com.iurac.crm.settings.service;

import com.iurac.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.settings.service
 * Author: IuRac
 * CreateTime: 2020-11-12 13:21
 * Description:
 */
public interface DicService {

    Map<String, List<DicValue>> getAll();
}
