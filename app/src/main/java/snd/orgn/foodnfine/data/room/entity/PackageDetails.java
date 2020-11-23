package snd.orgn.foodnfine.data.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class PackageDetails implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("name")
    @Expose
    private String name;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @NonNull
    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(@NonNull String packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
