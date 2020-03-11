package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;

/**
 * @description:
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 9:04 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E03HashMapSize {
    private static final int MAP_SIZE = 1024*64;
    private static final int MAP_SIZE_NOT_POWER = MAP_SIZE - 1;

    /**
     * 2的整数次幂
     */
    @Benchmark
    public void power() {
        HashMap<Integer, String> map = new HashMap<Integer, String>(MAP_SIZE);
        for (int i = 0; i < MAP_SIZE+2; i++) {
            map.put(i, "第" + i + "个");
        }
    }

    /**
     * 非2的整数次幂
     */
    @Benchmark
    public void notPower() {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < MAP_SIZE+2; i++) {
            map.put(i, "第" + i + "个");
        }
    }
}
