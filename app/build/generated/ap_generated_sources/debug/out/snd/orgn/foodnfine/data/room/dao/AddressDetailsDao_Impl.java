package snd.orgn.foodnfine.data.room.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AddressDetailsDao_Impl implements AddressDetailsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AddressDetails> __insertionAdapterOfAddressDetails;

  private final EntityDeletionOrUpdateAdapter<AddressDetails> __updateAdapterOfAddressDetails;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUserAddressDetails;

  private final SharedSQLiteStatement __preparedStmtOfClearAddressDetail;

  public AddressDetailsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAddressDetails = new EntityInsertionAdapter<AddressDetails>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `AddressDetails` (`userAddId`,`userId`,`location`,`house`,`building`,`landmark`,`instruction`,`contactPerson`,`contactNumber`,`locationType`,`otherDesc`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AddressDetails value) {
        if (value.getUserAddId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUserAddId());
        }
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserId());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocation());
        }
        if (value.getHouse() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getHouse());
        }
        if (value.getBuilding() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getBuilding());
        }
        if (value.getLandmark() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLandmark());
        }
        if (value.getInstruction() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getInstruction());
        }
        if (value.getContactPerson() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getContactPerson());
        }
        if (value.getContactNumber() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getContactNumber());
        }
        if (value.getLocationType() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getLocationType());
        }
        if (value.getOtherDesc() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getOtherDesc());
        }
      }
    };
    this.__updateAdapterOfAddressDetails = new EntityDeletionOrUpdateAdapter<AddressDetails>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `AddressDetails` SET `userAddId` = ?,`userId` = ?,`location` = ?,`house` = ?,`building` = ?,`landmark` = ?,`instruction` = ?,`contactPerson` = ?,`contactNumber` = ?,`locationType` = ?,`otherDesc` = ? WHERE `userAddId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AddressDetails value) {
        if (value.getUserAddId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUserAddId());
        }
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserId());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocation());
        }
        if (value.getHouse() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getHouse());
        }
        if (value.getBuilding() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getBuilding());
        }
        if (value.getLandmark() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLandmark());
        }
        if (value.getInstruction() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getInstruction());
        }
        if (value.getContactPerson() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getContactPerson());
        }
        if (value.getContactNumber() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getContactNumber());
        }
        if (value.getLocationType() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getLocationType());
        }
        if (value.getOtherDesc() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getOtherDesc());
        }
        if (value.getUserAddId() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getUserAddId());
        }
      }
    };
    this.__preparedStmtOfDeleteUserAddressDetails = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM AddressDetails WHERE userAddId=?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAddressDetail = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM AddressDetails";
        return _query;
      }
    };
  }

  @Override
  public void insertAddressDetail(final AddressDetails... addressDetails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAddressDetails.insert(addressDetails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateAddressDetail(final AddressDetails... addressDetails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAddressDetails.handleMultiple(addressDetails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUserAddressDetails(final String id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUserAddressDetails.acquire();
    int _argIndex = 1;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, id);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteUserAddressDetails.release(_stmt);
    }
  }

  @Override
  public void clearAddressDetail() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearAddressDetail.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearAddressDetail.release(_stmt);
    }
  }

  @Override
  public List<AddressDetails> getAddressDeatilsList() {
    final String _sql = "SELECT * FROM AddressDetails";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserAddId = CursorUtil.getColumnIndexOrThrow(_cursor, "userAddId");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfHouse = CursorUtil.getColumnIndexOrThrow(_cursor, "house");
      final int _cursorIndexOfBuilding = CursorUtil.getColumnIndexOrThrow(_cursor, "building");
      final int _cursorIndexOfLandmark = CursorUtil.getColumnIndexOrThrow(_cursor, "landmark");
      final int _cursorIndexOfInstruction = CursorUtil.getColumnIndexOrThrow(_cursor, "instruction");
      final int _cursorIndexOfContactPerson = CursorUtil.getColumnIndexOrThrow(_cursor, "contactPerson");
      final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
      final int _cursorIndexOfLocationType = CursorUtil.getColumnIndexOrThrow(_cursor, "locationType");
      final int _cursorIndexOfOtherDesc = CursorUtil.getColumnIndexOrThrow(_cursor, "otherDesc");
      final List<AddressDetails> _result = new ArrayList<AddressDetails>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final AddressDetails _item;
        _item = new AddressDetails();
        final String _tmpUserAddId;
        _tmpUserAddId = _cursor.getString(_cursorIndexOfUserAddId);
        _item.setUserAddId(_tmpUserAddId);
        final String _tmpUserId;
        _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        _item.setUserId(_tmpUserId);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _item.setLocation(_tmpLocation);
        final String _tmpHouse;
        _tmpHouse = _cursor.getString(_cursorIndexOfHouse);
        _item.setHouse(_tmpHouse);
        final String _tmpBuilding;
        _tmpBuilding = _cursor.getString(_cursorIndexOfBuilding);
        _item.setBuilding(_tmpBuilding);
        final String _tmpLandmark;
        _tmpLandmark = _cursor.getString(_cursorIndexOfLandmark);
        _item.setLandmark(_tmpLandmark);
        final String _tmpInstruction;
        _tmpInstruction = _cursor.getString(_cursorIndexOfInstruction);
        _item.setInstruction(_tmpInstruction);
        final String _tmpContactPerson;
        _tmpContactPerson = _cursor.getString(_cursorIndexOfContactPerson);
        _item.setContactPerson(_tmpContactPerson);
        final String _tmpContactNumber;
        _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
        _item.setContactNumber(_tmpContactNumber);
        final String _tmpLocationType;
        _tmpLocationType = _cursor.getString(_cursorIndexOfLocationType);
        _item.setLocationType(_tmpLocationType);
        final String _tmpOtherDesc;
        _tmpOtherDesc = _cursor.getString(_cursorIndexOfOtherDesc);
        _item.setOtherDesc(_tmpOtherDesc);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public AddressDetails getUserAddressDetails(final String id) {
    final String _sql = "SELECT * FROM AddressDetails WHERE userAddId LIKE?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserAddId = CursorUtil.getColumnIndexOrThrow(_cursor, "userAddId");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfHouse = CursorUtil.getColumnIndexOrThrow(_cursor, "house");
      final int _cursorIndexOfBuilding = CursorUtil.getColumnIndexOrThrow(_cursor, "building");
      final int _cursorIndexOfLandmark = CursorUtil.getColumnIndexOrThrow(_cursor, "landmark");
      final int _cursorIndexOfInstruction = CursorUtil.getColumnIndexOrThrow(_cursor, "instruction");
      final int _cursorIndexOfContactPerson = CursorUtil.getColumnIndexOrThrow(_cursor, "contactPerson");
      final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
      final int _cursorIndexOfLocationType = CursorUtil.getColumnIndexOrThrow(_cursor, "locationType");
      final int _cursorIndexOfOtherDesc = CursorUtil.getColumnIndexOrThrow(_cursor, "otherDesc");
      final AddressDetails _result;
      if(_cursor.moveToFirst()) {
        _result = new AddressDetails();
        final String _tmpUserAddId;
        _tmpUserAddId = _cursor.getString(_cursorIndexOfUserAddId);
        _result.setUserAddId(_tmpUserAddId);
        final String _tmpUserId;
        _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        _result.setUserId(_tmpUserId);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        _result.setLocation(_tmpLocation);
        final String _tmpHouse;
        _tmpHouse = _cursor.getString(_cursorIndexOfHouse);
        _result.setHouse(_tmpHouse);
        final String _tmpBuilding;
        _tmpBuilding = _cursor.getString(_cursorIndexOfBuilding);
        _result.setBuilding(_tmpBuilding);
        final String _tmpLandmark;
        _tmpLandmark = _cursor.getString(_cursorIndexOfLandmark);
        _result.setLandmark(_tmpLandmark);
        final String _tmpInstruction;
        _tmpInstruction = _cursor.getString(_cursorIndexOfInstruction);
        _result.setInstruction(_tmpInstruction);
        final String _tmpContactPerson;
        _tmpContactPerson = _cursor.getString(_cursorIndexOfContactPerson);
        _result.setContactPerson(_tmpContactPerson);
        final String _tmpContactNumber;
        _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
        _result.setContactNumber(_tmpContactNumber);
        final String _tmpLocationType;
        _tmpLocationType = _cursor.getString(_cursorIndexOfLocationType);
        _result.setLocationType(_tmpLocationType);
        final String _tmpOtherDesc;
        _tmpOtherDesc = _cursor.getString(_cursorIndexOfOtherDesc);
        _result.setOtherDesc(_tmpOtherDesc);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
