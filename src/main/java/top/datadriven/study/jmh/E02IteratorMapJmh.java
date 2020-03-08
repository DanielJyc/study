package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: map迭代的效率比对
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 2:38 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E02IteratorMapJmh {
    HashMap<Integer, String> map = new HashMap<>();

    @Setup
    public void init() {
        for (int i = 0; i < 4; i++) {
            map.put(i, "第" + i + "个");
        }
    }

    @Benchmark
    public void keys() {
        for (Integer kk : map.keySet()) {
        }
    }

    @Benchmark
    public void values() {
        for (String v : map.values()) {
        }
    }


    @Benchmark
    public void keysValues() {
        for (Integer kk : map.keySet()) {
            map.get(kk);
        }
    }

    @Benchmark
    public void entrySet() {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
    }

    @Benchmark
    public void iterator() {
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer ii = (Integer) it.next();
            map.get(ii);
        }
    }

    @Benchmark
    public void forSize() {
        Object key[] = map.keySet().toArray();
        for (int i = 0; i < map.size(); i++) {
            map.get(key[i]);
        }
    }

    @Benchmark
    public void forEach() {
        map.forEach((k, v) -> {

        });
    }
}
