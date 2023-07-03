package leetcode;

/**
 * @author dewu.de
 * @date 2023-07-03 7:12 下午
 */
public class TreeNode_Init {

    // [120,70,140,50,100,130,160,20,55,75,110,119,135,150,200]
    public static TreeNode init() {
        TreeNode node_120 = new TreeNode(120);
        TreeNode node_70 = new TreeNode(70);
        TreeNode node_140 = new TreeNode(140);
        TreeNode node_50 = new TreeNode(50);
        TreeNode node_100 = new TreeNode(100);
        TreeNode node_130 = new TreeNode(130);
        TreeNode node_160 = new TreeNode(160);
        TreeNode node_20 = new TreeNode(20);
        TreeNode node_55 = new TreeNode(55);
        TreeNode node_75 = new TreeNode(75);
        TreeNode node_110 = new TreeNode(110);
        TreeNode node_119 = new TreeNode(119);
        TreeNode node_135 = new TreeNode(135);
        TreeNode node_150 = new TreeNode(150);
        TreeNode node_200 = new TreeNode(200);

        node_120.left = node_70;
        node_120.right = node_140;
        node_70.left = node_50;
        node_70.right = node_100;
        node_140.left = node_130;
        node_140.right = node_160;
        node_50.left = node_20;
        node_50.right = node_55;
        node_100.left = node_75;
        node_100.right = node_110;
        node_130.left = node_119;
        node_130.right = node_135;
        node_160.left = node_150;
        node_160.right = node_200;
        return node_120;
    }
}
