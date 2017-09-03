package com.app.easyretrofit.api;

import com.app.easyretrofit.model.ProductRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiService<T> {

    //Define your HTTP method along with server URL
    @GET("/posts/1")
    Call<ProductRes> get_List();

}
