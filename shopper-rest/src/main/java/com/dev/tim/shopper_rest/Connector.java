package com.dev.tim.shopper_rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.aurae.retrofit.LoganSquareConverterFactory;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.Retrofit;

/**
 * Created by Tim on 12/27/2015.
 */
public class Connector {
    private Context context;
    private String host;
    private String authorizationCode;
    private ConnectivityManager connectivityManager;

    private Retrofit restAdapter;

    public Connector(Context context, String host, String authorizationCode) {
        this.host = host;
        this.authorizationCode = authorizationCode;
        this.context = context;
        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Retrofit getRestAdapter() {
        if (restAdapter == null) {
            restAdapter = new Retrofit.Builder()
                    .baseUrl(host)
                    .addConverterFactory(LoganSquareConverterFactory.create())
                    .client(getClient())
                    .build();
        }

        return restAdapter;
    }

    public ShopperAPI getShopperService() {
        return getRestAdapter().create(ShopperAPI.class);
    }

    public OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (!isNetworkAvailable()) {
                    throw new IOException(context.getString(R.string.network_issue));
                }

                Request request = chain.request();
                return chain.proceed(request);
            }
        });
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request newRequest = original.newBuilder()
                        .header("X-CZ-Authorization", authorizationCode)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(newRequest);

                return response;
            }
        });
        return client;
    }

    protected boolean isNetworkAvailable() {
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }
}
