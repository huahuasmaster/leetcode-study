package com.zyz.study.stack;

import java.util.Stack;

/**
 * 专门使用一个存储最小值的栈
 * @author 执零
 * @version 1.0
 * @date 2021/1/20 23:47
 */
public class Lc155_MinStack {

    Stack<Integer> stack = new Stack<>();
    Stack<Integer> min = new Stack<>();


    /**
     * 插入数据
     * @param x
     */
    public void push(int x) {
        stack.push(x);
        if (min.isEmpty() || min.peek() >= x) {
            min.push(x);
        }
    }

    /**
     * 弹出一个数据
     */
    public void pop() {
        if (stack.peek().equals(min.peek())) {
            min.pop();
        }
        stack.pop();
    }

    public int top() {
        return 0;
    }

    public int getMin() {
        return min.peek();
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
