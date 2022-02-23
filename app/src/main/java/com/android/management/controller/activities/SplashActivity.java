package com.android.management.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.management.R;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.helpers.LocaleHelper;
import com.orhanobut.hawk.Hawk;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(getApplicationContext(), Hawk.get(Constants.LANGUAGE_TYPE, "ar"));
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (Hawk.get(Constants.IS_LOGIN, false)) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, 2000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}