package top.datadriven.study.demo;

import java.util.*;

/**
 * @description:
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 10:29 上午
 * @version: 1.0.0
 */
public class ListDemo {
    public static void main(String[] args) {
        // testList();
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < 40000; i++) {
            map.put(i, "第" + i + "个");
        }
        long t1 = System.nanoTime();

        //循环第一种
        for (Integer kk : map.keySet()) {
            map.get(kk);
        }

        long t2 = System.nanoTime();

        // 循环第二种
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            entry.getValue();
        }
        long t3 = System.nanoTime();

        // 循环第三种
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer ii = (Integer) it.next();
            map.get(ii);
        }
        long t4 = System.nanoTime();

        // 循环第四种
        Object key[] = map.keySet().toArray();
        for (int i = 0; i < map.size(); i++) {
            map.get(key[i]);
        }
        long t5 = System.nanoTime();
        System.out.println("第一种方法耗时：" + (t2 - t1) / 1000 + "微秒");
        System.out.println("第二种方法耗时：" + (t3 - t2) / 1000 + "微秒");
        System.out.println("第三种方法耗时：" + (t4 - t3) / 1000 + "微秒");
        System.out.println("第四种方法耗时：" + (t5 - t4) / 1000 + "微秒");
    }

    private static void testList() {
        List<String> ls = new ArrayList<>(2);
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("a");
        ls.add("b");
        ls.add("c");
        System.out.println(ls);
    }
}
