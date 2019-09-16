package com.tutu2.thread;

import java.util.LinkedList;
import java.util.List;

public final class ThreadPool {
    // 默认线程个数
    private static int worker_num = 5;
    // 工作线程
    private WorkThread[] workThreads;
    // 未处理任务队列
    private List<Runnable> taskQueue = new LinkedList<>();
    // 已完成任务个数
    private static volatile int finished_task = 0;

    private static ThreadPool threadPool;

    // 初始化默认线程个数的线程池
    private ThreadPool(){this(5);}
    // 初始化自定义线程个数的线程池
    private ThreadPool(int worker_num){
        ThreadPool.worker_num = worker_num;
        workThreads = new WorkThread[worker_num];

        for (int i = 0 ; i < worker_num ; i++){
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }
    // 批量执行任务,其实只是把任务加入任务队列，什么时候执行由线程池管理器觉定
    public void execute(Runnable task){
        synchronized (taskQueue){
            taskQueue.add(task);
            taskQueue.notify();
        }
    }
    // 批量执行任务,其实只是把任务加入任务队列，什么时候执行由线程池管理器觉定
    public void execute(Runnable[] tasks){
        synchronized (taskQueue){
            for (Runnable task : tasks){
                taskQueue.add(task);
            }
            taskQueue.notify();
        }
    }

    public void execute(List<Runnable> tasks){
        synchronized (taskQueue){
            for (Runnable task : tasks){
                taskQueue.add(task);
            }
            taskQueue.notify();
        }
    }

    // 销毁线程池
    public void destroy(){
        // 判断是否有未执行的任务
        while (!taskQueue.isEmpty()){
            try {
                Thread.sleep(210);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 先停止 工作线程, 清空 工作线程内容, 再清空 任务队列, 最后线程池置空
        for (int i = 0; i < worker_num; i++){
            workThreads[i].stopWork();
            workThreads[i] = null;
        }
        taskQueue.clear();
        threadPool = null;
    }

    // 内部类, 工作线程
    private class WorkThread extends Thread {
        // 判断该工作线程是否有效, 用于结束工作线程
        private boolean isRunning = true;

        /* 关键, 如果任务队列不为空, 则取出任务执行, 如果任务队列空, 则等待 */
        @Override
        public void run() {
            Runnable r = null;
        // 若线程无效则自然结束run方法, 该线程就没用了
            while (isRunning) {
                // 判断是否有效, 以及队列是否有内容
                synchronized (taskQueue) {
                    // 再次判断isRunning 的原因是, 可能任务队列没任务了, 要销毁线程了, 将 isRunning=false, 防止死循环
                    while (taskQueue.isEmpty() && isRunning){
                        try {
                            taskQueue.wait(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 如果有任务, 则先取出任务, 当线程销毁时, 任务列表已经空了
                    if (!taskQueue.isEmpty()) {
                        r = taskQueue.remove(0);
                    }
                }
                // 执行任务
                if (r != null){
                    r.run();
                }
                finished_task ++ ;
                r = null;
            }
        }

        public void stopWork(){
            isRunning = false;
        }
    }
}
