package snd.orgn.foodnfine.data.room.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import snd.orgn.foodnfine.data.room.dao.AddressDetailsDao;
import snd.orgn.foodnfine.data.room.dao.AddressDetailsDao_Impl;
import snd.orgn.foodnfine.data.room.dao.PackageDetailsDao;
import snd.orgn.foodnfine.data.room.dao.PackageDetailsDao_Impl;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AddressDetailsDao _addressDetailsDao;

  private volatile PackageDetailsDao _packageDetailsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `AddressDetails` (`userAddId` TEXT NOT NULL, `userId` TEXT, `location` TEXT, `house` TEXT, `building` TEXT, `landmark` TEXT, `instruction` TEXT, `contactPerson` TEXT, `contactNumber` TEXT, `locationType` TEXT, `otherDesc` TEXT, PRIMARY KEY(`userAddId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PackageDetails` (`packageId` TEXT NOT NULL, `name` TEXT, `isSelected` INTEGER NOT NULL, PRIMARY KEY(`packageId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4ee7f75cf06ca6aa1a455961ba28c34f')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `AddressDetails`");
        _db.execSQL("DROP TABLE IF EXISTS `PackageDetails`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsAddressDetails = new HashMap<String, TableInfo.Column>(11);
        _columnsAddressDetails.put("userAddId", new TableInfo.Column("userAddId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("house", new TableInfo.Column("house", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("building", new TableInfo.Column("building", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("landmark", new TableInfo.Column("landmark", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("instruction", new TableInfo.Column("instruction", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("contactPerson", new TableInfo.Column("contactPerson", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("contactNumber", new TableInfo.Column("contactNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("locationType", new TableInfo.Column("locationType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddressDetails.put("otherDesc", new TableInfo.Column("otherDesc", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAddressDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAddressDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAddressDetails = new TableInfo("AddressDetails", _columnsAddressDetails, _foreignKeysAddressDetails, _indicesAddressDetails);
        final TableInfo _existingAddressDetails = TableInfo.read(_db, "AddressDetails");
        if (! _infoAddressDetails.equals(_existingAddressDetails)) {
          return new RoomOpenHelper.ValidationResult(false, "AddressDetails(snd.orgn.foodnfine.data.room.entity.AddressDetails).\n"
                  + " Expected:\n" + _infoAddressDetails + "\n"
                  + " Found:\n" + _existingAddressDetails);
        }
        final HashMap<String, TableInfo.Column> _columnsPackageDetails = new HashMap<String, TableInfo.Column>(3);
        _columnsPackageDetails.put("packageId", new TableInfo.Column("packageId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackageDetails.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackageDetails.put("isSelected", new TableInfo.Column("isSelected", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPackageDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPackageDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPackageDetails = new TableInfo("PackageDetails", _columnsPackageDetails, _foreignKeysPackageDetails, _indicesPackageDetails);
        final TableInfo _existingPackageDetails = TableInfo.read(_db, "PackageDetails");
        if (! _infoPackageDetails.equals(_existingPackageDetails)) {
          return new RoomOpenHelper.ValidationResult(false, "PackageDetails(snd.orgn.foodnfine.data.room.entity.PackageDetails).\n"
                  + " Expected:\n" + _infoPackageDetails + "\n"
                  + " Found:\n" + _existingPackageDetails);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "4ee7f75cf06ca6aa1a455961ba28c34f", "18da0fa6ffc5239ef303af0f5e0e93e9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "AddressDetails","PackageDetails");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `AddressDetails`");
      _db.execSQL("DELETE FROM `PackageDetails`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public AddressDetailsDao getAddressDetailsListDao() {
    if (_addressDetailsDao != null) {
      return _addressDetailsDao;
    } else {
      synchronized(this) {
        if(_addressDetailsDao == null) {
          _addressDetailsDao = new AddressDetailsDao_Impl(this);
        }
        return _addressDetailsDao;
      }
    }
  }

  @Override
  public PackageDetailsDao getPackageDetailsListDao() {
    if (_packageDetailsDao != null) {
      return _packageDetailsDao;
    } else {
      synchronized(this) {
        if(_packageDetailsDao == null) {
          _packageDetailsDao = new PackageDetailsDao_Impl(this);
        }
        return _packageDetailsDao;
      }
    }
  }
}
