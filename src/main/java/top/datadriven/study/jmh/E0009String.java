package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * @description: String  性能测试。执行结果：
 * Benchmark                      Mode  Cnt       Score      Error  Units
 * E0009String.intToString       thrpt    9   57482.661 ± 2587.864  ops/s
 * E0009String.newString         thrpt    9   59092.919 ± 4429.442  ops/s
 * E0009String.plusString        thrpt    9  133411.564 ± 2030.376  ops/s
 * E0009String.plusStringIntern  thrpt    9   10207.336 ±  174.257  ops/s
 * * 结果：int+""方式，性能较好一些。如果能预料到字符串常量池有了这些字符串，建议使用(int+"").intern();
 * *      并且String.valueOf(i)方式，最终会new String，会创建过多对象。
 * *      如果非要要这种方式，最好使用String.valueOf(i).intern();
 * * 备注：1. new String都是在堆上创建字符串对象。当调用 intern() 方法时，编译器会将字符串添加到常量池中（stringTable维护），并返回指向该常量的引用。
 * *      2. 常量字符串的“+”操作，编译阶段直接会合成为一个字符串。如string str=”JA”+”VA”，在编译阶段会直接合并成语句String str=”JAVA”，于是会去常量池中查找是否存在”JAVA”,从而进行创建或引用。
 * *      3. 常量字符串和变量拼接时（如：String str3=baseStr + “01”;）会调用stringBuilder.append()在堆上创建新的对象。
 * * 参考：https://blog.csdn.net/tyyking/article/details/82496901
 * *      https://blog.csdn.net/qq_39713775/article/details/100149499
 * *todo 测试一下内存使用量
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/9 10:00 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0009String {
    private static final String TEST_STR = "abcdefghijklmnopqrstuvwxyz";

    @Benchmark
    public void newString() {
        for (int i = 0; i < 1000; i++) {
            String.valueOf(i);
        }
    }

    @Benchmark
    public void plusString() {
        for (int i = 0; i < 1000; i++) {
            String str = i + "";
        }
    }

    @Benchmark
    public void plusStringIntern() {
        for (int i = 0; i < 1000; i++) {
            String str = (i + "").intern();
        }
    }

    @Benchmark
    public void intToString() {
        for (int i = 0; i < 1000; i++) {
            Integer.toString(i);
        }
    }

}
