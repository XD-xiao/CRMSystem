package MyJFrame;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import util.InputValidator;
import util.PopupUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginJFrame extends JFrame {
    UserDao userDao = new UserDaoImpl();

    public static User currentUser;         // 当前登录用户

    private final String customerimgurl = "Img/login_customer.jpg";
    private final String adminimgurl = "Img/login_admin.jpg";
    private final String employeeimgurl = "Img/login_employee.jpg";
    SpringLayout layout = new SpringLayout();
    JPanel panel = new JPanel(layout);

    JLabel accountLabel = new JLabel("账号:");
    JTextField accountField = new JTextField(18);
    JLabel passwordLabel = new JLabel("密码:");
    JPasswordField passwordField = new JPasswordField(18);
    JLabel roleLabel = new JLabel("角色:");
    JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"客户","员工","管理员"});
    JButton loginButton = new JButton("登录");
    JButton registerButton = new JButton("注册");
    Image imageIcon = new ImageIcon(customerimgurl).getImage() ;
    JLabel imageLabel = new JLabel(new ImageIcon(imageIcon));

    public LoginJFrame() {
        setTitle("CRMSystem-Login");
        setSize(500, 350);
        //固定窗口大小
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置背景颜色
        panel.setBackground(new Color(0xF0F8FF));

        this.setLayout(layout);
        this.setContentPane(panel);

        setComponentListener();     // 设置组件监听器

        panel.add(imageLabel);
        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(loginButton);
        panel.add(registerButton);

        setComponentLocation();     //设置组件位置



        this.setVisible(true);

    }
    //设置组件事件
    public void setComponentListener() {

        // 登录按钮
        loginButton.addActionListener(e -> {
            String username = accountField.getText();
            String password = new String(passwordField.getPassword());
            int role = roleComboBox.getSelectedIndex() + 1;

            currentUser = userDao.getUserByNamePassword(username, password,role);

            if (!InputValidator.isValidUsername(username) || !InputValidator.isValidPassword(password)) {
                PopupUtils.showInfoMessage("请输入合法的用户名或密码格式", "消息提示");
            }
            else if(userDao.getUserByName(username) == null){
                PopupUtils.showInfoMessage("用户不存在", "消息提示");
            }
            else if( currentUser == null){
                PopupUtils.showInfoMessage("用户名、密码或角色错误", "消息提示");
            }
            else{
                switch (role) {
                    case 1:case 2:
                        new UserJFrame();
                        break;
                    case 3:
                        new AdminJFrame();
                        break;
                }
                this.dispose();
            }

        });

        // 注册按钮
        registerButton.addActionListener(e -> {
            new RegJFrame();
            this.dispose();
        });

        // 角色选择框
        roleComboBox.addActionListener(e -> {
            switch (roleComboBox.getSelectedIndex()) {
                case 0:
                    imageIcon = new ImageIcon(customerimgurl).getImage();
                    break;
                case 1:
                    imageIcon = new ImageIcon(employeeimgurl).getImage();
                    break;
                case 2:
                    imageIcon = new ImageIcon(adminimgurl).getImage();
                    break;
            }
            imageLabel.setIcon(new ImageIcon(imageIcon));
        });

    }


    //设置组件位置
    public void setComponentLocation() {


        layout.putConstraint(SpringLayout.WEST, imageLabel, 45, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, imageLabel, 60, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.NORTH, accountLabel, 60, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, accountLabel, 200, SpringLayout.WEST, imageLabel);
        layout.putConstraint(SpringLayout.NORTH, accountField, 60, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, accountField, 50, SpringLayout.WEST, accountLabel);
        layout.putConstraint(SpringLayout.EAST, accountField, -50, SpringLayout.EAST, panel);

        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 40, SpringLayout.NORTH, accountLabel);
        layout.putConstraint(SpringLayout.WEST, passwordLabel, 0, SpringLayout.WEST, accountLabel);
        layout.putConstraint(SpringLayout.NORTH, passwordField, 40, SpringLayout.NORTH, accountField);
        layout.putConstraint(SpringLayout.WEST, passwordField, 50, SpringLayout.WEST, passwordLabel);
        layout.putConstraint(SpringLayout.EAST, passwordField, -50, SpringLayout.EAST, panel);

        layout.putConstraint(SpringLayout.NORTH, roleLabel, 40, SpringLayout.NORTH, passwordLabel);
        layout.putConstraint(SpringLayout.WEST, roleLabel, 0, SpringLayout.WEST, accountLabel);
        layout.putConstraint(SpringLayout.NORTH, roleComboBox, 40, SpringLayout.NORTH, passwordField);
        layout.putConstraint(SpringLayout.WEST, roleComboBox, 0, SpringLayout.WEST, accountField);

        // 按钮布局在底部
        layout.putConstraint(SpringLayout.NORTH, loginButton, 60, SpringLayout.NORTH, roleLabel);
        layout.putConstraint(SpringLayout.WEST, loginButton, 190, SpringLayout.WEST, imageLabel);
        layout.putConstraint(SpringLayout.NORTH, registerButton, 60, SpringLayout.NORTH, roleLabel);
        layout.putConstraint(SpringLayout.WEST, registerButton, 150, SpringLayout.WEST, loginButton);
    }


    public static void main(String[] args) {
        new LoginJFrame();
    }

}
