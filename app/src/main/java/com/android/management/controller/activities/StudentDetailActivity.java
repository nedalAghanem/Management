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
import com.android.management.model.User;
import com.android.management.model.Validity;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.base.Converter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class StudentDetailActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;
    private ImageView image;
    private ImageView imgDelete;
    private ImageView imgCamera;
    private TextInputLayout tvName;
    private TextInputEditText etName;
    private TextInputLayout tvId;
    private TextInputEditText etId;
    private TextInputLayout tvAddress;
    private TextInputEditText etAddress;
    private TextInputLayout tvPhone;
    private TextInputEditText etPhone;
    private TextInputLayout tvPassword;
    private TextInputEditText etPassword;
    private TextInputLayout tvDate;
    private TextInputEditText etDate;
    private TextInputLayout tvBranch;
    private AutoCompleteTextView etBranch;
    private TextInputLayout tvEpisode;
    private AutoCompleteTextView etEpisode;
    private TextInputLayout tvCenter;
    private AutoCompleteTextView etCenter;
    private TextInputLayout tvWallet;
    private AutoCompleteTextView etWallet;
    private AppCompatButton btn_save;
    private SpinKitView progressBar;
    private FloatingActionButton fab;
    private Calendar calendar;
    private String type = "";
    private String path = "";
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        initView();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        type = getIntent().getStringExtra(Constants.KEY);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        image = findViewById(R.id.studentDetail_image);
        imgDelete = findViewById(R.id.studentDetail_img_delete);
        imgCamera = findViewById(R.id.studentDetail_img_camera);
        tvName = findViewById(R.id.studentDetail_tv_name);
        etName = findViewById(R.id.studentDetail_et_name);
        tvId = findViewById(R.id.studentDetail_tv_id);
        etId = findViewById(R.id.studentDetail_et_id);
        tvAddress = findViewById(R.id.studentDetail_tv_address);
        etAddress = findViewById(R.id.studentDetail_et_address);
        tvPhone = findViewById(R.id.studentDetail_tv_phone);
        etPhone = findViewById(R.id.studentDetail_et_phone);
        tvPassword = findViewById(R.id.studentDetail_tv_password);
        etPassword = findViewById(R.id.studentDetail_et_password);
        tvDate = findViewById(R.id.studentDetail_tv_date);
        etDate = findViewById(R.id.studentDetail_et_date);
        tvBranch = findViewById(R.id.studentDetail_tv_branch);
        etBranch = findViewById(R.id.studentDetail_et_branch);
        tvEpisode = findViewById(R.id.studentDetail_tv_episode);
        etEpisode = findViewById(R.id.studentDetail_et_episode);
        tvCenter = findViewById(R.id.studentDetail_tv_center);
        etCenter = findViewById(R.id.studentDetail_et_center);
        tvWallet = findViewById(R.id.studentDetail_tv_wallet);
        etWallet = findViewById(R.id.studentDetail_et_wallet);
        btn_save = findViewById(R.id.studentDetail_btn);
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab_rate);

        imgBackTool.setOnClickListener(view -> onBackPressed());

        if (type.equals(Constants.TYPE_ADD)) {
            imgDelete.setVisibility(View.GONE);
            tvTool.setText("اضافة طالب جديد");
            btn_save.setOnClickListener(view -> addStudent());
        } else {
            tvTool.setText("تعديل بيانات الطالب");
            User model = (User) getIntent().getSerializableExtra(Constants.TYPE_MODEL);
            initData(model);
            btn_save.setOnClickListener(view -> editStudent(model));
            fab.setOnClickListener(view ->
                    startActivity(new Intent(this, TaskActivity.class)
                            .putExtra(Constants.KEY, model.getFullName())
                    ));
        }

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

        ArrayAdapter<String> centerWallet = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllWalletsName());
        etWallet.setAdapter(centerWallet);
    }

    private void initData(User model) {
        Glide.with(this).load(model.getPhoto()).placeholder(R.drawable.logo).into(image);
        etName.setText(model.getFullName());
        etId.setText(model.getP_id());
        etAddress.setText(model.getAddress());
        etPhone.setText(model.getPhone());
        etPassword.setText(model.getPassword());
        etDate.setText("" + DateConverter.toDate(model.getBirthDate().getTime()));
        etBranch.setText(model.getBranch_name());
        etEpisode.setText(model.getEpisode_name());
        etCenter.setText(model.getCenter_name());
        path = model.getPhoto();
    }

    private void addStudent() {
        if (isNotEmpty(etName, tvName)
                && isNotEmpty(etId, tvId)
                && isNotEmpty(etAddress, tvAddress)
                && isNotEmpty(etPhone, tvPhone)
                && isNotEmpty(etPassword, tvPassword)
                && isNotEmpty(etBranch, tvBranch)
                && isNotEmpty(etEpisode, tvEpisode)
                && isNotEmpty(etCenter, tvCenter)
                && isImageNotEmpty(path)
        ) {
            enableElements(false);
            User model = new User(
                    etId.getText().toString().trim(),
                    etName.getText().toString().trim(),
                    "",
                    etPhone.getText().toString().trim(),
                    Calendar.getInstance().getTime(),
                    etAddress.getText().toString().trim(),
                    etBranch.getText().toString().trim(),
                    etCenter.getText().toString().trim(),
                    etEpisode.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    Validity.Student,
                    path
            );
            long isInsert = viewModel.insertUser(model);
            new Handler().postDelayed(() -> {
                if (isInsert > -1) {
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
                enableElements(true);
            }, 1000);
        }
    }

    private void editStudent(User user) {
        if (isNotEmpty(etName, tvName)
                && isNotEmpty(etId, tvId)
                && isNotEmpty(etAddress, tvAddress)
                && isNotEmpty(etPhone, tvPhone)
                && isNotEmpty(etPassword, tvPassword)
                && isNotEmpty(etBranch, tvBranch)
                && isNotEmpty(etEpisode, tvEpisode)
                && isNotEmpty(etCenter, tvCenter)
                && isImageNotEmpty(path)
        ) {
            enableElements(false);
            User model = new User(
                    user.getId(),
                    etId.getText().toString().trim(),
                    etName.getText().toString().trim(),
                    user.getEmail(),
                    etPhone.getText().toString().trim(),
                    Calendar.getInstance().getTime(),
                    etAddress.getText().toString().trim(),
                    etBranch.getText().toString().trim(),
                    etEpisode.getText().toString().trim(),
                    etCenter.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    Validity.Student,
                    path
            );
            int isUpdate = viewModel.updateUser(model);
            Log.e("response", "" + isUpdate);
            new Handler().postDelayed(() -> {
                if (isUpdate > -1) {
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
                enableElements(true);
            }, 1000);
        }
    }

    private void enableElements(boolean enable) {
        btn_save.setEnabled(enable);
        if (!enable) {
            btn_save.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_grey));
            progressBar.setVisibility(View.VISIBLE);
        } else {
            btn_save.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_blue));
            progressBar.setVisibility(View.INVISIBLE);
        }

        imgBackTool.setEnabled(enable);
        imgDelete.setEnabled(enable);
        imgCamera.setEnabled(enable);
        etName.setEnabled(enable);
        etId.setEnabled(enable);
        etAddress.setEnabled(enable);
        etPhone.setEnabled(enable);
        etPassword.setEnabled(enable);
        etBranch.setEnabled(enable);
        etEpisode.setEnabled(enable);
        etCenter.setEnabled(enable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Constants.REQUEST_GALLERY_CODE) {
                Glide.with(this).load(ImagePicker.Companion.getFilePath(data)).into(image);
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