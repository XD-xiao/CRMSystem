package MyJFrame.User;

import MyJFrame.Admin.AdminOrderJFrame;
import MyJFrame.LoginJFrame;
import MyJFrame.UserJFrame;
import dao.CommentDao;
import dao.FeedbackDao;
import dao.OrderDao;
import dao.UserDao;
import dao.impl.FeedbackDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.CommentDaoImpl;
import dao.impl.UserDaoImpl;
import domain.*;
import util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class UserOrderJFrame extends JFrame{

    OrderDao orderDao = new OrderDaoImpl();
    UserDao userDao = new UserDaoImpl();
    FeedbackDao feedbackDao = new FeedbackDaoImpl();
    CommentDao commentDao = new CommentDaoImpl();
    SpringLayout layout = new SpringLayout();

    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);

    Order searchOrder = new Order();
    Order currentOrder = new Order();
    JTable orderTable = new JTable();

    //订单ID
    JLabel orderIDLabel = new JLabel("订单ID:");
    //订单金额
    JLabel orderPriceLabel = new JLabel("订单金额:");
    //完成订单
    JButton finishOrderButton = new JButton("完成订单");

    //反馈
    JLabel feedbackLabel = new JLabel("反馈:");
    //反馈文本
    JTextField feedbackText = new JTextField(20);
    //提交反馈
    JButton feedbackButton = new JButton("提交反馈");

    //写评价
    JLabel writeCommentLabel = new JLabel("写评价:");
    JTextField writeCommentText = new JTextField(20);
    JLabel writeCommentScoreLabel = new JLabel("评分:");
    JTextField writeCommentScore = new JTextField(5);
    JButton writeCommentButton = new JButton("提交评价");



    public UserOrderJFrame()  {
        super("UserOrderJFrame");
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

        finishOrderButton.setEnabled(false);
        feedbackButton.setEnabled(false);
        writeCommentButton.setEnabled(false);

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
                orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量", "员工" , "应付金额","状态"}));
            }
        });
        searchOrder.setUserid(LoginJFrame.currentUser.getId());
        DefaultTableModel productmodel = new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量", "员工" , "应付金额","状态"});
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

            orderIDLabel.setText("订单ID:" + currentOrder.getId());
            orderPriceLabel.setText("订单金额:" + currentOrder.getTotalamount());


            if( currentOrder.getStatus() == 1)      //未完成
            {
                finishOrderButton.setEnabled(true);
                feedbackButton.setEnabled(true);
            }
            else if( currentOrder.getStatus() == 2)  //反馈中
            {
                finishOrderButton.setEnabled(true);
                feedbackButton.setEnabled(false);
            }
            else{
                finishOrderButton.setEnabled(false);
                feedbackButton.setEnabled(false);
            }

            Comment comment = commentDao.getCommentByuseridproductid(currentOrder.getUserid(), currentOrder.getProductid()) ;

            if( comment != null)      //有评价
            {
                writeCommentButton.setEnabled(false);
                writeCommentText.setText(comment.getText());
                writeCommentScore.setText(comment.getEvaluate().toString());
            }
            else        //没有评价
            {
                writeCommentButton.setEnabled(true);
                writeCommentText.setText("");
                writeCommentScore.setText("");
            }

        });

        finishOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentOrder);
                currentOrder.setStatus(3);

                orderDao.updateOrder(currentOrder);
                orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量", "员工" , "应付金额","状态"}));
            }
        });

        feedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !InputValidator.isValidFeedbackText(feedbackText.getText())){
                    JOptionPane.showMessageDialog(null, "反馈内容格式错误");
                    return;
                }
                if ( feedbackDao.getFeedbackByOrderidUserid(currentOrder.getId(), currentOrder.getUserid()) != null ){
                    JOptionPane.showMessageDialog(null, "请不要重复反馈");
                    return;
                }
                Feedback feedback = new Feedback();
                feedback.setUserid(LoginJFrame.currentUser.getId());
                feedback.setStaffid(currentOrder.getStaffid());
                feedback.setOrderlistid(currentOrder.getId());
                feedback.setFeedbackdate(LocalDate.now().toString());
                feedback.setText(feedbackText.getText());
                feedback.setStatus(1);
                feedback.setProductname(currentOrder.getProductname());

                if(feedbackDao.addFeedback(feedback) == 1){
                    currentOrder.setStatus(2);
                    orderDao.updateOrder(currentOrder);
                    orderTable.setModel(new DefaultTableModel(getOrderData(searchOrder), new String[]{"编号","名称", "数量", "员工" , "应付金额","状态"}));
                }

                //JOptionPane.showMessageDialog(null, "反馈失败");

            }
        });

        writeCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !InputValidator.isValidCommentText(writeCommentText.getText())){
                    JOptionPane.showMessageDialog(null, "评价内容格式错误");
                }
                else if( !InputValidator.isValidComentScore(writeCommentScore.getText())){
                    JOptionPane.showMessageDialog(null, "评分内容格式错误");
                }
                else{
                    Comment comment = new Comment();
                    comment.setUserid(LoginJFrame.currentUser.getId());
                    comment.setProductid(currentOrder.getProductid());
                    comment.setText(writeCommentText.getText());
                    comment.setCommentdate(LocalDate.now().toString());
                    comment.setEvaluate(Integer.parseInt(writeCommentScore.getText()));
                    if(commentDao.addComment(comment) == 1){
                        writeCommentButton.setEnabled(false);
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

        layout.putConstraint(SpringLayout.WEST, orderIDLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, orderIDLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST , orderPriceLabel , 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, orderPriceLabel, 20, SpringLayout.SOUTH, orderIDLabel);

        layout.putConstraint(SpringLayout.WEST, finishOrderButton, 550, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, finishOrderButton, 405, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, feedbackLabel , 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackLabel, 50, SpringLayout.SOUTH, orderPriceLabel);

        layout.putConstraint(SpringLayout.WEST, feedbackText, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackText, 20, SpringLayout.SOUTH, feedbackLabel);

        layout.putConstraint(SpringLayout.WEST, feedbackButton, 550, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackButton, 80, SpringLayout.SOUTH, finishOrderButton);

        layout.putConstraint(SpringLayout.WEST,writeCommentLabel , 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH,writeCommentLabel, 405, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST,writeCommentText, 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH,writeCommentText, 20, SpringLayout.SOUTH, writeCommentLabel);

        layout.putConstraint(SpringLayout.WEST,writeCommentScoreLabel, 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH,writeCommentScoreLabel, 20, SpringLayout.SOUTH, writeCommentText);

        layout.putConstraint(SpringLayout.WEST,writeCommentScore, 20, SpringLayout.EAST, writeCommentScoreLabel);
        layout.putConstraint(SpringLayout.NORTH,writeCommentScore, 20, SpringLayout.SOUTH, writeCommentText);

        layout.putConstraint(SpringLayout.WEST,writeCommentButton, 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH,writeCommentButton, 20, SpringLayout.SOUTH, writeCommentScoreLabel);

        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeJComboBox);
        showPanel.add(searchJButton);
        showPanel.add(productPane);
        showPanel.add(orderIDLabel);
        showPanel.add(orderPriceLabel);
        showPanel.add(finishOrderButton);
        showPanel.add(feedbackLabel);
        showPanel.add(feedbackText);
        showPanel.add(feedbackButton);
        showPanel.add(writeCommentLabel);
        showPanel.add(writeCommentText);
        showPanel.add(writeCommentScoreLabel);
        showPanel.add(writeCommentScore);
        showPanel.add(writeCommentButton);


    }

    public void funComponentIni()               //功能栏
    {
        functionPanel.setBackground(Color.LIGHT_GRAY);

        Image imageIcon = new ImageIcon("Img/login_customer.jpg").getImage() ;
        JLabel imageLabel = new JLabel(new ImageIcon(imageIcon));

        JButton productlistButton = new JButton("产品中心");
        JButton orderlistButton = new JButton("订单中心");
        JButton feedbacklistButton = new JButton("反馈记录");
        JButton consumptionlistButton = new JButton("个人信息");

        productlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserProductJFrame();
                dispose();
            }
        });
        feedbacklistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserFeedbackJFrame();
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
            User staff = userDao.getUserById(orderDao.getOrders(order).get(i).getStaffid());
            data[i][3] = staff.getUsername();
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
        //LoginJFrame.currentUser = new User( 1,1,"admin","admin",18,"男","北京","12345678901",10);
        new LoginJFrame();
        //new UserOrderJFrame();
    }
}
