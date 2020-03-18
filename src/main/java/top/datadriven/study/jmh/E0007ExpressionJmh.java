package top.datadriven.study.jmh;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.mvel2.MVEL;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import top.datadriven.study.model.ExpressionParamModel;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * @description: 表达式引擎性能测试。执行结果：
 * Benchmark                            Mode  Cnt       Score       Error  Units
 * E0007ExpressionJmh.juel             thrpt   18   39709.214 ±  5731.886  ops/s
 * E0007ExpressionJmh.mvel             thrpt   18    2613.657 ±  1317.470  ops/s
 * E0007ExpressionJmh.mvelWithCompile  thrpt   18  102049.148 ± 33821.119  ops/s
 * E0007ExpressionJmh.qlExpress        thrpt   18   45495.842 ±  2773.283  ops/s
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/16 4:52 下午
 * @version: 1.0.0
 */
@State(Scope.Benchmark)
public class E0007ExpressionJmh {
    private static List<ExpressionParamModel> expressionParamModels = Lists.newArrayList();
    private Map<String, Serializable> mvelSerializable = Maps.newConcurrentMap();
    private static final Integer EXE_COUNT = 10;
    ExpressRunner runner = new ExpressRunner();
    ExpressionFactory factory = new ExpressionFactoryImpl();


    @Setup
    public void prepare() {
        for (int i = 0; i < EXE_COUNT; i++) {
            ExpressionParamModel expressionParamModel = new ExpressionParamModel();
            // 最常见的场景：多个条件进行and操作
            expressionParamModel.setExpression(" a<100 && b>=100 && c<=123");
            expressionParamModel.setParams(getInputParamMap());
            expressionParamModels.add(expressionParamModel);
        }
        for (int i = 0; i < EXE_COUNT; i++) {
            ExpressionParamModel expressionParamModel = new ExpressionParamModel();
            // 包含特殊的操作：contains
            expressionParamModel.setExpression(" a<100 && b>=100 && c<=123 && stringList.contains(str)");
            expressionParamModel.setParams(getInputParamMap());
            expressionParamModels.add(expressionParamModel);
        }
        for (int i = 0; i < EXE_COUNT; i++) {
            ExpressionParamModel expressionParamModel = new ExpressionParamModel();
            // 一个稍微复杂一点的语法树
            expressionParamModel.setExpression(" a>1 && ((b>1 || c<1) || (a>1 && b<1 && c>1))");
            expressionParamModel.setParams(getInputParamMap());
            expressionParamModels.add(expressionParamModel);
        }
    }

    private static Map<String, Object> getInputParamMap() {
        List<String> stringList = Lists.newArrayList("hello", "world");
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", RandomUtil.randomInt(30));
        paramMap.put("b", RandomUtil.randomInt(300));
        paramMap.put("c", RandomUtil.randomInt(100));
        paramMap.put("stringList", stringList);
        paramMap.put("str", "hello");
        return paramMap;
    }


    /**
     * ql表达式引擎
     */
    @Benchmark
    public void qlExpress() throws Exception {
        for (ExpressionParamModel expressionParamModel : expressionParamModels) {
            DefaultContext<String, Object> context = new DefaultContext<>();
            context.putAll(expressionParamModel.getParams());
            runner.execute(expressionParamModel.getExpression(), context, null, true, false);
        }
    }

    /**
     * mvel表达式引擎
     */
    @Benchmark
    public void mvel() {
        for (ExpressionParamModel expressionParamModel : expressionParamModels) {
            DefaultContext<String, Object> context = new DefaultContext<>();
            context.putAll(expressionParamModel.getParams());
            MVEL.eval(expressionParamModel.getExpression(), context);
        }
    }

    /**
     * mvel表达式引擎：预编译
     */
    @Benchmark
    public void mvelWithCompile() {
        for (ExpressionParamModel expressionParamModel : expressionParamModels) {
            String expression = expressionParamModel.getExpression();
            Serializable ser = mvelSerializable.get(expression);
            if (ser == null) {
                ser = MVEL.compileExpression(expression);
                mvelSerializable.put(expression, ser);
            }
            MVEL.executeExpression(ser, expressionParamModel.getParams());
        }

    }

    /**
     * juel表达式引擎
     */
    @Benchmark
    public void juel() {
        for (ExpressionParamModel expressionParamModel : expressionParamModels) {
            SimpleContext context = new SimpleContext();
            expressionParamModel.getParams().forEach((k, v) -> {
                context.setVariable(k, factory.createValueExpression(v, v.getClass()));
            });
            ValueExpression e = factory.createValueExpression(context, "${" + expressionParamModel.getExpression() + "}", Boolean.class);
            // 获取结果
            e.getValue(context);
        }
    }

}
