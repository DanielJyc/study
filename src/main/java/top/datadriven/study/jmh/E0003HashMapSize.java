package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;

/**
 * @description: hashmap 2的整数次幂测试。
 * MAP_SIZE: 128
 * Benchmark                                Mode  Cnt       Score        Error  Units
 * E0003HashMapSize.notPower               thrpt    9  420283.283 ±  23334.147  ops/s
 * E0003HashMapSize.power                  thrpt    9  394122.107 ±  40066.092  ops/s
 * E0003HashMapSize.powerNotSpecifiedSize  thrpt    9  331625.126 ± 100268.711  ops/s
 * * MAP_SIZE: 1024
 * Benchmark                                Mode  Cnt      Score       Error  Units
 * E0003HashMapSize.notPower               thrpt    9  47463.499 ±  2553.343  ops/s
 * E0003HashMapSize.power                  thrpt    9  37716.722 ± 11414.081  ops/s
 * E0003HashMapSize.powerNotSpecifiedSize  thrpt    9  38182.892 ±  1781.893  ops/s
 * * 结论：初始化map的大小并不能提高效率。
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 9:04 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0003HashMapSize {
    private static final int MAP_SIZE = 1024;
    private static final int MAP_SIZE_NOT_POWER = MAP_SIZE + 1;

    /**
     * 2的整数次幂
     */
    @Benchmark
    public void power() {
        HashMap<Integer, String> map = new HashMap<>(MAP_SIZE);
        for (int i = 0; i < MAP_SIZE; i++) {
            map.put(i, "第" + i + "个");
        }
    }

    /**
     * 非2的整数次幂
     */
    @Benchmark
    public void notPower() {
        HashMap<Integer, String> map = new HashMap<>(MAP_SIZE_NOT_POWER);
        for (int i = 0; i < MAP_SIZE_NOT_POWER; i++) {
            map.put(i, "第" + i + "个");
        }
    }

    /**
     * 2的整数次幂：不指定大小
     */
    @Benchmark
    public void powerNotSpecifiedSize() {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < MAP_SIZE; i++) {
            map.put(i, "第" + i + "个");
        }
    }
}
