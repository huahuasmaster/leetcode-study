package com.zyz.study.linkedlist;

import com.zyz.study.common.ListNode;


/**
 * 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
 *
 * 如果有两个中间结点，则返回第二个中间结点。
 * @author 执零
 * @version 1.0
 * @date 2021/1/17 20:52
 */
public class Lc876_MiddleOfList {
    /**
     * 思路：快慢指针法，快的一次性走两步，慢的一次性走一步。当快指针为null或者快指针的next为null时，slow就是中间节点
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;

    }
}
