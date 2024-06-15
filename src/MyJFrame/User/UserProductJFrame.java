package MyJFrame.User;

import MyJFrame.Admin.AdminProductListJFrame;
import MyJFrame.LoginJFrame;
import MyJFrame.UserJFrame;
import dao.CommentDao;
import dao.OrderDao;
import dao.ProductDao;
import dao.UserDao;
import dao.impl.CommentDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.ProductDaoImpl;
import dao.impl.UserDaoImpl;
import domain.Comment;
import domain.Order;
import domain.Product;
import domain.User;
import util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;


public class UserProductJFrame extends JFrame {

    private String[] typeArray = AdminProductListJFrame.typeArray;
    ProductDao productDao = new ProductDaoImpl();
    UserDao userDao = new UserDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    CommentDao commentDao = new CommentDaoImpl();

    Product currentProduct = new Product();
    Product searchProduct = new Product();
    Comment comment = new Comment();

    SpringLayout layout = new SpringLayout();
    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);

    JTable productTable;

    JButton introductionJButton = new JButton("简介");
    JButton commentJButton = new JButton("评论");
    JLabel introductionJLabel = new JLabel("-----简介-------");
    //JLabel commentJLabel = new JLabel("-----评论-----");

    JLabel productNameJLabel = new JLabel("产品名称:" );                 ///////////////////
    JLabel productNumJLabel = new JLabel("购买数量:");
    JTextField productNumJTextField = new JTextField(3);
    JLabel TotalJLabel = new JLabel();
    JButton buyJButton = new JButton("下单");

    JTable commentTable;

    public UserProductJFrame()  {

        setTitle("UserProductJFrame");

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
        JLabel label = new JLabel(" >>产品中心");

        JLabel searchJLabel = new JLabel("搜索：");
        searchJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JTextField searchJTextField = new JTextField(15);
        searchJTextField.setPreferredSize(new Dimension(80, 30));

        JLabel chooseTypeJLabel = new JLabel("类型：");
        chooseTypeJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JComboBox chooseTypeJComboBox = new JComboBox(typeArray);
        chooseTypeJComboBox.setPreferredSize(new Dimension(90, 30));

        JButton searchJButton = new JButton("搜索");
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取
                searchProduct.setName(searchJTextField.getText());
                searchProduct.setType((String) chooseTypeJComboBox.getSelectedItem());
                if(chooseTypeJComboBox.getSelectedItem().equals("全部"))
                    searchProduct.setType(null);
                if(searchJTextField.getText().equals(""))
                    searchProduct.setName(null);
                //更新表格
                productTable.setModel(new DefaultTableModel(getProductData(searchProduct), new String[]{"编号","名称", "数量", "单价" , "类型","员工","上架时间"}));

            }
        });

        DefaultTableModel productmodel = new DefaultTableModel(getProductData(new Product()), new String[]{"编号","名称", "数量", "单价" , "类型","员工","上架时间"});
        productTable = new JTable(productmodel);
        //表格不可以编辑
        productTable.setDefaultEditor(Object.class, null);
        //每次只能选择一行
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(20);
        JScrollPane productPane = new JScrollPane(productTable);
        productPane.setPreferredSize(new Dimension(900, 500));
        //表格选择的内容
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if(productTable.getSelectedRow() == -1)
                return;
            currentProduct.setId( Integer.parseInt(productTable.getValueAt(productTable.getSelectedRow(), 0).toString()));
            currentProduct.setName(productTable.getValueAt(productTable.getSelectedRow(), 1).toString());
            currentProduct.setTotal(Integer.parseInt(productTable.getValueAt(productTable.getSelectedRow(), 2).toString()));
            introductionJLabel.setText(productDao.getProductByid(currentProduct.getId()).getText());

            comment.setProductid(currentProduct.getId());
            commentTable.setModel(new DefaultTableModel(getCommentData(comment),  new String[]{"名字","评分", "内容"}));
            commentTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            commentTable.getColumnModel().getColumn(1).setPreferredWidth(3);

            productNameJLabel.setText("名称:" + currentProduct.getName());
            TotalJLabel.setText("/" + currentProduct.getTotal());
            productNumJTextField.setText("");

        });

        //显示边框
        introductionJButton.setEnabled(false);
        introductionJLabel.setVisible(true);
        introductionJLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        DefaultTableModel commentmodel = new DefaultTableModel(null, new String[]{"名字","评分", "内容"});
        commentTable = new JTable(commentmodel);
        //表格不可以编辑
        commentTable.setDefaultEditor(Object.class, null);
        commentTable.setRowHeight(15);
        //设置 列宽度
        commentTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        commentTable.getColumnModel().getColumn(1).setPreferredWidth(3);
        JScrollPane commentPane = new JScrollPane(commentTable);
        commentPane.setPreferredSize(new Dimension(360, 100));

        commentJButton.setEnabled(true);
        commentPane.setVisible(false);


        introductionJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //显示简介、屏蔽评论
                commentJButton.setEnabled(true);
                commentPane.setVisible(false);
                introductionJButton.setEnabled(false);
                introductionJLabel.setVisible(true);

            }
        });
        commentJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commentJButton.setEnabled(false);
                commentPane.setVisible(true);
                introductionJButton.setEnabled(true);
                introductionJLabel.setVisible(false);
            }
        });

        buyJButton.setBackground(Color.pink);
        //购买
        buyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!InputValidator.isValidProductTotal(productNumJTextField.getText())){
                    JOptionPane.showMessageDialog(null, "数量格式不正确");
                    return;
                }
                int buynum = Integer.parseInt(productNumJTextField.getText());
                if(currentProduct.getTotal() - buynum < 0)
                    JOptionPane.showMessageDialog(null, "库存不足");
                else {
                    currentProduct = productDao.getProductByid(currentProduct.getId());       //获取原始数据
                    currentProduct.setTotal(currentProduct.getTotal() - buynum);
                    productDao.updateProduct(currentProduct);                          //更新库存
                    productTable.setModel(new DefaultTableModel(getProductData(searchProduct), new String[]{"编号","名称", "数量", "单价" , "类型","员工","上架时间"}));

                    Order order = new Order();
                    order.setUserid(LoginJFrame.currentUser.getId());
                    order.setProductid(currentProduct.getId());
                    order.setBuynumber(buynum);
                    order.setStaffid(currentProduct.getStaffid());
                    order.setTotalamount(currentProduct.getPrice() * buynum);
                    order.setPurchasedate(LocalDate.now().toString());
                    order.setStatus(1);     //1表示未完成    2表示已完成  3表示反馈中
                    order.setProductname(currentProduct.getName());

                    orderDao.addOrder(order);
                    //System.out.println(currentProduct);
                }
            }
        });
        // 定位
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

        layout.putConstraint(SpringLayout.WEST, introductionJButton, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, introductionJButton, 400, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST, commentJButton, 20, SpringLayout.EAST, introductionJButton);
        layout.putConstraint(SpringLayout.NORTH, commentJButton, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, introductionJLabel, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, introductionJLabel, 40, SpringLayout.NORTH, introductionJButton);
        layout.putConstraint(SpringLayout.EAST, introductionJLabel, 600, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, introductionJLabel, 140, SpringLayout.SOUTH, introductionJButton);

        layout.putConstraint(SpringLayout.WEST, commentPane, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, commentPane, 40, SpringLayout.NORTH, introductionJButton);
        layout.putConstraint(SpringLayout.EAST, commentPane ,600, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, commentPane, 140, SpringLayout.SOUTH, introductionJButton);

        layout.putConstraint(SpringLayout.WEST, productNameJLabel, 740, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productNameJLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, productNumJLabel, 740, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productNumJLabel, 10, SpringLayout.SOUTH, productNameJLabel);

        layout.putConstraint(SpringLayout.WEST, productNumJTextField, 20, SpringLayout.EAST, productNumJLabel);
        layout.putConstraint(SpringLayout.NORTH, productNumJTextField, 10, SpringLayout.SOUTH, productNameJLabel);

        layout.putConstraint(SpringLayout.WEST, TotalJLabel, 5, SpringLayout.EAST, productNumJTextField);
        layout.putConstraint(SpringLayout.NORTH, TotalJLabel, 10, SpringLayout.SOUTH, productNameJLabel);

        layout.putConstraint(SpringLayout.WEST, buyJButton, 740, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, buyJButton, 30, SpringLayout.SOUTH, productNumJLabel);
        layout.putConstraint(SpringLayout.EAST, buyJButton, 920, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, buyJButton, 100, SpringLayout.SOUTH, productNumJLabel);

        layout.putConstraint(SpringLayout.WEST, label, 160, SpringLayout.WEST, showPanel);

        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeJComboBox);
        showPanel.add(searchJButton);
        showPanel.add(productPane);
        showPanel.add(introductionJButton);
        showPanel.add(commentJButton);
        showPanel.add(introductionJLabel);
        showPanel.add(commentPane);
        showPanel.add(productNameJLabel);
        showPanel.add(productNumJLabel);
        showPanel.add(productNumJTextField);
        showPanel.add(TotalJLabel);
        showPanel.add(buyJButton);

        showPanel.add(label);

    }

    private Object[][] getCommentData(Comment comment) {
        Object[][] data = new Object[commentDao.getComments(comment).size()][3];
        for (int i = 0; i < commentDao.getComments(comment).size(); i++) {
            User user = userDao.getUserById(commentDao.getComments(comment).get(i).getUserid());
            data[i][0] = user.getUsername();
            data[i][1] = commentDao.getComments(comment).get(i).getEvaluate();
            data[i][2] = commentDao.getComments(comment).get(i).getText();
        }
        return data;
    }

    private Object[][] getProductData(Product product) {
        Object[][] data = new Object[productDao.getProducts(product).size()][7];
        if(productDao.getProducts(product).isEmpty())
            return null;
        for (int i = 0; i < productDao.getProducts(product).size(); i++) {
            data[i][0] = productDao.getProducts(product).get(i).getId();
            data[i][1] = productDao.getProducts(product).get(i).getName();
            data[i][2] = productDao.getProducts(product).get(i).getTotal();
            data[i][3] = productDao.getProducts(product).get(i).getPrice();
            data[i][4] = productDao.getProducts(product).get(i).getType();
            User user = userDao.getUserById(productDao.getProducts(product).get(i).getStaffid());
            data[i][5] = user.getUsername();
            data[i][6] = productDao.getProducts(product).get(i).getProductiondata();
        }
        return data;
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


        orderlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserOrderJFrame();
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
        productlistButton.setEnabled(false);
        orderlistButton.setBackground(Color.LIGHT_GRAY);
        orderlistButton.setBorderPainted(false);
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
    public static void main(String[] args) {
        LoginJFrame.currentUser = new User(4,1,"admin","123456",18,"男","北京","12345678901",100);
        new UserProductJFrame();
    }

}
