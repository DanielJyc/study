package top.datadriven.performance.tmp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 单例队列容器消费
 * 1. 多线程题目：单例队列容器消费
 * 实现一个单例队列容器，提供三个方法 offer，poll，size
 * 写三个线程，
 * 线程1将字符串a挨个添加到容器中，
 * 线程2将字符串b挨个添加到容器中，
 * 线程1与线程2同时启动后交替执行
 * 线程3监听容器变化，调用poll每隔1s挨个输出
 * 示例：
 * 输入：a = "hloaiaa", b = "el,lbb"
 * 输出：hello,alibaba
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/7/2 7:27 下午
 * @version: 1.0.0
 */
public class ConsumeQueue {

    private static Lock lock = new ReentrantLock();

    private static Condition bCondition = lock.newCondition();

    private static Condition aCondition = lock.newCondition();

    private static CountDownLatch count = new CountDownLatch(1);

    public static void main(String[] args) {

        String a = "hloaiaa";
        char[] aChar = a.toCharArray();

        String b = "el,lbb";
        char[] bChar = b.toCharArray();

        MyQueue myQueue = new MyQueue();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                //保证第一个线程开始执行
                count.countDown();
                for (char ac : aChar) {
                    bCondition.signal();
                    myQueue.offer(ac);
                    aCondition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                count.await();
                for (char bc : bChar) {
                    aCondition.signal();
                    myQueue.offer(bc);
                    bCondition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t3 = new Thread(() -> {
            while (myQueue.size() > 0) {
                try {
                    Thread.sleep(1000);
                    System.out.println(myQueue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //开始执行：放入容器
        t1.start();
        t2.start();
        //从容器取出
        t3.start();
    }


    private static class MyQueue {

        LinkedBlockingDeque<Character> queue = new LinkedBlockingDeque<>();

        AtomicInteger size = new AtomicInteger();

        public void offer(Character s) {
            queue.offer(s);
            size.getAndIncrement();
        }

        public Character poll() {
            Character ret = queue.poll();
            size.getAndDecrement();
            return ret;
        }

        public int size() {
            return size.get();
        }
    }

}
