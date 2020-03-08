package top.datadriven.study.gc;

import java.util.HashMap;

/**
 * @description: * 启动参数：-Xmx512m -Xms512m -XX:+UseSerialGC -XX:+PrintGCDetails -Xmn1m
 * * -XX:+UseSerialGC:新生代和老年代都用单线程的串行回收器。适合单核并发能力差得处理器。
 * * -XX:+UseParNewGC:新生代用并行的ParNew回收期，老年代都用单线程的串行回收器。适合多核，并发能力强的处理器。
 * * -XX:+UseParallelGC:新生代使用ParallelGC回收器，老年代使用串行回收器。
 * * -XX:+UseParallelOldGC:新生代使用ParallelGC回收器，老年代使用ParallelOldGC回收器。
 * * -XX:+UseConcMarkSweepGC:老年代使用CMS回收器。
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/1/31 5:48 下午
 * @version: 1.0.0
 */
public class StopWorldDemo {
    public static class MyThread extends Thread {
        HashMap<Long, byte[]> map = new HashMap<>();

        @Override
        public void run() {
            try {
                while (true) {
                    if (map.size() * 512 / 1024 / 1024 >= 450) {
                        //大于450M时，清理内存。
                        System.out.println("============准备清理==========:" + map.size());
                        map.clear();
                        System.out.println("clean map");
                    }
                    for (int i = 0; i < 100; i++) {
                        //消耗内存。
                        map.put(System.nanoTime(), new byte[512]);
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class PrintThread extends Thread {

        public static final long START_TIME = System.currentTimeMillis();

        @Override
        public void run() {
            try {
                while (true) {
                    long t = System.currentTimeMillis() - START_TIME;
                    System.out.println("time:" + t);
                    Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        PrintThread p = new PrintThread();
        t.start();
        p.start();
    }
}
