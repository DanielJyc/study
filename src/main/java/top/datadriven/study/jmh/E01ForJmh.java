package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.*;

/**
 * @description: 结论：倒序遍历优于正序遍历。
 * for (int i = length; i > 0; i--)优于for (int i = 0; i < length; i++)，
 * 原因：i > 0优于i < length
Benchmark                  Mode  Cnt           Score           Error  Units
Helloworld.countBackward  thrpt   30  1562451226.200 ± 167230567.087  ops/s
Helloworld.countForward   thrpt   30  1641957271.120 ±  75079213.514  ops/s

 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 12:00 下午
 * @version: 1.0.0
 */
//@State(Scope.Benchmark)是修饰成员变量作用域是整个测试，能够被整个测试过程可见。
@State(Scope.Benchmark)
public class E01ForJmh {
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void countBackward() {
        for (int i = 0; i < 1_000_000; i++) {
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void countForward() {
        for (int i = 1_000_000; i > 0; i--) {

        }
    }
}
