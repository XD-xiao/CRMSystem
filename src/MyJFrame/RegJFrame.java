package MyJFrame;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import util.InputValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegJFrame extends JFrame{
    UserDao userDao = new UserDaoImpl();


    SpringLayout layout = new SpringLayout();
    JPanel panel = new JPanel(layout);

    JLabel nameLabel = new JLabel("姓名:");
    JTextField nameLabelField = new JTextField(18);
    JLabel passwordLabel = new JLabel("密码:");
    JPasswordField passwordField = new JPasswordField(18);
    JLabel roleLabel = new JLabel("角色:");
    JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"客户","员工","管理员"});

    JLabel ageLabel = new JLabel("年龄:");
    JTextField ageField = new JTextField(6);
    JLabel genderLabel = new JLabel("性别:");
    JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"男","女"});
    JLabel phoneLabel = new JLabel("电话:");
    JTextField phoneField = new JTextField(18);
    JLabel addressLabel = new JLabel("地址:");
    JTextField addressField = new JTextField(18);
    JLabel validCodeLabel = new JLabel("验证码:");
    JTextField validCodeField = new JTextField(8);

    ValidCode validCode = new ValidCode();
    JButton regButton = new JButton("注册");

    JButton exitButton = new JButton("< 退出");

    public RegJFrame()
    {
        setTitle("CRMSystem-Register");
        setSize(500, 400);
        //固定窗口大小
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //背景颜色
        panel.setBackground(new Color(0xF0F8FF));

        this.setLayout(layout);
        this.setContentPane(panel);

        setComponentListener();     // 设置组件监听器

        panel.add(nameLabel);
        panel.add(nameLabelField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(validCodeLabel);
        panel.add(validCodeField);
        panel.add(validCode);
        panel.add(regButton);
        panel.add(exitButton);

        setComponentLocation();     //设置组件位置


        this.setVisible(true);
    }

    private void setComponentLocation() {
        layout.putConstraint(SpringLayout.EAST, nameLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 40, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, nameLabelField, 10, SpringLayout.EAST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, nameLabelField, 40, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.EAST, passwordLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 15, SpringLayout.SOUTH, nameLabel);
        layout.putConstraint(SpringLayout.WEST, passwordField, 10, SpringLayout.EAST, passwordLabel);
        layout.putConstraint(SpringLayout.NORTH, passwordField, 15, SpringLayout.SOUTH, nameLabel);

        layout.putConstraint(SpringLayout.EAST, roleLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, roleLabel, 15, SpringLayout.SOUTH, passwordLabel);
        layout.putConstraint(SpringLayout.WEST, roleComboBox, 10, SpringLayout.EAST, roleLabel);
        layout.putConstraint(SpringLayout.NORTH, roleComboBox, 15, SpringLayout.SOUTH, passwordLabel);

        layout.putConstraint(SpringLayout.EAST, ageLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, ageLabel, 15, SpringLayout.SOUTH, roleLabel);
        layout.putConstraint(SpringLayout.WEST, ageField, 10, SpringLayout.EAST, ageLabel);
        layout.putConstraint(SpringLayout.NORTH, ageField, 15, SpringLayout.SOUTH, roleLabel);

        layout.putConstraint(SpringLayout.EAST, genderLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, genderLabel, 15, SpringLayout.SOUTH, ageLabel);
        layout.putConstraint(SpringLayout.WEST, genderComboBox, 10, SpringLayout.EAST, genderLabel);
        layout.putConstraint(SpringLayout.NORTH, genderComboBox, 15, SpringLayout.SOUTH, ageLabel);

        layout.putConstraint(SpringLayout.EAST, phoneLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, phoneLabel, 15, SpringLayout.SOUTH, genderLabel);
        layout.putConstraint(SpringLayout.WEST, phoneField, 10, SpringLayout.EAST, phoneLabel);
        layout.putConstraint(SpringLayout.NORTH, phoneField, 15, SpringLayout.SOUTH, genderLabel);

        layout.putConstraint(SpringLayout.EAST, addressLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addressLabel, 15, SpringLayout.SOUTH, phoneLabel);
        layout.putConstraint(SpringLayout.WEST, addressField, 10, SpringLayout.EAST, addressLabel);
        layout.putConstraint(SpringLayout.NORTH, addressField, 15, SpringLayout.SOUTH, phoneLabel);

        layout.putConstraint(SpringLayout.EAST, validCodeLabel, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, validCodeLabel, 20, SpringLayout.SOUTH, addressLabel);
        layout.putConstraint(SpringLayout.WEST, validCodeField, 10, SpringLayout.EAST, validCodeLabel);
        layout.putConstraint(SpringLayout.NORTH, validCodeField, 20, SpringLayout.SOUTH, addressLabel);
        layout.putConstraint(SpringLayout.WEST, validCode, 10, SpringLayout.EAST, validCodeField);
        layout.putConstraint(SpringLayout.NORTH, validCode, 10, SpringLayout.SOUTH, addressLabel);

        layout.putConstraint(SpringLayout.WEST, regButton, 200, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, regButton, 20, SpringLayout.SOUTH, validCodeLabel);

        layout.putConstraint(SpringLayout.WEST, exitButton, 2, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, exitButton, 2, SpringLayout.NORTH, panel);

    }

    private void setComponentListener() {
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = nameLabelField.getText();
                String password = new String(passwordField.getPassword());
                int role = roleComboBox.getSelectedIndex()+1;
                int age = Integer.parseInt(ageField.getText());
                String gender = genderComboBox.getSelectedItem().toString();
                String phone = phoneField.getText();
                String address = addressField.getText();
                String code = validCodeField.getText();
                if(!InputValidator.isValidUsername(username))
                    JOptionPane.showMessageDialog(RegJFrame.this, "用户名不合法");
                else if(!InputValidator.isValidPassword(password))
                    JOptionPane.showMessageDialog(RegJFrame.this, "密码不合法");
                else if(!InputValidator.isValidAge(ageField.getText()))
                    JOptionPane.showMessageDialog(RegJFrame.this, "年龄不合法");
                else if(!InputValidator.isValidGender(gender))
                    JOptionPane.showMessageDialog(RegJFrame.this, "性别不合法");
                else if(!InputValidator.isValidPhone(phone))
                    JOptionPane.showMessageDialog(RegJFrame.this, "电话不合法");
                else if(!InputValidator.isValidAddress(address))
                    JOptionPane.showMessageDialog(RegJFrame.this, "地址不合法");
                else if(! code.equalsIgnoreCase(validCode.getCode()))
                    JOptionPane.showMessageDialog(RegJFrame.this, "验证码错误");
                else if(userDao.getUserByName(username) != null)
                    JOptionPane.showMessageDialog(RegJFrame.this, "用户名已存在");
                else {
                    User user = new User( role, username, password, age, gender, address, phone, 100);
                    UserDao userDao = new UserDaoImpl();
                    int result = userDao.addUser(user);
                    if(result == 1){
                        JOptionPane.showMessageDialog(RegJFrame.this, "注册成功");
                        new LoginJFrame().setVisible(true);
                        dispose();
                    }

                }


            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginJFrame().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new RegJFrame();
    }

}
