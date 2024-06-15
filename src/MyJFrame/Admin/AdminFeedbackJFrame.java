package MyJFrame.Admin;

import MyJFrame.AdminJFrame;
import MyJFrame.LoginJFrame;
import MyJFrame.User.UserProductJFrame;
import MyJFrame.UserJFrame;
import dao.FeedbackDao;
import dao.UserDao;
import dao.impl.FeedbackDaoImpl;
import dao.impl.UserDaoImpl;
import domain.Feedback;
import domain.User;
import util.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AdminFeedbackJFrame extends JFrame{

    FeedbackDao feedbackDao = new FeedbackDaoImpl();
    UserDao userDao = new UserDaoImpl();

    public static String[] statusArray = {"全部","待处理","已完成"};
    SpringLayout layout = new SpringLayout();

    JPanel functionPanel = new JPanel(layout);
    JPanel showPanel = new JPanel(layout);

    Feedback searchFeedback = new Feedback();
    Feedback currentFeedback = new Feedback();
    JTable feedbackTable;


    //反馈内容
    JLabel feedbackTextLabel = new JLabel("反馈内容:");
    JTextField feedbackText = new JTextField(20);

    JLabel feedbackStatusLabel = new JLabel("反馈状态:");
    JComboBox feedbackStatus = new JComboBox(Arrays.copyOfRange(statusArray, 1, statusArray.length));
    JButton finishButton  = new JButton("修改");
    JButton deleteButton  = new JButton("删除");

    public AdminFeedbackJFrame()  {
        super("AdminFeedbackJFrame");
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
                feedbackTable.setModel(new DefaultTableModel(getFeedbackData(searchFeedback), new String[]{"编号","名称", "员工", "时间" ,"用户", "内容","状态"}));
            }
        });

        DefaultTableModel productmodel = new DefaultTableModel(getFeedbackData(searchFeedback), new String[]{"编号","名称", "员工", "时间" ,"用户", "内容","状态"});
        feedbackTable = new JTable(productmodel);
        //表格不可以编辑
        feedbackTable.setDefaultEditor(Object.class, null);
        //每次只能选择一行
        feedbackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        feedbackTable.setRowHeight(20);
        JScrollPane feedbackPane = new JScrollPane(feedbackTable);
        feedbackPane.setPreferredSize(new Dimension(900, 500));
        //表格选择的内容
        feedbackTable.getSelectionModel().addListSelectionListener(e -> {
            if(feedbackTable.getSelectedRow() == -1)
                return;
            currentFeedback.setId( Integer.parseInt(feedbackTable.getValueAt(feedbackTable.getSelectedRow(), 0).toString()));
            currentFeedback = feedbackDao.getFeedbackByid(currentFeedback.getId());

            feedbackText.setText(currentFeedback.getText());
            feedbackStatus.setSelectedIndex(currentFeedback.getStatus()-1);

        });



        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !InputValidator.isValidFeedbackText(feedbackText.getText())){
                    JOptionPane.showMessageDialog(null, "反馈内容格式错误");
                    return;
                }
                Feedback feedback = currentFeedback;
                feedback.setStatus(feedbackStatus.getSelectedIndex()+1);
                feedback.setText(feedbackText.getText());

                if(feedbackDao.updateFeedback(feedback) == 1){
                    feedbackTable.setModel(new DefaultTableModel(getFeedbackData(searchFeedback), new String[]{"编号","名称", "员工", "时间" ,"用户", "内容","状态"}));
                }
                else {
                    JOptionPane.showMessageDialog(null, "反馈失败");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(feedbackDao.deleteFeedback(currentFeedback) == 1){
                    feedbackTable.setModel(new DefaultTableModel(getFeedbackData(searchFeedback), new String[]{"编号","名称", "员工", "时间" ,"用户", "内容","状态"}));
                }
                else {
                    JOptionPane.showMessageDialog(null,"删除失败");
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

        layout.putConstraint(SpringLayout.WEST, feedbackPane, 240, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackPane, 80, SpringLayout.NORTH, showPanel);
        layout.putConstraint(SpringLayout.EAST, feedbackPane, 1000, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.SOUTH, feedbackPane, 360, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, feedbackTextLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackTextLabel, 400, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, feedbackText, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackText, 20, SpringLayout.SOUTH, feedbackTextLabel);

        layout.putConstraint(SpringLayout.WEST, feedbackStatusLabel, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackStatusLabel, 20, SpringLayout.SOUTH, feedbackText);

        layout.putConstraint(SpringLayout.WEST, feedbackStatus, 250, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, feedbackStatus, 20, SpringLayout.SOUTH, feedbackStatusLabel);

        layout.putConstraint(SpringLayout.WEST, finishButton, 550, SpringLayout.WEST, showPanel);
        layout.putConstraint(SpringLayout.NORTH, finishButton, 430, SpringLayout.NORTH, showPanel);

        layout.putConstraint(SpringLayout.WEST, deleteButton, 50, SpringLayout.EAST, finishButton);
        layout.putConstraint(SpringLayout.NORTH, deleteButton, 430, SpringLayout.NORTH, showPanel);



        showPanel.add(searchJLabel);
        showPanel.add(searchJTextField);
        showPanel.add(chooseTypeJLabel);
        showPanel.add(chooseTypeJComboBox);
        showPanel.add(searchJButton);
        showPanel.add(feedbackPane);
        showPanel.add(feedbackTextLabel);
        showPanel.add(feedbackText);
        showPanel.add(feedbackStatusLabel);
        showPanel.add(feedbackStatus);
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
//        feedbacklistButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new AdminFeedbackJFrame();
//                dispose();
//            }
//        });
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

        feedbacklistButton.setEnabled(false);

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

    private Object[][] getFeedbackData(Feedback feedback){
        Object[][] data = new Object[feedbackDao.getFeedbacks(feedback).size()][7];
        if(feedbackDao.getFeedbacks(feedback).isEmpty())
            return null;
        for (int i = 0; i < feedbackDao.getFeedbacks(feedback).size(); i++) {
            data[i][0] = feedbackDao.getFeedbacks(feedback).get(i).getId();
            data[i][1] = feedbackDao.getFeedbacks(feedback).get(i).getProductname();
            User staff = userDao.getUserById(feedbackDao.getFeedbacks(feedback).get(i).getStaffid());
            data[i][2] = staff.getUsername();
            data[i][3] = feedbackDao.getFeedbacks(feedback).get(i).getFeedbackdate();
            User user = userDao.getUserById(feedbackDao.getFeedbacks(feedback).get(i).getUserid());
            data[i][4] = user.getUsername();
            data[i][5] = feedbackDao.getFeedbacks(feedback).get(i).getText();
            data[i][6] = switch(feedbackDao.getFeedbacks(feedback).get(i).getStatus()){
                case 1 -> "待处理";
                case 2 -> "已完成";
                default -> "未知";
            };

        }
        return data;
    }


    public static void main(String[] args)
    {
//        LoginJFrame.currentUser = new User(4,1,"admin","123456",18,"男","北京","12345678901",100);
//        new AdminFeedbackJFrame();
        new LoginJFrame();
    }
}
