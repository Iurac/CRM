package com.iurac.crm.workbench.domain;

/**
 * BelongsProject: workspaceForCrm
 * BelongsPackage: com.iurac.crm.workbench.domain
 * Author: IuRac
 * CreateTime: 2020-11-05 17:17
 * Description:
 */
public class ActivityRemark {
    private String id;              //主键 UUID
    private String noteContent;     //备注信息
    private String createTime;      //创建时间 年-月-日 时:分:秒
    private String createBy;        //创建人
    private String editTime;        //修改时间 年-月-日 时:分:秒
    private String editBy;          //修改人
    private String editFlag;        //是否被修改过
    private String activityId;      //活动id(外键，关联t_activity.id) UUID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
