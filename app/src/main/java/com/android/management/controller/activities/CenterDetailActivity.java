package com.android.management.controller.activities;

import android.app.AlertDialog;
import android.content.Context;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.management.R;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.Center;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CenterDetailActivity extends BaseActivity {

    private TextView tvTool;
    private ImageView imgBackTool;
    private ImageView centerImage;
    private ImageView centerImgDelete;
    private ImageView centerImgCamera;
    private TextInputLayout centerTvName;
    private TextInputEditText centerEtName;
    private TextInputLayout centerTvAddress;
    private TextInputEditText centerEtAddress;
    private TextInputLayout centerTvCount;
    private TextInputEditText centerEtCount;
    private TextInputLayout centerTvBranch;
    private AutoCompleteTextView centerEtBranch;
    private TextInputLayout centerTvAdmin;
    private AutoCompleteTextView centerEtAdmin;
    private FloatingActionButton fab;
    private AppCompatButton btn_save;
    private SpinKitView progressBar;

    private String type = "";
    private String path = "";
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_detail);

        initView();

    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        type = getIntent().getStringExtra(Constants.KEY);

        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        centerImage = findViewById(R.id.center_image);
        centerImgDelete = findViewById(R.id.center_img_delete);
        centerImgCamera = findViewById(R.id.center_img_camera);
        centerTvName = findViewById(R.id.center_tv_name);
        centerEtName = findViewById(R.id.center_et_name);
        centerTvAddress = findViewById(R.id.center_tv_address);
        centerEtAddress = findViewById(R.id.center_et_address);
        centerTvCount = findViewById(R.id.center_tv_count);
        centerEtCount = findViewById(R.id.center_et_count);
        centerTvBranch = findViewById(R.id.center_tv_branch);
        centerEtBranch = findViewById(R.id.center_et_branch);
        centerTvAdmin = findViewById(R.id.center_tv_admin);
        centerEtAdmin = findViewById(R.id.center_et_admin);
        fab = findViewById(R.id.fab);
        btn_save = findViewById(R.id.center_btn);
        progressBar = findViewById(R.id.progressBar);

        imgBackTool.setOnClickListener(view -> onBackPressed());

        if (type.equals(Constants.TYPE_ADD)) {
            fab.setVisibility(View.GONE);
            centerImgDelete.setVisibility(View.GONE);
            tvTool.setText("اضافة مركز جديد");
            btn_save.setOnClickListener(view -> addCenter());
        } else {
            tvTool.setText("تعديل بيانات المركز");
            Center model = (Center) getIntent().getSerializableExtra(Constants.TYPE_MODEL);
            initData(model);
            btn_save.setOnClickListener(view -> editCenter(model));
            centerImgDelete.setOnClickListener(view -> dialogDelete(this, model));
        }

        centerImgCamera.setOnClickListener(v ->
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(Constants.REQUEST_GALLERY_CODE)
        );

        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, EpisodesActivity.class)
                    .putExtra(Constants.KEY, centerEtName.getText().toString().trim())
            );
        });

        setAdapters();
    }

    private void setAdapters() {
        ArrayAdapter<String> centerBranch = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllBranchName());
        centerEtBranch.setAdapter(centerBranch);

        ArrayAdapter<String> centerAdmin = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getManagersName());
        centerEtAdmin.setAdapter(centerAdmin);
    }

    private void initData(Center model) {
        Glide.with(this).load(model.getLogo()).placeholder(R.drawable.logo).into(centerImage);
        centerEtName.setText(model.getName());
        centerEtAddress.setText(model.getAddress());
        centerEtCount.setText(model.getNumberEpisodes());
        centerEtBranch.setText(model.getBra_name());
        centerEtAdmin.setText(model.getManager_name());
        path = model.getLogo();
    }

    private void addCenter() {
        if (isNotEmpty(centerEtName, centerTvName)
                && isNotEmpty(centerEtAddress, centerTvAddress)
                && isNotEmpty(centerEtCount, centerTvCount)
                && isNotEmpty(centerEtBranch, centerTvBranch)
                && isNotEmpty(centerEtAdmin, centerTvAdmin)
                && isNotEmpty(centerEtAdmin, centerTvAdmin)
                && isImageNotEmpty(path)
        ) {
            enableElements(false);
            Center model = new Center(
                    centerEtName.getText().toString().trim(),
                    centerEtBranch.getText().toString().trim(),
                    path,
                    centerEtAddress.getText().toString().trim(),
                    centerEtCount.getText().toString().trim(),
                    centerEtAdmin.getText().toString().trim()
            );
            long isInsert = viewModel.insertCenter(model);
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

    private void editCenter(Center center) {
        if (isNotEmpty(centerEtName, centerTvName)
                && isNotEmpty(centerEtAddress, centerTvAddress)
                && isNotEmpty(centerEtCount, centerTvCount)
                && isNotEmpty(centerEtBranch, centerTvBranch)
                && isNotEmpty(centerEtAdmin, centerTvAdmin)
                && isNotEmpty(centerEtAdmin, centerTvAdmin)
                && isImageNotEmpty(path)
        ) {
            enableElements(false);
            Center model = new Center(
                    center.getId(),
                    centerEtName.getText().toString().trim(),
                    centerEtBranch.getText().toString().trim(),
                    path,
                    centerEtAddress.getText().toString().trim(),
                    centerEtCount.getText().toString().trim(),
                    centerEtAdmin.getText().toString().trim()
            );
            int isUpdate = viewModel.updateCenter(model);
            Log.e("response", "isUpdate = " + isUpdate);
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
        centerImgDelete.setEnabled(enable);
        centerImgCamera.setEnabled(enable);
        centerEtName.setEnabled(enable);
        centerEtAddress.setEnabled(enable);
        centerEtCount.setEnabled(enable);
        centerEtBranch.setEnabled(enable);
        centerEtAdmin.setEnabled(enable);
        fab.setEnabled(enable);
    }

    private void dialogDelete(Context context, Center model) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_item)
                .setMessage(R.string.delete_item_sure)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    int isDeleted = viewModel.deleteCenter(model);
                    if (isDeleted > 0) {
                        dialog.dismiss();
                        onBackPressed();
                    } else {
                        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, (dialog, i) -> {
                    dialog.dismiss();
                })
                .setNeutralButton(android.R.string.cancel, (dialog, i) -> {
                    dialog.dismiss();
                })
                .setIcon(R.drawable.ic_delete)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Constants.REQUEST_GALLERY_CODE) {
                Glide.with(this).load(ImagePicker.Companion.getFilePath(data)).into(centerImage);
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