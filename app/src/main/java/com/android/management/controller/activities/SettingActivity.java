package com.android.management.controller.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.android.management.R;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.helpers.LocaleHelper;
import com.android.management.model.User;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class SettingActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;
    private TextInputLayout tvLanguage;
    private AutoCompleteTextView etLanguage;
    private TextView settingTvChangePassword;
    private TextView settingTvMessage;
    private TextView settingTvProfile;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        tvLanguage = findViewById(R.id.setting_tv_language);
        etLanguage = findViewById(R.id.setting_et_language);
        settingTvChangePassword = findViewById(R.id.setting_tv_changePassword);
        settingTvMessage = findViewById(R.id.setting_tv_message);
        settingTvProfile = findViewById(R.id.setting_tv_profile);
        tvTool.setText(getString(R.string.setting));

        setAdapters();

        imgBackTool.setOnClickListener(view -> onBackPressed());

        settingTvChangePassword.setOnClickListener(view -> showPopup());

        settingTvProfile.setOnClickListener(view -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });


    }

    private void setAdapters() {
        etLanguage.setText(Hawk.get(Constants.LANGUAGE, "English"));
        ArrayList<String> listType = new ArrayList<>();
        listType.add("English");
        listType.add("العربية");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listType);
        etLanguage.setAdapter(typeAdapter);
        etLanguage.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            switch (arg2) {
                case 0:
                    Hawk.put(Constants.LANGUAGE, "English");
                    Hawk.put(Constants.LANGUAGE_TYPE, "en");
                    LocaleHelper.setLocale(this, "en");
                    startActivity(new Intent(this, SplashActivity.class));
                    finish();
                    break;
                case 1:
                    Hawk.put(Constants.LANGUAGE, "العربية");
                    Hawk.put(Constants.LANGUAGE_TYPE, "ar");
                    LocaleHelper.setLocale(this, "ar");
                    startActivity(new Intent(this, SplashActivity.class));
                    finish();
                    break;
                default:
            }
        });
    }

    private void showPopup() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_popup_change_password);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv = dialog.findViewById(R.id.customPopupChangePass_tv);
        SpinKitView progressBar = dialog.findViewById(R.id.progressBar);
        TextInputLayout tvOldPass = dialog.findViewById(R.id.customPopupChangePass_tv_name);
        TextInputEditText etOldPass = dialog.findViewById(R.id.customPopupChangePass_et_name);
        TextInputLayout tvNewPass = dialog.findViewById(R.id.customPopupChangePass_tv_email);
        TextInputEditText etNewPass = dialog.findViewById(R.id.customPopupChangePass_et_email);
        TextInputLayout tvConfPass = dialog.findViewById(R.id.customPopupChangePass_tv_message);
        TextInputEditText etConfPass = dialog.findViewById(R.id.customPopupChangePass_et_message);
        Button btnSend = dialog.findViewById(R.id.customPopupChangePass_btn_send);

        btnSend.setOnClickListener(v -> {
            if (isNotEmpty(etOldPass, tvOldPass)
                    && isNotEmpty(etNewPass, tvNewPass)
                    && isNotEmpty(etConfPass, tvConfPass)
                    && isConfirmPassword(etConfPass, tvConfPass, etNewPass, tvNewPass)
            ) {
                User user = Hawk.get(Constants.USER, null);
                if (user != null) {
                    int isSuccesses = viewModel.changePassword(user.getId(),
                            etNewPass.getText().toString().trim());
                    if (isSuccesses > -1) {
                        Toast.makeText(this, getString(R.string.successes), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    //exporting database
    private void exportDB() {
//        FirebaseStorage  storageRef = FirebaseStorage.getInstance().reference
//                .child("database/${FirebaseAuth.getInstance().currentUser?.uid}");
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = ("//data//" + "PackageName" + "//databases//" + "DatabaseName");
                String backupDBPath = "/BackupFolder/DatabaseName";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(this, backupDB.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}