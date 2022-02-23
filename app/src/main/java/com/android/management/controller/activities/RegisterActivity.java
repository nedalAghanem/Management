package com.android.management.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.management.R;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.User;
import com.android.management.model.Validity;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;

    private CircleImageView registerImg;
    private ImageView registerImgCamera;
    private TextInputLayout registerTvName;
    private TextInputEditText registerEtName;
    private TextInputLayout registerTvEmail;
    private TextInputEditText registerEtEmail;
    private TextInputLayout registerTvPhone;
    private TextInputEditText registerEtPhone;
    private TextInputLayout registerTvDate;
    private TextInputEditText registerEtDate;
    private TextInputLayout registerTvBranch;
    private AutoCompleteTextView registerEtBranch;
    private TextInputLayout registerTvAddress;
    private TextInputEditText registerEtAddress;
    private TextInputLayout registerTvId;
    private TextInputEditText registerEtId;
    private TextInputLayout registerTvPassword;
    private TextInputEditText registerEtPassword;
    private TextInputLayout registerTvConfPassword;
    private TextInputEditText registerEtConfPassword;
    private AppCompatButton registerBtn;
    private SpinKitView progressBar;
    private String path = "";

    Calendar calendar;
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);

        registerImg = findViewById(R.id.register_img);
        registerImgCamera = findViewById(R.id.register_img_camera);
        registerTvName = findViewById(R.id.register_tv_name);
        registerEtName = findViewById(R.id.register_et_name);
        registerTvEmail = findViewById(R.id.register_tv_email);
        registerEtEmail = findViewById(R.id.register_et_email);
        registerTvPhone = findViewById(R.id.register_tv_phone);
        registerEtPhone = findViewById(R.id.register_et_phone);
        registerTvDate = findViewById(R.id.register_tv_date);
        registerEtDate = findViewById(R.id.register_et_date);
        registerTvBranch = findViewById(R.id.register_tv_branch);
        registerEtBranch = findViewById(R.id.register_et_branch);
        registerTvAddress = findViewById(R.id.register_tv_address);
        registerEtAddress = findViewById(R.id.register_et_address);
        registerTvId = findViewById(R.id.register_tv_id);
        registerEtId = findViewById(R.id.register_et_id);
        registerTvPassword = findViewById(R.id.register_tv_password);
        registerEtPassword = findViewById(R.id.register_et_password);
        registerTvConfPassword = findViewById(R.id.register_tv_conf_password);
        registerEtConfPassword = findViewById(R.id.register_et_conf_password);
        registerBtn = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progressBar);

        imgBackTool.setOnClickListener(view -> onBackPressed());
        tvTool.setText(getString(R.string.register));

        registerBtn.setOnClickListener(view -> register());

        registerImgCamera.setOnClickListener(v ->
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(Constants.REQUEST_GALLERY_CODE)
        );

        registerEtDate.setOnClickListener(view -> {
            DatePickerDialog dialog = DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) -> {
                registerEtDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                setCalendar(year, monthOfYear, dayOfMonth);
            }, Calendar.getInstance());
            dialog.show(getSupportFragmentManager(), null);
        });

        setAdapters();
    }

    private void setAdapters() {
        ArrayAdapter<String> centerBranch = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllBranchName());
        registerEtBranch.setAdapter(centerBranch);
    }

    private void setCalendar(int year, int month, int day) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    private void register() {
        if (isNotEmpty(registerEtName, registerTvName)
                && isNotEmpty(registerEtId, registerTvId)
                && isNotEmpty(registerEtEmail, registerTvEmail)
                && isNotEmpty(registerEtPhone, registerTvPhone)
                && isNotEmpty(registerEtDate, registerTvDate)
                && isNotEmpty(registerEtAddress, registerTvAddress)
                && isNotEmpty(registerEtBranch, registerTvBranch)
                && isNotEmpty(registerEtPassword, registerTvPassword)
                && isNotEmpty(registerEtConfPassword, registerTvConfPassword)
                && isValidEmail(registerEtEmail, registerTvEmail)
                && isExistEmail(registerTvEmail, viewModel.isUserEmailExist(registerEtEmail.getText().toString().trim()))
                && isExistId(registerTvId, viewModel.isUserIdExist(registerEtId.getText().toString().trim()))
                && isConfirmPassword(registerEtPassword, registerTvPassword,
                registerEtConfPassword, registerTvConfPassword)
        ) {
            enableElements(false);
            User user = new User(
                    registerEtId.getText().toString().trim(),
                    registerEtName.getText().toString().trim(),
                    registerEtEmail.getText().toString().trim(),
                    registerEtPhone.getText().toString().trim(),
                    calendar.getTime(),
                    registerEtAddress.getText().toString().trim(),
                    registerEtBranch.getText().toString().trim(),
                    "",
                    "",
                    registerEtPassword.getText().toString().trim(),
                    Validity.Manager,
                    path
            );
            long isInsert = viewModel.insertUser(user);
            new Handler().postDelayed(() -> {
                if (isInsert > -1) {
                    Hawk.put(Constants.IS_LOGIN, true);
                    Hawk.put(Constants.USER, user);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
                enableElements(true);
            }, 1000);
        }

    }

    private void enableElements(boolean enable) {
        registerBtn.setEnabled(enable);
        if (!enable) {
            registerBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_grey));
            progressBar.setVisibility(View.VISIBLE);
        } else {
            registerBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_blue));
            progressBar.setVisibility(View.INVISIBLE);
        }

        registerImg.setEnabled(enable);
        registerImgCamera.setEnabled(enable);
        registerEtName.setEnabled(enable);
        registerEtEmail.setEnabled(enable);
        registerEtPhone.setEnabled(enable);
//        registerEtDate.setEnabled(enable);
        registerTvBranch.setEnabled(enable);
        registerEtBranch.setEnabled(enable);
        registerEtAddress.setEnabled(enable);
        registerEtId.setEnabled(enable);
        registerEtPassword.setEnabled(enable);
        registerEtConfPassword.setEnabled(enable);
        imgBackTool.setEnabled(enable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Constants.REQUEST_GALLERY_CODE) {
                Glide.with(this).load(ImagePicker.Companion.getFilePath(data)).into(registerImg);
                path = ImagePicker.Companion.getFilePath(data);
                Log.e("response -> path", "path = " + path);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}