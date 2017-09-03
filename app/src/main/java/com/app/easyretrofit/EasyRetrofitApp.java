package com.app.easyretrofit;

import android.app.Application;
import android.content.Context;

import com.app.easyretrofit.api.ApiRequestHelper;
import com.app.easyretrofit.session.Preferences;

public class EasyRetrofitApp extends Application {

    private Preferences preferences;
    private ApiRequestHelper apiRequestHelper;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        doInit();
    }

    private void doInit() {
        context = getApplicationContext();
        this.preferences = new Preferences(this);
        this.apiRequestHelper = apiRequestHelper.init(this);
    }

    public static Context getContext() {
        return context;
    }

    public synchronized ApiRequestHelper getApiRequestHelper() {
        return apiRequestHelper;
    }

    public synchronized Preferences getPreferences() {
        return preferences;
    }
}
