package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @description: LinkedList遍历效率测试
 * size=20
 * Benchmark                         Mode  Cnt         Score         Error  Units
 * E05LinkedListFor.foreach         thrpt   18  43805828.244 ± 1316149.635  ops/s
 * E05LinkedListFor.fori            thrpt   18  12590602.406 ±  574553.661  ops/s
 * E05LinkedListFor.iterator        thrpt   18  83886419.915 ± 2127840.301  ops/s
 * E05LinkedListFor.listIterator    thrpt   18  84547918.135 ± 1198777.015  ops/s
 * E05LinkedListFor.parallelStream  thrpt   18    259028.896 ±   32294.843  ops/s
 * E05LinkedListFor.stream          thrpt   18  44366655.130 ±  710717.560  ops/s
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/9 10:00 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0005LinkedListFor {
    private static final int SIZE = 20;

    List<String> stringLinkedList = new LinkedList<>();

    @Setup
    public void prepare() {
        for (int i = 0; i < SIZE; i++) {
            stringLinkedList.add("第" + i + "个");
        }
    }


    /**
     * 用迭代器遍历
     */
    @Benchmark
    public void iterator() {
        Iterator iterator = stringLinkedList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    /**
     * 用列表迭代器遍历
     */
    @Benchmark
    public void listIterator() {
        ListIterator<String> listIterator = stringLinkedList.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
        }

    }

    /**
     * 用size(),get()遍历
     */
    @Benchmark
    public void fori() {
        for (int i = 0; i < stringLinkedList.size(); i++) {
            stringLinkedList.get(i);
        }
    }

    /**
     * 用增强for遍历
     */
    @Benchmark
    public void foreach() {
        for (String str : stringLinkedList) {
        }
    }

    /**
     * 用增强for遍历
     */
    @Benchmark
    public void stream() {
        stringLinkedList.forEach(element -> {
        });
    }

    /**
     * 用增强for遍历
     */
    @Benchmark
    public void parallelStream() {
        stringLinkedList.parallelStream().forEach(element -> {
        });
    }

}
