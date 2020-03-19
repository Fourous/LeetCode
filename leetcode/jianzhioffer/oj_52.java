package jianzhioffer;

/**
 * @author fourous
 * @date: 2020/3/19
 * @description: 两个链表的公共节点
 * 这题解法很巧妙，对于这种两个长度有差值求共同点的问题，其实都可以用这个方式解决
 */
public class oj_52 {
    public class ListNode {
        int var;
        ListNode next;

        ListNode(int x) {
            var = x;
            next = null;
        }
    }

    /**
     * 首先AB链表的长度不一样，出发位置也不一样，但是要求求共同点
     * 首先A走完链表以后，会直接走到B的头节点，同样B走完链表后，会直接走到A头节点
     * 最终会在交接处相遇，如果没有交接处，最后肯定都为null
     * 这种复杂度O(m+n)
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headB == null || headA == null) {
            return null;
        }
        ListNode node1 = headA, node2 = headB;
        while (node1 != node2) {
            node1 = node1 == null ? headB : node1.next;
            node2 = node2 == null ? headA : node2.next;
        }
        return node1;
    }

}
