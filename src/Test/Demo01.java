package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Demo01 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 创建并设置窗口
            JFrame frame = new JFrame("JToggleButton 示例");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);
            frame.setLayout(new FlowLayout()); // 使用FlowLayout布局管理器

            // 创建JToggleButton
            JToggleButton toggleButton = new JToggleButton("关");

            // 添加按钮的监听器
            toggleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 根据按钮的当前状态切换文本并打印状态
                    if (toggleButton.isSelected()) {
                        toggleButton.setText("开");
                        System.out.println("按钮已打开");
                    } else {
                        toggleButton.setText("关");
                        System.out.println("按钮已关闭");
                    }
                }
            });

            // 将按钮添加到窗口
            frame.add(toggleButton);

            // 显示窗口
            frame.setVisible(true);
        });
    }
}