package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestrurentList {
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("restaurant_pin")
    @Expose
    private String restaurantPin;
    @SerializedName("image")
    @Expose
    private String image;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRestaurantPin() {
        return restaurantPin;
    }

    public void setRestaurantPin(String restaurantPin) {
        this.restaurantPin = restaurantPin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
