package domain;

public class Order {
    private Integer id;
    private Integer userid;
    private Integer staffid;
    private String purchasedate;
    private Integer productid;
    private Integer buynumber;
    private Integer totalamount;
    private Integer status;
    private String productname;

    public Order()
    {

    }

    public Order(Integer id, Integer userid, Integer staffid, String purchasedate, Integer productid, Integer buynumber, Integer totalamount, Integer status, String productname) {
        this.id = id;
        this.userid = userid;
        this.staffid = staffid;
        this.purchasedate = purchasedate;
        this.productid = productid;
        this.buynumber = buynumber;
        this.totalamount = totalamount;
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

    public String getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(String purchasedate) {
        this.purchasedate = purchasedate;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getBuynumber() {
        return buynumber;
    }

    public void setBuynumber(Integer buynumber) {
        this.buynumber = buynumber;
    }

    public Integer getTotalamount() {
        return totalamount;
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

    public void setTotalamount(Integer totalamount) {
        this.totalamount = totalamount;
    }
    @Override
    public String toString() {
        return "Order[id=" + id +
                ", userid=" + userid +
                ", staffid=" + staffid +
                ", purchasedate='" + purchasedate +
                ", productid=" + productid +
                ", buynumber=" + buynumber +
                ", totalamount=" + totalamount +
                ", status=" + status +
                ", productname='" + productname +
                "]\n";
    }
}
