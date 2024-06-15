package util;

import javax.swing.*;

public class PopupUtils {
    public static void showInfoMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 显示警告提示框.
     * @param message 要显示的消息内容
     * @param title 对话框的标题
     */
    public static void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * 显示错误提示框.
     * @param message 要显示的消息内容
     * @param title 对话框的标题
     */
    public static void showErrorMesage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
//        showInfoMessage("操作成功！", "信息提示");
//        showWarningMessage("请小心操作！", "警告提示");
//        showErrorMesage("发生了一个错误！", "错误提示");
    }

}
