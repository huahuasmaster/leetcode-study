package com.zyz.study.linkedlist;

import com.zyz.study.common.ListNode;

import java.util.Currency;

/**
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author 执零
 * @version 1.0
 * @date 2021/1/17 21:00
 */
public class Lc2_AddTwoNumbers {
    /**
     * 思路1：模仿数学进位
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }


        ListNode vHead = new ListNode(-1);
        ListNode curNode = null;

        //上一次计算进位
        int pre = 0;
        while (l1 != null || l2 != null || pre > 0) {
            int n1,n2;
            n1 = l1 == null ? 0 : l1.val;
            n2 = l2 == null ? 0 : l2.val;

            int result = n1 + n2 + pre;
            pre = result / 10;
            int cur = result % 10;

            ListNode listNode = new ListNode(cur);

            if (curNode == null) {
                curNode = listNode;
                vHead.next = curNode;
            } else {
                curNode.next = listNode;
                curNode = listNode;
            }

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return vHead.next;

    }

    /**
     * 思路2：转链表为数字
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        return null;
    }
}
