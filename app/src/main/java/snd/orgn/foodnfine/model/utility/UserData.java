package snd.orgn.foodnfine.model.utility;

public class UserData {

    private String userMobile;
    private String userPhone;
    private String name;
    private String dev_key;
    private String otp;
    private String user_nm;
    private String user_eml;
    private String user_id;
    private String rest_id;
    private String food_catid;
    private String catId;
    private String cartId;
    private String pId;
    private String quantity;
    private String orderType;
    private Integer price;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getFood_catid() {
        return food_catid;
    }

    public void setFood_catid(String food_catid) {
        this.food_catid = food_catid;
    }

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDev_key() {
        return dev_key;
    }

    public void setDev_key(String dev_key) {
        this.dev_key = dev_key;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUser_nm() {
        return user_nm;
    }

    public void setUser_nm(String user_nm) {
        this.user_nm = user_nm;
    }

    public String getUser_eml() {
        return user_eml;
    }

    public void setUser_eml(String user_eml) {
        this.user_eml = user_eml;
    }
}
