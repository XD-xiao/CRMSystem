package dao.impl;

import dao.FeedbackDao;
import domain.Feedback;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedbackDaoImpl implements FeedbackDao {
    private JdbcUtil jdbcUtil = JdbcUtil.getJdbcUtil();
    private PreparedStatement pstmt = null;
    private Connection conn = null;

    ResultSet rs = null;


    @Override
    public int addFeedback(Feedback feedback){
        String sql = "insert into feedbacklist(userid,staffid,feedbackdate,orderlistid,text,status,productname) values(?,?,?,?,?,?,?)";
        Object[] params = new Object[]{
                feedback.getUserid(),
                feedback.getStaffid(),
                feedback.getFeedbackdate(),
                feedback.getOrderlistid(),
                feedback.getText(),
                feedback.getStatus(),
                feedback.getProductname()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public Feedback getFeedbackByid(Integer id) {
        String sql = "select * from feedbacklist where id=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{id});
        Feedback feedback = null;
        if(!map.isEmpty()){
            feedback = new Feedback();
            feedback.setId((Integer) map.get("id"));
            feedback.setUserid((Integer) map.get("userid"));
            feedback.setStaffid((Integer) map.get("staffid"));
            feedback.setFeedbackdate((String) map.get("feedbackdate"));
            feedback.setOrderlistid((Integer) map.get("orderlistid"));
            feedback.setText((String) map.get("text"));
            feedback.setStatus((Integer) map.get("status"));
            feedback.setProductname((String) map.get("productname"));

        }

        return feedback;
    }

    @Override
    public Feedback getFeedbackByOrderidUserid(Integer orderid, Integer userid){
        String sql = "select * from feedbacklist where orderlistid=? and userid=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{orderid,userid});
        Feedback feedback = null;
        if(!map.isEmpty()){
            feedback = new Feedback();
            feedback.setId((Integer) map.get("id"));
            feedback.setUserid((Integer) map.get("userid"));
            feedback.setStaffid((Integer) map.get("staffid"));
            feedback.setFeedbackdate((String) map.get("feedbackdate"));
            feedback.setOrderlistid((Integer) map.get("orderlistid"));
            feedback.setText((String) map.get("text"));
            feedback.setStatus((Integer) map.get("status"));
            feedback.setProductname((String) map.get("productname"));

        }

        return feedback;
    }

    @Override
    public int updateFeedback(Feedback feedback) {
        String sql = "update feedbacklist set userid=?, staffid=?,feedbackdate=?,orderlistid=?,text=?,status=?,productname=? where id=?";

        Object[] params = new Object[]{
                feedback.getUserid(),
                feedback.getStaffid(),
                feedback.getFeedbackdate(),
                feedback.getOrderlistid(),
                feedback.getText(),
                feedback.getStatus(),
                feedback.getProductname(),
                feedback.getId()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteFeedback(Feedback feedback){
        String sql = "delete from feedbacklist where id=?";
        Object[] params = new Object[]{feedback.getId()};
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteFeedbackByOrderid(Integer orderid) {
        String sql = "delete from feedbacklist where orderlistid=?";
        Object[] params = new Object[]{orderid};
        return jdbcUtil.executeUpdate(sql, params);
    }
    @Override
    public int deleteFeedbackByUserid(Integer userid) {
        String sql = "delete from feedbacklist where userid=?";
        Object[] params = new Object[]{userid};
        return jdbcUtil.executeUpdate(sql, params);
    }
    @Override
    public int deleteFeedbackByStaffid(Integer staffid) {
        String sql = "delete from feedbacklist where staffid=?";
        Object[] params = new Object[]{staffid};
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public List<Feedback> getFeedbacks(Feedback feedback){
        StringBuffer sb = new StringBuffer("select * from feedbacklist where true ");
        if ( feedback.getUserid() != 0) {
            sb.append(" and userid =")
                    .append(feedback.getUserid());
        }
        if ( feedback.getStaffid() != 0) {
            sb.append(" and staffid = ")
                    .append(feedback.getStaffid());
        }
        if ( feedback.getStatus() != 0){
            sb.append(" and status = ")
                    .append(feedback.getStatus());
        }
        if ( feedback.getProductname() != null && !feedback.getProductname().equals("")) {
            sb.append(" and productname ='")
                    .append(feedback.getProductname())
                    .append("'");
        }


        List<Map<String, Object>> maps = jdbcUtil.executeQueryList(sb.toString(), null);
        //System.out.println(maps.size());
        List<Feedback> feedbacks = new ArrayList<>();

        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> map = maps.get(i);
            feedback = new Feedback();
            feedback.setId((Integer) map.get("id"));
            feedback.setUserid((Integer) map.get("userid"));
            feedback.setStaffid((Integer) map.get("staffid"));
            feedback.setFeedbackdate((String) map.get("feedbackdate"));
            feedback.setOrderlistid((Integer) map.get("orderlistid"));
            feedback.setText((String) map.get("text"));
            feedback.setStatus((Integer) map.get("status"));
            feedback.setProductname((String) map.get("productname"));

            feedbacks.add(feedback);
        }
        return feedbacks;
    }
}
