package snd.orgn.foodnfine.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import snd.orgn.foodnfine.data.room.entity.PackageDetails;

@Dao
public interface PackageDetailsDao {
    @Query("SELECT * FROM PackageDetails")
    List<PackageDetails> getPackageDetailsList();
    @Query("SELECT * FROM packagedetails WHERE packageId LIKE:id")
    PackageDetails getPackageName(String id);

    @Insert
    public void insertPackageDetail(PackageDetails... packageDetails);

    @Update
    public void updatePackageDetail(PackageDetails... packageDetails);

    @Query("DELETE FROM PackageDetails")
    void clearPackageDetail();
}
