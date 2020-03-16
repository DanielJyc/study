package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @description: ArrayList 遍历测试
 * size=20
 * Benchmark                        Mode  Cnt          Score         Error  Units
 * E04ArrayListFor.foreach         thrpt   18   51132518.389 ± 1282716.978  ops/s
 * E04ArrayListFor.fori            thrpt   18  220638393.552 ± 5365801.885  ops/s
 * E04ArrayListFor.iterator        thrpt   18  203471378.768 ± 4536702.277  ops/s
 * E04ArrayListFor.listIterator    thrpt   18  204653085.510 ± 4235076.790  ops/s
 * E04ArrayListFor.parallelStream  thrpt   18     353627.830 ±   35923.332  ops/s
 * E04ArrayListFor.stream          thrpt   18   55688678.251 ± 1563777.723  ops/s
 * <p>
 * size=2000
 * Benchmark                        Mode  Cnt          Score         Error  Units
 * E04ArrayListFor.foreach         thrpt   18     540095.495 ±    9901.884  ops/s
 * E04ArrayListFor.fori            thrpt   18  208236976.241 ± 3338814.426  ops/s
 * E04ArrayListFor.iterator        thrpt   18  204092635.747 ± 4701834.332  ops/s
 * E04ArrayListFor.listIterator    thrpt   18  202016839.806 ± 6961530.883  ops/s
 * E04ArrayListFor.parallelStream  thrpt   18     152087.632 ±   22758.341  ops/s
 * E04ArrayListFor.stream          thrpt   18     548676.272 ±   13370.800  ops/s
 * <p>
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/9 10:00 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0004ArrayListFor {
    private static final int SIZE = 10;

    List<String> arrayList = new ArrayList<>();

    @Setup
    public void prepare() {
        for (int i = 0; i < SIZE; i++) {
            arrayList.add("第" + i + "个");
        }
    }


    /**
     * 用迭代器遍历
     */
    @Benchmark
    public void iterator() {
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    /**
     * 用列表迭代器遍历
     */
    @Benchmark
    public void listIterator() {
        ListIterator<String> listIterator = arrayList.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
        }

    }

    /**
     * 用size(),get()遍历
     */
    @Benchmark
    public void fori() {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i);
        }
    }

    /**
     * 用增强for遍历
     */
    @Benchmark
    public void foreach() {
        for (String str : arrayList) {
        }
    }

    /**
     * 用增强for遍历
     */
    @Benchmark
    public void stream() {
        arrayList.forEach(element -> {
        });
    }

    /**
     * 用增强for遍历
     */
    @Benchmark
    public void parallelStream() {
        arrayList.parallelStream().forEach(element -> {
        });
    }

}
