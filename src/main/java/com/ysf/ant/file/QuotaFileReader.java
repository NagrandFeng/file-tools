package com.ysf.ant.file;


import com.ysf.ant.file.entity.QuotaFile;
import com.ysf.ant.file.entity.QuotaFileReadMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author Yeshufeng
 * @title
 * @date 2018/9/5
 */
public class QuotaFileReader implements Runnable {

    private String filePath;

    private BlockingQueue<QuotaFileReadMessage> queue;


    public QuotaFileReader(String filePath, BlockingQueue<QuotaFileReadMessage> queue) {
        this.filePath = filePath;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            //read file
            List<QuotaFile> fileList = readFile(filePath);

            //produce message
            QuotaFileReadMessage message = new QuotaFileReadMessage();
            message.setQuotaFileList(fileList);
            queue.put(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private List<QuotaFile> readFile(String filePath) throws IOException {
        List<QuotaFile> list = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            //文件不存在
            return list;
        }

        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String lineContent = null;
        while ((lineContent = br.readLine()) != null) {
            String[] pojoAttributeArr = lineContent.split(",");

            if (pojoAttributeArr.length != ATTRIBUTE_LENGTH) {
                continue;
            }
            QuotaFile quotaFile = new QuotaFile();
            quotaFile.setId(pojoAttributeArr[0]);
            quotaFile.setGroupId(pojoAttributeArr[1]);
            quotaFile.setQuota(Float.parseFloat(pojoAttributeArr[2]));
            list.add(quotaFile);
        }
        br.close();
        fileReader.close();
        return list;
    }

    private static final int ATTRIBUTE_LENGTH = 3;

}
