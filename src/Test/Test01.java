package Test;

import javax.swing.*;
import java.awt.*;

public class Test01 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SpringLayout Example");
            frame.setSize(1100, 660);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 使用SpringLayout
            SpringLayout layout = new SpringLayout();
            JPanel contentPane = new JPanel(layout);
            frame.setContentPane(contentPane);

            // 创建顶部容器
            JPanel topPanel = new JPanel();
            topPanel.setBackground(Color.BLUE);
            //topPanel.setPreferredSize(new Dimension(0, 100)); // 高度为100像素
            contentPane.add(topPanel);

            // 设置顶部容器的约束
            layout.putConstraint(SpringLayout.WEST, topPanel, 0, SpringLayout.WEST, contentPane);
            layout.putConstraint(SpringLayout.EAST, topPanel, 0, SpringLayout.EAST, contentPane);
            layout.putConstraint(SpringLayout.NORTH, topPanel, 0, SpringLayout.NORTH, contentPane);
            layout.putConstraint(SpringLayout.SOUTH, topPanel, -500, SpringLayout.SOUTH, contentPane);

            // 创建左侧下方容器
            JPanel leftBottomPanel = new JPanel();
            leftBottomPanel.setBackground(Color.GREEN);
            //leftBottomPanel.setPreferredSize(new Dimension(200, 100)); // 宽度为200像素
            contentPane.add(leftBottomPanel);

            // 设置左侧下方容器的约束
            layout.putConstraint(SpringLayout.WEST, leftBottomPanel, 0, SpringLayout.WEST, contentPane);
            layout.putConstraint(SpringLayout.NORTH, leftBottomPanel, 0, SpringLayout.SOUTH, topPanel); // 在顶部容器下方
            layout.putConstraint(SpringLayout.SOUTH, leftBottomPanel, 0, SpringLayout.SOUTH, contentPane);
            layout.putConstraint(SpringLayout.EAST, leftBottomPanel, -800, SpringLayout.EAST, contentPane); // 让右侧有足够的空间留给第三个容器


            // 创建右侧下方容器（占据剩余空间）
            JPanel rightBottomPanel = new JPanel();
            rightBottomPanel.setBackground(Color.RED);
            contentPane.add(rightBottomPanel);

            // 设置右侧下方容器的约束
            layout.putConstraint(SpringLayout.WEST, rightBottomPanel, 0, SpringLayout.EAST, leftBottomPanel); // 紧挨着左侧容器
            layout.putConstraint(SpringLayout.NORTH, rightBottomPanel, 0, SpringLayout.SOUTH, topPanel); // 与左侧容器底部对齐
            layout.putConstraint(SpringLayout.EAST, rightBottomPanel, 0, SpringLayout.EAST, contentPane);
            layout.putConstraint(SpringLayout.SOUTH, rightBottomPanel, 0, SpringLayout.SOUTH, contentPane); // 底部对齐容器底部

            // 显示窗口
            frame.setVisible(true);
        });
    }
}