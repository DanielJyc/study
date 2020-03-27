package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Arrays;
import java.util.Random;

/**
 * @description: stream 并行性能测试。
 * size:10000
 * Benchmark                                Mode  Cnt       Score        Error  Units
 * E0012StreamIntJmh.minIntFor             thrpt    9  356276.657 ± 211646.114  ops/s
 * E0012StreamIntJmh.minIntParallelStream  thrpt    9   73683.204 ±  15310.969  ops/s
 * E0012StreamIntJmh.minIntStream          thrpt    9   33701.386 ±   4804.593  ops/s
 * *
 * size:1000000
 * Benchmark                                Mode  Cnt      Score       Error  Units
 * E0012StreamIntJmh.minIntFor             thrpt    9   2489.567 ±   247.489  ops/s
 * E0012StreamIntJmh.minIntParallelStream  thrpt    9   2444.333 ±   230.244  ops/s
 * E0012StreamIntJmh.minIntStream          thrpt    9  24642.739 ± 58229.286  ops/s
 * *
 * size:1000
 * Benchmark                                Mode  Cnt        Score        Error  Units
 * E0012StreamIntJmh.minIntFor             thrpt    9  3269656.703 ± 149236.815  ops/s
 * E0012StreamIntJmh.minIntParallelStream  thrpt    9   144272.732 ±  65161.622  ops/s
 * E0012StreamIntJmh.minIntStream          thrpt    9   349978.337 ±  14040.005  ops/s
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 12:00 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0012StreamIntJmh {
    int[] arr;

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Setup
    public void init() {
        arr = new int[100000];
        randomInt(arr);
        // 配置线程池个数，最大值不能超过MAX_CAP,即32767
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8");
    }


    @Benchmark
    public void minIntFor() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
    }

    @Benchmark
    public void minIntStream() {
        Arrays.stream(arr).min().getAsInt();
    }

    @Benchmark
    public void minIntParallelStream() {
        Arrays.stream(arr).parallel().min().getAsInt();
    }

    private void randomInt(int[] arr) {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt();
        }
    }
}
