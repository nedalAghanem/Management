package com.android.management.helpers;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Hawk
        Hawk.init(getApplicationContext()).build();

        LocaleHelper.setLocale(getApplicationContext(), Hawk.get(Constants.LANGUAGE_TYPE, "ar"));

    }

}
