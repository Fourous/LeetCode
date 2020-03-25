package jianzhioffer;

/**
 * @author fourous
 * @date: 2020/3/25
 * @description: 二叉搜索树转双向链表，并使尾节点和头节点相连
 * 很明显，二叉搜索树如果采用中序遍历，得到的数组是有序的
 * 前序：根，左，右
 * 中序：左，根，右
 * 后序：左，右，根
 * 最简单的写法其实就是递归实现，可能有些地方需要非递归要求，都是用栈实现的
 */
public class oj_36 {
    Node head = null, pre = null, tail = null;

    /**
     * 连接头尾节点
     *
     * @param root
     * @return
     */
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return root;
        }
        inOrder(root);
        // 链接头尾节点
        head.left = tail;
        tail.right = head;
        return head;
    }

    /**
     * 中序遍历
     * 这个算法一定要记熟，对于搜索树的出现大概率会出现遍历的题变形
     *
     * @param root
     */
    public void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        if (pre == null) {
            head = root;
        } else {
            pre.right = root;
        }
        root.left = pre;
        pre = root;
        tail = root;
        inOrder(root.right);
        return;
    }

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int _val, Node _left, Node _right) {
            this.val = _val;
            this.left = _left;
            this.right = _right;
        }

        public Node(int _val) {
            this.val = _val;
        }

        public Node() {
        }
    }
}
