package top.datadriven.study.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 案例。执行结果：
 * Benchmark                               Mode  Cnt        Score        Error  Units
 * E0008StringSplit2.indexOf              thrpt    9  4027842.129 ± 219511.055  ops/s
 * E0008StringSplit2.regex                thrpt    9   298722.108 ±  30021.782  ops/s
 * E0008StringSplit2.regexWithoutCompile  thrpt    9   274046.115 ±  24887.690  ops/s
 * *    结论：使用indexOf性能最高，高于正则方式20倍。  编译与否性能影响不大，主要是影响内存。
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/9 10:00 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0008StringSplitWithIndexOf2 {
    private static Pattern p1 = Pattern.compile("<\\?([\\s\\S]*)\\?>");
    private static Pattern p2 = Pattern.compile("<root([\\s\\S]*)</root>");
    private static Pattern p3 = Pattern.compile("\\{S:([\\s\\S]*)}");


    /**
     * 正则方式：使用已有实例
     */
    @Benchmark
    public void regex() {
       String text = "<?xml version=\"UTF-8\"?> " +
                "<root xmlns=\"namespace_string\">." +
                System.currentTimeMillis() +
                "</root>" +
                "{S:mXJFLDdfslsdfsdf." +
                System.currentTimeMillis() +
                ".sdfGQ==}";
        getString(text, p1, 0);
        getString(text, p2, 0);
        getString(text, p3, 1);
    }

    /**
     * 正则方式：每次创建实例。即不进行预编译
     */
    @Benchmark
    public void regexWithoutCompile() {
        String text = "<?xml version=\"UTF-8\"?> " +
                "<root xmlns=\"namespace_string\">." +
                System.currentTimeMillis() +
                "</root>" +
                "{S:mXJFLDdfslsdfsdf." +
                System.currentTimeMillis() +
                ".sdfGQ==}";
        Pattern p1 = Pattern.compile("<\\?([\\s\\S]*)\\?>");
        Pattern p2 = Pattern.compile("<root([\\s\\S]*)</root>");
        Pattern p3 = Pattern.compile("\\{S:([\\s\\S]*)}");
        getString(text, p1, 0);
        getString(text, p2, 0);
        getString(text, p3, 1);
    }

    /**
     * index方式
     */
    @Benchmark
    public void indexOf() {
        String text = "<?xml version=\"UTF-8\"?> " +
                "<root xmlns=\"namespace_string\">." +
                System.currentTimeMillis() +
                "</root>" +
                "{S:mXJFLDdfslsdfsdf." +
                System.currentTimeMillis() +
                ".sdfGQ==}";
        getBetweenString(text, "<?", "?>", true);
        getBetweenString(text, "<root", "</root>", true);
        getBetweenString(text, "{S:", "}", false);
    }

    private static String getBetweenString(String text,
                                           String firstStart,
                                           String firstEnd,
                                           Boolean includeStartEnd) {
        int start = text.indexOf(firstStart);
        int end = text.indexOf(firstEnd);
        if (includeStartEnd) {
            return text.substring(start, end + firstEnd.length());
        } else {
            return text.substring(start + firstStart.length(), end);
        }
    }

    static String getString(String text, Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            return matcher.group(group);
        }
        throw new RuntimeException();
    }
}
