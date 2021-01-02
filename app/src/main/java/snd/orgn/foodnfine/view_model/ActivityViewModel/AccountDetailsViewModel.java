package snd.orgn.foodnfine.view_model.ActivityViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.base.BaseViewModel;
import snd.orgn.foodnfine.callbacks.CallBackUserProfile;
import snd.orgn.foodnfine.callbacks.CallbackGetAddress;
import snd.orgn.foodnfine.constant.RetrofitInstance;
import snd.orgn.foodnfine.constant.WebConstants;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.model.user_data.UserDataAddAddress;
import snd.orgn.foodnfine.model.utility.SaveAddressDetails;
import snd.orgn.foodnfine.model.utility.UserData;
import snd.orgn.foodnfine.rest.api.ApiInterface;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static snd.orgn.foodnfine.constant.AppConstants.WEBSERVICE_SUCCESS;
import static snd.orgn.foodnfine.constant.ErrorMessageConstant.NETWORK_ERROR_MESSAGE;

public class AccountDetailsViewModel extends BaseViewModel {
    private CallbackGetAddress callbackGetAddress;
    private List<AddressDetails> addressLists;
    private CallBackUserProfile callback;

    public AccountDetailsViewModel(){
        addressLists = new ArrayList<>();
    }


    public void setCallback(CallbackGetAddress callback) {
        this.callbackGetAddress = callback;
    }


    public void getaddressList(UserDataAddAddress userData) {
        makeRequest(userData);
    }

    private void makeRequest(UserDataAddAddress userData) {
                SaveAddressDetails user = new SaveAddressDetails();
                user.setUserId(userData.getUserId());
                apiCallGetAddressList(user);
    }

    private void apiCallGetAddressList(SaveAddressDetails request) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<List<AddressDetails>> call = service.getUserAddressList(request.getUserId());
        call.enqueue(new Callback<List<AddressDetails>>() {
            @Override
            public void onResponse(Call<List<AddressDetails>> call, retrofit2.Response<List<AddressDetails>> response) {
                if (response.body() != null) {
                    addressLists.clear();
                    addressLists.addAll(response.body());
                    callbackGetAddress.onSuccessGetAddress(addressLists);
                    //clearAddressDetailsInDatabase(addressLists);
                }
            }

            @Override
            public void onFailure(Call<List<AddressDetails>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callbackGetAddress.onErrorGetAddress(t.getMessage());
                // loadingDialog.hideDialog();
            }
        });
    }

    public void getUserData(CallBackUserProfile callback, String userId) {
        this.callback = callback;
        getUserDetails(userId);
    }

    @SuppressLint("CheckResult")
    private void getUserDetails(String userId) {
        callback.onStarted();
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<ResponseBody> call = service.getUserProfile(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    //Log.d("TESY", jsonObject.toString() + userId);
                    if (jsonObject.getString("status").equalsIgnoreCase(WEBSERVICE_SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray("profile");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        UserData userData = new UserData();
                        userData.setName(jsonObject1.getString("user_name"));
                        userData.setUser_id(jsonObject1.getString("userid"));
                        userData.setUser_eml(jsonObject1.getString("user_email"));
                        userData.setUserMobile(jsonObject1.getString("user_mobile"));
                        callback.onSuccess(userData);
                    } else
                        callback.onError(jsonObject.getString("stat"));

                } catch (Exception e) {
                    callback.onNetworkError(NETWORK_ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onNetworkError(NETWORK_ERROR_MESSAGE);
            }
        });
    }
}
