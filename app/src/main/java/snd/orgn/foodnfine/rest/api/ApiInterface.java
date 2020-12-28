package snd.orgn.foodnfine.rest.api;

import org.json.JSONArray;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import snd.orgn.foodnfine.rest.response.RestResponseAssignOrder;
import snd.orgn.foodnfine.rest.response.RestResponsePickup;
import snd.orgn.foodnfine.data.room.entity.AddressDetails;
import snd.orgn.foodnfine.data.room.entity.PackageDetails;
import snd.orgn.foodnfine.model.SearchResponse;
import snd.orgn.foodnfine.rest.response.AddToCartRestResponse;
import snd.orgn.foodnfine.rest.response.ElecMedRestResponse;
import snd.orgn.foodnfine.rest.response.FoodcategoryListResponse;
import snd.orgn.foodnfine.rest.response.GroceryAllProductListResponse;
import snd.orgn.foodnfine.rest.response.GroceryMainCategoryListResponse;
import snd.orgn.foodnfine.rest.response.GroceryRestResponse;
import snd.orgn.foodnfine.rest.response.KmChargesRestResponse;
import snd.orgn.foodnfine.rest.response.OrderDetailsResponse;
import snd.orgn.foodnfine.rest.response.RestRespnseDeleteCart;
import snd.orgn.foodnfine.rest.response.RestResponse;
import snd.orgn.foodnfine.rest.response.RestResponseCart;
import snd.orgn.foodnfine.rest.response.RestResponsePlaceOrder;
import snd.orgn.foodnfine.rest.response.RestrurentRestResponse;
import snd.orgn.foodnfine.rest.response.SubscriptionResponse;

public interface ApiInterface {

    /*@FormUrlEncoded
    @POST("service.php?action=signup")
    Observable<RestResponse> phoneNumberCheck(@Field("user_mobile") String userMobile, @Field("dev_key") String devKey);*/
    @FormUrlEncoded
    @POST("service.php?action=signup")
    Call<ResponseBody> phoneNumberCheck(@Field("user_mobile") String user_mobile, @Field("dev_key") String dev_key);

    @FormUrlEncoded
    @POST("service.php?action=verifyotp")
    Observable<RestResponse> verifyOtp(@Field("user_mobile") String userMobile, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("service.php?action=resendotp")
    Observable<RestResponse> resendOtp(@Field("user_mobile") String userMobile, @Field("dev_key") String devKey);

    @FormUrlEncoded
    @POST("service.php?action=user_updateprofile")
    Observable<RestResponse> updateProfile(@Field("user_nm") String userName, @Field("user_eml") String user_email,
                                           @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("service.php?action=user_profile")
    Call<ResponseBody> getUserProfile(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("service.php?action=addlocation")
    Observable<RestResponse> addAddressLocation(@Field("user_id") String userId, @Field("location") String location,
                                                @Field("house") String house, @Field("building") String building,
                                                @Field("landmark") String landmark, @Field("instruction") String instruction,
                                                @Field("contact_person") String contactPerson,
                                                @Field("contact_number") String contactNumber,
                                                @Field("location_type") String locationType, @Field("other_desc") String otherDesc);

    @FormUrlEncoded
    @POST("service.php?action=updateaddress")
    Observable<RestResponse> updateAddressLocation(@Field("user_add_id") String userAddId, @Field("user_id") String userId,
                                                   @Field("location") String location, @Field("house") String house,
                                                   @Field("building") String building, @Field("landmark") String landmark,
                                                   @Field("instruction") String instruction,
                                                   @Field("contact_person") String contactPerson,
                                                   @Field("contact_number") String contactNumber,
                                                   @Field("location_type") String locationType,
                                                   @Field("other_desc") String otherDesc);

    @FormUrlEncoded
    @POST("service.php?action=getaddress")
    Observable<RestResponse> getUserAddress(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("service.php?action=getaddress")
    Call<List<AddressDetails>> getUserAddressList(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("service.php?action=deleteaddress")
    Call<ResponseBody> deleteUserAddress(@Field("user_id") String userId, @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST("service.php?action=getcategory")
    Call<List<AddressDetails>> getCategoryList(@Field("user_id") String userId);

    @GET("service.php?action=all_package")
    Call<List<PackageDetails>> getPackageList();

    @GET("service.php?action=kmcharges")
    Observable<KmChargesRestResponse> getKmcharges();

    // for package
    @FormUrlEncoded
    @POST("service.php?action=pickup_request")
    Observable<RestResponsePickup> pickupRequest(@Field("user_id") String userId, @Field("pickup_add") String pickupAdd,
                                                 @Field("delivar_add") String deliveryAdd, @Field("package_id") String packageId,
                                                 @Field("distance") String distance, @Field("charges") String charges,
                                                 @Field("pay_stat") String payStat, @Field("pay_type") String payType,
                                                 @Field("pay_id") String payId, @Field("remark") String remark,
                                                 @Field("order_type") String orderType, @Field("estimate_value") String value,
                                                 @Field("txn_id") String txn_id, @Field("currency_code") String currency_code,
                                                 @Field("pickup_address_lat") String pick_up_lat, @Field("pickup_address_lon") String pick_up_lon);

    @POST("service.php?action=all_restaurant")
    Observable<RestrurentRestResponse> getAllRestaurant();

    //@POST("service.php?action=all_grocery")
    @POST("service.php?action=all_fnf_shop")
    Observable<GroceryRestResponse> getAllGrocery();

    @GET
    Call<ResponseBody> get_Distance_Duration(@Url String ur);

    @FormUrlEncoded
    @POST("service.php?action=getfood_cateory")
    Observable<FoodcategoryListResponse> getFoodCategoryResquest(@Field("rest_id") String restId);

    @POST("service.php?action=grocery_main_category")
    Observable<GroceryMainCategoryListResponse> getGroceryCategoryResquest();

    @FormUrlEncoded
    @POST("service.php?action=getfood_byrestid")
    Observable<FoodcategoryListResponse> getFoodResquest(@Field("rest_id") String restId,@Field("food_catid") String foodCatId);

    @FormUrlEncoded
    @POST("service.php?action=product_bycategoryid")
    Observable<GroceryAllProductListResponse> getAllProductListResquest(@Field("category_id") String categoryId);

    // Delete Cart
    @FormUrlEncoded
    @POST("service.php?loaditem=delete_cart")
    Observable<RestRespnseDeleteCart> delete_cart(@Field("user_id") String user_id, @Field("cart_id") String cart_id);

    // Add to Cart
    @FormUrlEncoded
    @POST("service.php?loaditem=addtocart")
    Observable<AddToCartRestResponse> add_to_cart(@Field("user_id") String user_id, @Field("pid") String pid,
                                                  @Field("qty") String qty, @Field("dev_key") String dev_key,
                                                  @Field("order_type") String orderType, @Field("price") Integer price,
                                                  @Field("rest_grocery") String restGrocery);

    @FormUrlEncoded
    @POST("service.php?loaditem=addtocart")
    Call<ResponseBody> addToCart(@Field("user_id") String user_id, @Field("pid") String pid,
                                            @Field("qty") String qty, @Field("dev_key") String dev_key,
                                            @Field("order_type") String orderType, @Field("price") Integer price,
                                            @Field("rest_grocery") String restGrocery);

    // Update Cart
    @FormUrlEncoded
    @POST("service.php?loaditem=update_cart")
    Observable<RestRespnseDeleteCart> update_cart(@Field("user_id") String user_id, @Field("cart_id") String cart_id,
                                                  @Field("qty") String qty, @Field("price") Integer price);

    @FormUrlEncoded
    @POST("service.php?loaditem=cartdetail")
    Observable<RestResponseCart> getCartDetails(@Field("user_id") String user_id, @Field("order_type") String orderType);

    @FormUrlEncoded
    @POST("service.php?loaditem=mdelete_cart")
    Observable<RestRespnseDeleteCart> deleteAllCartDetails(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("service.php?action=customer_view_order")
    Observable<OrderDetailsResponse> customerOrderList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("service.php?action=place_order")
    Observable<RestResponsePlaceOrder> placeOrder(@Field("order_details") JSONArray orderDetails, @Field("user_id") String userId,
                                                  @Field("remark") String remark, @Field("total_price") String totalPrice,
                                                  @Field("pay_mode") String payMode, @Field("pay_id") String payId,
                                                  @Field("pay_status") String payStatus, @Field("delivar_add") String deliveryAdd,
                                                  @Field("order_type") String orderType, @Field("rest_grocery") String restGrocery,
                                                  @Field("txn_id") String txn_id, @Field("currency_code") String currency_code,
                                                  @Field("cancellation_charge") String cancellation_charge, @Field("copoun_code") String copoun_code,
                                                  @Field("copoun_type") String copoun_type, @Field("coupon_category") String coupon_category,
                                                  @Field("coupon_id") String coupon_id, @Field("delivery_charge") String delivery_charge,
                                                  @Field("discount_amount") String discount_amount, @Field("order_actual_amount") String order_actual_amount,
                                                  @Field("remark_type") String remark_type, @Field("delivery_fixed_amt") String fixed_cost,
                                                  @Field("total_product_price") String total_product_price);

    @FormUrlEncoded
    @POST("service.php?action=place_order")
    Call<ResponseBody> _placeOrder(@Field("order_details") JSONArray orderDetails, @Field("user_id") String userId,
                                                  @Field("remark") String remark, @Field("total_price") String totalPrice,
                                                  @Field("pay_mode") String payMode, @Field("pay_id") String payId,
                                                  @Field("pay_status") String payStatus, @Field("delivar_add") String deliveryAdd,
                                                  @Field("order_type") String orderType, @Field("rest_grocery") String restGrocery,
                                                  @Field("txn_id") String txn_id, @Field("currency_code") String currency_code,
                                                  @Field("cancellation_charge") String cancellation_charge, @Field("copoun_code") String copoun_code,
                                                  @Field("copoun_type") String copoun_type, @Field("coupon_category") String coupon_category,
                                                  @Field("coupon_id") String coupon_id, @Field("delivery_charge") String delivery_charge,
                                                  @Field("discount_amount") String discount_amount, @Field("order_actual_amount") String order_actual_amount,
                                                  @Field("remark_type") String remark_type, @Field("delivery_fixed_amt") String fixed_cost,
                                                  @Field("total_product_price") String total_product_price);

    @FormUrlEncoded
    @POST("service.php?action=place_order")
    Call<ResponseBody> placeOrderNew(@Field("order_details") JSONArray orderDetails, @Field("user_id") String userId,
                                     @Field("remark") String remark, @Field("total_price") String totalPrice,
                                     @Field("pay_mode") String payMode, @Field("pay_id") String payId,
                                     @Field("pay_status") String payStatus, @Field("delivar_add") String deliveryAdd,
                                     @Field("order_type") String orderType, @Field("rest_grocery") String restGrocery,
                                     @Field("txn_id") String txn_id, @Field("currency_code") String currency_code,
                                     @Field("parent_id") String parent_id, @Field("rest_grocery_id") String rest_grocery_id,
                                     @Field("cancellation_charge") String cancellation_charge, @Field("copoun_code") String copoun_code,
                                     @Field("copoun_type") String copoun_type, @Field("coupon_category") String coupon_category,
                                     @Field("coupon_id") String coupon_id, @Field("delivery_charge") String delivery_charge,
                                     @Field("discount_amount") String discount_amount, @Field("order_actual_amount") String order_actual_amount,
                                     @Field("remark_type") String remark_type, @Field("delivery_fixed_amt") String fixed_cost,
                                     @Field("total_product_price") String total_product_price);


    // *************  currently not required **********
    // for swadesi, electronic, medicine
    @FormUrlEncoded
    @POST("service.php?action=place_order")
    Call<RestResponsePlaceOrder> placeOrderSwadesi(@Field("order_details") JSONArray orderDetails, @Field("user_id") String userId,
                                                   @Field("remark") String remark, @Field("total_price") String totalPrice,
                                                   @Field("pay_mode") String payMode, @Field("pay_id") String payId,
                                                   @Field("pay_status") String payStatus, @Field("delivar_add") String deliveryAdd,
                                                   @Field("order_type") String orderType, @Field("rest_grocery") String restGrocery,
                                                   @Field("txn_id") String txn_id, @Field("currency_code") String currency_code,
                                                   @Field("parent_id") String parent_id, @Field("rest_grocery_id") String rest_grocery_id);

    @GET("service.php?action=subscription_charges")
    Observable<SubscriptionResponse> getSubcriptionPlain();

    @FormUrlEncoded
    @POST("service.php?action=user_subscription")
    Observable<RestResponsePlaceOrder> confirmSubcriptionPlain(@Field("user_id") String userId,
                                                               @Field("subscription_plan_id") String subscription_plan_id,
                                                               @Field("subscription_plan") String subscriptionPlan,
                                                               @Field("pay_id") String payId, @Field("pay_type") String payType);

    /* New API*/
    /*@GET("service.php?action=household_essentials")
    Call<ResponseBody> household_essentials();*/
    @FormUrlEncoded
    @POST("service.php?action=household_essentials")
    Call<ResponseBody> household_essentials(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("service.php?action=cms_detail")
    Call<ResponseBody> contactus(@Field("cms_type") String userId);

    @FormUrlEncoded
    @POST("service.php?action=grocery_detail")
    Call<ResponseBody> groceryDetail(@Field("grocery_id") String grocery_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("service.php?action=resturent_detail")
    Call<ResponseBody> restaurantDetail(@Field("resturent_id") String resturent_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("service.php?action=cms_detail")
    Call<ResponseBody> about_page(@Field("cms_type") String userId);

    // for electronic & grocery shop details
    @FormUrlEncoded
    @POST("service.php?action=swadeshi_product")
    Call<ResponseBody> swadeshi_product(@Field("grocery_id") String grocery_id, @Field("user_id") String user_id);//shop_id = grocery_id

    @FormUrlEncoded
    @POST("service.php?action=swadeshi_pro_detail")
    Call<ResponseBody> swadeshi_pro_detail(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("service.php?action=all_shop")
    Observable<ElecMedRestResponse> get_all_shop(@Field("shop_type") String shop_type);

    @GET("service.php?action=all_coupon")
    Call<ResponseBody> all_coupon();

    @FormUrlEncoded
    @POST("service.php?action=global_search")
    Observable<SearchResponse> searchResult(@Field("key") String key);

    /* @FormUrlEncoded
    @POST("service.php?action=used_copon")
    Observable<CouponDTO> used_coupon(@Field("copoun_code") String copoun_code, @Field("user_id") String user_id,
                                      @Field("coupon_category") String coupon_category, @Field("res_id_gro_id") String res_id_gro_id);*/

    @FormUrlEncoded
    @POST("service.php?action=used_copon")
    Call<ResponseBody> used_coupon(@Field("copoun_code") String copoun_code, @Field("user_id") String user_id,
                                   @Field("coupon_category") String coupon_category, @Field("res_id_gro_id") String res_id_gro_id);

    @FormUrlEncoded
    @POST("service.php?action=GetAllCancelOrder")
    Call<ResponseBody> getAllCancelOrder(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("service.php?action=UserCancelOrder")
    Call<ResponseBody> cancelOrder(@Field("user_id") String user_id, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("service.php?action=delivery_boy_assign_order")
    Observable<RestResponseAssignOrder> delivery_boy_assign_order(@Field("order_number") String order_number);

}