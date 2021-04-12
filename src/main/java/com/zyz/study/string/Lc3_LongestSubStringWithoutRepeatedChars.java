package com.zyz.study.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/3 19:41
 */
public class Lc3_LongestSubStringWithoutRepeatedChars {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() <= 0) {
            return 0;
        }

        // 使用前后指针实现滑动窗口，时间复杂度O(n)
        // 存储当前子串和每个字符的下标
        Map<Character,Integer> window = new HashMap<>();
        int fastIndex = 0;
        int slowIndex = 0;
        int max = 0;
        while (fastIndex < s.length()) {
            char cur = s.charAt(fastIndex);
            if (window.containsKey(cur)) {
                // 出现了重复字符慢指针需要移动到不重复的位置
                slowIndex = Math.max(slowIndex, window.get(cur) + 1);
            }
            // 更新最大长度
            max = Math.max(max, fastIndex - slowIndex + 1);
            window.put(cur, fastIndex);

            fastIndex++;
        }
        return max;
    }
}
