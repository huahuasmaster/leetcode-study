package com.zyz.study.linkedlist;

import com.zyz.study.common.ListNode;

/**
 * 编写一个程序，找到两个单链表相交的起始节点。
 * @author <a href="mailto:yunzhuang.zyz@alibaba.com">朱云壮</a>
 * @version 1.0
 * @date 2021/3/5 16:27
 */
public class Lc160_IntersectionOfTwoLists {
    /**
     * 思路：如果链表相交，则A=a+c B=b+c，拼接在一起之后变成a+c+b+c;pa和pb必定在c相遇；
     * 如果不相交，则一个为a+c 一个为b+d，相遇点就是null
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersection(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pa = headA;
        ListNode pb = headB;
        while (pa != pb) {
            pa = pa == null ? headB : pa.next;
            pb = pb == null ? headA : pb.next;
        }
        return pa;
    }
}
