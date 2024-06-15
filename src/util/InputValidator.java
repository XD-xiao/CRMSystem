package util;

public class InputValidator {

    public static boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }
    // 用户名
    public static boolean isValidUsername(String username) {
        return isValid(username) && username.length() >= 2 && username.length() <= 40;
    }
    // 密码
    public static boolean isValidPassword(String password) {
        // 只能包含大小写字母和数字
        return isValid(password) && password.matches("^[a-zA-Z0-9]+$") && password.length() >= 6 && password.length() <= 18;
    }
    // 年龄
    public static boolean isValidAge(String age) {
        return isValid(age) && age.matches("^[0-9]+$") && Integer.parseInt(age) >= 0 && Integer.parseInt(age) <= 148;
    }
    // 性别
    public static boolean isValidGender(String gender) {
        return isValid(gender) && gender.matches("^[男女]$");
    }

    // 电话
    public static boolean isValidPhone(String phone) {
        return isValid(phone) && phone.matches("^[0-9]+$") && phone.length() == 11;
    }

    // 地址
    public static boolean isValidAddress(String address) {
        return isValid(address) && address.length() >= 3 && address.length() <= 250;
    }

    //产品名称
    public static boolean isValidProductName(String productName) {
        return isValid(productName) && productName.length() >= 2 && productName.length() <= 40;
    }
    //产品价格
    public static boolean isValidProductPrice(String productPrice) {
        return isValid(productPrice) && productPrice.matches("^[0-9]+$") && Integer.parseInt(productPrice) >= 0 && Integer.parseInt(productPrice) <= 1000000;
    }
    //产品库存
    public static boolean isValidProductTotal(String productTotal) {
        return isValid(productTotal) && productTotal.matches("^[0-9]+$") && Integer.parseInt(productTotal) > 0 && Integer.parseInt(productTotal) <= 1000000;
    }
    //产品简介
    public static boolean isValidProductText(String productText) {
        return isValid(productText) && productText.length() >= 3 && productText.length() <= 99;
    }


    public static boolean isValidCreditrating(String text) {
        return isValid(text) && text.matches("^[0-9]+$") && Integer.parseInt(text) >= 0 && Integer.parseInt(text) <= 100;
    }
    public static boolean isValidFeedbackText(String text) {
        return isValid(text) && text.length() >= 2 && text.length() <= 99;
    }
    public static boolean isValidCommentText(String text) {
        return isValid(text) && text.length() >= 2 && text.length() <= 29;
    }
    public static boolean isValidComentScore(String text) {
        return isValid(text) && text.matches("^[0-9]+$") && Integer.parseInt(text) >= 0 && Integer.parseInt(text) <= 10;
    }

    public static boolean isValidProductId(String text) {
        return (text.matches("^[0-9]+$") && Integer.parseInt(text) >= 0 && Integer.parseInt(text) <= 1000000) || !isValid(text) ;
    }
}
