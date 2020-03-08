package top.datadriven.study.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/2/1 10:41 上午
 * @version: 1.0.0
 */
public class GcTypeTest {
    static List<Object> l;

    public static void main(String... args) {
        l = new ArrayList<>();
        for (int c = 0; c < 100_000_000; c++) {
            l.add(new Object());
        }
    }
}
