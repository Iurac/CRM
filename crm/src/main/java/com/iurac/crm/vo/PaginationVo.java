package com.iurac.crm.vo;

import java.util.List;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.vo
 * Author: IuRac
 * CreateTime: 2020-11-08 23:10
 * Description:
 */
public class PaginationVo<T> {
    int total;
    List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
