package domain;

public class Feedback {

    private Integer id;
    private Integer userid;
    private Integer staffid;
    private String feedbackdate;
    private Integer orderlistid;
    private String text;
    private Integer status;
    private String productname;

    public Feedback()
    {
        this.id = 0;
        this.userid = 0;
        this.staffid = 0;
        this.feedbackdate = null;
        this.orderlistid = 0;
        this.text = null;
        this.status = 0;
    }

    public Feedback(Integer id, Integer userid, Integer staffid, String feedbackdate, Integer orderlistid, String text, Integer status , String productname) {
        this.id = id;
        this.userid = userid;
        this.staffid = staffid;
        this.feedbackdate = feedbackdate;
        this.orderlistid = orderlistid;
        this.text = text;
        this.status = status;
        this.productname = productname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStaffid() {
        return staffid;
    }

    public void setStaffid(Integer staffid) {
        this.staffid = staffid;
    }

    public String getFeedbackdate() {
        return feedbackdate;
    }

    public void setFeedbackdate(String feedbackdate) {
        this.feedbackdate = feedbackdate;
    }

    public Integer getOrderlistid() {
        return orderlistid;
    }

    public void setOrderlistid(Integer orderlistid) {
        this.orderlistid = orderlistid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    @Override
    public String toString() {
        return "Feedback{" + "id=" + id + ", userid=" + userid + ", staffid=" + staffid + ", feedbackdate=" + feedbackdate + ", orderlistid=" + orderlistid + ", text=" + text + ", status=" + status + ", productname=" + productname + "}\n";
    }
}
