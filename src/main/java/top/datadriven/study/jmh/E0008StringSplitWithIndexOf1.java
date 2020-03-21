package top.datadriven.study.jmh;

import org.apache.commons.lang.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * @description: String split 、index和tringUtil两种方式 性能测试。执行结果：
 * Benchmark                            Mode  Cnt         Score          Error  Units
 * E0008StringSplit.indexOneChar       thrpt    9  72440253.386 ± 16016962.563  ops/s
 * E0008StringSplit.indexTwoChar       thrpt    9  77594587.127 ±  1201769.767  ops/s
 * E0008StringSplit.splitOneChar       thrpt    9  11468268.244 ±   262297.276  ops/s
 * E0008StringSplit.splitTwoChar       thrpt    9   3382815.969 ±    70351.268  ops/s
 * E0008StringSplit.stringUtilOneChar  thrpt    9  11179531.251 ±   472332.985  ops/s
 * E0008StringSplit.stringUtilTwoChar  thrpt    9   6323514.286 ±   855572.829  ops/s
 * *  结论：能用indexOf的场景，必须使用indexOf。
 * *  备注：如果含有多个ij，split效率会更低。比如，再添加一个ij，效果如下：
 * Benchmark                            Mode  Cnt         Score         Error  Units
 * E0008StringSplit.indexOneChar       thrpt    9  77546696.073 ± 2880476.930  ops/s
 * E0008StringSplit.indexTwoChar       thrpt    9  77458399.657 ± 2461236.802  ops/s
 * E0008StringSplit.splitOneChar       thrpt    9   9095105.831 ±  117563.399  ops/s
 * E0008StringSplit.splitTwoChar       thrpt    9   2970828.885 ±   71137.948  ops/s
 * E0008StringSplit.stringUtilOneChar  thrpt    9   8578179.692 ±  555821.627  ops/s
 * E0008StringSplit.stringUtilTwoChar  thrpt    9   4821900.002 ±  343689.870  ops/s
 * *
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/9 10:00 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0008StringSplitWithIndexOf1 {
    private static final String TEST_STR = "abcdefghijklmnopqrstuvwxyz";

    /**
     * split方式：一个字节
     */
    @Benchmark
    public void splitOneChar() {
        String ret = TEST_STR.split("i")[0];
    }

    /**
     * split方式：2个字节
     */
    @Benchmark
    public void splitTwoChar() {
        String ret = TEST_STR.split("ij")[0];
    }

    /**
     * index方式：一个字节
     */
    @Benchmark
    public void indexOneChar() {
        int index = TEST_STR.indexOf("i");
        String ret = TEST_STR.substring(0, index);
    }

    /**
     * index方式：2个字节
     */
    @Benchmark
    public void indexTwoChar() {
        int index = TEST_STR.indexOf("ij");
        String ret = TEST_STR.substring(0, index);
    }

    /**
     * StringUtil 方式：1个字节
     */
    @Benchmark
    public void stringUtilOneChar() {
        String ret = StringUtils.split(TEST_STR, "i")[0];
    }

    /**
     * StringUtil 方式：2个字节
     */
    @Benchmark
    public void stringUtilTwoChar() {
        String ret = StringUtils.split(TEST_STR, "ij")[0];

    }
}
