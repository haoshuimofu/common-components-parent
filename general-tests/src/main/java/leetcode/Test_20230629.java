package leetcode;

import java.util.Stack;

/**
 * @author dewu.de
 * @date 2023-06-29 10:06 上午
 */
public class Test_20230629 {

    public static void main(String[] args) {
        String[] strs = new String[]{"4", "13", "5", "/", "+"};
        System.out.println(evalRPN(strs));

    }

    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (token.length() == 1) {
                if ("+".equals(token)) {
                    stack.push(stack.pop() + stack.pop());
                } else if ("-".equals(token)) {
                    int n1 = stack.pop();
                    int n2 = stack.pop();
                    stack.push(n2 - n1);
                } else if ("*".equals(token)) {
                    stack.push(stack.pop() * stack.pop());
                } else if ("/".equals(token)) {
                    int n1 = stack.pop();
                    int n2 = stack.pop();
                    stack.push(n2 / n1);
                } else {
                    stack.push(Integer.valueOf(token));
                }
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.pop();
    }

}
