package snd.orgn.foodnfine.callbacks;

public interface CallbackGetChargesInKM {
    //void onSuccessGetCharges(String oneKmCharges, String lessThan5KmCharges, String greater5kmless10km, String greterTenKmPrice);
    void onSuccessGetCharges(String fixed_cost, String per_kilometer);
    void onfailure();
}
