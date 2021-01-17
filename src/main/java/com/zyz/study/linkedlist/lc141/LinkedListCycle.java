package com.zyz.study.linkedlist.lc141;

import com.sun.xml.internal.ws.runtime.config.TubelineFeatureReader;
import com.zyz.study.linkedlist.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个链表，判断链表中是否有环。
 *
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 *
 * 如果链表中存在环，则返回 true 。 否则，返回 false 。
 *
 *
 * 进阶：
 *
 * 你能用 O(1)（即，常量）内存解决此问题吗
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author 执零
 * @version 1.0
 * @date 2021/1/17 11:08
 */
public class LinkedListCycle {
    // 使用一个set存储所有出现过的node，时间复杂度O(nlogn), 空间复杂度o(n)
    public boolean hasCycle(ListNode head) {
        // null or only one node
        if (head == null || head.next == null) {
            return false;
        }

        // two node cycle
        if (head.next.next == head) {
            return true;
        }

        Set<ListNode> set = new HashSet<>();

        while (head != null) {
            // 发生了重复
            if (!set.add(head)) {
                return true;
            }
            head = head.next;
        }

        return false;

    }

    // 一快一慢两个指针，如果两个指针相遇，则说明有环。时间复杂度O(n)，空间复杂度O(1)
    public boolean hasCycle1(ListNode head) {
        // null or only one node
        if (head == null || head.next == null) {
            return false;
        }

        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != slow) {
            if (fast == null || fast.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
        }

        return true;
    }
}
