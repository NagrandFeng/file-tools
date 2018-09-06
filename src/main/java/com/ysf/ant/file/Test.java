package com.ysf.ant.file;


/**
 * @author Yeshufeng
 * @title
 * @date 2018/9/5
 */
public class Test {

    public static void main(String[] args) {
        QuotaFileProcessor quotaFileProcessor = new QuotaFileProcessor("/data/antFile");
        quotaFileProcessor.getMinQuotaFromFilePath();
    }
}
