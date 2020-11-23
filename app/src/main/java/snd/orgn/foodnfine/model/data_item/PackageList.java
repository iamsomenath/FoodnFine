package snd.orgn.foodnfine.model.data_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageList {
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("name")
    @Expose
    private String name;

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
}
