package snd.orgn.foodnfine.callbacks;

import java.util.List;

import snd.orgn.foodnfine.data.room.entity.PackageDetails;

public interface CallbackButtomSheetSelectPackage {
    void onPackageDataListFetched(List<PackageDetails> packageDetailsList);
    void onNoDataFound();
}
