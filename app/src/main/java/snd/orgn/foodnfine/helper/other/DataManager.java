package snd.orgn.foodnfine.helper.other;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.application.DeliveryEverything;
import snd.orgn.foodnfine.callbacks.CallbackDataManager;
import snd.orgn.foodnfine.data.room.database.AppDatabase;
import snd.orgn.foodnfine.data.shared_presferences.AppSharedPreferences;
import snd.orgn.foodnfine.interfaces.iDataManager;

public class DataManager implements iDataManager {

    private static DataManager singleInstance = null;
    private static Context context;
    private AppSharedPreferences sharedPreference;
    private AppDatabase appDatabase;
    private static CallbackDataManager callback;

    private boolean databaseCleared = false;
    private boolean sharedPreferencesCleared = false;

    private DataManager() {

        sharedPreference = DeliveryEverything.getAppSharedPreference();
        appDatabase = DeliveryEverything.getAppDatabase();
    }

    public static DataManager getInstance(Context c, CallbackDataManager cb) {
        if (singleInstance == null) {
            singleInstance = new DataManager();
            context = c;
            callback = cb;
        }
        return singleInstance;
    }

    private void clearSharedPreferenceData() {
        sharedPreference.clearData();
        sharedPreferencesCleared = true;
        checkDataCleared();
    }


    private void clearDatabase() {
        try {
            Observable.create(s -> {
                appDatabase.getPackageDetailsListDao().clearPackageDetail();
                appDatabase.getAddressDetailsListDao().clearAddressDetail();
                s.onNext(Boolean.TRUE);
            }).subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(x -> {
                        if (x == Boolean.TRUE) {
                            databaseCleared = true;
                            checkDataCleared();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void clearLocalDirectoryData() {
//        File fileDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_FOLDER_NAME);
//        if (fileDirectory.isDirectory())
//            if(fileDirectory.listFiles().length!=0){
//                for (File child : fileDirectory.listFiles()) {
//                    child.delete();
//                }
//            }
//    }

//    public void clearUpdatePostDirectoryData() {
//        File fileDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_FOLDER_NAME+ "/" + APP_UPDATE_FOLDER_NAME);
//        if (fileDirectory.isDirectory()){
//            if(fileDirectory.listFiles().length!=0){
//                int totalFiles=fileDirectory.listFiles().length;
//                int counter=0;
//                for (File child : fileDirectory.listFiles()) {
//                    counter++;
//                    child.delete();
//                    if(counter==totalFiles){
//                        callback.userUploadPostDataCleared();
//                    }
//                }
//            }else{
//                callback.userUploadPostDataCleared();
//            }
//        }

//    }

    @Override
    public void clearAllData() {
        clearDatabase();
        clearSharedPreferenceData();
        //clearLocalDirectoryData();
    }



    private void checkDataCleared() {
        if (databaseCleared && sharedPreferencesCleared) {
            callback.userDataCleared();
        }
    }
}
