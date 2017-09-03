package com.app.easyretrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.app.easyretrofit.adapter.ProductAdapter;
import com.app.easyretrofit.api.ApiRequestHelper;
import com.app.easyretrofit.api.ApiService;
import com.app.easyretrofit.model.ProductRes;
import com.app.easyretrofit.utils.ConnectionDetector;
import com.app.easyretrofit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    Context context;

    EasyRetrofitApp easyRetrofitApp;

    @Bind(R.id.rv_productList)
    RecyclerView rvProductList;

    ProductAdapter productAdapter;

    private List<ProductRes> productRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = MainActivity.this;

        easyRetrofitApp = (EasyRetrofitApp) getApplication();

        productRes = new ArrayList<>();

        //Initialize RecyclerView & set Animator
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvProductList.setLayoutManager(layoutManager);
        rvProductList.setItemAnimator(new DefaultItemAnimator());

        //Call getData function
        getData();
    }

    private void getData() {

        //Check whether device is connected to internet or not
        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {
            //Show progress dialog with message
            final ProgressDialog cpd = new ProgressDialog(context);
            cpd.setMessage("Loading...");
            cpd.show();

            //Call function from api service
            ApiService apiService = easyRetrofitApp.getApiRequestHelper().getClient();
            Call<ProductRes> call = apiService.get_List();

            easyRetrofitApp.getApiRequestHelper().callApi(call, new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    cpd.dismiss();
                    ProductRes listRes = (ProductRes) object;
                    if (listRes != null) {

                        //Add fetched result into list
                        productRes.add(listRes);
                        productAdapter = new ProductAdapter(context, productRes);
                        rvProductList.setAdapter(productAdapter);

                    } else {
                        Toast.makeText(context, R.string.no_data_available, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                    cpd.dismiss();
                    Toast.makeText(context, "" + Utils.UNPROPER_RESPONSE, Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            Toast.makeText(context, "" + Utils.NO_INTERNET, Toast.LENGTH_LONG).show();

        }
    }
}
