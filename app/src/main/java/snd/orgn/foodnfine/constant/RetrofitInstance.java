package snd.orgn.foodnfine.constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import snd.orgn.foodnfine.util.Constants;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = Constants.INSTANCE.getROOT_URL();

    /**
     * Create an instance of Retrofit object
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}