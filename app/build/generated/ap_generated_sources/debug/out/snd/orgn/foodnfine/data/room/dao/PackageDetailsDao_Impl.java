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
import snd.orgn.foodnfine.data.room.entity.PackageDetails;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PackageDetailsDao_Impl implements PackageDetailsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PackageDetails> __insertionAdapterOfPackageDetails;

  private final EntityDeletionOrUpdateAdapter<PackageDetails> __updateAdapterOfPackageDetails;

  private final SharedSQLiteStatement __preparedStmtOfClearPackageDetail;

  public PackageDetailsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPackageDetails = new EntityInsertionAdapter<PackageDetails>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `PackageDetails` (`packageId`,`name`,`isSelected`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackageDetails value) {
        if (value.getPackageId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPackageId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final int _tmp;
        _tmp = value.isSelected() ? 1 : 0;
        stmt.bindLong(3, _tmp);
      }
    };
    this.__updateAdapterOfPackageDetails = new EntityDeletionOrUpdateAdapter<PackageDetails>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `PackageDetails` SET `packageId` = ?,`name` = ?,`isSelected` = ? WHERE `packageId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackageDetails value) {
        if (value.getPackageId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPackageId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final int _tmp;
        _tmp = value.isSelected() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getPackageId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPackageId());
        }
      }
    };
    this.__preparedStmtOfClearPackageDetail = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM PackageDetails";
        return _query;
      }
    };
  }

  @Override
  public void insertPackageDetail(final PackageDetails... packageDetails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPackageDetails.insert(packageDetails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePackageDetail(final PackageDetails... packageDetails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPackageDetails.handleMultiple(packageDetails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearPackageDetail() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearPackageDetail.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearPackageDetail.release(_stmt);
    }
  }

  @Override
  public List<PackageDetails> getPackageDetailsList() {
    final String _sql = "SELECT * FROM PackageDetails";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfPackageId = CursorUtil.getColumnIndexOrThrow(_cursor, "packageId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
      final List<PackageDetails> _result = new ArrayList<PackageDetails>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PackageDetails _item;
        _item = new PackageDetails();
        final String _tmpPackageId;
        _tmpPackageId = _cursor.getString(_cursorIndexOfPackageId);
        _item.setPackageId(_tmpPackageId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final boolean _tmpIsSelected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsSelected);
        _tmpIsSelected = _tmp != 0;
        _item.setSelected(_tmpIsSelected);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public PackageDetails getPackageName(final String id) {
    final String _sql = "SELECT * FROM packagedetails WHERE packageId LIKE?";
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
      final int _cursorIndexOfPackageId = CursorUtil.getColumnIndexOrThrow(_cursor, "packageId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
      final PackageDetails _result;
      if(_cursor.moveToFirst()) {
        _result = new PackageDetails();
        final String _tmpPackageId;
        _tmpPackageId = _cursor.getString(_cursorIndexOfPackageId);
        _result.setPackageId(_tmpPackageId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
        final boolean _tmpIsSelected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsSelected);
        _tmpIsSelected = _tmp != 0;
        _result.setSelected(_tmpIsSelected);
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
