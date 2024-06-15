package MyJFrame.User;

import MyJFrame.Admin.AdminFeedbackJFrame;
import MyJFrame.Admin.AdminOrderJFrame;
import MyJFrame.LoginJFrame;
import MyJFrame.UserJFrame;
import dao.FeedbackDao;
import dao.UserDao;
import dao.impl.FeedbackDaoImpl;
import dao.impl.UserDaoImpl;
import domain.Comment;
import domain.Feedback;
import domain.Order;
import domain.User;
import util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class UserFeedbackJFrame extends JFrame{

    FeedbackDao feedbackDao = new FeedbackDaoImpl();
    UserDao userDao = new UserDaoImpl();
    SpringLayout layout = new SpringLayout();

    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);

    Feedback searchFeedback = new Feedback();
    Feedback currentFeedback = new Feedback();
    JTable feedbackTable;


    //反馈内容
    JLabel feedbackTextLabel = new JLabel("反馈内容:");
    JTextField feedbackText = new JTextField(20);
    JButton updataButton = new JButton("提交");



    public UserFeedbackJFrame()  {
        super("UserFeedbackJFrame");
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
        showPanel.add(new JLabel(" >>反馈中心"));

        JLabel searchJLabel = new JLabel("搜索：");
        searchJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JTextField searchJTextField = new JTextField(15);
        searchJTextField.setPreferredSize(new Dimension(80, 30));

        JLabel chooseTypeJLabel = new JLabel("类型：");
        chooseTypeJLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JComboBox chooseTypeJComboBox = new JComboBox(AdminFeedbackJFrame.statusArray);
        chooseTypeJComboBox.setPreferredSize(new Dimension(90, 30));

        JButton searchJButton = new JButton("搜索");

        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取
                searchFeedback.setProductname(searchJTextField.getText());
                searchFeedback.setStatus( chooseTypeJComboBox.getSelectedIndex() );
                if(chooseTypeJComboBox.getSelectedItem().equals("全部"))
                    searchFeedback.setStatus(0);
                if(searchJTextField.getText().equals(""))
                    searchFeedback.setProductname(null);
                //更新表格
                feedbackTable.setModel(new DefaultTableModel(getFeedbackData(searchFeedback), new String[]{"编号","名称", "员工", "时间" , "内容","状态"}));
            }
        });
        searchFeedback.setUserid(LoginJFrame.currentUser.getId());
        DefaultTableModel productmodel = new DefaultTableModel(getFeedbackData(searchFeedback), new String[]{"编号","名称", "员工", "时间" , "内容","状态"});
        feedbackTable = new JTable(productmodel);
        //表格不可以编辑
        feedbackTable.setDefaultEditor(Object.class, null);
        //每次只能选择一行
        feedbackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        feedbackTable.setRowHeight(20);
        JScrollPane productPane = new JScrollPane(feedbackTable);
        productPane.setPreferredSize(new Dimension(900, 500));
        //表格选择的内容
        feedbackTable.getSelectionModel().addListSelectionListener(e -> {
            if(feedbackTable.getSelectedRow() == -1)
                return;
            currentFeedback.setId( Integer.parseInt(feedbackTable.getValueAt(feedbackTable.getSelectedRow(), 0).toString()));
            currentFeedback = feedbackDao.getFeedbackByid(currentFeedback.getId());

            feedbackText.setText(currentFeedback.getText());

            if(currentFeedback.getStatus() == 2){
                updataButton.setEnabled(false);
            }
            else{
                updataButton.setEnabled(true);
            }

        });



        updataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !InputValidator.isValidFeedbackText(feedbackText.getText())){
                    JOptionPane.showMessageDialog(null, "反馈内容格式错误");
                    return;
                }
                Feedback feedback = currentFeedback;
                feedback.setText(feedbackText.getText());

                if(feedbackDao.updateFeedback(feedback) == 1){
                    feedbackTable.setModel(new DefaultTableModel(getFeedbackData(searchFeedback), new String[]{"编号","名称", "员工", "时间" , "内容","状态"}));
                }
                else {
                    JOptionPane.showMessageDialog(null, "反馈失败");
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

        layout.putConstraint(SpringLayout.WEST, feedbackTextLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackTextLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, feedbackText, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackText, 20, SpringLayout.SOUTH, feedbackTextLabel);

        layout.putConstraint(SpringLayout.WEST, updataButton, 550, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, updataButton, 430, SpringLayout.NORTH, showPanel);



        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeJComboBox);
        showPanel.add(searchJButton);
        showPanel.add(productPane);
        showPanel.add(feedbackTextLabel);
        showPanel.add(feedbackText);
        showPanel.add(updataButton);



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
        orderlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserOrderJFrame();
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
        feedbacklistButton.setBackground(Color.LIGHT_GRAY);
        feedbacklistButton.setBorderPainted(false);
        feedbacklistButton.setEnabled(false);
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

    private Object[][] getFeedbackData(Feedback feedback){
        Object[][] data = new Object[feedbackDao.getFeedbacks(feedback).size()][6];
        if(feedbackDao.getFeedbacks(feedback).isEmpty())
            return null;
        for (int i = 0; i < feedbackDao.getFeedbacks(feedback).size(); i++) {
            data[i][0] = feedbackDao.getFeedbacks(feedback).get(i).getId();
            data[i][1] = feedbackDao.getFeedbacks(feedback).get(i).getProductname();
            User staff = userDao.getUserById(feedbackDao.getFeedbacks(feedback).get(i).getStaffid());
            data[i][2] = staff.getUsername();
            data[i][3] = feedbackDao.getFeedbacks(feedback).get(i).getFeedbackdate();
            data[i][4] = feedbackDao.getFeedbacks(feedback).get(i).getText();
            data[i][5] = switch(feedbackDao.getFeedbacks(feedback).get(i).getStatus()){
                case 1 -> "待处理";
                case 2 -> "已完成";
                default -> "未知";
            };

        }
        return data;
    }

    public static void main(String[] args)
    {
        //LoginJFrame.currentUser = new User(4,1,"admin","123456",18,"男","北京","12345678901",100);
        //new UserFeedbackJFrame();
        new LoginJFrame();
    }
}
