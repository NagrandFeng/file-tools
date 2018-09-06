package com.ysf.ant.file;


import com.ysf.ant.file.entity.QuotaFile;
import com.ysf.ant.file.entity.QuotaFileReadMessage;
import com.ysf.ant.file.entity.ThreadQuotaFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yeshufeng
 * @title 文件处理入口
 * @date 2018/9/5
 */
public class QuotaFileProcessor {

    private CountDownLatch latch;

    private BlockingQueue<QuotaFileReadMessage> queue;

    private ExecutorManager executorManager;

    private String directoryPath;

    private List<String> filePathList;

    private void init(){
        this.queue = new ArrayBlockingQueue<>(100);
        this.executorManager = new ExecutorManager();
        this.filePathList = getFilePath(directoryPath);
        this.latch = new CountDownLatch(filePathList.size());
    }

    public QuotaFileProcessor(String directoryPath) {
        this.directoryPath = directoryPath;
        init();
    }

    public void getMinQuotaFromFilePath(){

        //reader
        for (String path : filePathList) {
            QuotaFileReader fileReader = new QuotaFileReader(path, queue);
            executorManager.registerTask4Read(fileReader);
        }
        ThreadQuotaFile quotaFile = new ThreadQuotaFile();

        //若使用TreeMap,只能起单个finder线程
        Thread sortThread = new Thread(new QuotaFileFinder(latch, queue, quotaFile));
        sortThread.start();

        try {
            latch.await();
            System.out.println("done...");
            executorManager.shutdown();

            Map<String, QuotaFile> map = quotaFile.getGroupIdToMinQuotaFile();
            map.forEach((groupId, minQuotaFile) -> System.out.println( groupId + "," + minQuotaFile.getId() + "," + minQuotaFile.getQuota()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private List<String> getFilePath(String filePath){
        List<String> filePathList = new ArrayList<>();
        File dirFile = new File(filePath);
        //判断是否存在
        if (!dirFile.exists()) {
            System.out.println("not exit");
            return filePathList;
        }
        //判断如果不是一个目录，要求必须是目录
        if (!dirFile.isDirectory()) {
            System.out.println("filePath must be directory");
            return filePathList;
        }

        //获取此目录下的所有文件名
        String[] fileList = dirFile.list();
        if(null != fileList){
            for (String fileName:fileList) {
                filePathList.add(filePath+"/"+fileName);
            }
        }

        return filePathList;
    }

}
