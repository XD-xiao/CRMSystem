package dao;

import domain.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentDao {
    int addComment(Comment comment) ;
    Comment getCommentByuseridproductid(Integer userid, Integer productid) ;
    int updateComment(Comment comment) ;
    int deleteComment(Comment comment) ;
    int deleteCommentByUserid(Integer userid) ;
    int deleteCommentByProductid(Integer productid) ;
    List<Comment> getComments(Comment comment);

}
