package com.ysf.ant.file.entity;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author Yeshufeng
 * @title
 * @date 2018/9/5
 */
public class ThreadQuotaFile {

    private TreeMap<String,QuotaFile> groupIdToMinQuotaFile;


    public ThreadQuotaFile() {
        groupIdToMinQuotaFile = new TreeMap<String,QuotaFile>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return new Double(o1).compareTo(new Double(o2));
            }
        });

    }

    public TreeMap<String, QuotaFile> getGroupIdToMinQuotaFile() {
        return groupIdToMinQuotaFile;
    }

}
