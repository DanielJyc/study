package top.datadriven.performance.tmp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 单词统计
 * 给定一个字符串，计算字符串中每个单词出现的数量，并排序输出。
 * a. 单词一个大写字母开始，接着跟随0个或任意个小写字母；
 * b. 如果单词数量大于 1，单词后会跟着数字表示单词的数量。如果数量等于 1 则不会跟数字。例如，Hello2World 和 是合法，但 Hello1World2 这个表达是不合法的；
 * c. Hello2表示HelloHello；
 * d. (Hello2World2)3 可以等于Hello2World2Hello2World2Hello2World2；
 * 输出格式为：第一个（按字典序）单词，跟着它的数量（如果单词数量为1，则不输出），然后是第二个单词的名字（按字典序），跟着它的数量（如果单词数量为1，则不输出），以此类推。
 * 示例1：
 * 输入：字符串 = "World3Hello"
 * 输出: "HelloWorld3"
 * 解释: 单词数量是 {'Hello': 1, 'World': 3}。
 * 示例 2:
 * 输入: 字符串 = "Welcome4(ToAlibaba(To3)2)2"
 * 输出: "Alibaba2To14Welcome4"
 * 解释: 单词数量是 {'Alibaba': 2,  'To': 14', Welcome': 4,}。
 * 注意:
 * 字符串的长度在[1, 100000]之间。
 * 字符串只包含字母、数字和圆括号，并且题目中给定的都是合法的字符串。。
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/7/2 7:59 下午
 * @version: 1.0.0
 */
public class WordCount {

    public static void main(String[] args) {
        String str = "World3Hello";

        System.out.println(getWords(str));
    }

    private static String getWords(String str) {
        //边界判断
        if (str == null || "".equals(str)) {
            return "";
        }

        Map<String, Integer> wordCount = new HashMap<>();
        char[] arr = str.toCharArray();

        int startStr = 0;
        for (int i = 0; i < arr.length; i++) {
            //TODO 1.需要考虑连续多个数字的场景：保存数字开始和结束标识，并动态更新
            //TODO 2.需要考虑加括号的场景：使用栈。从第一个字符开始处理。如果是数字，则连续出栈，直到栈空或遇到左括号。
            // 出栈完毕以后，组成的字串，乘以数字。继续迭代下一个字符。
            // 如果是右括号，则从栈中找左括号，并把左括号出栈。右括号和左括号之间的内容要暂存。
            //数字
            if (Character.isDigit(arr[i])) {
                wordCount.put(String.valueOf(Arrays.copyOfRange(arr, startStr, i)),
                        Integer.parseInt(String.valueOf(arr[i])));
                startStr = i;
            }

            //最后一个字符，且非数字
            if (i == arr.length - 1 && !Character.isDigit(arr[i])) {
                wordCount.put(String.valueOf(Arrays.copyOfRange(arr, startStr + 1, i + 1)), 1);
            }
        }

        // 排序map
        StringBuilder result = sortAndGetResult(wordCount);

        //返回结果
        return String.valueOf(result);
    }

    private static StringBuilder sortAndGetResult(Map<String, Integer> wordCount) {
        HashMap<String, Integer> finalOut = new LinkedHashMap<>();
        wordCount.entrySet()
                .stream()
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
                .collect(Collectors.toList())
                .forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : finalOut.entrySet()) {
            result.append(entry.getKey());
            if (entry.getValue() != 1) {
                result.append(entry.getValue());
            }
        }
        return result;
    }


}
