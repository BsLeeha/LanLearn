package Concurrent;

/*
 * start 启动一条单独的执行流，JVM 为该线程分配运行环境（PCB、 PC 、栈等），让 PC 指向 run，交由 OS 调度
 * 线程是独立执行的，main 线程退出，HelloThread 不会被强制退出
 * 不过 c/c++ 中遇到 return 0 似乎会 kill 所有的线程
 */


public class SimpleUsage {

    private boolean flag = false;

    public synchronized boolean getFlag() {
        return flag;
    }

    public static void main(String[] args) throws InterruptedException {

        SimpleUsage usage = new SimpleUsage();

        new Thread() {
            @Override
            public void run() {
                while (usage.getFlag()) ;
                System.out.println("yes2");
            }
        }.start();

        Thread.sleep(300);

        synchronized (usage) {
            usage.flag = true;
        }
        System.out.println("yes1");

    }
}
