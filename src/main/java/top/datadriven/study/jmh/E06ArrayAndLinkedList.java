package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 测试list性能
 * -----size: 10
 * Benchmark                          Mode  Cnt        Score        Error  Units
 * E04ArrayAndLinkedList.arrayList   thrpt   18  9949718.297 ± 154220.215  ops/s
 * E04ArrayAndLinkedList.linkedList  thrpt   18  9590472.248 ± 275213.516  ops/s
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 9:04 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E06ArrayAndLinkedList {
    private static final int SIZE = 100000;

    /**
     * 2的整数次幂
     */
    @Benchmark
    public void arrayList() {
        List<String> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add("第" + i + "个");
        }
        for (String s : list) {

        }
    }

    /**
     * 非2的整数次幂
     */
    @Benchmark
    public void linkedList() {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add("第" + i + "个");
        }
        for (String s : list) {
        }
    }

    @Benchmark
    public void linkedListFori() {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add("第" + i + "个");
        }
        for (int i = 0; i < list.size(); i++) {

        }
    }

    @Benchmark
    public void linkedListForIt() {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add("第" + i + "个");
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()){
            it.next();
        }
    }
}
