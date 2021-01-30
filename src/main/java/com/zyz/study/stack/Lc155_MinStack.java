package com.zyz.study.stack;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/20 23:47
 */
public class Lc155_MinStack {

    Deque<Integer> stack = new LinkedList<>();
    Deque<Integer> minStack = new LinkedList<>();


    public Lc155_MinStack() {
        minStack.push(Integer.MAX_VALUE);
    }

    public void push(int x) {
        stack.push(x);
        minStack.push(Math.min(x, minStack.peek()));
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }



//    int[] stack = new int[1000];
//    int count = 0;
//
//    private class Node {
//        int val;
//        Node minNext;
//
//        public Node(int val) {
//            this.val = val;
//        }
//    }
//    Node tail;
//    Node head;
//
//
//
//
//    public Lc155_MinStack() {
//        head = new Node(Integer.MIN_VALUE);
//        tail = new Node(Integer.MAX_VALUE);
//        head.minNext = tail;
//    }
//
//    public void push(int x) {
//        stack[count++] = x;
//
//        Node p = head;
//        while(p.minNext != null) {
//            if (p.val <= x && x <= p.minNext.val) {
//                // 执行插入
//                Node xNode = new Node(x);
//                xNode.minNext = p.minNext;
//                p.minNext = xNode;
//                return;
//            }
//            p = p.minNext;
//        }
//    }
//
//    public void pop() {
//
//        int pop = stack[--count];
//
//        Node p = head;
//        while(p.minNext != null) {
//            if (p.minNext.val == pop) {
//                // 执行删除
//                p.minNext = p.minNext.minNext;
//                return;
//            }
//        }
//    }
//
//    public int top() {
//        int p = count - 1;
//        return stack[p];
//    }
//
//    public int getMin() {
//        return head.minNext.val;
//    }

}
