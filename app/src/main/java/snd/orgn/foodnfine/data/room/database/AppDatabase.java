package snd.orgn.foodnfine.data.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import snd.orgn.foodnfine.data.room.dao.AddressDetailsDao;
import snd.orgn.foodnfine.data.room.dao.PackageDetailsDao;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.data.room.entity.PackageDetails;

import static snd.orgn.foodnfine.constant.AppConstants.DATABASE_NAME;
import static snd.orgn.foodnfine.constant.AppConstants.DATABASE_VERSION;

@Database( entities = {AddressDetails.class, PackageDetails.class},version=DATABASE_VERSION,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance= Room.databaseBuilder(context, AppDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

    public abstract AddressDetailsDao getAddressDetailsListDao();
    public abstract PackageDetailsDao getPackageDetailsListDao();

}
