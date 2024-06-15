package MyJFrame;

import MyJFrame.Admin.AdminFeedbackJFrame;
import MyJFrame.Admin.AdminOrderJFrame;
import MyJFrame.Admin.AdminProductListJFrame;
import MyJFrame.Employee.EmployeeFeedbackJFrame;
import MyJFrame.Employee.EmployeeOrderJFrame;
import MyJFrame.Employee.EmployeeProductJFrame;
import MyJFrame.User.UserFeedbackJFrame;
import MyJFrame.User.UserOrderJFrame;
import MyJFrame.User.UserProductJFrame;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import util.InputValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class UserJFrame extends JFrame{

    UserDao userDao = new UserDaoImpl();

    SpringLayout layout = new SpringLayout();

    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);


    public UserJFrame() {
        switch(LoginJFrame.currentUser.getrole()){
            case 1: setTitle("UserInfoJFrame");
                break;
            case 2: setTitle("EmployeeInfoJFrame");
                break;
            default:setTitle("Error");
        }

        setSize(1100, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(layout);
        setOverallLocation();
        showComponentIni();
        funComponentIni();

        this.add(functionPanel);
        this.add(showPanel);


        this.setVisible(true);

    }
    //总布局
    public void setOverallLocation()        //总布局
    {
        layout.putConstraint(SpringLayout.WEST, functionPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, functionPanel, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, functionPanel,160 , SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, functionPanel, 660, SpringLayout.NORTH, this);


        layout.putConstraint(SpringLayout.WEST, showPanel, 160, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, showPanel, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, showPanel, 1100, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, showPanel, 660, SpringLayout.NORTH, this);

    }

    public void showComponentIni () {
        showPanel.setBackground(Color.white);

        JLabel userNameLabel = new JLabel("用户名：");
        JLabel userNameValue = new JLabel(LoginJFrame.currentUser.getUsername());
        JTextField userNameTextField = new JTextField(LoginJFrame.currentUser.getUsername(), 18);
        //输入框大小
        userNameTextField.setPreferredSize(new Dimension(200, 30));
        //不显示
        userNameTextField.setVisible(false);
        //设置字体
        userNameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userNameValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel userPasswordLabel = new JLabel("密码：");
        JLabel userPasswordValue = new JLabel(LoginJFrame.currentUser.getUserpassword());
        JTextField userPasswordTextField = new JTextField(LoginJFrame.currentUser.getUserpassword(), 18);
        userPasswordTextField.setPreferredSize(new Dimension(200, 30));
        userPasswordTextField.setVisible(false);
        userPasswordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userPasswordValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel userRoleLabel = new JLabel("角色：");
        JLabel userRoleValue = new JLabel(String.valueOf(LoginJFrame.currentUser.getrole()));
        userRoleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userRoleValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel userAgeLabel = new JLabel("年龄：");
        JLabel userAgeValue = new JLabel(String.valueOf(LoginJFrame.currentUser.getAge()));
        JTextField userAgeTextField = new JTextField(String.valueOf(LoginJFrame.currentUser.getAge()), 18);
        userAgeTextField.setPreferredSize(new Dimension(200, 30));
        userAgeTextField.setVisible(false);
        userAgeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userAgeValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel userSexLabel = new JLabel("性别：");
        JLabel userSexValue = new JLabel(LoginJFrame.currentUser.getSex());
        JTextField userSexTextField = new JTextField(LoginJFrame.currentUser.getSex(), 18);
        userSexTextField.setPreferredSize(new Dimension(200, 30));
        userSexTextField.setVisible(false);
        userSexLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userSexValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel userPhoneLabel = new JLabel("电话：");
        JLabel userPhoneValue = new JLabel(LoginJFrame.currentUser.getPhone());
        JTextField userPhoneTextField = new JTextField(LoginJFrame.currentUser.getPhone(), 18);
        userPhoneTextField.setPreferredSize(new Dimension(200, 30));
        userPhoneTextField.setVisible(false);
        userPhoneLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userPhoneValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel userAddressLabel = new JLabel("地址：");
        JLabel userAddressValue = new JLabel(LoginJFrame.currentUser.getAddress());
        JTextField userAddressTextField = new JTextField(LoginJFrame.currentUser.getAddress(), 30);
        userAddressTextField.setPreferredSize(new Dimension(200, 30));
        userAddressTextField.setVisible(false);
        userAddressLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userAddressValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel userCreditratingLabel = new JLabel("信用等级：");
        JLabel userCreditratingValue = new JLabel(String.valueOf(LoginJFrame.currentUser.getCreditrating()));
        userCreditratingLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userCreditratingValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JButton UpdateButton = new JButton("修改");
        JButton SaveButton = new JButton("保存");
        SaveButton.setVisible(false);

        //UpdateOrSaveButton.setVisible(true);
        UpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateButton.setVisible(false);
                SaveButton.setVisible(true);
                userNameValue.setVisible(false);
                userNameTextField.setVisible(true);
                userPasswordValue.setVisible(false);
                userPasswordTextField.setVisible(true);
                userAgeValue.setVisible(false);
                userAgeTextField.setVisible(true);
                userSexValue.setVisible(false);
                userSexTextField.setVisible(true);
                userPhoneValue.setVisible(false);
                userPhoneTextField.setVisible(true);
                userAddressValue.setVisible(false);
                userAddressTextField.setVisible(true);
                setOverallLocation();
            }
        });
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = userNameTextField.getText();
                String password = userPasswordTextField.getText();
                int age = Integer.parseInt(userAgeTextField.getText());
                String gender = userSexTextField.getText();
                String phone = userPhoneTextField.getText();
                String address = userAddressTextField.getText();

                if(!InputValidator.isValidUsername(username))
                    JOptionPane.showMessageDialog( UserJFrame.this, "用户名不合法");
                else if(!InputValidator.isValidPassword(password))
                    JOptionPane.showMessageDialog(UserJFrame.this, "密码不合法");
                else if(!InputValidator.isValidAge(userAgeTextField.getText()))
                    JOptionPane.showMessageDialog(UserJFrame.this, "年龄不合法");
                else if(!InputValidator.isValidGender(gender))
                    JOptionPane.showMessageDialog(UserJFrame.this, "性别不合法");
                else if(!InputValidator.isValidPhone(phone))
                    JOptionPane.showMessageDialog(UserJFrame.this, "电话不合法");
                else if(!InputValidator.isValidAddress(address))
                    JOptionPane.showMessageDialog(UserJFrame.this, "地址不合法");
                else{

                    LoginJFrame.currentUser.setUsername( username);
                    LoginJFrame.currentUser.setUserpassword( password);
                    LoginJFrame.currentUser.setrole( LoginJFrame.currentUser.getrole());
                    LoginJFrame.currentUser.setSex(gender);
                    LoginJFrame.currentUser.setPhone(phone);
                    LoginJFrame.currentUser.setAddress(address);
                    LoginJFrame.currentUser.setAge(age);
                    LoginJFrame.currentUser.setCreditrating(LoginJFrame.currentUser.getCreditrating());

                    //System.out.println(LoginJFrame.currentUser);
                    userDao.updateUser(LoginJFrame.currentUser);

                    SaveButton.setVisible(false);
                    UpdateButton.setVisible(true);
                    userNameTextField.setVisible(false);
                    userNameValue.setVisible(true);
                    userNameValue.setText(username);
                    userPasswordTextField.setVisible(false);
                    userPasswordValue.setVisible(true);
                    userPasswordValue.setText(password);
                    userAgeTextField.setVisible(false);
                    userAgeValue.setVisible(true);
                    userAgeValue.setText(String.valueOf(age));
                    userSexTextField.setVisible(false);
                    userSexValue.setVisible(true);
                    userSexValue.setText(gender);
                    userPhoneTextField.setVisible(false);
                    userPhoneValue.setVisible(true);
                    userPhoneValue.setText(phone);
                    userAddressTextField.setVisible(false);
                    userAddressValue.setVisible(true);
                    userAddressValue.setText(address);
                    setOverallLocation();
                }

            }

        });



        layout.putConstraint(SpringLayout.EAST, userNameLabel, 120, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userNameLabel, 90, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST, userNameValue, 20, SpringLayout.EAST, userNameLabel);
        layout.putConstraint(SpringLayout.NORTH, userNameValue, 90, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST, userNameTextField, 20, SpringLayout.EAST, userNameLabel);
        layout.putConstraint(SpringLayout.NORTH, userNameTextField, 90, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.EAST, userPasswordLabel, 300, SpringLayout.EAST, userNameLabel);
        layout.putConstraint(SpringLayout.NORTH, userPasswordLabel, 90, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST, userPasswordValue, 20, SpringLayout.EAST, userPasswordLabel);
        layout.putConstraint(SpringLayout.NORTH, userPasswordValue, 90, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST, userPasswordTextField, 20, SpringLayout.EAST, userPasswordLabel);
        layout.putConstraint(SpringLayout.NORTH, userPasswordTextField, 90, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.EAST, userRoleLabel, 120, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userRoleLabel, 20, SpringLayout.SOUTH, userNameLabel);
        layout.putConstraint(SpringLayout.WEST, userRoleValue, 20, SpringLayout.EAST, userRoleLabel);
        layout.putConstraint(SpringLayout.NORTH, userRoleValue, 20, SpringLayout.SOUTH, userNameValue);

        layout.putConstraint(SpringLayout.EAST, userAgeLabel, 300, SpringLayout.EAST, userRoleLabel);
        layout.putConstraint(SpringLayout.NORTH, userAgeLabel, 20, SpringLayout.SOUTH, userPasswordLabel);
        layout.putConstraint(SpringLayout.WEST, userAgeValue, 20, SpringLayout.EAST, userAgeLabel);
        layout.putConstraint(SpringLayout.NORTH, userAgeValue, 20, SpringLayout.SOUTH, userPasswordLabel);
        layout.putConstraint(SpringLayout.WEST, userAgeTextField, 20, SpringLayout.EAST, userAgeLabel);
        layout.putConstraint(SpringLayout.NORTH, userAgeTextField, 20, SpringLayout.SOUTH, userPasswordLabel);

        layout.putConstraint(SpringLayout.EAST, userSexLabel, 120, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userSexLabel, 20, SpringLayout.SOUTH, userRoleLabel);
        layout.putConstraint(SpringLayout.WEST, userSexValue, 20, SpringLayout.EAST, userSexLabel);
        layout.putConstraint(SpringLayout.NORTH, userSexValue, 20, SpringLayout.SOUTH, userRoleLabel);
        layout.putConstraint(SpringLayout.WEST, userSexTextField, 20, SpringLayout.EAST, userSexLabel);
        layout.putConstraint(SpringLayout.NORTH, userSexTextField, 20, SpringLayout.SOUTH, userRoleLabel);

        layout.putConstraint(SpringLayout.EAST, userPhoneLabel, 300, SpringLayout.EAST, userSexLabel);
        layout.putConstraint(SpringLayout.NORTH, userPhoneLabel, 20, SpringLayout.SOUTH, userAgeLabel);
        layout.putConstraint(SpringLayout.WEST, userPhoneValue, 20, SpringLayout.EAST, userPhoneLabel);
        layout.putConstraint(SpringLayout.NORTH, userPhoneValue, 20, SpringLayout.SOUTH, userAgeValue);
        layout.putConstraint(SpringLayout.WEST, userPhoneTextField, 20, SpringLayout.EAST, userPhoneLabel);
        layout.putConstraint(SpringLayout.NORTH, userPhoneTextField, 20, SpringLayout.SOUTH, userAgeValue);

        layout.putConstraint(SpringLayout.EAST, userAddressLabel, 120, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userAddressLabel, 20, SpringLayout.SOUTH, userSexLabel);
        layout.putConstraint(SpringLayout.WEST, userAddressValue, 20, SpringLayout.EAST, userAddressLabel);
        layout.putConstraint(SpringLayout.NORTH, userAddressValue, 20, SpringLayout.SOUTH, userSexValue);
        layout.putConstraint(SpringLayout.WEST, userAddressTextField, 20, SpringLayout.EAST, userAddressLabel);
        layout.putConstraint(SpringLayout.NORTH, userAddressTextField, 20, SpringLayout.SOUTH, userSexValue);

        layout.putConstraint(SpringLayout.EAST, userCreditratingLabel, 120, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userCreditratingLabel, 20, SpringLayout.SOUTH, userAddressLabel);
        layout.putConstraint(SpringLayout.WEST, userCreditratingValue, 20, SpringLayout.EAST, userCreditratingLabel);
        layout.putConstraint(SpringLayout.NORTH, userCreditratingValue, 20, SpringLayout.SOUTH, userAddressValue);

        layout.putConstraint(SpringLayout.WEST, UpdateButton, 200, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, UpdateButton, 500, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.EAST, UpdateButton, 350, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, UpdateButton, 550, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, SaveButton, 300, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, SaveButton, 500, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.EAST, SaveButton, 450, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, SaveButton, 550, SpringLayout.NORTH, showPanel);


        showPanel.add(userNameLabel);
        showPanel.add(userNameValue);
        showPanel.add(userNameTextField);
        showPanel.add(userPasswordLabel);
        showPanel.add(userPasswordValue);
        showPanel.add(userPasswordTextField);
        showPanel.add(userRoleLabel);
        showPanel.add(userRoleValue);
        showPanel.add(userAgeLabel);
        showPanel.add(userAgeValue);
        showPanel.add(userAgeTextField);
        showPanel.add(userSexLabel);
        showPanel.add(userSexValue);
        showPanel.add(userSexTextField);
        showPanel.add(userPhoneLabel);
        showPanel.add(userPhoneValue);
        showPanel.add(userPhoneTextField);
        showPanel.add(userAddressLabel);
        showPanel.add(userAddressValue);
        showPanel.add(userAddressTextField);
        showPanel.add(userCreditratingLabel);
        showPanel.add(userCreditratingValue);
        showPanel.add(UpdateButton);
        showPanel.add(SaveButton);


        showPanel.add(new JLabel(" >>个人信息"));
    }

    public void funComponentIni()               //功能栏
    {
        functionPanel.setBackground(Color.LIGHT_GRAY);

        String imgurl = switch (LoginJFrame.currentUser.getrole()){
            case 1 -> "Img/login_customer.jpg";
            case 2 -> "Img/login_employee.jpg";
            default -> "null";
        };


        Image imageIcon = new ImageIcon(imgurl).getImage() ;
        JLabel imageLabel = new JLabel(new ImageIcon(imageIcon));

        JButton productlistButton = new JButton("产品中心");
        JButton orderlistButton = new JButton("订单中心");
        JButton feedbacklistButton = new JButton("反馈记录");
        JButton consumptionlistButton = new JButton("个人信息");

        productlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(LoginJFrame.currentUser.getrole()){
                    case 1:
                        new UserProductJFrame();
                        break;
                    case 2:
                        new EmployeeProductJFrame();
                        break;
                }
                dispose();
            }
        });
        orderlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(LoginJFrame.currentUser.getrole()){
                    case 1:
                        new UserOrderJFrame();
                        break;
                    case 2:
                        new EmployeeOrderJFrame();
                        break;
                }
                dispose();
            }
        });
        feedbacklistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (LoginJFrame.currentUser.getrole()){
                    case 1:
                        new UserFeedbackJFrame();
                        break;
                    case 2:
                        new EmployeeFeedbackJFrame();
                        break;
                }
                dispose();
            }
        });

        //按钮透明
        productlistButton.setBackground(Color.LIGHT_GRAY);
        //无边框
        productlistButton.setBorderPainted(false);
        orderlistButton.setBackground(Color.LIGHT_GRAY);
        orderlistButton.setBorderPainted(false);
        feedbacklistButton.setBackground(Color.LIGHT_GRAY);
        feedbacklistButton.setBorderPainted(false);
        consumptionlistButton.setBackground(Color.LIGHT_GRAY);
        consumptionlistButton.setBorderPainted(false);
        consumptionlistButton.setEnabled(false);
        

        layout.putConstraint(SpringLayout.WEST, imageLabel, 25, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, imageLabel, 25, SpringLayout.NORTH, functionPanel);
        layout.putConstraint(SpringLayout.EAST, imageLabel, 135, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, imageLabel, 135, SpringLayout.NORTH, functionPanel);

        layout.putConstraint(SpringLayout.WEST, productlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, productlistButton, 180, SpringLayout.NORTH, functionPanel);
        layout.putConstraint(SpringLayout.EAST, productlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, productlistButton, 240, SpringLayout.NORTH, functionPanel);

        layout.putConstraint(SpringLayout.WEST, orderlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, orderlistButton, 80, SpringLayout.NORTH, productlistButton);
        layout.putConstraint(SpringLayout.EAST, orderlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, orderlistButton, 80, SpringLayout.SOUTH, productlistButton);

        layout.putConstraint(SpringLayout.WEST, feedbacklistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbacklistButton, 80, SpringLayout.NORTH, orderlistButton);
        layout.putConstraint(SpringLayout.EAST, feedbacklistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, feedbacklistButton, 80, SpringLayout.SOUTH,orderlistButton);

        layout.putConstraint(SpringLayout.WEST, consumptionlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, consumptionlistButton, 80, SpringLayout.NORTH, feedbacklistButton);
        layout.putConstraint(SpringLayout.EAST, consumptionlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, consumptionlistButton, 80, SpringLayout.SOUTH, feedbacklistButton);



        functionPanel.add(imageLabel);
        functionPanel.add(productlistButton);
        functionPanel.add(orderlistButton);
        functionPanel.add(feedbacklistButton);
        functionPanel.add(consumptionlistButton);

    }


    public static void main(String[] args) throws SQLException {
        //LoginJFrame.currentUser = new User(4,1,"admin","123456",18,"男","北京","12345678901",100);
        new LoginJFrame();
    }

}
