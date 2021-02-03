package com.zyz.study.stack;

import sun.font.GlyphLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author 执零
 * @version 1.0
 * @date 2021/1/19 23:27
 */
public class Lc20_ValidParentheses {
    /**
     * 使用栈来实现
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s == null || "".equals(s)) {
            return true;
        }

        if (s.length() % 2 == 1) {
            return false;
        }

        char[] stack = new char[s.length()];

        int count = -1;

        for (char c : s.toCharArray()) {
            if (isRight(c)) {
                if (count == -1 || !match(stack[count--], c)) {
                    return false;
                }
            } else {
                stack[++count] = c;
            }

        }
        return count == -1;

    }

    public boolean isRight(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    public boolean match(char left, char right) {
        return (left == '(' && right == ')')
                || (left == '[' && right == ']')
                || (left == '{' && right == '}');
    }

    public static void main(String[] args) {
        Lc20_ValidParentheses validParentheses  = new Lc20_ValidParentheses();
        validParentheses.isValid("{[]}");
    }
}
