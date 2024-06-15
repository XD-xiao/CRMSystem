package Test;
import dao.CommentDao;

import dao.impl.CommentDaoImpl;
import domain.Comment;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class CommentDaoImpltest {
    CommentDao commentDao = new CommentDaoImpl();
    @Test
    public void addtest()
    {

        Comment comment = new Comment();
        comment.setProductid(2);
        comment.setText("测试");
        comment.setUserid(3);
        comment.setCommentdate("2020-05-05");
        comment.setEvaluate(6);

        if (commentDao.addComment(comment) == 1)
            System.out.println("添加成功");
        else
            System.out.println("添加失败");


    }

    @Test
    public void updatetest()
    {
        Comment comment = new Comment();
        comment.setProductid(1);


        comment.setText("测试");
        comment.setUserid(1);
        comment.setCommentdate("2020-05-05");
        comment.setEvaluate(9);
        if (commentDao.updateComment(comment) == 1)
            System.out.println("修改成功");
        else
            System.out.println("修改失败");

    }


    @Test
    public void deletetest()
    {
        Comment comment = new Comment();
        comment.setProductid(4);
        comment.setUserid(1);

            if (commentDao.deleteComment(comment) == 1)
                System.out.println("删除成功");
            else
                System.out.println("删除失败");


    }
    @Test
    public void gettest()
    {
        Comment comment = new Comment();

        if (commentDao.getCommentByuseridproductid(8,14) != null)
            System.out.println("查询成功");
        else
            System.out.println("查询失败");
    }
    @Test
    public void getlisttest()
    {
        Comment comment = new Comment();
//        comment.setProductid(2);
//        comment.setUserid(1);
        comment.setEvaluate(3);

        System.out.println(commentDao.getComments(comment));

    }
}
