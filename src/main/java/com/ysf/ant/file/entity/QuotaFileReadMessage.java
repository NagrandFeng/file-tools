package com.ysf.ant.file.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yeshufeng
 * @title
 * @date 2018/9/5
 */
public class QuotaFileReadMessage implements Serializable {

    private List<QuotaFile> quotaFileList;

    public List<QuotaFile> getQuotaFileList() {
        return quotaFileList;
    }

    public void setQuotaFileList(List<QuotaFile> quotaFileList) {
        this.quotaFileList = quotaFileList;
    }

}
