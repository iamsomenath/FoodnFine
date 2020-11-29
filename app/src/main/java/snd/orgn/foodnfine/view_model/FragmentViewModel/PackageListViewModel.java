package snd.orgn.foodnfine.view_model.FragmentViewModel;



import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackGetChargesInKM;
import snd.orgn.foodnfine.callbacks.CallbackgetAllPackageList;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.data.room.database.AppDatabase;
import snd.orgn.foodnfine.data.room.entity.PackageDetails;
import snd.orgn.foodnfine.rest.api.ApiInterface;
import snd.orgn.foodnfine.rest.response.KmChargesRestResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;

public class PackageListViewModel extends BaseViewModel {
  
    ApiInterface apiInterface;
    private List<PackageDetails> packageDetailsList;
    private AppDatabase appDatabase;
    private int totalDataCount_PackageDetail = 0;
    private int totalDataSavedCount_packageDetail = 0;
    private Boolean packageDetailListFetched = false;
    CallbackgetAllPackageList callback;
    CallbackGetChargesInKM callbackGetChargesInKM;

    public PackageListViewModel(){
        apiInterface = getAPIInterface();
        packageDetailsList = new ArrayList<>();
        appDatabase = FoodnFine.getAppDatabase();
    }

    public void setCallback2(CallbackGetChargesInKM callbackGetChargesInKM) {
        this.callbackGetChargesInKM = callbackGetChargesInKM;
    }

    public void setCallback(CallbackgetAllPackageList callback) {
        this.callback = callback;
    }

    public void getPackageList() {
        makeRequest();
    }

    private void makeRequest() {

        apiCallPackageList();
    }

    public void getKMCharges(){
        apiCall_getKmIncharges();
    }

    /*@SuppressLint("CheckResult")
    private void apiCall_getKmIncharges() {
        Observable<KmChargesRestResponse> userResponseObservable = apiInterface.getKmcharges();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                        callbackGetChargesInKM.onSuccessGetCharges(restResponse.get_1Km(), restResponse.getGreater1kmLess5km(),
                                restResponse.getGreater5kmLess10km(), restResponse.getGreater_than_10());
                    } else {
                        callbackGetChargesInKM.onfailure();
                    }
                }, e -> {
                    callbackGetChargesInKM.onfailure();
                });
    }*/

    @SuppressLint("CheckResult")
    private void apiCall_getKmIncharges() {
        Observable<KmChargesRestResponse> userResponseObservable = apiInterface.getKmcharges();
        userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restResponse -> {
                    if (restResponse.getSuccess().equals(WEBSERVICE_SUCCESS)) {
                        callbackGetChargesInKM.onSuccessGetCharges(restResponse.getFixed_cost(), restResponse.getPer_kilometer());
                    } else {
                        callbackGetChargesInKM.onfailure();
                    }
                }, e -> {
                    callbackGetChargesInKM.onfailure();
                });
    }

    private void apiCallPackageList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<List<PackageDetails>> call = service.getPackageList();
        call.enqueue(new Callback<List<PackageDetails>>() {
            @Override
            public void onResponse(Call<List<PackageDetails>> call, retrofit2.Response<List<PackageDetails>> response) {
                if (response.body() != null) {
                    packageDetailsList.clear();
                    packageDetailsList.addAll(response.body());
                    clearPackageDetailsInDatabase(packageDetailsList);

                }
            }

            @Override
            public void onFailure(Call<List<PackageDetails>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callback.onError(t.getMessage());
                // loadingDialog.hideDialog();
            }
        });
    }



    private void clearPackageDetailsInDatabase(List<PackageDetails> packageDetailList1) {
        try {
            Observable.create(s -> { // create observable
                appDatabase.getPackageDetailsListDao().clearPackageDetail();
                s.onNext(Boolean.TRUE);
            }).subscribeOn(Schedulers.single()) //mention background thread
                    .observeOn(AndroidSchedulers.mainThread())// mention foreground/ui thread where you need the output
                    .subscribe(x -> {
                        if (x == Boolean.TRUE) {

                            totalDataSavedCount_packageDetail = 0;
                            totalDataCount_PackageDetail = packageDetailList1.size();

                            for (PackageDetails packageDetails : packageDetailList1) {
                                try {

                                    savePackageDetailsInDatabase(packageDetails);
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }

                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePackageDetailsInDatabase(PackageDetails packageDetails) {
        try {
            Observable.create(s -> { // create observable
                appDatabase.getPackageDetailsListDao().insertPackageDetail(packageDetails);
                s.onNext(Boolean.TRUE);
            }).subscribeOn(Schedulers.single()) //mention background thread
                    .observeOn(AndroidSchedulers.mainThread())// mention foreground/ui thread where you need the output
                    .subscribe(x -> {
                        if (x == Boolean.TRUE) {
                            totalDataSavedCount_packageDetail++;
                            checkAllDataLoaded_packageDetails();

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAllDataLoaded_packageDetails() {
        if (totalDataSavedCount_packageDetail == totalDataCount_PackageDetail) {
            packageDetailListFetched=true;
            callback.onSuccess();
        }else{
            packageDetailListFetched=false;
        }
    }
}


