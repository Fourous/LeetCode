package jianzhioffer;

import java.util.Stack;

/**
 * @author fourous
 * @date: 2020/3/18
 * @description: 我们知道Int还是Double都是有上限的，假设两个数字很大，基本存储不了，但是又要相加输出，都将其归纳为大数解法
 * 简单来说，数字都用字符串来存储，相加我们通过程序来控制进位，如果遇到大数相见，也是一样的，通过程序来控制退位
 * 实现方式很多种，一般都是从最低位加到最高位，有用字符串翻转来实现，我们这里用栈来实现
 */
public class BigNumber {
    public static Stack<Integer> stringToStack(String str) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char charStr = str.charAt(i);
            // 这里做个过滤，防止出现非数字情况，导致程序错误
            if (charStr >= '0' && charStr <= '9') {
                stack.push((Integer.valueOf(String.valueOf(charStr))));
            } else {
                continue;
            }
        }
        return stack;
    }

    public static String addNumber(String a, String b) {
        Stack<Integer> stackA = stringToStack(a);
        Stack<Integer> stackB = stringToStack(b);
        Stack<Integer> stack = new Stack<>();
        int tempNum;
        boolean carry = false;
        while (!stackA.isEmpty() && !stackB.isEmpty()) {
            tempNum = stackA.pop() + stackB.pop();
            if (carry) {
                tempNum++;
                carry = false;
            }
            if (tempNum >= 10) {
                tempNum -= 10;
                carry = true;
            }
            stack.push(tempNum);
        }
        // 接下来处理不为空的栈
        Stack<Integer> stackTmp = !stackA.isEmpty() ? stackA : stackB;
        while (!stackTmp.isEmpty()) {
            if (carry) {
                int end = stackTmp.pop();
                end++;
                if (end >= 10) {
                    end -= 10;
                    stack.push(end);
                    carry = true;
                } else {
                    stack.push(end);
                    carry = false;
                }
            } else {
                stack.push(stackTmp.pop());
            }
        }
        if (carry) {
            stack.push(1);
        }
        String result = new String();
        while (!stack.isEmpty()) {
            result = result.concat(stack.pop().toString());
        }
        return result;
    }

    public static void main(String[] args) {
        String a = "1823908978";
        String b = "98890900";
        System.out.println(addNumber(a, b));
        long x = 1823908978;
        long y = 98890900;
        System.out.println(x + y);
    }
}
