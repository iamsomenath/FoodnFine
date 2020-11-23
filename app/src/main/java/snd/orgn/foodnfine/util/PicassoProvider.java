package snd.orgn.foodnfine.util;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class PicassoProvider {

    public Picasso getPicassoWithOAuth(Context context) {

            return  new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(new OkHttpClient.Builder()
                            .addInterceptor(httpLoggingInterceptor())
                            .addInterceptor(chain -> {
                                Request newRequest = chain.request().newBuilder()
                                      //  .addHeader("authorization", OAuthUtils.getOauthTokenPhrase())
                                        .build();
                                return chain.proceed(newRequest);
                            })
                            .cache(cache(file(context)))
                            .build()))
                    .build();

    }

    public Picasso getPicasso(Context context) {

            return new Picasso
                    .Builder(context)
                    .downloader(new OkHttp3Downloader(okHttpClient(httpLoggingInterceptor(),cache(file(context))))).build();

    }

    public HttpLoggingInterceptor httpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("httpLoggingInterceptor".toUpperCase(),message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public OkHttpClient okHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public OkHttpClient okHttpClient( HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
    public OkHttpClient okHttpClient( HttpLoggingInterceptor httpLoggingInterceptor,Cache cache){
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }
    public OkHttpClient okHttpClient(Interceptor... interceptors){
        OkHttpClient.Builder okHttpClientBuilder=new OkHttpClient().newBuilder();
        for (Interceptor in:interceptors)
            okHttpClientBuilder
                    .addInterceptor(in);

        return okHttpClientBuilder
                .build();
    }

    public File file(Context context){
        File file = new File(context.getCacheDir(), "HttpCache");
        file.mkdirs();
        return file;
    }

    public Cache cache(File cacheFile){
        return new Cache(cacheFile, 10 * 1000 * 1000); //10 MB
    }
}
