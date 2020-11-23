package snd.orgn.foodnfine.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

import snd.orgn.foodnfine.data.room.entity.AddressDetails;

@Dao
public interface AddressDetailsDao {

    @Query("SELECT * FROM AddressDetails")
    List<AddressDetails> getAddressDeatilsList();

    @Query("SELECT * FROM AddressDetails WHERE userAddId LIKE:id")
    AddressDetails getUserAddressDetails(String id);

    @Insert
    void insertAddressDetail(AddressDetails... addressDetails);

    @Query("DELETE FROM AddressDetails WHERE userAddId=:id")
    void deleteUserAddressDetails(String id);

    @Update
    public void updateAddressDetail(AddressDetails... addressDetails);

    @Query("DELETE FROM AddressDetails")
    void clearAddressDetail();
}
