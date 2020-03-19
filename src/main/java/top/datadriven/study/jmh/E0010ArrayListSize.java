package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 测试list性能。运行结果：
 * Benchmark                                   Mode  Cnt    Score    Error  Units
 * E0010ArrayListSize.arrayList               thrpt    9  549.941 ± 65.241  ops/s
 * E0010ArrayListSize.arrayListSpecifiedSize  thrpt    9  587.429 ± 21.878  ops/s
 * 结论：指定大小，或者不指定大小，性能差距不大。
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 9:04 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0010ArrayListSize {
    private static final int SIZE = 100000;

    /**
     * 指定大小
     */
    @Benchmark
    public void arrayListSpecifiedSize() {
        List<String> list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add("第" + i + "个");
        }
    }

    /**
     * 不指定大小
     */
    @Benchmark
    public void arrayList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            list.add("第" + i + "个");
        }
    }
}
