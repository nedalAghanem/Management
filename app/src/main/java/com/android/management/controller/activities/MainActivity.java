package com.android.management.controller.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.management.R;
import com.android.management.controller.fragments.AdminsFragment;
import com.android.management.controller.fragments.AdsFragment;
import com.android.management.controller.fragments.CenterFragment;
import com.android.management.controller.fragments.EpisodesFragment;
import com.android.management.controller.fragments.HomeFragment;
import com.android.management.controller.fragments.StudentsFragment;
import com.android.management.controller.fragments.WalletsFragment;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.helpers.LocaleHelper;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends BaseActivity {

    public static Context context;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public static TextView tv_toolbar;

    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(this, Hawk.get(Constants.LANGUAGE_TYPE, "ar"));
        setContentView(R.layout.activity_main);

        initView(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    private void initView(Bundle savedInstanceState) {
        context = MainActivity.this;
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        tv_toolbar = findViewById(R.id.tv_tool);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_nav_icon);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        if (savedInstanceState == null) {
            tv_toolbar.setText(R.string.home);
            replaceFragment(new HomeFragment());
        }

        navigationItemSelected();

        try {
            Log.e("response", "size = " + viewModel.getManagers().getValue().size());
        } catch (Exception e) {
        }


    }

    @SuppressLint("NonConstantResourceId")
    private void navigationItemSelected() {
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_centers:
                    replaceFragment(new CenterFragment());
                    break;
                case R.id.nav_episodes:
                    replaceFragment(new EpisodesFragment());
                    break;
                case R.id.nav_wallets:
                    replaceFragment(new WalletsFragment());
                    break;
//                case R.id.nav_admins:
//                    replaceFragment(new AdminsFragment());
//                    break;
                case R.id.nav_students:
                    replaceFragment(new StudentsFragment());
                    break;
                case R.id.nav_ads:
                    replaceFragment(new AdsFragment());
                    break;
                case R.id.nav_logout:
                    Constants.logout(this);
                    break;
            }
            drawer.closeDrawers();
            return true;
        });
    }

    public static void replaceFragment(Fragment fragment) {
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_nav_host_fragment, fragment);
        transaction.addToBackStack("TAG");
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            switch (item.getItemId()) {
                case R.id.nav_setting:
                    startActivity(new Intent(this, SettingActivity.class));
                    break;
                case R.id.nav_notification:
                    break;
            }
        }
//        return toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    private Toast backToasty;
    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1)
                getSupportFragmentManager().popBackStack();
            else {
                tv_toolbar.setText(R.string.home);
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    backToasty.cancel();
                    finishAffinity();
                    return;
                } else {
                    backToasty = Toast.makeText(context, "اضغط مرة اخرى للخروج.", Toast.LENGTH_LONG);
                    backToasty.show();
                }
                backPressedTime = System.currentTimeMillis();
            }
        }
    }

}