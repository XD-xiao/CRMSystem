package MyJFrame.Employee;

import MyJFrame.Admin.AdminOrderJFrame;
import MyJFrame.LoginJFrame;
import MyJFrame.User.UserProductJFrame;
import MyJFrame.UserJFrame;
import dao.OrderDao;
import dao.ProductDao;
import dao.UserDao;
import dao.impl.OrderDaoImpl;
import dao.impl.ProductDaoImpl;
import dao.impl.UserDaoImpl;
import domain.Order;
import domain.Product;
import domain.User;
import util.InputValidator;
import util.PopupUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeOrderJFrame extends JFrame{

    OrderDao orderDao = new OrderDaoImpl();
    UserDao userDao = new UserDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
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

    public EmployeeOrderJFrame()  {
        super("EmployeeOrderJFrame");
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
                orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量", "用户" , "应付金额","状态"}));
            }
        });
        searchOrder.setStaffid(LoginJFrame.currentUser.getId());
        DefaultTableModel productmodel = new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量", "用户" , "应付金额","状态"});
        orderTable = new JTable(productmodel);
        //表格不可以编辑
        orderTable.setDefaultEditor(Object.class, null);
        //每次只能选择一行
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.setRowHeight(20);
        JScrollPane productPane = new JScrollPane(orderTable);
        productPane.setPreferredSize(new Dimension(900, 500));
        //表格选择的内容
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if(orderTable.getSelectedRow() == -1)
                return;
            currentOrder.setId( Integer.parseInt(orderTable.getValueAt(orderTable.getSelectedRow(), 0).toString()));
            currentOrder = orderDao.getOrderByid(currentOrder.getId());

            orderidLabel.setText("订单ID:" + currentOrder.getId());
            orderNumberText.setText(currentOrder.getBuynumber().toString());
            orderTotalText.setText(currentOrder.getTotalamount().toString());

            if(currentOrder.getStatus() == 3){
                updateOrderButton.setEnabled(false);
            }
            else{
                updateOrderButton.setEnabled(true);
            }

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
                        orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量", "用户" , "应付金额","状态"}));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "订单更新失败");
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
        layout.putConstraint(SpringLayout.WEST,chooseTypeJComboBox, 20, SpringLayout.EAST, chooseTypeJLabel);
        layout.putConstraint(SpringLayout.NORTH,chooseTypeJComboBox, 40, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST,searchJButton, 180, SpringLayout.EAST, chooseTypeJComboBox);
        layout.putConstraint(SpringLayout.NORTH,searchJButton, 42, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, productPane, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productPane, 80, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.EAST, productPane, 1000, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, productPane, 360, SpringLayout.NORTH, showPanel);

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


        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeJComboBox);
        showPanel.add(searchJButton);
        showPanel.add(productPane);
        showPanel.add(orderidLabel);
        showPanel.add(orderNumberLabel);
        showPanel.add(orderNumberText);
        showPanel.add(orderTotalLabel);
        showPanel.add(orderTotalText);
        showPanel.add(updateOrderButton);

    }

    public void funComponentIni()               //功能栏
    {
        functionPanel.setBackground(Color.LIGHT_GRAY);


        Image imageIcon = new ImageIcon("Img/login_employee.jpg").getImage() ;
        JLabel imageLabel = new JLabel(new ImageIcon(imageIcon));

        JButton productlistButton = new JButton("产品中心");
        JButton orderlistButton = new JButton("订单中心");
        JButton feedbacklistButton = new JButton("反馈记录");
        JButton consumptionlistButton = new JButton("个人信息");

        productlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeProductJFrame();
                dispose();
            }
        });
        feedbacklistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new EmployeeFeedbackJFrame();
                dispose();
            }
        });
        consumptionlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserJFrame();
                dispose();
            }
        });
        //按钮透明
        productlistButton.setBackground(Color.LIGHT_GRAY);
        //无边框
        productlistButton.setBorderPainted(false);
        orderlistButton.setBackground(Color.LIGHT_GRAY);
        orderlistButton.setBorderPainted(false);
        orderlistButton.setEnabled(false);
        feedbacklistButton.setBackground(Color.LIGHT_GRAY);
        feedbacklistButton.setBorderPainted(false);
        consumptionlistButton.setBackground(Color.LIGHT_GRAY);
        consumptionlistButton.setBorderPainted(false);
        //consumptionlistButton.setEnabled(false);


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


    private Object[][] getOrderData(Order order){
        Object[][] data = new Object[orderDao.getOrders(order).size()][6];
        if(orderDao.getOrders(order).isEmpty())
            return null;
        for (int i = 0; i < orderDao.getOrders(order).size(); i++) {
            data[i][0] = orderDao.getOrders(order).get(i).getId();
            data[i][1] = orderDao.getOrders(order).get(i).getProductname();
            data[i][2] = orderDao.getOrders(order).get(i).getBuynumber();
            User user = userDao.getUserById(orderDao.getOrders(order).get(i).getUserid());
            data[i][3] = user.getUsername();
            data[i][4] = orderDao.getOrders(order).get(i).getTotalamount();
            data[i][5] = switch (orderDao.getOrders(order).get(i).getStatus()){
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
        new LoginJFrame();
    }
}
