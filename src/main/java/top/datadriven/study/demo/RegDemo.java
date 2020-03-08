package top.datadriven.study.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/3/7 9:41 下午
 * @version: 1.0.0
 */
public class RegDemo {
    public static void main(String[] args) {
        String text = "abbcabbbc";
        String reg = "ab{1,3}?c";

        Pattern pattern = Pattern.compile(reg);


        Matcher m = pattern.matcher(text);
        if (m.find()) {
            System.out.println(m.group(0));
        }

    }
}
