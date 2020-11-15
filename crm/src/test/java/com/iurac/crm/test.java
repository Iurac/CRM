package com.iurac.crm;

import com.iurac.crm.utils.DateTimeUtil;
import com.iurac.crm.utils.MD5Util;
import com.iurac.crm.utils.UUIDUtil;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm
 * Author: IuRac
 * CreateTime: 2020-11-05 12:12
 * Description:
 */
public class test {
    public static void main(String[] args) {
        System.out.println(UUIDUtil.getUUID());
        System.out.println(MD5Util.getMD5("123"));

        System.out.println(DateTimeUtil.getSysTime());
    }
}
