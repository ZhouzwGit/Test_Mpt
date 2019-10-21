package com.mpt.hxqh.mpt_project.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.mpt.hxqh.mpt_project.unit.SSLUtil;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by think on 2015/8/11.
 */

public class BaseApplication extends Application {

    private String username;
    private static BaseApplication mContext;

    private static Context context;

    private String OrderResult;
    public static SSLSocketFactory socketFactory = null;
    private static File file = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderResult() {
        return OrderResult;
    }

    public void setOrderResult(String orderResult) {
        OrderResult = orderResult;
    }

    public static BaseApplication getInstance(){
        return mContext;
    }

//    public AndroidClientService getWsService() {
//        return new AndroidClientService(Constants.getWsUrl(this));
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        context = this.getApplicationContext();
        if (socketFactory == null) {
            socketFactory = SSLUtil.getSSLSocketFactory(BaseApplication.class.getClassLoader().getResourceAsStream("raw/ceritify.cer"));
        }
    }


    public static Context getContext() {
        return context;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
