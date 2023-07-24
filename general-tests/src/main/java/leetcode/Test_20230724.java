package leetcode;

import java.util.Stack;

public class Test_20230724 {

    public static void main(String[] args) {
//        System.out.println(decodeString("3[a2[c]]"));
        System.out.println(decodeString("2[abc]3[cd]ef"));
        System.out.println((int) '0');
        System.out.println((int) '9');
    }

    /**
     * 394. 字符串解码
     *
     * @param s
     * @return
     */
    public static String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        int start = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                stack.push(s.substring(start, i));
                start = i + 1;
                stack.push("]");
            } else if (s.charAt(i) == ']') {
                String subString = s.substring(start, i);
                // 往回弹出
                while (true) {
                    String matchStr = stack.pop();
                    if (matchStr.equals("]")) {
                        break;
                    } else {
                        subString = subString + matchStr;
                    }
                }
                int num = Integer.parseInt(stack.pop());
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < num; j++) {
                    sb.append(subString);
                }
                subString = sb.toString();
                if (!stack.isEmpty()) {
                    String subStr = stack.peek();
                    if (subStr != null && !"]".equals(subStr)) {
                        stack.pop();
                        subString = subStr + subString;
                    }
                }
                System.out.println(sb.toString());
                stack.push(subString);
                start = i + 1;
            } else {
                if (i == s.length() - 1) {
                    return stack.isEmpty() ? s.substring(start, i + 1) : stack.peek() +s.substring(start, i + 1);
                } else {
                    if (isNumberChar(s.charAt(start))) {
                        if (!isNumberChar(s.charAt(i))) {
                            stack.push(s.substring(start, i));
                            start = i;
                        }
                    } else {
                        if (isNumberChar(s.charAt(i))) {
                            stack.push(s.substring(start, i));
                            start = i;
                        }
                    }
                }
            }
        }
        return stack.peek();

    }

    private static boolean isNumberChar(char ch) {
        int val = (int) ch;
        return val >= 48 && val <= 57;
    }
}
