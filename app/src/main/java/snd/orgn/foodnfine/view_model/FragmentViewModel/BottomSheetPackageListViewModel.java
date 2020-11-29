package snd.orgn.foodnfine.view_model.FragmentViewModel;

import android.annotation.SuppressLint;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallbackButtomSheetSelectPackage;
import snd.orgn.foodnfine.data.room.entity.PackageDetails;

public class BottomSheetPackageListViewModel extends BaseViewModel {

    CallbackButtomSheetSelectPackage callback;
    private List<PackageDetails> packageDetailslist;

    public void setCallback(CallbackButtomSheetSelectPackage callback){
        this.callback=callback;
    }


    @SuppressLint("CheckResult")
    public void getpackageList(){
        try {
            Observable.create(s -> { // create observable
                packageDetailslist = FoodnFine.getAppDatabase().getPackageDetailsListDao().getPackageDetailsList();
                s.onNext(Boolean.TRUE);
            }).subscribeOn(Schedulers.single()) //mention background thread
                    .observeOn(AndroidSchedulers.mainThread())// mention foreground/ui thread where you need the output
                    .subscribe(x -> {
                        if (x == Boolean.TRUE) {
                            if (packageDetailslist != null && packageDetailslist.size() != 0) {
                                callback.onPackageDataListFetched(packageDetailslist);
                            } else {
                                callback.onNoDataFound();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
