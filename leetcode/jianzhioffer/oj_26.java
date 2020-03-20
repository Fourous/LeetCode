package jianzhioffer;

/**
 * @author fourous
 * @date: 2020/3/20
 * @description: 树的子结构
 * 输入树A，B，检测A是否包含B的结构
 */
public class oj_26 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 首先判断AB是否为Null，然后分为三种情况，这三种情况都可以判断是否为子树
     * 一种是以根节点开始的子树，一种是以右节点开始的子树，一种是以左节点开始的子树
     *
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        return A == null || B == null ? false : recv(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    /**
     * 从给定节点出发，判断左右子树是否相同
     *
     * @param A
     * @param B
     * @return
     */
    public boolean recv(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null || A.val != B.val) {
            return false;
        }
        return recv(A.left, B.left) && recv(A.right, B.right);
    }

}
