package domain;

public class Product {
    private Integer id;
    private String name;
    private Integer total;
    private Integer staffid;
    private Integer price;
    private String productiondata;
    private String type;
    private String text;
    private String imgurl;

    public Product()
    {
        this.id = 0;
        this.name = null;
        this.total = 0;
        this.staffid = 0;
        this.price = 0;
        this.productiondata = null;
        this.type = null;
        this.text = null;
        this.imgurl = null;
    }

    public Product(Integer id, String name, int total, Integer staffid, int price, String productiondata, String type, String text, String imgurl) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.staffid = staffid;
        this.price = price;
        this.productiondata = productiondata;
        this.type = type;
        this.text = text;
        this.imgurl = imgurl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getStaffid() {
        return staffid;
    }

    public void setStaffid(Integer staffid) {
        this.staffid = staffid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductiondata() {
        return productiondata;
    }

    public void setProductiondata(String productiondata) {
        this.productiondata = productiondata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "productlist [id=" + this.id +
                ", name='" + this.name +
                ", total=" + this.total +
                ", staffid=" + this.staffid +
                ", price=" + this.price +
                ", productiondata='" + this.productiondata +
                ", type=" + this.type +
                ", text=" + this.text +
                ", imgurl=" + this.imgurl +
                ']';
    }
}
