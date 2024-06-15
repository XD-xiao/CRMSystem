package MyJFrame.Admin;

import MyJFrame.AdminJFrame;
import MyJFrame.Employee.EmployeeFeedbackJFrame;
import MyJFrame.Employee.EmployeeOrderJFrame;
import MyJFrame.LoginJFrame;
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
import java.util.Arrays;

public class AdminProductListJFrame extends JFrame{
    public static String[] typeArray = {"全部","图书", "数码", "服装", "食品","水果", "其他"};
    ProductDao productDao = new ProductDaoImpl();
    UserDao userDao = new UserDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    FeedbackDao feedbackDao = new FeedbackDaoImpl();
    CommentDao commentDao = new CommentDaoImpl();
    Product currentProduct = new Product();
    Product searchProduct = new Product();
    SpringLayout layout = new SpringLayout();
    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);
    JTable productTable;

    JLabel productIdLabel = new JLabel("商品ID:");
    JLabel productNameLabel = new JLabel("商品名称:");
    JTextField productNameTextField = new JTextField(10);
    JLabel productTypeLabel = new JLabel("商品类型:");
    JComboBox productTypeComboBox = new JComboBox(Arrays.copyOfRange(typeArray, 1, typeArray.length));
    JLabel productPriceLabel = new JLabel("商品价格:");
    JTextField productPriceTextField = new JTextField(10);
    JLabel productStockLabel = new JLabel("商品库存:");
    JTextField productStockTextField = new JTextField(10);
    JLabel productTextLabel = new JLabel("商品简介:");
    JTextArea productTextArea = new JTextArea(3, 30);

    //JButton addProductButton = new JButton("创建商品");
    JButton updateProductButton = new JButton("修改商品");
    JButton deleteProductButton = new JButton("删除商品");

    public AdminProductListJFrame(){
        setTitle("AdminProductJFrame");

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

    private void showComponentIni() {
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
        deleteProductButton.setEnabled(false);
        updateProductButton.setEnabled(false);

        DefaultTableModel productmodel = new DefaultTableModel(getProductData(searchProduct), new String[]{"编号","名称", "数量", "单价" , "类型","员工","上架时间"});
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

            deleteProductButton.setEnabled(true);
            updateProductButton.setEnabled(true);
            currentProduct = productDao.getProductByid(currentProduct.getId());
            productNameTextField.setText(currentProduct.getName());
            Integer price = currentProduct.getPrice();
            Integer total = currentProduct.getTotal();
            productPriceTextField.setText(price.toString());
            productTypeComboBox.setSelectedItem(currentProduct.getType());
            productStockTextField.setText(total.toString());
            productTextArea.setText(currentProduct.getText());
            productIdLabel.setText("商品ID:" + currentProduct.getId().toString());
        });

        //显示边框
        productTextArea.setBorder(BorderFactory.createLineBorder(Color.black));




        updateProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!InputValidator.isValidProductName(productNameTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的商品名称");
                }
                else if(!InputValidator.isValidProductPrice(productPriceTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的商品价格");
                }
                else if(!InputValidator.isValidProductTotal(productStockTextField.getText()) ){
                    JOptionPane.showMessageDialog(null, "请输入正确的商品数量");
                }
                else if(!InputValidator.isValid(productTextArea.getText()) ){
                    JOptionPane.showMessageDialog( null, "请输入正确的商品描述");
                }
                else{
                    Product product = new Product();
                    product.setId(currentProduct.getId());
                    product.setName(productNameTextField.getText());
                    product.setPrice(Integer.parseInt(productPriceTextField.getText()));
                    product.setTotal(Integer.parseInt(productStockTextField.getText()));
                    product.setStaffid(LoginJFrame.currentUser.getId());
                    product.setType(productTypeComboBox.getSelectedItem().toString());
                    product.setText(productTextArea.getText());
                    product.setProductiondata(LocalDate.now().toString());
                    product.setImgurl("");

                    productDao.updateProduct(product);

                    productTable.setModel(new DefaultTableModel(getProductData(searchProduct), new String[]{"编号","名称", "数量", "单价" , "类型","员工","上架时间"}));

                }
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int productresult = productDao.deleteProduct(currentProduct);
                //int orderresult = orderDao.deleteOrderByProductid(currentProduct.getId());
                int commentresult = commentDao.deleteCommentByProductid(currentProduct.getId());
                //int feedbackresult = feedbackDao.deleteFeedbackByOrderid( orderDao.);
                if( productresult == 1 && commentresult ==1 ){
                    JOptionPane.showMessageDialog(null, "删除成功");
                    productTable.setModel(new DefaultTableModel(getProductData(searchProduct), new String[]{"编号","名称", "数量", "单价" , "类型","员工","上架时间"}));
                }


//                if(productDao.deleteProduct(currentProduct) == 1){
//                    JOptionPane.showMessageDialog(null, "删除成功");
//                    productTable.setModel(new DefaultTableModel(getProductData(searchProduct), new String[]{"编号","名称", "数量", "单价" , "类型","员工","上架时间"}));
//                }
//                else{
//                    JOptionPane.showMessageDialog(null, "删除失败");
//                }
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

        layout.putConstraint(SpringLayout.WEST, productIdLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productIdLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, productNameLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productNameLabel, 20, SpringLayout.SOUTH, productIdLabel);

        layout.putConstraint(SpringLayout.WEST, productNameTextField, 20, SpringLayout.EAST, productNameLabel);
        layout.putConstraint(SpringLayout.NORTH, productNameTextField, 20, SpringLayout.SOUTH, productIdLabel);

        layout.putConstraint(SpringLayout.WEST, productTypeLabel, 20, SpringLayout.EAST, productNameTextField);
        layout.putConstraint(SpringLayout.NORTH, productTypeLabel, 20, SpringLayout.SOUTH, productIdLabel);

        layout.putConstraint(SpringLayout.WEST, productTypeComboBox, 20, SpringLayout.EAST, productTypeLabel);
        layout.putConstraint(SpringLayout.NORTH, productTypeComboBox, 20, SpringLayout.SOUTH, productIdLabel);

        layout.putConstraint(SpringLayout.WEST, productPriceLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productPriceLabel, 20, SpringLayout.SOUTH, productNameLabel);

        layout.putConstraint(SpringLayout.WEST, productPriceTextField, 20, SpringLayout.EAST, productPriceLabel);
        layout.putConstraint(SpringLayout.NORTH, productPriceTextField, 20, SpringLayout.SOUTH, productNameLabel);

        layout.putConstraint(SpringLayout.WEST, productStockLabel, 20, SpringLayout.EAST, productPriceTextField);
        layout.putConstraint(SpringLayout.NORTH, productStockLabel, 20, SpringLayout.SOUTH, productTypeLabel);

        layout.putConstraint(SpringLayout.WEST, productStockTextField, 20, SpringLayout.EAST, productStockLabel);
        layout.putConstraint(SpringLayout.NORTH, productStockTextField, 20, SpringLayout.SOUTH, productTypeLabel);

        layout.putConstraint(SpringLayout.WEST, productTextLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productTextLabel, 20, SpringLayout.SOUTH, productPriceLabel);

        layout.putConstraint(SpringLayout.WEST, productTextArea, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productTextArea, 20, SpringLayout.SOUTH, productTextLabel);

        layout.putConstraint(SpringLayout.WEST, updateProductButton, 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, updateProductButton, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, deleteProductButton, 800, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, deleteProductButton, 20, SpringLayout.SOUTH, updateProductButton);

        layout.putConstraint(SpringLayout.WEST, label, 160, SpringLayout.WEST, showPanel);

        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeJComboBox);
        showPanel.add(searchJButton);
        showPanel.add(productPane);
        showPanel.add(productIdLabel);
        showPanel.add(productNameLabel);
        showPanel.add(productNameTextField);
        showPanel.add(productTypeLabel);
        showPanel.add(productTypeComboBox);
        showPanel.add(productPriceLabel);
        showPanel.add(productPriceTextField);
        showPanel.add(productStockLabel);
        showPanel.add(productStockTextField);
        showPanel.add(productTextLabel);
        showPanel.add(productTextArea);
        showPanel.add(updateProductButton);
        showPanel.add(deleteProductButton);


        showPanel.add(label);
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
//        productlistButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new AdminProductListJFrame();
//                dispose();
//            }
//        });
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

        productlistButton.setEnabled(false);

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



}
