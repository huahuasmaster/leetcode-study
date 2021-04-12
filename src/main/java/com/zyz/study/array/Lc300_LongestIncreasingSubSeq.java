package com.zyz.study.array;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 *
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 *
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/5 11:55
 */
public class Lc300_LongestIncreasingSubSeq {
    /**
     * 暴力列出所有序列，算出最长长度，复杂度O(n^2)
     * @return
     */
    public int solve1(int[] nums) {
        return 0;
    }

    /**
     * 使用动态规划，定义dp[i]为以nums[i]为结尾的各种子序列(nums[i]也在子序列之内)中的最大长度，
     * 在0<=j<i的范围中，如果某个数nums[j]小于nums[i]，那么nums[i]就可以接上"以nums[j]为结尾的各种子序列的最大长度"，进行nums[j]+1
     * 由于j可能有多个，那么nums[j]+1也会有多个取值，nums[i]只需要从这些值中取最大值即可
     * 所以dp[i] = max((for (0<=j<i && nums[j]<nums[i]) dp[j]) + 1)
     * dp[i]初始值为1，因为最不济也可以把num[i]这单个数字作为一个序列
     * @param nums
     * @return
     */
    public int solve2(int[] nums) {
        // 可以有两种写法，递归或者非递归，本质都是按照i从小到大的顺序，算出dp[i]
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }

        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i;j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
        }

        int ans = dp[0];
        for (int i = 0; i < nums.length;i++) {
            ans = Math.max(dp[i], ans);
        }

        return ans;

    }

}
