package snd.orgn.foodnfine.data.shared_presferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import snd.orgn.foodnfine.rest.response.CartDatum;

public class AppSharedPreferences {

    private static AppSharedPreferences singleInstance = null;
    // Shared Preferences
    private static SharedPreferences pref;

    private static SharedPreferences.Editor editor;

    private static String SELECTED_PROPERY_ID;
    private static String SELECTED_GROCERY_REST_ID = "";

    private static Context context;
    private static final String USER_ID = "user_id";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "log";
    private static final String CURRENT_LOCATION = "current_location";
    private static final String DEV_KEY = "deb_key";
    private static final String PICKUP_ADD = "pickup_add";
    private static final String DELIVERY_ADD = "delivery_add";
    private static final String PACKAGE_ID = "package_id";
    private static final String DISTANCE = "distance";
    private static final String CHARGES = "charges";
    private static final String USER_MOBILE = "user_mobile";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";
    private static final String ITEM_QUANTITY = "quantity";
    private static final String ITEM_PRICE = "rs100";
    private static final String REMARKS = "remark";
    private static final String CART_LIST = "cart_list";
    private static final String ORDER_TYPE = "order_type";
    private static final String ESTIMATE_VALUE = "estimate";
    private static final String USER_TYPE = "user_type";
    private static final String DELIVERY_COST = "delivery_cost";
    private static final String COST1 = "cost1";
    private static final String COST2 = "cost2";
    private static final String COST3 = "cost3";
    private static final String COST4 = "cost4";
    private static final String FIXED_COST = "fixed_cost";
    private static final String PER_KM_COST = "per_km";

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "deliver_everything_preferences";

    private AppSharedPreferences() {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static AppSharedPreferences getInstance(Context c) {
        if (singleInstance == null) {
            context = c;
            singleInstance = new AppSharedPreferences();
        }
        return singleInstance;
    }

    public void saveLatitude(String latitude) {
        editor.putString(LATITUDE, latitude);
        editor.commit();
    }

    public String getLatitude() {
        return pref.getString(LATITUDE, "");
    }

    public void saveLongitude(String logitude) {
        editor.putString(LONGITUDE, logitude);
        editor.commit();
    }

    public String getLongitude() {
        return pref.getString(LONGITUDE, "");
    }

    public void saveUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public String getDeliveryCost() {
        return pref.getString(DELIVERY_COST, "");
    }

    public void saveDeliveryCost(String cost) {
        editor.putString(DELIVERY_COST, cost);
        editor.commit();
    }

    public String getCost1() {
        return pref.getString(COST1, "");
    }

    public void saveCost1(String cost) {
        editor.putString(COST1, cost);
        editor.commit();
    }

    public String getCost2() {
        return pref.getString(COST2, "");
    }

    public void saveCost2(String cost) {
        editor.putString(COST2, cost);
        editor.commit();
    }

    public String getCost3() {
        return pref.getString(COST3, "");
    }

    public void saveCost3(String cost) {
        editor.putString(COST3, cost);
        editor.commit();
    }

    public String getCost4() {
        return pref.getString(COST4, "");
    }

    public void saveCost4(String cost) {
        editor.putString(COST4, cost);
        editor.commit();
    }

    public String getFixedCost() {
        return pref.getString(FIXED_COST, "0");
    }

    public void saveFixedCost(String cost) {
        editor.putString(FIXED_COST, cost);
        editor.commit();
    }

    public String getPerKm() {
        return pref.getString(PER_KM_COST, "");
    }

    public void savePerKm(String cost) {
        editor.putString(PER_KM_COST, cost);
        editor.commit();
    }

    public String getUserId() {
        return pref.getString(USER_ID, "");
    }

    public String getDevKey() {
        return pref.getString(DEV_KEY, "");
    }

    public void saveDevKey(String dev_key) {
        editor.putString(DEV_KEY, dev_key);
        editor.commit();
    }

    public String getPickupAdd() {
        return pref.getString(PICKUP_ADD, "");
    }

    public void savePickupAdd(String pickup_add) {
        editor.putString(PICKUP_ADD, pickup_add);
        editor.commit();
    }

    public String getDeliveryAdd() {
        return pref.getString(DELIVERY_ADD, "");
    }

    public void saveDeliveryAdd(String deliver_add) {
        editor.putString(DELIVERY_ADD, deliver_add);
        editor.commit();
    }

    public String getCharges() {
        return pref.getString(CHARGES, "");
    }

    public void saveCharges(String charges) {
        editor.putString(CHARGES, charges);
        editor.commit();
    }

    public String getPackageId() {
        return pref.getString(PACKAGE_ID, "");
    }

    public void savePackageId(String package_id) {
        editor.putString(PACKAGE_ID, package_id);
        editor.commit();
    }

    public String getDistance() {
        return pref.getString(DISTANCE, "");
    }

    public void saveDistance(String distance) {
        editor.putString(DISTANCE, distance);
        editor.commit();
    }


    public String getCurrentLocation() {
        return pref.getString(CURRENT_LOCATION, "");
    }

    public void saveCurrentLocation(String current_location) {
        editor.putString(CURRENT_LOCATION, current_location);
        editor.commit();
    }


    public void clearData() {
        editor.putString(USER_ID, "");
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.clear();
        editor.commit();
    }

    public void setSeletedRestGroceryId(String restGroceryId) {
        editor.putString(SELECTED_GROCERY_REST_ID, restGroceryId);
        editor.commit();
    }

    public String getSelectedRestGroceryId() {
        return pref.getString(SELECTED_GROCERY_REST_ID, "");
    }


    public void setItemQuantity(String itemQuantity) {
        editor.putString(ITEM_QUANTITY, itemQuantity);
        editor.commit();
    }

    public String getItemQuantity() {
        return pref.getString(ITEM_QUANTITY, "");
    }

    public void setUserMobile(String userMobile) {
        editor.putString(USER_MOBILE, userMobile);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public String getUserMobile() {
        return pref.getString(USER_MOBILE, "");
    }

    public boolean getKeyIsLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public void setUserEmail(String useremail) {
        editor.putString(USER_EMAIL, useremail);
        editor.commit();
    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }

    public void setUserName(String username) {
        editor.putString(USER_NAME, username);
        editor.commit();
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public String getUsername() {
        return pref.getString(USER_NAME, "");
    }


    public void setItemPrice(String itemPrice) {
        editor.putString(ITEM_PRICE, itemPrice);
        editor.commit();
    }

    public String getItemPrice() {
        return pref.getString(ITEM_PRICE, "");
    }

    public void saveArrayList(List<CartDatum> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(CART_LIST, json);
        editor.apply();
    }

    public List<CartDatum> getArrayList() {
        Gson gson = new Gson();
        String json = pref.getString(CART_LIST, null);
        Type type = new TypeToken<List<CartDatum>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void saveRemark(String remark) {
        editor.putString(REMARKS, remark);
        editor.commit();
    }

    public String getRemarkse() {
        return pref.getString(REMARKS, "");
    }

    public void setEstimateValue(String value) {
        editor.putString(ESTIMATE_VALUE, value);
        editor.commit();
    }

    public String getEstimateValue() {
        return pref.getString(ESTIMATE_VALUE, "");
    }

    public void setOrderType(String orderType) {
        editor.putString(ORDER_TYPE, orderType);
        editor.commit();
    }

    public String getOrderType() {
        return pref.getString(ORDER_TYPE, "");
    }

    public void setUserType(String userType) {
        editor.putString(USER_TYPE, userType);
        editor.commit();
    }

    public String getUserType() {
        return pref.getString(USER_TYPE, "");
    }
}
