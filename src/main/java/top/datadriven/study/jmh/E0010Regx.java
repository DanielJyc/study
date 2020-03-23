package top.datadriven.study.jmh;

import org.apache.commons.lang.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 正则性能测试。 继续使用 {@link E0008StringSplitWithIndexOf2}中的例子
 * 运行结果：
 * xml常规1500字节：
 * Benchmark                     Mode  Cnt        Score        Error  Units
 * E0010Regx.greedyPattern      thrpt    9    52316.029 ±   1088.196  ops/s
 * E0010Regx.possessivePattern  thrpt    9    66036.027 ±   1356.228  ops/s
 * E0010Regx.reluctantPattern   thrpt    9  2129980.666 ± 359231.150  ops/s
 * *
 * xml大小1M左右：
 * Benchmark                     Mode  Cnt        Score        Error  Units
 * E0010Regx.greedyPattern      thrpt    9       52.255 ±      1.727  ops/s
 * E0010Regx.possessivePattern  thrpt    9       78.346 ±      2.718  ops/s
 * E0010Regx.reluctantPattern   thrpt    9  2101429.976 ± 485704.019  ops/s
 * *
 * 结论：1M左右的xml内容，性能会差万倍左右。
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 9:04 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0010Regx {
    private static final int SIZE = 100000;
    private static String msgContent = "<?xml version=\"UTF-8\"?> " +
            "<root xmlns=\"namespace_string\">." +
            RandomStringUtils.random(1500, true, true) +
            "</root>" +
            "{S:mXJFLDdfslsdfsdf." +
            System.currentTimeMillis() +
            ".sdfGQ==}";

    /**
     * 贪婪模式（Greedy）
     */
    private static Pattern greedyPattern = Pattern.compile("<\\?([\\s\\S]*)\\?>");

    /**
     * 懒惰模式（Reluctant），加"?"
     */
    private static Pattern reluctantPattern = Pattern.compile("<\\?([\\s\\S]*?)\\?>");

    /**
     * 独占模式（Possessive）,加"+"
     */
    private static Pattern possessivePattern = Pattern.compile("<\\?([\\s\\S]*+)\\?>");


    public static void main(String[] args) {

        Matcher matcher = possessivePattern.matcher(msgContent);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    /**
     * 贪婪模式（Greedy）
     * 正则表达式会匹配尽可能多的内容
     */
    @Benchmark
    public void greedyPattern() {
        matchCommon(greedyPattern);
    }


    /**
     * 懒惰模式（Reluctant）
     */
    @Benchmark
    public void reluctantPattern() {
        matchCommon(reluctantPattern);
    }

    /**
     * 独占模式（Possessive）：不能满足需求。
     */
    @Benchmark
    public void possessivePattern() {
        matchCommon(possessivePattern);
    }

    private void matchCommon(Pattern pattern) {
        Matcher matcher = pattern.matcher(msgContent);
        if (matcher.find()) {
            matcher.group();
        }
    }

}
