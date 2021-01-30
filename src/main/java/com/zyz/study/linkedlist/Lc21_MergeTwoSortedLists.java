package com.zyz.study.linkedlist;

import com.zyz.study.common.ListNode;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * @author 执零
 * @version 1.0
 * @date 2021/1/17 13:23
 */
public class Lc21_MergeTwoSortedLists {
    // 思路：使用类似归并算法
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }



        /**
         * 4->6->7->10
         * ↑
         * 1->2->9->10->11
         * ↑
         *
         * 链表2遍历，直到找到刚好不大于4的节点
         * 1->2->9->10->11
         *    ↑
         *
         * 执行拼接（插入）
         * 6->7->10
         * ↑
         * 1->2->4->9->10->11
         *          ↑
         * 1进行遍历，直到刚好找到不大于9的节点
         * 4->6->7->10
         *       ↑
         * 插入
         * 10
         * ↑
         * 1->2->4->6->7->9->10->11
         *                    ↑
         * 执行插入
         * 1->2->4->6->7->9->10->10->11
         */

        // 先保证cur1的头节点是大于cur2的
        if (l1.val < l2.val) {
            ListNode tmp = l1;
            l1 = l2;
            l2 = tmp;
        }

        ListNode cur1 = l1;
        ListNode cur2 = l2;

        // 执行2的遍历，找到一个节点，它的next是null或者它的next比cur1还要大
        while(cur2.next != null && cur2.next.val <= cur1.val ) {
            cur2 = cur2.next;
        }
        // 找到了 执行拼接 将l1插入到cur2后面
        if (cur2.next == null) {
            cur2.next = l1;
        } else {
            // 1的下一个节点
            cur1 = l1.next;
            // l1插入,l1后继还需要计算，前驱可以确定是cur2
            l1.next = mergeTwoLists(cur1, cur2.next);
            cur2.next = l1;
        }
        return l2;

    }

    /**
     * 递归解法
     * 假设l1的头节点比l2的头节点小，则merge(l1,l2)等价于l1.next = merge(l1.next, l2);
     * 时间复杂度(m+n)
     * 空间复杂度(m+n)
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists1(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists1(l2.next, l1);
            return l2;
        }
    }

    /**
     * 迭代解法，先设定一个哨兵头节点，从两个头节点中挑选小的设为next
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode vHead = new ListNode(-1);
        ListNode prev = vHead;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        prev.next = l1 == null ? l2 : l1;
        return vHead.next;
    }
}
