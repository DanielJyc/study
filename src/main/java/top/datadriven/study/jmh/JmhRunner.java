package top.datadriven.study.jmh;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @description: 执行器
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/8 12:00 下午
 * @version: 1.0.0
 */
public class JmhRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                //JMH 会找寻标注了@Benchmark类型的方法，可能会跑一些你所不需要的测试，这样就需要通过include和exclude两个方法来完成包含以及排除的语义。
                .include("E0009String")
                //预热做2次
                .warmupIterations(2)
                //正式计量测试做10次，而每次都是先执行完预热再执行正式计量，内容都是调用标注了@Benchmark的代码
                .measurementIterations(3)
                //做3轮测试，因为一次测试无法有效的代表结果，所以通过3轮测试较为全面的测试，而每一轮都是先预热，再正式计量。
                .forks(3)
                .build();

        new Runner(opt).run();
    }

}
