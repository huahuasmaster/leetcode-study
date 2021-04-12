package com.zyz.study.array;

import java.util.Arrays;

/**
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 如果数组中不存在目标值 target，返回[-1, -1]。
 *
 * 进阶：
 *
 * 你可以设计并实现时间复杂度为O(log n)的算法解决此问题吗？
 *
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/3 21:30
 */
public class Lc34_FindFirstAndLastPositionOfSortedArray {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length <= 0) {
            return new int[] {-1, -1};
        }
        int left = findLeft(nums, target);
        if (left == -1) {
            return new int[] {-1, -1};
        }

        return new int[] {left, findRight(nums, left, target)};


    }

    private int findLeft(int[] nums, int target) {
        int min = 0;
        int max = nums.length - 1;
        while (min < max) {
            int mid = min + ((max - min) >> 1);
            if (nums[mid] == target) {
                // 搜索区间为[min, mid]
                max = mid;
            } else if (nums[mid] < target) {
                // 搜索区间为[mid+1, right]
                min = mid + 1;
            } else {
                // 搜索区间为[min, mid-1]
                max = mid - 1;
            }
        }
        return nums[min] == target ? min : -1;
    }

    private int findRight(int[] nums, int min, int target) {
        int max = nums.length - 1;
        while (min < max) {
            int mid = min + ((max - min + 1) >> 1);
            if (nums[mid] == target) {
                // 搜索区间为[mid, right]
                min = mid;
            } else if (nums[mid] < target) {
                // 搜索区间为[mid+1, right]
                min = mid + 1;
            } else {
                // 搜索区间为[min, mid-1]
                max = mid - 1;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Lc34_FindFirstAndLastPositionOfSortedArray instance = new Lc34_FindFirstAndLastPositionOfSortedArray();
        System.out.println(Arrays.toString(instance.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
    }
}
