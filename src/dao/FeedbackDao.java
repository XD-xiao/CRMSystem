package dao;

import domain.Feedback;

import java.sql.SQLException;
import java.util.List;

public interface FeedbackDao {
    int addFeedback(Feedback feedback);
    Feedback getFeedbackByid(Integer id);
    Feedback getFeedbackByOrderidUserid(Integer orderid, Integer userid);
    int updateFeedback(Feedback feedback);
    int deleteFeedback(Feedback feedback);
    int deleteFeedbackByStaffid(Integer staffid);
    int deleteFeedbackByUserid(Integer userid);
    int deleteFeedbackByOrderid(Integer orderid);
    List<Feedback> getFeedbacks(Feedback feedback);


}
