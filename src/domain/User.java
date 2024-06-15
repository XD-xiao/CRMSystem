package domain;


//enum Role {
//    ADMINISTRATOR,  //1
//    CUSTOMER,   //2
//    EMPLOYEE    //3
//}


public class User {
    private Integer id;
    private Integer role;
    private String username;
    private String userpassword;
    private Integer age;
    private String sex;
    private String address;
    private String phone;
    private Integer creditrating;

    public User(Integer id, Integer role, String username, String userpassword, Integer age, String sex, String address, String phone, Integer creditrating) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.userpassword = userpassword;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
        this.creditrating = creditrating;
    }

    public User(int role, String username, String userpassword, Integer age, String sex, String address, String phone, Integer creditrating) {
        this.role = role;
        this.username = username;
        this.userpassword = userpassword;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
        this.creditrating = creditrating;
    }
    public User() {
        this.id = 0;
        this.role = 0;
        this.username = null;
        this.userpassword = null;
        this.age = 0;
        this.sex = null;
        this.address = null;
        this.phone = null;
        this.creditrating = 0;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getrole() {
        return role;
    }

    public void setrole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCreditrating() {
        return creditrating;
    }

    public void setCreditrating(Integer creditrating) {
        this.creditrating = creditrating;
    }

    @Override
    public String toString() {
        return "users [id=" + this.id +
                ", role=" + this.role +
                ", username=" + this.username +
                ", userpassword=" + this.userpassword +
                ", age=" + this.age +
                ", sex=" + this.sex +
                ", phone=" + phone +
                ", address=" + address +
                ", creditrating=" + creditrating +
                "]";
    }
}
