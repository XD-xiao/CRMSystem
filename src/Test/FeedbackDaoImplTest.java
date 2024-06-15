package Test;

import dao.FeedbackDao;
import dao.impl.FeedbackDaoImpl;
import domain.Feedback;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class FeedbackDaoImplTest {

    FeedbackDao feedbackDao = new FeedbackDaoImpl();

    @Test
    public void addtest()
    {
        Feedback feedback = new Feedback();
        feedback.setUserid(2);
        feedback.setStaffid(2);
        feedback.setFeedbackdate("2020-01-03");
        feedback.setOrderlistid(3);
        feedback.setText("测试");
        feedback.setStatus(2);

        if (feedbackDao.addFeedback(feedback) ==1 )
            System.out.println("添加成功");
        else
            System.out.println("添加失败");

    }

    @Test
    public void gettest()
    {
        Feedback feedback = new Feedback();
        feedback.setId(2);

        if (feedbackDao.getFeedbackByid(feedback.getId()) != null)
            System.out.println("查询成功");
        else
            System.out.println("查询失败");

    }

    @Test
    public void updatetest()
    {
        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setUserid(2);
        feedback.setStaffid(2);
        feedback.setFeedbackdate("2020-01-03");
        feedback.setOrderlistid(3);
        feedback.setText("修改测试");
        feedback.setStatus(2);

        if (feedbackDao.updateFeedback(feedback) ==1 )
            System.out.println("修改成功");
        else
            System.out.println("修改失败");

    }

    @Test
    public void deletetest()
    {
        Feedback feedback = new Feedback();
        feedback.setId(1);
        if (feedbackDao.deleteFeedback(feedback) ==1 )
            System.out.println("删除成功");
        else
            System.out.println("删除失败");

    }
    @Test
    public void getfeedabckstest()
    {
        Feedback feedback = new Feedback();
        //feedback.setUserid(1);
        //feedback.setStaffid(1);
        feedback.setStatus(2);

        System.out.println(feedbackDao.getFeedbacks(feedback));

    }
}
