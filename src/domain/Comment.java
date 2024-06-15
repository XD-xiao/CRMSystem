package domain;

public class Comment {
    private Integer productid;
    private Integer userid;
    private String text;
    private String commentdate;
    private Integer evaluate;

    public Comment()
    {

    }

    public Comment(Integer productid, Integer userid, String text, String commentdate, Integer evaluate) {
        this.productid = productid;
        this.userid = userid;
        this.text = text;
        this.commentdate = commentdate;
        this.evaluate = evaluate;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(String commentdate) {
        this.commentdate = commentdate;
    }

    public Integer getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "productid=" + productid +
                ", userid=" + userid +
                ", text='" + text + '\'' +
                ", commentdate='" + commentdate + '\'' +
                ", evaluate=" + evaluate +
                "}\n";
    }
}
