package com.ysf.ant.file;

import com.ysf.ant.file.entity.QuotaFile;
import com.ysf.ant.file.entity.QuotaFileReadMessage;
import com.ysf.ant.file.entity.ThreadQuotaFile;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yeshufeng
 * @title
 * @date 2018/9/5
 */
public class QuotaFileFinder implements Runnable{

    private CountDownLatch latch;

    private BlockingQueue<QuotaFileReadMessage> queue;

    private ThreadQuotaFile threadQuotaFile;


    public QuotaFileFinder(CountDownLatch latch, BlockingQueue<QuotaFileReadMessage> queue, ThreadQuotaFile threadQuotaFile) {
        this.latch = latch;
        this.queue = queue;
        this.threadQuotaFile = threadQuotaFile;
    }

    @Override
    public void run() {


        while(latch.getCount() > 0){

            try {
                QuotaFileReadMessage message = queue.take();
                //read list
                findMinQuota(threadQuotaFile,message.getQuotaFileList());

                latch.countDown();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("consume message executor error");
                throw new RuntimeException("consume message executor error");
            }

        }
    }


    private void findMinQuota(ThreadQuotaFile threadQuotaFile,List<QuotaFile> quotaFileList){

        Map<String,QuotaFile> groupIdToMinQuotaFile = threadQuotaFile.getGroupIdToMinQuotaFile();
        quotaFileList.forEach(item -> {
            if(groupIdToMinQuotaFile.isEmpty() || null == groupIdToMinQuotaFile.get(item.getGroupId())){

                groupIdToMinQuotaFile.put(item.getGroupId(),item);

            }else if(null != groupIdToMinQuotaFile.get(item.getGroupId())){

                float oldMinQuota = groupIdToMinQuotaFile.get(item.getGroupId()).getQuota();
                if(item.getQuota() < oldMinQuota){
                    groupIdToMinQuotaFile.put(item.getGroupId(),item);
                }

            }
        });
    }


}
