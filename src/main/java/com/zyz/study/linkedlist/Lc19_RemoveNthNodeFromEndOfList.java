package com.zyz.study.linkedlist;

import com.zyz.study.common.ListNode;

/**
 * 给定一个链表，删除链表的倒数第n个节点，并且返回链表的头结点。
 *
 * 示例：
 *
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 *
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 *
 * 给定的 n保证是有效的。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author 执零
 * @version 1.0
 * @date 2021/1/17 20:26
 */
public class Lc19_RemoveNthNodeFromEndOfList {
    /**
     * 思路：先用步长法，找到倒数第n个节点，步长=n，这样然后进行删除操作
     * 特殊情况分析：当n=1，步长=0，也能兼容这种情况
     * 当删除的刚好是正序的第一个节点，删除操作有所不同，所以head的指针要存下来用于判断
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {

        if (head == null) {
            return null;
        }

        // 一前一后两个指针，前指针先走n步
        ListNode behind = head;
        ListNode ahead = head;
        for (int i = 0; i < n; i++) {
            ahead = ahead.next;
        }

        if (ahead == null) {
            // 说明要删除的是头节点
            return head.next;
        }

        while (ahead.next != null) {
            ahead = ahead.next;
            behind = behind.next;
        }

        // 此时behind的next恰好是要删除的节点，这里的删除兼容尾部节点的情况
        behind.next = behind.next.next;
        return head;

    }

    /**
     * 同样是快慢指针，但是加一个哨兵节点以兼容删除头节点的情况
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd1(ListNode head, int n) {
        ListNode vHead = new ListNode(-1);
        vHead.next = head;
        ListNode ahead = head;
        ListNode behind = vHead;
        for (int i = 0; i < n; i++) {
            ahead = ahead.next;
        }

        while (ahead != null) {
            ahead = ahead.next;
            behind = behind.next;
        }

        behind.next = behind.next.next;
        return vHead.next;
    }
}
