package com.demo.base;

/**
 * @author dewu.de
 * @date 2022-06-21 7:14 下午
 */
public class SwitchTest {

    /**
     * switch case
     * case子句要以break结尾，否则匹配到case后会继续遍历执行后面的case子句
     *
     * @param args
     */
    public static void main(String[] args) {
        int s = 3;
        switch (s) {
            case 1: {
                System.out.println(1);
//                break;
            }
            case 3: {
                System.out.println(3);
//                break;
            }
            case 2: {
                System.out.println(2);
//                break;
            }

            case 4: {
                System.out.println(4);
//                break;
            }
            case 5: {
                System.out.println(5);
                break;
            }
            default:
                System.out.println("default");
                break;
        }

    }

}
