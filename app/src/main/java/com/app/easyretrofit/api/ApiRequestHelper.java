package com.app.easyretrofit.api;

import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.app.easyretrofit.EasyRetrofitApp;
import com.app.easyretrofit.utils.Utils;

import java.io.IOException;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHelper {

    //An interface
    public static interface OnRequestComplete {
        public void onSuccess(Object object);

        public void onFailure(String apiResponse);
    }

    private static ApiRequestHelper instance;
    private EasyRetrofitApp application;
    private ApiService apiService;
    static Gson gson;
    private Retrofit retrofit;

    public static synchronized ApiRequestHelper init(EasyRetrofitApp application) {
        if (null == instance) {
            instance = new ApiRequestHelper();
            instance.setApplication(application);
            gson = new GsonBuilder().setLenient().create();
            instance.createRestAdapter();

        }
        return instance;


    }

    //API call
    public <T> void callApi(Call<T> call, final OnRequestComplete onRequestComplete) {

        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {

                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure(Utils.UNPROPER_RESPONSE);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t != null && t.getMessage() != null)
                    onRequestComplete.onFailure(Html.fromHtml(t.getMessage()) + "");
                else
                    onRequestComplete.onFailure(Utils.UNPROPER_RESPONSE);
            }

        });

    }


    //Rest Adapter
    private void createRestAdapter() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();
        httpClient.interceptors().add(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)  //Base Url
                .addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.client(httpClient.build()).build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getClient() {
        return apiService = retrofit.create(ApiService.class);
    }

    public EasyRetrofitApp getApplication() {
        return application;
    }

    public void setApplication(EasyRetrofitApp application) {
        this.application = application;
    }
}
