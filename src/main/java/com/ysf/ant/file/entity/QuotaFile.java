package com.ysf.ant.file.entity;


/**
 * @author Yeshufeng
 * @title
 * @date 2018/9/5
 */
public class QuotaFile {

    private String id;

    private String groupId;

    private float quota;

    public QuotaFile() {
    }

    public QuotaFile(String id, String groupId, float quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

}
