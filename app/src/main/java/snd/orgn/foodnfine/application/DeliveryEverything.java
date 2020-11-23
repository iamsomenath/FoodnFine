package snd.orgn.foodnfine.application;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.squareup.picasso.Picasso;

import snd.orgn.foodnfine.data.room.database.AppDatabase;
import snd.orgn.foodnfine.data.shared_presferences.AppSharedPreferences;
import snd.orgn.foodnfine.util.PicassoProvider;

public class DeliveryEverything  extends MultiDexApplication {

    private static Context appContext;
    private static AppSharedPreferences appSharedPreference;
    private static Picasso picassoWithOAuth;
    private static Picasso picasso;
    private static AppDatabase appDatabase;


    public void onCreate() {
        super.onCreate();

        appContext=this;
        //Setting App Context
        DeliveryEverything.appContext = getApplicationContext();

        //init AppSharedPreferences
        appSharedPreference = AppSharedPreferences.getInstance(appContext);

        //init Database
        appDatabase = AppDatabase.getInstance(appContext);
        //init NetworkAuthentication

//

    }
    public static Picasso getPicasso(Context context) {
        if (picasso == null)
            return new PicassoProvider().getPicasso(context);
        else
            return picasso;
    }


    //Getting AppDatabase
    public static Context getAppContext() {
        return appContext;
    }

    //Getting AppDatabase
    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }


    //Getting AppSharedPreferences
    public static AppSharedPreferences getAppSharedPreference() {
        return appSharedPreference;
    }
}


