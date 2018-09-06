package com.ysf.ant.file;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Yeshufeng
 * @title
 * @date 2018/9/5
 */
public class ExecutorManager {

    private static ExecutorService readExecutor;

    private static BlockingQueue<Runnable> readExecutorBlockQueue;

    static {
        readExecutorBlockQueue = new ArrayBlockingQueue<Runnable>(100);

        readExecutor = new ThreadPoolExecutor(10, 10,0L, TimeUnit.MILLISECONDS, readExecutorBlockQueue, new RejectedExecutionHandler() {
            @Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //线程池已满,加到队列
                try {
                    readExecutorBlockQueue.put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void registerTask4Read(Runnable readFileTask) {
        readExecutor.submit(readFileTask);
    }

    public void shutdown(){
        readExecutor.shutdown();
    }

}
