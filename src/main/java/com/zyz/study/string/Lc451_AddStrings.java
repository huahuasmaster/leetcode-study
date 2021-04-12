package com.zyz.study.string;

/**
 * 给定两个字符串形式的非负整数num1 和num2，计算它们的和。
 * <p>
 * num1 和num2的长度都小于 5100
 * num1 和num2 都只包含数字0-9
 * num1 和num2 都不包含任何前导零
 * 你不能使用任何內建 BigInteger 库，也不能直接将输入的字符串转换为整数形式
 *
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/3 14:00
 */
public class Lc451_AddStrings {
    /**
     * 使用进位计算
     *
     * @return
     */
    public String solution(String a, String b) {
        if (isEmpty(a) && isEmpty(b)) {
            return "0";
        }
        if (isEmpty(a)) {
            return b;
        }
        if (isEmpty(b)) {
            return a;
        }

        if (a.length() > b.length()) {
            // 调换位置，方便后续处理
            String tmp = b;
            b = a;
            a = tmp;
        }

        // 此时a的长度已经小于等于b
        // 截取高位无需相加的部分
        int diffLen = b.length() - a.length();
        boolean flag = false;
        char[] result = b.toCharArray();
        // 以更长的b为基准
        for (int i = b.length() - 1; i >= 0; i--) {
            int add = (flag ? 1 : 0) + getNum(b.charAt(i));
            if (i < diffLen) {
                // 只关心b的头部
                if (!flag) {
                    // 无进位直接退出
                    break;
                }
            } else {
                add += getNum(a.charAt(i - diffLen));
            }
            flag = add >= 10;
            result[i] = (char) (48 + add % 10);
        }
        String resultStr = new String(result);

        if (flag) {
            resultStr = "1" + resultStr;
        }
        return resultStr;

    }

    private boolean isEmpty(String s) {
        return s == null || s.length() <= 0;
    }

    private int getNum(char c) {
        return (int) c - (int) '0';
    }

    public static void main(String[] args) {
        Lc451_AddStrings lc451_addStrings = new Lc451_AddStrings();
        System.out.println(lc451_addStrings.solution("67842789473294", "73812478932017489032174863214976312947389104"));
    }
}
