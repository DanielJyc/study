package top.datadriven.study.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @description:
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/18 8:36 上午
 * @version: 1.0.0
 */
@Getter
@Setter
public class ExpressionParamModel {
    /**
     * 表达式
     */
    private String expression;

    /**
     * 入参
     */
    private Map<String,Object> params;


}
