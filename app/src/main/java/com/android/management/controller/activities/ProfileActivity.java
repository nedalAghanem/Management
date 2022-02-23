package com.android.management.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
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
import com.android.management.helpers.DateConverter;
import com.android.management.model.Episodes;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;
    private CircleImageView img;
    private ImageView imgCamera;
    private TextInputLayout tvName;
    private TextInputEditText etName;
    private TextInputLayout tvEmail;
    private TextInputEditText etEmail;
    private TextInputLayout tvPhone;
    private TextInputEditText etPhone;
    private TextInputLayout tvDate;
    private TextInputEditText etDate;
    private TextInputLayout tvBranch;
    private AutoCompleteTextView etBranch;
    private TextInputLayout tvCenter;
    private AutoCompleteTextView etCenter;
    private TextInputLayout tvEpisode;
    private AutoCompleteTextView etEpisode;
    private TextInputLayout tvValidity;
    private AutoCompleteTextView etValidity;
    private TextInputLayout tvAddress;
    private TextInputEditText etAddress;
    private TextInputLayout tvId;
    private TextInputEditText etId;
    private TextInputLayout tvPassword;
    private TextInputEditText etPassword;
    private AppCompatButton profileBtn;
    private SpinKitView progressBar;
    private Calendar calendar;
    private String path = "";
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();

    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        User user = Hawk.get(Constants.USER, null);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        img = findViewById(R.id.profile_img);
        imgCamera = findViewById(R.id.profile_img_camera);
        tvName = findViewById(R.id.profile_tv_name);
        etName = findViewById(R.id.profile_et_name);
        tvEmail = findViewById(R.id.profile_tv_email);
        etEmail = findViewById(R.id.profile_et_email);
        tvPhone = findViewById(R.id.profile_tv_phone);
        etPhone = findViewById(R.id.profile_et_phone);
        tvDate = findViewById(R.id.profile_tv_date);
        etDate = findViewById(R.id.profile_et_date);

        tvBranch = findViewById(R.id.profile_tv_branch);
        etBranch = findViewById(R.id.profile_et_branch);
        tvCenter = findViewById(R.id.profile_tv_center);
        etCenter = findViewById(R.id.profile_et_center);
        tvEpisode = findViewById(R.id.profile_tv_episode);
        etEpisode = findViewById(R.id.profile_et_episode);
        tvValidity = findViewById(R.id.profile_tv_validity);
        etValidity = findViewById(R.id.profile_et_validity);

        tvAddress = findViewById(R.id.profile_tv_address);
        etAddress = findViewById(R.id.profile_et_address);
        tvId = findViewById(R.id.profile_tv_id);
        etId = findViewById(R.id.profile_et_id);
        tvPassword = findViewById(R.id.profile_tv_password);
        etPassword = findViewById(R.id.profile_et_password);
        profileBtn = findViewById(R.id.profile_btn);
        progressBar = findViewById(R.id.progressBar);

        tvTool.setText(getString(R.string.profile));

        imgBackTool.setOnClickListener(view -> onBackPressed());

        imgCamera.setOnClickListener(v ->
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(Constants.REQUEST_GALLERY_CODE)
        );

        etDate.setOnClickListener(view -> {
            DatePickerDialog dialog = DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) -> {
                etDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                setCalendar(year, monthOfYear, dayOfMonth);
            }, Calendar.getInstance());
            dialog.show(getSupportFragmentManager(), null);
        });

        setAdapters();
        initData(user);

        profileBtn.setOnClickListener(view -> editUser(user));
    }

    private void initData(User model) {
        if (model != null) {
            Glide.with(this).load(model.getPhoto()).placeholder(R.drawable.logo).into(img);
            etName.setText(model.getFullName());
            etEmail.setText(model.getEmail());
            etPhone.setText(model.getPhone());
            etDate.setText(DateConverter.toDate(model.getBirthDate().getTime()) + "");
            etBranch.setText(model.getBranch_name());
            etCenter.setText(model.getCenter_name());
            etEpisode.setText(model.getEpisode_name());
            etValidity.setText(model.getValidity().getValue() + "");
            etAddress.setText(model.getAddress());
            etId.setText(model.getP_id());
            etPassword.setText(model.getPassword());
        } else {
            Toast.makeText(this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
        }
    }

    private void editUser(User user) {
        if (isNotEmpty(etName, tvName)
                && isNotEmpty(etEmail, tvEmail)
                && isNotEmpty(etPhone, tvPhone)
                && isNotEmpty(etDate, tvDate)
                && isNotEmpty(etBranch, tvBranch)
                && isNotEmpty(etCenter, tvCenter)
                && isNotEmpty(etEpisode, tvEpisode)
                && isNotEmpty(etValidity, tvValidity)
                && isNotEmpty(etAddress, tvAddress)
                && isNotEmpty(etId, tvId)
                && isNotEmpty(etPassword, tvPassword)
                && isImageNotEmpty(path)
        ) {
            enableElements(false);
            User model = new User(
                    user.getId(),
                    etId.getText().toString().trim(),
                    etName.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etPhone.getText().toString().trim(),
                    calendar.getTime(),
                    etAddress.getText().toString().trim(),
                    etBranch.getText().toString().trim(),
                    etEpisode.getText().toString().trim(),
                    etCenter.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    Validity.valueOf(etValidity.getText().toString().trim()),
                    path
            );
            int isInsert = viewModel.updateUser(model);
            new Handler().postDelayed(() -> {
                if (isInsert > -1) {
                    Toast.makeText(this, getString(R.string.successes),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.error),
                            Toast.LENGTH_SHORT).show();
                }
                enableElements(true);
            }, 1000);
        }
    }

    private void setCalendar(int year, int month, int day) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    private void setAdapters() {
        ArrayAdapter<String> centerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllCenterName());
        etCenter.setAdapter(centerAdapter);

        ArrayAdapter<String> centerBranch = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllBranchName());
        etBranch.setAdapter(centerBranch);

        ArrayAdapter<String> centerAdmin = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllEpisodesName());
        etEpisode.setAdapter(centerAdmin);

        ArrayList<String> listValidity = new ArrayList<>();
        listValidity.add("Manager");
        listValidity.add("Admin");
        listValidity.add("Wallet");
        listValidity.add("Student");
        ArrayAdapter<String> validityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listValidity);
        etValidity.setAdapter(validityAdapter);
    }

    private void enableElements(boolean enable) {
        profileBtn.setEnabled(enable);
        if (!enable) {
            profileBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_grey));
            progressBar.setVisibility(View.VISIBLE);
        } else {
            profileBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_blue));
            progressBar.setVisibility(View.INVISIBLE);
        }

        imgBackTool.setEnabled(enable);
        imgCamera.setEnabled(enable);
        etName.setEnabled(enable);
        etEmail.setEnabled(enable);
        etPhone.setEnabled(enable);
        etCenter.setEnabled(enable);
        etDate.setEnabled(enable);
        etBranch.setEnabled(enable);
        etCenter.setEnabled(enable);
        etEpisode.setEnabled(enable);
        etValidity.setEnabled(enable);
        etAddress.setEnabled(enable);
        etId.setEnabled(enable);
        etPassword.setEnabled(enable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Constants.REQUEST_GALLERY_CODE) {
                Glide.with(this).load(ImagePicker.Companion.getFilePath(data)).into(img);
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