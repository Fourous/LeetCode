package jianzhioffer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fourous
 * @date: 2020/3/25
 * @description: 复杂链表的复制，特殊就在链表有个指针执行任意节点或者null节点
 * 这题可以有个总结性思路，就是复杂链表的复制的常规解法
 * 1：迭代，就地复制Node，将节点复制并放在当前节点后面
 * 2：Hash法，Java里面直接用HashMap就可以
 * 3：DFS
 * 4：BFS
 */
public class oj_35 {
    /**
     * 采用Hash表方式，直接用Key Value值将Node对应起来，最简单直接的方式
     * 时间复杂度O(n)空间复杂度O(n)
     *
     * @param head
     * @return
     */
    public Node copyByHash(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node p = head;
        while (p != null) {
            map.put(p, new Node(p.val));
            p = p.next;
        }
        p = head;
        while (p != null) {
            /**
             * 这里很容易出现错误，将value的指向到Key中去了
             * key的指向是Key，value指向也是Value，这是不能交叉的
             */
            map.get(p).next = map.get(p.next);
            map.get(p).random = map.get(p.random);
            p = p.next;
        }
        return map.get(head);
    }

    /**
     * 通过优化的迭代来实现复制
     * 时间复杂度O(n)空间复杂度O(1)
     *
     * @param head
     * @return
     */
    public Node copyByIteration(Node head) {
        /**
         * 通过就地拷贝的方式来复制，首先复制节点并将这复制的节点放到当前节点后面，然后再将复制后的节点赋值random，最后再拆分
         *
         */
        if (head == null) {
            return null;
        }
        // 复制
        while (head != null) {
            Node cloneNode = new Node(head.val);
            Node tempNode = head.next;
            head.next = cloneNode;
            cloneNode.next = tempNode;
            head = cloneNode.next;
        }
        //指定随机指针random
        while (head != null) {
            Node cloneNode = head.next;
            if (head.random != null) {
                Node indexNode = head.random;
                cloneNode.random = indexNode.next;
            }
            head = cloneNode.next;
        }
        // 重新链接链表
        /**
         * FixMe 这里出现了指针问题
         */
        Node cloneNode = head.next;
        Node cloneHead = cloneNode;
        head.next = cloneNode.next;
        head = head.next;
        while (head != null) {
            cloneNode.next = head.next;
            head.next = head.next.next;
            head = head.next;
            cloneNode = head.next;
        }
        return cloneHead;
    }

    public static void main(String[] args) {

    }

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
