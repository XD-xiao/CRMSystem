package dao.impl;

import dao.CommentDao;
import domain.Comment;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentDaoImpl implements CommentDao {
    private JdbcUtil jdbcUtil = JdbcUtil.getJdbcUtil();
    private PreparedStatement pstmt = null;
    private Connection conn = null;

    ResultSet rs = null;


    @Override
    public int addComment(Comment comment) {
        String sql = "insert into commentlist(productid,userid,text,commendate,evaluate) values(?,?,?,?,?)";
        Object[] params = new Object[]{
                comment.getProductid(),
                comment.getUserid(),
                comment.getText(),
                comment.getCommentdate(),
                comment.getEvaluate()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public Comment getCommentByuseridproductid(Integer userid, Integer productid) {
        String sql = "select * from commentlist where productid=? and userid=? ";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{productid,userid});
        Comment comment = null;
        if(!map.isEmpty()){
            comment = new Comment();
            comment.setProductid((Integer) map.get("productid"));
            comment.setUserid((Integer) map.get("userid"));
            comment.setText((String) map.get("text"));
            comment.setCommentdate((String) map.get("commendate"));
            comment.setEvaluate((Integer) map.get("evaluate"));

        }

        return comment;
    }

    @Override
    public int updateComment(Comment comment) {
        String sql = "update commentlist set userid=?,text=?,commendate=?,evaluate=? where productid=?";

        Object[] params = new Object[]{
                comment.getUserid(),
                comment.getText(),
                comment.getCommentdate(),
                comment.getEvaluate(),
                comment.getProductid()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteComment(Comment comment) {
        String sql = "delete from commentlist where productid=? and userid=?";
        Object[] params = new Object[]{comment.getProductid(),comment.getUserid()};
        return jdbcUtil.executeUpdate(sql, params);
    }
    @Override
    public int deleteCommentByUserid(Integer userid) {
        return jdbcUtil.executeUpdate("delete from commentlist where userid=?", new Object[]{userid});
    }
    @Override
    public int deleteCommentByProductid(Integer productid) {
        return jdbcUtil.executeUpdate("delete from commentlist where productid=?", new Object[]{productid});
    }

    @Override
    public List<Comment> getComments(Comment comment) {
        StringBuffer sb = new StringBuffer("select * from commentlist where true ");
        if (comment.getProductid() != null ) {
            sb.append(" and productid =")
                    .append(comment.getProductid());
        }
        if (comment.getUserid() != null) {
            sb.append(" and userid =")
                    .append(comment.getUserid());
        }
        if (comment.getEvaluate() != null) {
            sb.append("and evaluate =")
                    .append(comment.getEvaluate());
        }
        List<Map<String, Object>> maps = jdbcUtil.executeQueryList(sb.toString(), null);
        List<Comment> comments = new ArrayList<>();

        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> map = maps.get(i);
            comment = new Comment();
            comment.setProductid((Integer) map.get("productid"));
            comment.setUserid((Integer) map.get("userid"));
            comment.setText((String) map.get("text"));
            comment.setCommentdate((String) map.get("commendate"));
            comment.setEvaluate((Integer) map.get("evaluate"));

            comments.add(comment);
        }
        return comments;
    }
}
