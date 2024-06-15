package MyJFrame.Admin;

import MyJFrame.AdminJFrame;
import MyJFrame.LoginJFrame;
import MyJFrame.User.UserProductJFrame;
import MyJFrame.UserJFrame;
import dao.*;
import dao.impl.*;
import domain.Product;
import domain.User;
import util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AdminUserListJFrame extends JFrame {

    UserDao userDao = new UserDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
    FeedbackDao feedbackDao = new FeedbackDaoImpl();
    CommentDao commentDao = new CommentDaoImpl();
    SpringLayout layout = new SpringLayout();
    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);

    User currentUser = new User();
    User searchUser = new User();

    JTable userTable;

    JComboBox chooseTypeComboBox = new JComboBox(new String[]{"全部","客户","员工","管理员"});


    JLabel userIdLabel = new JLabel("用户ID:");
    JLabel userNameLabel = new JLabel("用户名称:");
    JTextField userNameTextField = new JTextField(10);
    JLabel userRoleLabel = new JLabel("角色:");
    JComboBox userRoleComboBox = new JComboBox(new String[]{"客户","员工","管理员"});

    JLabel userPasswordLabel = new JLabel("密码:");
    JPasswordField userPasswordTextField = new JPasswordField(10);
    JLabel userAgeLabel = new JLabel("年龄:");
    JTextField userAgeTextField = new JTextField(5);
    JLabel userSexLabel = new JLabel("性别:");
    JComboBox userSexComboBox = new JComboBox(new String[]{"男","女"});
    JLabel userPhoneLabel = new JLabel("手机号:");
    JTextField userPhoneTextField = new JTextField(10);
    JLabel userAddressLabel = new JLabel("地址:");
    JTextField userAddressTextField = new JTextField(20);
    JLabel userCreditratingLabel = new JLabel("信用度:");
    JTextField userCreditratingTextField = new JTextField(5);

    JButton updateUserButton = new JButton("修改");
    JButton deleteUserButton = new JButton("删除");

    public AdminUserListJFrame() {
        setTitle("AdminUserListJFrame");

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
        showPanel.add(new JLabel("                                                   >>用户中心"));

        JLabel searchJLabel = new JLabel("搜索：");
        searchJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JTextField searchJTextField = new JTextField(15);
        searchJTextField.setPreferredSize(new Dimension(80, 30));

        JLabel chooseTypeJLabel = new JLabel("类型：");
        chooseTypeJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        chooseTypeComboBox.setPreferredSize(new Dimension(90, 30));

        JButton searchJButton = new JButton("搜索");
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取
                searchUser.setUsername(searchJTextField.getText());
                searchUser.setrole(chooseTypeComboBox.getSelectedIndex());
                if(chooseTypeComboBox.getSelectedItem().equals("全部"))
                    searchUser.setrole(0);
                if(searchJTextField.getText().equals(""))
                    searchUser.setUsername(null);
                //更新表格
                userTable.setModel(new DefaultTableModel(getUserData(searchUser), new String[]{"编号","名称", "角色", "密码" , "年龄","性别","电话","地址","信用度"}));

            }
        });
        deleteUserButton.setEnabled(false);
        updateUserButton.setEnabled(false);
        DefaultTableModel usermodel = new DefaultTableModel(getUserData(searchUser), new String[]{"编号","名称", "角色", "密码" , "年龄","性别","电话","地址","信用度"});
        userTable = new JTable(usermodel);
        //表格不可以编辑
        userTable.setDefaultEditor(Object.class, null);
        //每次只能选择一行
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setRowHeight(20);
        JScrollPane userPane = new JScrollPane(userTable);
        userPane.setPreferredSize(new Dimension(900, 500));
        //表格选择的内容
        userTable.getSelectionModel().addListSelectionListener(e -> {
            if(userTable.getSelectedRow() == -1)
                return;
            currentUser.setUsername( userTable.getValueAt(userTable.getSelectedRow(), 1).toString());

            deleteUserButton.setEnabled(true);
            updateUserButton.setEnabled(true);
            currentUser = userDao.getUserByName(currentUser.getUsername());
            userIdLabel.setText("用户ID: "+currentUser.getId());
            userNameTextField.setText(currentUser.getUsername());
            userRoleComboBox.setSelectedIndex(currentUser.getrole()-1);
            userPasswordTextField.setText(currentUser.getUserpassword());
            userAgeTextField.setText(currentUser.getAge()+"");
            userSexComboBox.setSelectedItem(currentUser.getSex());
            userPhoneTextField.setText(currentUser.getPhone());
            userAddressTextField.setText(currentUser.getAddress());
            userCreditratingTextField.setText(currentUser.getCreditrating()+"");
        });

        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!InputValidator.isValidUsername(userNameTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的名称");
                }
                else if(!InputValidator.isValidPassword(userPasswordTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的密码");
                }
                else if(!InputValidator.isValidAge(userAgeTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的年龄");
                }
                else if(!InputValidator.isValidPhone(userPhoneTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的电话");
                }
                else if(!InputValidator.isValidAddress(userAddressTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的地址");
                }
                else if(!InputValidator.isValidGender(userSexComboBox.getSelectedItem().toString()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的性别");
                }
                else if(!InputValidator.isValidCreditrating(userCreditratingTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的信用度");
                }
                else{
                    System.out.println("更新用户信息");
                    User user = new User();
                    user.setId(currentUser.getId());
                    user.setUsername(userNameTextField.getText());
                    user.setUserpassword(userPasswordTextField.getText());
                    user.setAge(Integer.parseInt(userAgeTextField.getText()));
                    user.setSex(userSexComboBox.getSelectedItem().toString());
                    user.setPhone(userPhoneTextField.getText());
                    user.setAddress(userAddressTextField.getText());
                    user.setCreditrating(Integer.parseInt(userCreditratingTextField.getText()));
                    user.setrole(userRoleComboBox.getSelectedIndex()+1);

                    if(userDao.updateUser(user) != 1){
                        JOptionPane.showMessageDialog(null, "更新失败");
                    }
                    userTable.setModel(new DefaultTableModel(getUserData(searchUser), new String[]{"编号","名称", "角色", "密码" , "年龄","性别","电话","地址","信用度"}));
                }
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( currentUser.getrole() == 1){//用户
                    int userresult = userDao.deleteUser(currentUser);
                    int orderresult = orderDao.deleteOrderByUserid(currentUser.getId());
                    int commentresult = commentDao.deleteCommentByUserid(currentUser.getId());
                    int feedbackresult = feedbackDao.deleteFeedbackByUserid(currentUser.getId());
                    if( userresult == 1 && orderresult == 1 && commentresult == 1 && feedbackresult == 1 ){
                        JOptionPane.showMessageDialog(null, "删除成功");
                        userTable.setModel(new DefaultTableModel(getUserData(searchUser), new String[]{"编号","名称", "角色", "密码" , "年龄","性别","电话","地址","信用度"}));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "删除失败");
                    }
                }
                else if( currentUser.getrole() == 2){//员工
                    int userresult = userDao.deleteUser(currentUser);
                    int orderresult = orderDao.deleteOrderByStaffid(currentUser.getId());
                    int commentresult = commentDao.deleteCommentByUserid(currentUser.getId());
                    int feedbackresult = feedbackDao.deleteFeedbackByStaffid(currentUser.getId());
                    if( userresult == 1 && orderresult == 1 && commentresult ==1 && feedbackresult == 1 ){
                        JOptionPane.showMessageDialog(null, "删除成功");
                        userTable.setModel(new DefaultTableModel(getUserData(searchUser), new String[]{"编号","名称", "角色", "密码" , "年龄","性别","电话","地址","信用度"}));
                    }
                }
                else if( currentUser.getrole() == 3){//管理员
                    int userresult = userDao.deleteUser(currentUser);
                    if( userresult == 1 ){
                        JOptionPane.showMessageDialog(null, "删除成功");
                        userTable.setModel(new DefaultTableModel(getUserData(searchUser), new String[]{"编号","名称", "角色", "密码" , "年龄","性别","电话","地址","信用度"}));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "删除失败");
                    }
                }


            }
        });










        layout.putConstraint(SpringLayout.WEST,searchJLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH,searchJLabel, 40, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST,searchJTextField, 20, SpringLayout.EAST, searchJLabel);
        layout.putConstraint(SpringLayout.NORTH,searchJTextField, 40, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST,chooseTypeJLabel, 100, SpringLayout.EAST, searchJTextField);
        layout.putConstraint(SpringLayout.NORTH,chooseTypeJLabel, 40, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST,chooseTypeComboBox, 20, SpringLayout.EAST, chooseTypeJLabel);
        layout.putConstraint(SpringLayout.NORTH,chooseTypeComboBox, 40, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST,searchJButton, 180, SpringLayout.EAST, chooseTypeComboBox);
        layout.putConstraint(SpringLayout.NORTH,searchJButton, 42, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, userPane, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userPane, 80, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.EAST, userPane, 1000, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, userPane, 360, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, userIdLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userIdLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.EAST, userNameLabel, 260, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userNameLabel, 20, SpringLayout.SOUTH, userIdLabel);
        layout.putConstraint(SpringLayout.WEST, userNameTextField, 20, SpringLayout.EAST, userNameLabel);
        layout.putConstraint(SpringLayout.NORTH, userNameTextField, 20, SpringLayout.SOUTH, userIdLabel);

        layout.putConstraint(SpringLayout.EAST, userRoleLabel, 210, SpringLayout.EAST, userNameLabel);
        layout.putConstraint(SpringLayout.NORTH, userRoleLabel, 20, SpringLayout.SOUTH, userIdLabel);
        layout.putConstraint(SpringLayout.WEST, userRoleComboBox, 20, SpringLayout.EAST, userRoleLabel);
        layout.putConstraint(SpringLayout.NORTH, userRoleComboBox, 20, SpringLayout.SOUTH, userIdLabel);

        layout.putConstraint(SpringLayout.EAST, userPasswordLabel, 260, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userPasswordLabel, 20, SpringLayout.SOUTH, userNameLabel);
        layout.putConstraint(SpringLayout.WEST, userPasswordTextField, 20, SpringLayout.EAST, userPasswordLabel);
        layout.putConstraint(SpringLayout.NORTH, userPasswordTextField, 20, SpringLayout.SOUTH, userNameLabel);

        layout.putConstraint(SpringLayout.EAST, userAgeLabel, 210, SpringLayout.EAST, userPasswordLabel);
        layout.putConstraint(SpringLayout.NORTH, userAgeLabel, 20, SpringLayout.SOUTH, userRoleLabel);
        layout.putConstraint(SpringLayout.WEST, userAgeTextField, 20, SpringLayout.EAST, userAgeLabel);
        layout.putConstraint(SpringLayout.NORTH, userAgeTextField, 20, SpringLayout.SOUTH, userRoleLabel);

        layout.putConstraint(SpringLayout.EAST, userSexLabel, 260, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userSexLabel, 20, SpringLayout.SOUTH, userAgeLabel);
        layout.putConstraint(SpringLayout.WEST, userSexComboBox, 20, SpringLayout.EAST, userSexLabel);
        layout.putConstraint(SpringLayout.NORTH, userSexComboBox, 20, SpringLayout.SOUTH, userAgeLabel);

        layout.putConstraint(SpringLayout.EAST, userPhoneLabel, 210, SpringLayout.EAST, userSexLabel);
        layout.putConstraint(SpringLayout.NORTH, userPhoneLabel, 20, SpringLayout.SOUTH, userAgeLabel);
        layout.putConstraint(SpringLayout.WEST, userPhoneTextField, 20, SpringLayout.EAST, userPhoneLabel);
        layout.putConstraint(SpringLayout.NORTH, userPhoneTextField, 20, SpringLayout.SOUTH, userPasswordLabel);

        layout.putConstraint(SpringLayout.EAST, userAddressLabel, 260, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userAddressLabel, 20, SpringLayout.SOUTH, userSexLabel);
        layout.putConstraint(SpringLayout.WEST, userAddressTextField, 20, SpringLayout.EAST, userAddressLabel);
        layout.putConstraint(SpringLayout.NORTH, userAddressTextField, 20, SpringLayout.SOUTH, userSexLabel);

        layout.putConstraint(SpringLayout.EAST, userCreditratingLabel, 260, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, userCreditratingLabel, 20, SpringLayout.SOUTH, userAddressLabel);
        layout.putConstraint(SpringLayout.WEST, userCreditratingTextField, 20, SpringLayout.EAST, userCreditratingLabel);
        layout.putConstraint(SpringLayout.NORTH, userCreditratingTextField, 20, SpringLayout.SOUTH, userAddressLabel);

        layout.putConstraint(SpringLayout.WEST, updateUserButton, 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, updateUserButton, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, deleteUserButton, 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, deleteUserButton, 20, SpringLayout.SOUTH, updateUserButton);


        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeComboBox);
        showPanel.add(searchJButton);
        showPanel.add(userPane);
        showPanel.add(userIdLabel);
        showPanel.add(userNameLabel);
        showPanel.add(userNameTextField);
        showPanel.add(userRoleLabel);
        showPanel.add(userRoleComboBox);
        showPanel.add(userPasswordLabel);
        showPanel.add(userPasswordTextField);
        showPanel.add(userAgeLabel);
        showPanel.add(userAgeTextField);
        showPanel.add(userSexLabel);
        showPanel.add(userSexComboBox);
        showPanel.add(userPhoneLabel);
        showPanel.add(userPhoneTextField);
        showPanel.add(userAddressLabel);
        showPanel.add(userAddressTextField);
        showPanel.add(userCreditratingLabel);
        showPanel.add(userCreditratingTextField);
        showPanel.add(updateUserButton);
        showPanel.add(deleteUserButton);



    }

    public void funComponentIni()               //功能栏
    {
        functionPanel.setBackground(Color.LIGHT_GRAY);

        Image imageIcon = new ImageIcon("Img/login_employee.jpg").getImage() ;
        JLabel imageLabel = new JLabel(new ImageIcon(imageIcon));

        JButton userlistButton = new JButton("用户中心");
        JButton productlistButton = new JButton("产品中心");
        JButton orderlistButton = new JButton("订单中心");
        JButton feedbacklistButton = new JButton("反馈记录");
        JButton commentlistButton = new JButton("评论中心");
        JButton consumptionlistButton = new JButton("个人信息");

//        userlistButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new AdminUserListJFrame();
//                dispose();
//            }
//        });
        productlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminProductListJFrame();
                dispose();
            }
        });
        orderlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminOrderJFrame();
                dispose();
            }
        });
        feedbacklistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminFeedbackJFrame();
                dispose();
            }
        });
        commentlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminCommentJFrame();
                dispose();
            }
        });
        consumptionlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminJFrame();
                dispose();
            }
        });
        userlistButton.setBackground(Color.LIGHT_GRAY);
        userlistButton.setBorderPainted(false);
        productlistButton.setBackground(Color.LIGHT_GRAY);
        productlistButton.setBorderPainted(false);
        orderlistButton.setBackground(Color.LIGHT_GRAY);
        orderlistButton.setBorderPainted(false);
        feedbacklistButton.setBackground(Color.LIGHT_GRAY);
        feedbacklistButton.setBorderPainted(false);
        commentlistButton.setBackground(Color.LIGHT_GRAY);
        commentlistButton.setBorderPainted(false);
        consumptionlistButton.setBackground(Color.LIGHT_GRAY);
        consumptionlistButton.setBorderPainted(false);

        userlistButton.setEnabled(false);

        layout.putConstraint(SpringLayout.WEST, imageLabel, 25, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, imageLabel, 25, SpringLayout.NORTH, functionPanel);
        layout.putConstraint(SpringLayout.EAST, imageLabel, 135, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, imageLabel, 135, SpringLayout.NORTH, functionPanel);

        layout.putConstraint(SpringLayout.WEST, userlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, userlistButton, 180, SpringLayout.NORTH, functionPanel);
        layout.putConstraint(SpringLayout.EAST, userlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, userlistButton, 220, SpringLayout.NORTH, functionPanel);

        layout.putConstraint(SpringLayout.WEST, productlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, productlistButton, 50, SpringLayout.NORTH, userlistButton);
        layout.putConstraint(SpringLayout.EAST, productlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, productlistButton, 50, SpringLayout.SOUTH, userlistButton);

        layout.putConstraint(SpringLayout.WEST, orderlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, orderlistButton, 50, SpringLayout.NORTH, productlistButton);
        layout.putConstraint(SpringLayout.EAST, orderlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, orderlistButton, 50, SpringLayout.SOUTH, productlistButton);

        layout.putConstraint(SpringLayout.WEST, feedbacklistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbacklistButton, 50, SpringLayout.NORTH, orderlistButton);
        layout.putConstraint(SpringLayout.EAST, feedbacklistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, feedbacklistButton, 50, SpringLayout.SOUTH,orderlistButton);

        layout.putConstraint(SpringLayout.WEST, commentlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, commentlistButton, 50, SpringLayout.NORTH, feedbacklistButton);
        layout.putConstraint(SpringLayout.EAST, commentlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, commentlistButton, 50, SpringLayout.SOUTH, feedbacklistButton);

        layout.putConstraint(SpringLayout.WEST, consumptionlistButton, 0, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.NORTH, consumptionlistButton, 50, SpringLayout.NORTH, commentlistButton);
        layout.putConstraint(SpringLayout.EAST, consumptionlistButton, 160, SpringLayout.WEST, functionPanel);
        layout.putConstraint(SpringLayout.SOUTH, consumptionlistButton, 50, SpringLayout.SOUTH, commentlistButton);


        functionPanel.add(imageLabel);
        functionPanel.add(userlistButton);
        functionPanel.add(productlistButton);
        functionPanel.add(orderlistButton);
        functionPanel.add(feedbacklistButton);
        functionPanel.add(consumptionlistButton);
        functionPanel.add(commentlistButton);
    }

    private Object[][] getUserData(User user) {
        Object[][] data = new Object[userDao.getUsers(user).size()][9];
        if(userDao.getUsers(user).isEmpty())
            return null;
        for (int i = 0; i < userDao.getUsers(user).size(); i++) {
            data[i][0] = userDao.getUsers(user).get(i).getId();
            data[i][1] = userDao.getUsers(user).get(i).getUsername();
            data[i][2] = switch(userDao.getUsers(user).get(i).getrole()){
                case 1 -> "用户";
                case 2 -> "员工";
                case 3 -> "管理员";
                default -> "未知";
            };
            data[i][3] = userDao.getUsers(user).get(i).getUserpassword();
            data[i][4] = userDao.getUsers(user).get(i).getAge();
            data[i][5] = userDao.getUsers(user).get(i).getSex();
            data[i][6] = userDao.getUsers(user).get(i).getPhone();
            data[i][7] = userDao.getUsers(user).get(i).getAddress();
            data[i][8] = userDao.getUsers(user).get(i).getCreditrating();
        }
        return data;
    }

    public static void main(String[] args)
    {
//        LoginJFrame.currentUser = new User(4,3,"admin","123456",18,"男","北京","12345678901",100);
//
//        new AdminUserListJFrame();
        new LoginJFrame();
    }

}
