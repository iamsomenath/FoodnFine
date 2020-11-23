package snd.orgn.foodnfine.data.shared_presferences

import android.content.Context
import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import snd.orgn.foodnfine.model.data_item.Cart_Details
import java.util.*

class SessionManager(  // Context
        private val _context: Context) {
    // Shared Preferences
    private val pref: SharedPreferences

    // Editor for Shared preferences
    private val editor: SharedPreferences.Editor

    // Shared pref mode
    private val PRIVATE_MODE = 0

    /**
     * Create login session
     */
    fun createLoginSession(name: String?, email: String?) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)
        // Storing name in pref
        editor.putString(KEY_NAME, name)
        // Storing email in pref
        editor.putString(KEY_EMAIL, email)
        // commit changes
        editor.commit()
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    fun checkLogin(): Boolean {
        // Check login status
        var status = true
        if (!isLoggedIn) {
            status = false
        }
        return status
    }// return user

    /**
     * Get stored session data
     */
    val details: HashMap<String, String?>
        get() {
            val data = HashMap<String, String?>()
            data[SCHEDULE_DAYS] = pref.getString(SCHEDULE_DAYS, "0")
            data[DABBA_SESSION] = pref.getString(DABBA_SESSION, "null")
            data[USER_ADDRESS] = pref.getString(USER_ADDRESS, "null")
            data[USER_ID] = pref.getString(USER_ID, "null")
            data[USER_NAME] = pref.getString(USER_NAME, "null")
            data[USER_MOBILE] = pref.getString(USER_MOBILE, "null")
            data[USER_TYPE] = pref.getString(USER_TYPE, "null")
            data[DABBA_TOTAL] = pref.getString(DABBA_TOTAL, "0")
            data[DABBA_COUNT] = pref.getString(DABBA_COUNT, "0")
            data[GRAND_TOTAL] = pref.getString(GRAND_TOTAL, "0")
            data[AREA_CODE] = pref.getString(AREA_CODE, "null")
            data[LOCATION] = pref.getString(LOCATION, "")
            data[WALLET_BALANCE] = pref.getString(WALLET_BALANCE, "0")
            data[USER_EMAIL] = pref.getString(USER_EMAIL, "null")
            data[COUNT_SCHEDULE] = pref.getString(COUNT_SCHEDULE, "0")
            data[COUNT_SESSION] = pref.getString(COUNT_SESSION, "0")
            data[COUNT_TYPE] = pref.getString(COUNT_TYPE, "0")
            data[DELIVERY_ADDRESS] = pref.getString(DELIVERY_ADDRESS, "Add Address Details")
            data[CART_VALUE] = pref.getString(CART_VALUE, "0")
            data[PAYABLE_AMOUNT] = pref.getString(PAYABLE_AMOUNT, "0")
            data[TRANSACTION_ID] = pref.getString(TRANSACTION_ID, "0")
            data[TRANSACTION_AMOUNT] = pref.getString(TRANSACTION_AMOUNT, "0")
            data[ACCOUNT_ID] = pref.getString(ACCOUNT_ID, "0")
            data[WALLET_ADD_AMOUNT] = pref.getString(WALLET_ADD_AMOUNT, "0")
            data[REQUEST_CODE] = pref.getString(REQUEST_CODE, "0")
            data[DELIVERY_PIN] = pref.getString(DELIVERY_PIN, "0")
            data[SUBS_STATUS] = pref.getString(SUBS_STATUS, "false")
            data[DELIVERY_TIME] = pref.getString(DELIVERY_TIME, "0")
            data[DELIVERY_DATE] = pref.getString(DELIVERY_DATE, "0")
            data[REDIRECT] = pref.getString(REDIRECT, "0")
            data[REDIRECT_ADD] = pref.getString(REDIRECT_ADD, "null")
            data[MODE] = pref.getString(MODE, "specialItem")
            data[ADD_WALLET_CALL] = pref.getString(ADD_WALLET_CALL, "wallet")
            data[SUCCESS_CALL] = pref.getString(SUCCESS_CALL, "success")
            data[INDEX_LUNCH] = pref.getString(INDEX_LUNCH, "0")
            data[INDEX_DINNER] = pref.getString(INDEX_DINNER, "0")
            data[SOURCE] = pref.getString(SOURCE, "null")
            data[SOURCE_LOGIN] = pref.getString(SOURCE_LOGIN, "null")
            data[CALL] = pref.getString(CALL, "null")
            data[MESSAGE] = pref.getString(MESSAGE, "null")
            data[PAY_TYPE] = pref.getString(PAY_TYPE, "null")
            data[COUPON_STATUS] = pref.getString(COUPON_STATUS, "not applied")
            data[OFFER_TOTAL] = pref.getString(OFFER_TOTAL, GRAND_TOTAL)
            data[DEDUCTION] = pref.getString(DEDUCTION, "0")
            data[MINIMUM_AMOUNT] = pref.getString(MINIMUM_AMOUNT, "0")
            data[COUPON_ID] = pref.getString(COUPON_ID, "NA")
            data[COUPON_CODE] = pref.getString(COUPON_CODE, "null")
            data[COUPON_DESCRIPTION] = pref.getString(COUPON_DESCRIPTION, "null")
            data[GRAND_TOTAL_ORDER] = pref.getString(GRAND_TOTAL_ORDER, "null")
            data[MAIN_ITEM_CAT] = pref.getString(MAIN_ITEM_CAT, "")
            data[MAIN_CAT_ID] = pref.getString(MAIN_CAT_ID, "")
            data[DABBA_TYPE] = pref.getString(DABBA_TYPE, "")
            data[DABBA_NAME] = pref.getString(DABBA_NAME, "")
            data[DABBA_ADDR] = pref.getString(DABBA_ADDR, "")
            data[VEHICLE_NO] = pref.getString(VEHICLE_NO, "")

            // return user
            return data
        }

    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear()
        editor.commit()
    }

    fun setLogin(
            isLoggedIn: Boolean, id: String, name: String, email: String, phone: String
    ) {
        editor.putBoolean(IS_LOGIN, isLoggedIn)
        editor.putString(USER_ID, id)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.putString(USER_MOBILE, phone)
        // commit changes
        editor.commit()
    }

    private val isLoggedIn: Boolean
        private get() = pref.getBoolean(IS_LOGIN, false)

    fun userDetail(u_id: String?, u_name: String?, u_mobile: String?, u_email: String?, u_type: String?) {
        editor.putString(USER_ID, u_id)
        editor.putString(USER_NAME, u_name)
        editor.putString(USER_MOBILE, u_mobile)
        editor.putString(USER_EMAIL, u_email)
        editor.putString(USER_TYPE, u_type)
        editor.commit()
    }

    fun setLocation(uloc: String?) {
        editor.putString(LOCATION, uloc)
        editor.commit()
    }

    fun setCartCount(cnt: String?) {
        editor.putString(COUNT_SESSION, cnt)
        editor.commit()
    }

    var keyOrderType: String?
        get() = pref.getString(KEY_ORDER_TYPE, "")
        set(type) {
            editor.putString(KEY_ORDER_TYPE, type)
            editor.commit()
        }

    fun walletBalance(wb: String?) {
        editor.putString(WALLET_BALANCE, wb)
        editor.commit()
    }

    fun checkout(g_total: String?, d_total: String?) {
        editor.putString(GRAND_TOTAL, g_total)
        editor.putString(DABBA_TOTAL, d_total)
        editor.commit()
    }

    fun cartValue(cartValue: String?) {
        editor.putString(CART_VALUE, cartValue)
        editor.commit()
    }

    fun payAmt(p: String?) {
        editor.putString(PAYABLE_AMOUNT, p)
        editor.commit()
    }

    fun setCoupon(cid: String?, code: String?) {
        editor.putString(COUPON_ID, cid)
        editor.putString(COUPON_CODE, code)
        editor.commit()
    }

    fun pickupDetails(d_time_hour: String?, d_time_minutes: String?) {
        editor.putString(DELIVERY_TIME, d_time_hour)
        editor.putString(DELIVERY_DATE, d_time_minutes)
        editor.commit()
    }

    fun redirect(redirect: String?) {
        editor.putString(REDIRECT, redirect)
        editor.commit()
    }

    fun redirectAdd(redirectAdd: String?) {
        editor.putString(REDIRECT_ADD, redirectAdd)
        editor.commit()
    }

    val redirect: String?
        get() = pref.getString(REDIRECT, "redirect")

    fun addwalletCall(adc: String?) {
        editor.putString(ADD_WALLET_CALL, adc)
        editor.commit()
    }

    fun addCall(call: String?) {
        editor.putString(CALL, call)
        editor.commit()
    }

    fun addItemDescription(ITEM_CAT: String?, CAT_ID: String?) {
        editor.putString(MAIN_ITEM_CAT, ITEM_CAT)
        editor.putString(MAIN_CAT_ID, CAT_ID)
        editor.commit()
    }

    fun message(message: String?) {
        editor.putString(MESSAGE, message)
        editor.commit()
    }

    fun successCall(sc: String?) {
        editor.putString(SUCCESS_CALL, sc)
        editor.commit()
    }

    fun setUsername(uname: String?) {
        editor.putString(USER_NAME, uname)
        editor.commit()
    }

    fun setMobile(mob: String?) {
        editor.putString(USER_MOBILE, mob)
        editor.commit()
    }

    fun setVehicleNo(vehicleNo: String?) {
        editor.putString(VEHICLE_NO, vehicleNo)
        editor.commit()
    }

    fun setPayType(paytype: String?) {
        editor.putString(SOURCE_LOGIN, paytype)
        editor.commit()
    }

    fun customAdfdress(delAddress: String?) {
        editor.putString(DELIVERY_ADDRESS, delAddress)
        editor.commit()
    }

    // set email and name
    fun setEmail_Name(email: String?, name: String?) {
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_NAME, name)
        editor.commit()
    }

    // fetch email
    val keyEmail: String?
        get() = pref.getString(KEY_EMAIL, "")

    // fetch name
    val keyName: String?
        get() = pref.getString(KEY_NAME, "")

    fun saveArrayList(list: ArrayList<Cart_Details?>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("CART_LIST", json)
        editor.apply()
    }

    val arrayList: ArrayList<Cart_Details>
        get() {
            val gson = Gson()
            val json = pref.getString("CART_LIST", null)
            val type = object : TypeToken<ArrayList<Cart_Details?>?>() {}.type
            return gson.fromJson(json, type)
        }

    fun update_dhaba(dhaba_id: String?, dhaba_name: String?, dhaba_addr: String?) {
        editor.putString(DABBA_TYPE, dhaba_id)
        editor.putString(DABBA_NAME, dhaba_name)
        editor.putString(DABBA_ADDR, dhaba_addr)
        editor.apply()
    }

    var shopId: String?
        get() = pref.getString(SHOP_ID, "")
        set(shop_id) {
            editor.putString(SHOP_ID, shop_id)
            editor.commit()
        }

    companion object {
        // Sharedpref file name
        private const val PREF_NAME = "MyPref"

        // All Shared Preferences Keys
        private const val IS_LOGIN = "IsLoggedIn"

        // User name (make variable public to access from outside)
        private const val KEY_NAME = "name"

        // Email address (make variable public to access from outside)
        private const val KEY_EMAIL = "email"

        // Order type like Grocery or Medicine
        private const val KEY_ORDER_TYPE = "order-type"
        private const val SHOP_ID = "shop_id"
        const val SCHEDULE_DAYS = "scheduledays"
        const val USER_NAME = "user_name"
        const val USER_ID = "subsuser"
        const val USER_ADDRESS = "custaddress"
        const val USER_EMAIL = "custemail"
        const val AREA_CODE = "areacode"
        const val DABBA_COUNT = "dabbacount"
        const val GRAND_TOTAL = "grandtotal"
        const val GRAND_TOTAL_ORDER = "grandtotal_order"
        const val DABBA_TOTAL = "dabbatotal"
        const val DABBA_SESSION = "dabbasession"
        const val USER_MOBILE = "usermobile"
        const val LOCATION = "location"
        const val WALLET_BALANCE = "wallet_balance"
        const val DELIVERY_ADDRESS = "delivery_address"
        const val DELIVERY_PIN = "delivery_pin"
        const val USER_TYPE = "userType"
        const val CART_VALUE = "cartValue"
        const val COUNT_SCHEDULE = "countSchedule"
        const val COUNT_SESSION = "countSession"
        const val COUNT_TYPE = "countType"
        const val PAYABLE_AMOUNT = "payable_amount"
        const val TRANSACTION_ID = "transaction_id"
        const val ACCOUNT_ID = "account_id"
        const val TRANSACTION_AMOUNT = "transaction_amount"
        const val SUBS_ID = "subs_id"
        const val WALLET_ADD_AMOUNT = "wallet_add_amount"
        const val REQUEST_CODE = "requeust_code"
        const val SUBS_STATUS = "subs_status"
        const val DELIVERY_TIME = "delivery_time"
        const val DELIVERY_DATE = "delivery_date"
        const val REDIRECT = "redirect"
        const val REDIRECT_ADD = "redirect_add"
        const val MODE = "mode"
        const val ADD_WALLET_CALL = "add_wallet_call"
        const val SUCCESS_CALL = "success_call"
        const val INDEX_LUNCH = "index_lunch"
        const val INDEX_DINNER = "index_dinner"
        const val INDEX_DELIVERY = "index_delivery"
        const val SOURCE = "source"
        const val SOURCE_LOGIN = "source_login"
        const val CALL = "call"
        const val MESSAGE = "message"
        const val PAY_TYPE = "pay_type"
        const val COUPON_STATUS = "coupon_status"
        const val OFFER_TOTAL = "offer_total"
        const val DEDUCTION = "deduction"
        const val MINIMUM_AMOUNT = "minimum_amount"
        const val COUPON_ID = "coupon_id"
        const val COUPON_CODE = "coupon_code"
        const val COUPON_DESCRIPTION = "coupon_description"
        const val MAIN_ITEM_CAT = "MAIN_ITEM_CAT"
        const val MAIN_CAT_ID = "MAIN_CAT_ID"
        const val DABBA_TYPE = "dhaba_id"
        const val DABBA_NAME = "dhaba_name"
        const val DABBA_ADDR = "dhaba_addr"
        const val VEHICLE_NO = "vehicle_no"
    }

    // Constructor
    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}