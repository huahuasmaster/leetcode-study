package com.zyz.study.linkedlist.lc206;

import com.zyz.study.linkedlist.ListNode;

/**
 * 反转一个链表,
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 *
 * @author 执零
 * @version 1.0
 * @date 2021/1/16 16:46
 */
public class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode ahead;
        ListNode cur = head;// =1
        ListNode behind = null;
        while (cur != null) {// while cur is not a tail
            ahead = cur.next;// =2
            cur.next = behind;// 1->null

            // go a step
            behind = cur;
            cur = ahead;

        }
        return behind;

    }

    /**
     * 递归
     *
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 原链表的尾节点会一层一层返回，最终称为新的头节点
        ListNode prev = reverseList2(head.next);
        head.next.next = head;
        head.next = null;// 解决头节点的倒序
        return prev;

    }
}
