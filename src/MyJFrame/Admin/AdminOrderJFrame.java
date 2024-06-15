package MyJFrame.Admin;

import MyJFrame.AdminJFrame;
import MyJFrame.LoginJFrame;
import MyJFrame.User.UserOrderJFrame;
import MyJFrame.User.UserProductJFrame;
import MyJFrame.UserJFrame;
import dao.*;
import dao.impl.*;
import domain.Order;
import domain.Product;
import domain.User;
import util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminOrderJFrame extends JFrame{
    OrderDao orderDao = new OrderDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
    UserDao userDao = new UserDaoImpl();
    FeedbackDao feedbackDao = new FeedbackDaoImpl();

    public static String[] statusArray = {"全部","未完成", "反馈中","已完成"};
    SpringLayout layout = new SpringLayout();

    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);


    Order searchOrder = new Order();
    Order currentOrder = new Order();
    JTable orderTable = new JTable();

    JLabel orderidLabel = new JLabel("订单ID:");

    //数量
    JLabel orderNumberLabel = new JLabel("购买数量:");
    JTextField orderNumberText = new JTextField(5);
    //总金额
    JLabel orderTotalLabel = new JLabel("总金额:");
    JTextField orderTotalText = new JTextField(8);

    JButton updateOrderButton = new JButton("确认修改");
    JButton deleteOrderButton = new JButton("删除订单");

    public AdminOrderJFrame()  {
        super("AdminOrderJFrame");
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
        showPanel.add(new JLabel(" >>订单中心"));



        JLabel searchJLabel = new JLabel("搜索：");
        searchJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JTextField searchJTextField = new JTextField(15);
        searchJTextField.setPreferredSize(new Dimension(80, 30));

        JLabel chooseTypeJLabel = new JLabel("类型：");
        chooseTypeJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JComboBox chooseTypeJComboBox = new JComboBox(AdminOrderJFrame.statusArray);
        chooseTypeJComboBox.setPreferredSize(new Dimension(90, 30));

        JButton searchJButton = new JButton("搜索");
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取
                searchOrder.setProductname(searchJTextField.getText());
                searchOrder.setStatus( chooseTypeJComboBox.getSelectedIndex() );
                if(chooseTypeJComboBox.getSelectedItem().equals("全部"))
                    searchOrder.setStatus(null);
                if(searchJTextField.getText().equals(""))
                    searchOrder.setProductname(null);
                //更新表格
                orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量","员工", "用户" , "应付金额","时间","状态"}));
            }
        });

        DefaultTableModel productmodel = new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量","员工", "用户" , "应付金额","时间","状态"});
        orderTable = new JTable(productmodel);
        //表格不可以编辑
        orderTable.setDefaultEditor(Object.class, null);
        //每次只能选择一行
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.setRowHeight(20);
        JScrollPane orderPane = new JScrollPane(orderTable);
        orderPane.setPreferredSize(new Dimension(900, 500));
        //表格选择的内容
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if(orderTable.getSelectedRow() == -1)
                return;
            currentOrder.setId( Integer.parseInt(orderTable.getValueAt(orderTable.getSelectedRow(), 0).toString()));
            currentOrder = orderDao.getOrderByid(currentOrder.getId());

            orderidLabel.setText("订单ID:" + currentOrder.getId());
            orderNumberText.setText(currentOrder.getBuynumber().toString());
            orderTotalText.setText(currentOrder.getTotalamount().toString());

        });

        updateOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = new Product();
                product = productDao.getProductByid(currentOrder.getProductid());
                if(!InputValidator.isValidProductTotal(orderNumberText.getText())){
                    JOptionPane.showMessageDialog(null, "产品数量格式错误");
                }
                else if(!InputValidator.isValidProductPrice(orderTotalText.getText())){
                    JOptionPane.showMessageDialog(null, "产品价格格式错误");
                }
                else if( Integer.parseInt(orderNumberText.getText()) > (product.getTotal() + currentOrder.getBuynumber())){
                    JOptionPane.showMessageDialog(null, "产品数量不足");
                }
                else{
                    Order order = orderDao.getOrderByid(currentOrder.getId());
                    order.setBuynumber(Integer.parseInt(orderNumberText.getText()));
                    order.setTotalamount(Integer.parseInt(orderTotalText.getText()));
                    if(orderDao.updateOrder(order) == 1){
                        JOptionPane.showMessageDialog(null, "订单更新成功");
                        orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量","员工", "用户" , "应付金额","时间","状态"}));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "订单更新失败");
                    }
                }
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int orderresult = orderDao.deleteOrder(currentOrder);
                int feedbackresult = feedbackDao.deleteFeedbackByOrderid( currentOrder.getId());
                if( orderresult == 1 && feedbackresult ==1 ){
                    JOptionPane.showMessageDialog(null, "删除成功");
                }
                orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量","员工", "用户" , "应付金额","时间","状态"}));

            }
        });



        layout.putConstraint(SpringLayout.WEST,searchJLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH,searchJLabel, 40, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST,searchJTextField, 20, SpringLayout.EAST, searchJLabel);
        layout.putConstraint(SpringLayout.NORTH,searchJTextField, 40, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST,chooseTypeJLabel, 100, SpringLayout.EAST, searchJTextField);
        layout.putConstraint(SpringLayout.NORTH,chooseTypeJLabel, 40, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST,chooseTypeJComboBox, 20, SpringLayout.EAST, chooseTypeJLabel);
        layout.putConstraint(SpringLayout.NORTH,chooseTypeJComboBox, 40, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST,searchJButton, 180, SpringLayout.EAST, chooseTypeJComboBox);
        layout.putConstraint(SpringLayout.NORTH,searchJButton, 42, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, orderPane, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, orderPane, 80, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.EAST, orderPane, 1000, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, orderPane, 360, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, orderidLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, orderidLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, orderNumberLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, orderNumberLabel, 20, SpringLayout.SOUTH, orderidLabel);

        layout.putConstraint(SpringLayout.WEST, orderNumberText, 20, SpringLayout.EAST, orderNumberLabel);
        layout.putConstraint(SpringLayout.NORTH, orderNumberText, 20, SpringLayout.SOUTH, orderidLabel);

        layout.putConstraint(SpringLayout.WEST, orderTotalLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, orderTotalLabel, 20, SpringLayout.SOUTH, orderNumberLabel);

        layout.putConstraint(SpringLayout.WEST, orderTotalText, 20, SpringLayout.EAST, orderTotalLabel);
        layout.putConstraint(SpringLayout.NORTH, orderTotalText, 20, SpringLayout.SOUTH, orderNumberLabel);

        layout.putConstraint(SpringLayout.WEST, updateOrderButton, 550, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, updateOrderButton, 450, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, deleteOrderButton, 50, SpringLayout.EAST, updateOrderButton);
        layout.putConstraint(SpringLayout.NORTH, deleteOrderButton, 450, SpringLayout.NORTH, showPanel);


        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeJComboBox);
        showPanel.add(searchJButton);
        showPanel.add(orderPane);
        showPanel.add(orderidLabel);
        showPanel.add(orderNumberLabel);
        showPanel.add(orderNumberText);
        showPanel.add(orderTotalLabel);
        showPanel.add(orderTotalText);
        showPanel.add(updateOrderButton);
        showPanel.add(deleteOrderButton);

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

        userlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminUserListJFrame();
                dispose();
            }
        });
        productlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminProductListJFrame();
                dispose();
            }
        });
//        orderlistButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new AdminOrderJFrame();
//                dispose();
//            }
//        });
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

        orderlistButton.setEnabled(false);

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

    private Object[][] getOrderData(Order order){
        Object[][] data = new Object[orderDao.getOrders(order).size()][8];
        if(orderDao.getOrders(order).isEmpty())
            return null;
        for (int i = 0; i < orderDao.getOrders(order).size(); i++) {
            data[i][0] = orderDao.getOrders(order).get(i).getId();
            data[i][1] = orderDao.getOrders(order).get(i).getProductname();
            data[i][2] = orderDao.getOrders(order).get(i).getBuynumber();
            User staff = userDao.getUserById(orderDao.getOrders(order).get(i).getStaffid());
            data[i][3] = staff.getUsername();
            User user = userDao.getUserById(orderDao.getOrders(order).get(i).getUserid());
            data[i][4] = user.getUsername();
            data[i][5] = orderDao.getOrders(order).get(i).getTotalamount();
            data[i][6] = orderDao.getOrders(order).get(i).getPurchasedate();
            data[i][7] = switch (orderDao.getOrders(order).get(i).getStatus()){
                case 1 -> "未完成";
                case 2 -> "反馈中";
                case 3 -> "已完成";
                default -> "未知";
            };
        }
        return data;
    }

    public static void main(String[] args)
    {
//        LoginJFrame.currentUser = new User( 1,1,"admin","admin",18,"男","北京","12345678901",10);
          new LoginJFrame();
//        new AdminOrderJFrame();
    }
}
