package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressList {
    @SerializedName("user_add_id")
    @Expose
    private String userAddId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("instruction")
    @Expose
    private String instruction;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("location_type")
    @Expose
    private String locationType;
    @SerializedName("other_desc")
    @Expose
    private String otherDescription;

    public String getUserAddId() {
        return userAddId;
    }

    public void setUserAddId(String userAddId) {
        this.userAddId = userAddId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }
}
