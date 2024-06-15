package MyJFrame.Admin;

import MyJFrame.AdminJFrame;
import MyJFrame.LoginJFrame;
import dao.CommentDao;
import dao.ProductDao;
import dao.UserDao;
import dao.impl.CommentDaoImpl;
import dao.impl.ProductDaoImpl;
import dao.impl.UserDaoImpl;
import domain.Comment;
import domain.Product;
import domain.User;
import util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCommentJFrame extends JFrame {

    CommentDao commentDao = new CommentDaoImpl();
    UserDao userDao = new UserDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    SpringLayout layout = new SpringLayout();

    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);

    Comment searchComment = new Comment();
    Comment currentComment = new Comment();
    JTable commentTable;


    //反馈内容
    JLabel commentTextLabel = new JLabel("评论内容:");
    JTextField commentText = new JTextField(20);

    JLabel commentScoreLabel = new JLabel("评分:");
    JTextField commentScore = new JTextField(5);
    JButton finishButton  = new JButton("确认修改");
    JButton deleteButton  = new JButton("确认删除");

    public AdminCommentJFrame()  {
        super("AdminCommentJFrame");
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
        showPanel.add(new JLabel(" >>反馈中心"));


        JLabel searchJLabel = new JLabel("产品ID：");
        searchJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JTextField searchJTextField = new JTextField(5);
        searchJTextField.setPreferredSize(new Dimension(80, 30));


        JButton searchJButton = new JButton("搜索");

        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(searchJTextField.getText().equals("")){
                    searchComment = new Comment();
                }
                else if(!InputValidator.isValidProductId(searchJTextField.getText())){
                    JOptionPane.showMessageDialog(null, "请输入正确的产品ID");
                    return;
                }
                else{
                    searchComment.setProductid(Integer.valueOf(searchJTextField.getText()));
                }


                if(searchJTextField.getText().equals(""))
                    searchComment.setProductid(null);
                //更新表格
                commentTable.setModel(new DefaultTableModel(getCommentData(searchComment), new String[]{"产品ID","产品","用户ID","用户", "时间", "评分" , "内容"}));
            }
        });

        DefaultTableModel model = new DefaultTableModel(getCommentData(searchComment), new String[]{"产品ID","产品","用户ID","用户", "时间", "评分" , "内容"});
        commentTable = new JTable(model);
        //表格不可以编辑
        commentTable.setDefaultEditor(Object.class, null);
        //每次只能选择一行
        commentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        commentTable.setRowHeight(20);
        JScrollPane productPane = new JScrollPane(commentTable);
        productPane.setPreferredSize(new Dimension(900, 500));
        //表格选择的内容
        commentTable.getSelectionModel().addListSelectionListener(e -> {
            if(commentTable.getSelectedRow() == -1)
                return;
            //System.out.println("选中了第" + Integer.parseInt(commentTable.getValueAt(commentTable.getSelectedRow(), 0).toString()));
            currentComment.setProductid( Integer.parseInt(commentTable.getValueAt(commentTable.getSelectedRow(), 0).toString()));
            currentComment.setUserid(Integer.parseInt(commentTable.getValueAt(commentTable.getSelectedRow(), 2).toString()));
            currentComment = commentDao.getCommentByuseridproductid( currentComment.getUserid(),currentComment.getProductid());

            commentText.setText(currentComment.getText());
            commentScore.setText(currentComment.getEvaluate().toString());

        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!InputValidator.isValidCommentText(commentText.getText())){
                    JOptionPane.showMessageDialog(null, "请输入正确的评论内容");
                }
                else if(!InputValidator.isValidComentScore(commentScore.getText())){
                    JOptionPane.showMessageDialog(null, "请输入正确的评分");
                }
                else{
                    Comment comment = new Comment();
                    comment.setProductid(currentComment.getProductid());
                    comment.setUserid(currentComment.getUserid());
                    comment = commentDao.getCommentByuseridproductid( currentComment.getUserid(),currentComment.getProductid());
                    comment.setEvaluate(Integer.valueOf(commentScore.getText()));
                    comment.setText(commentText.getText());
                    if(commentDao.updateComment(comment) == 1){
                        JOptionPane.showMessageDialog(null, "修改成功");
                        commentTable.setModel(new DefaultTableModel(getCommentData(searchComment), new String[]{"产品ID","产品","用户ID","用户", "时间", "评分" , "内容"}));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "修改失败");
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commentDao.deleteComment(currentComment) == 1){
                    JOptionPane.showMessageDialog(null, "删除成功");
                    commentTable.setModel(new DefaultTableModel(getCommentData(searchComment), new String[]{"产品ID","产品","用户ID","用户", "时间", "评分" , "内容"}));
                }
                else{
                    JOptionPane.showMessageDialog(null, "删除失败");
                }
            }
        });



        layout.putConstraint(SpringLayout.WEST,searchJLabel, 220, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH,searchJLabel, 40, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.WEST,searchJTextField, 20, SpringLayout.EAST, searchJLabel);
        layout.putConstraint(SpringLayout.NORTH,searchJTextField, 40, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST,searchJButton, 280, SpringLayout.EAST, searchJTextField);
        layout.putConstraint(SpringLayout.NORTH,searchJButton, 42, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, productPane, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, productPane, 80, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.EAST, productPane, 1000, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, productPane, 360, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, commentTextLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, commentTextLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, commentText, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, commentText, 20, SpringLayout.SOUTH, commentTextLabel);

        layout.putConstraint(SpringLayout.WEST, commentScoreLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, commentScoreLabel, 20, SpringLayout.SOUTH, commentText);

        layout.putConstraint(SpringLayout.WEST, commentScore, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, commentScore, 20, SpringLayout.SOUTH, commentScoreLabel);

        layout.putConstraint(SpringLayout.WEST, finishButton, 550, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, finishButton, 430, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, deleteButton, 30, SpringLayout.EAST, finishButton);
        layout.putConstraint(SpringLayout.NORTH, deleteButton, 430, SpringLayout.NORTH, showPanel);



        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(searchJButton);
        showPanel.add(productPane);
        showPanel.add(commentTextLabel);
        showPanel.add(commentText);
        showPanel.add(commentScoreLabel);
        showPanel.add(commentScore);
        showPanel.add(finishButton);
        showPanel.add(deleteButton);

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
//        commentlistButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new AdminCommentJFrame();
//                dispose();
//            }
//        });
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

        commentlistButton.setEnabled(false);

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

    private Object[][] getCommentData(Comment comment){
        Object[][] data = new Object[commentDao.getComments(comment).size()][7];
        if(commentDao.getComments(comment).isEmpty())
            return null;
        for (int i = 0; i < commentDao.getComments(comment).size(); i++) {
            data[i][0] = commentDao.getComments(comment).get(i).getProductid();
            Product product = productDao.getProductByid(commentDao.getComments(comment).get(i).getProductid());
            data[i][1] = product.getName();
            data[i][2] = commentDao.getComments(comment).get(i).getUserid();
            User user = userDao.getUserById(commentDao.getComments(comment).get(i).getUserid());
            data[i][3] = user.getUsername();
            data[i][4] = commentDao.getComments(comment).get(i).getCommentdate();
            data[i][5] = commentDao.getComments(comment).get(i).getEvaluate();
            data[i][6] = commentDao.getComments(comment).get(i).getText();

        }
        return data;
    }

    public static void main(String[] args)
    {
//        LoginJFrame.currentUser = new User(4,1,"admin","123456",18,"男","北京","12345678901",100);
//        new AdminCommentJFrame();
        new LoginJFrame();
    }

}
