package leetcode;

import java.util.Stack;

public class Test_20230724 {

    public static void main(String[] args) {
        System.out.println(decodeString("3[a2[c]dd]"));
        System.out.println(decodeString("2[abc]3[cd]ef"));
        System.out.println((int) '0');
        System.out.println((int) '9');
        System.out.println('8');
    }

    /**
     * 394. 字符串解码
     * <p>
     * 3[10a2[c]ef]
     *
     * @param s
     * @return
     */
    public static String decodeString(String s) {
        Stack<StrNode> stack = new Stack<>();
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '[') {
                // 遇到[, start -> i-1 这部分可能有 string + num 的组合
                if (i > 0 && i > start) {
                    if (isNumberChar(s.charAt(i - 1))) {
                        int numIndex = i - 1;
                        while (true) {
                            if (numIndex == 0 || !isNumberChar(s.charAt(numIndex - 1))) {
                                break;
                            }
                        }
                        if (numIndex > start) {
                            stack.push(new StrNode(2, s.substring(start, numIndex), 0));
                        }
                        stack.push(new StrNode(1, null, Integer.parseInt(s.substring(numIndex, i))));
                    } else {
                        StrNode node = new StrNode(2, s.substring(start, i), 0);
                        stack.push(node);
                    }
                }
                // 存储一个 [ -> ]
                StrNode node = new StrNode(0, null, 0);
                stack.push(node);
                start = i + 1;
            } else if (ch == ']') {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("格式错误: " + i + "位置上的]前面啥也没有");
                }

                String subString = "";
                if (i > start) {
                    subString = s.substring(start, i);
                }

                StrNode node = stack.peek();
                if (node.type == 2) {
                    subString = node.value + subString;
                    stack.pop();
                    // 下一个应该是[
                    node = stack.peek();
                }

                if (node.type != 0) {
                    throw new IllegalArgumentException("[]不匹配");
                }
                stack.pop();

                // 再往前是数字
                if (stack.isEmpty() || stack.peek().type != 1) {
                    throw new IllegalArgumentException("[前面没数字!");
                }
                StrNode numNode = stack.pop();
                String repeatStr = "";
                for (int j = 0; j < numNode.num; j++) {
                    repeatStr = repeatStr + subString;
                }
                if (!stack.isEmpty() && stack.peek().type == 2) {
                    repeatStr = stack.pop().value + repeatStr;
                }
                stack.push(new StrNode(2, repeatStr, 0));
                start = i + 1;
            } else {
                if (i == s.length() - 1) {
                    String subString = s.substring(start);
                    if (!stack.isEmpty()) {
                        subString = stack.pop().value + subString;
                    }
                    return subString;
                }
            }
        }
        return stack.pop().value;

    }

    private static boolean isNumberChar(char ch) {
        int val = ch;
        return val >= 48 && val <= 57;
    }

    static class StrNode {

        private int type;
        private String value;
        private int num;

        public StrNode(int type, String value, int num) {
            this.type = type;
            this.value = value;
            this.num = num;
        }
    }
}
