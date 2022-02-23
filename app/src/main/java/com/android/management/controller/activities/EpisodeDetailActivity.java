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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.management.R;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.Center;
import com.android.management.model.Episodes;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EpisodeDetailActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;
    private ImageView image;
    private ImageView imgDelete;
    private ImageView imgCamera;
    private TextInputLayout tvName;
    private TextInputEditText etName;
    private TextInputLayout tvAddress;
    private TextInputEditText etAddress;
    private TextInputLayout tvCount;
    private TextInputEditText etCount;
    private TextInputLayout tvCenter;
    private AutoCompleteTextView etCenter;
    private TextInputLayout tvBranch;
    private AutoCompleteTextView etBranch;
    private TextInputLayout tvAdmin;
    private AutoCompleteTextView etAdmin;
    private TextInputLayout tvDesc;
    private TextInputEditText etDesc;
    private AppCompatButton btnAddAdmin;
    private AppCompatButton btn_save;
    private SpinKitView progressBar;
    private FloatingActionButton fabStudent;
    private FloatingActionButton fabWallet;

    private String type = "";
    private String path = "";
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);

        initView();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        type = getIntent().getStringExtra(Constants.KEY);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        image = findViewById(R.id.episodeDetail_image);
        imgDelete = findViewById(R.id.episodeDetail_img_delete);
        imgCamera = findViewById(R.id.episodeDetail_img_camera);
        tvName = findViewById(R.id.episodeDetail_tv_name);
        etName = findViewById(R.id.episodeDetail_et_name);
        tvAddress = findViewById(R.id.episodeDetail_tv_address);
        etAddress = findViewById(R.id.episodeDetail_et_address);
        tvCount = findViewById(R.id.episodeDetail_tv_count);
        etCount = findViewById(R.id.episodeDetail_et_count);
        tvCenter = findViewById(R.id.episodeDetail_tv_center);
        etCenter = findViewById(R.id.episodeDetail_et_center);
        tvBranch = findViewById(R.id.episodeDetail_tv_branch);
        etBranch = findViewById(R.id.episodeDetail_et_branch);
        tvAdmin = findViewById(R.id.episodeDetail_tv_admin);
        etAdmin = findViewById(R.id.episodeDetail_et_admin);
        tvDesc = findViewById(R.id.episodeDetail_tv_desc);
        etDesc = findViewById(R.id.episodeDetail_et_desc);
        btnAddAdmin = findViewById(R.id.episodeDetail_btn_add_admin);
        btn_save = findViewById(R.id.episodeDetail_btn);
        progressBar = findViewById(R.id.progressBar);
        fabStudent = findViewById(R.id.fab_student);
        fabWallet = findViewById(R.id.fab_wallet);

        imgBackTool.setOnClickListener(view -> onBackPressed());

        if (type.equals(Constants.TYPE_ADD)) {
            fabStudent.setVisibility(View.GONE);
            fabWallet.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);
            tvTool.setText("اضافة حلقة جديدة");
            btn_save.setOnClickListener(view -> addEpisode());
        } else {
            tvTool.setText("تعديل بيانات الحلقة");
            Episodes model = (Episodes) getIntent().getSerializableExtra(Constants.TYPE_MODEL);
            initData(model);
            btn_save.setOnClickListener(view -> editEpisode(model));
            imgDelete.setOnClickListener(view -> dialogDelete(this, model));
            fabStudent.setOnClickListener(view -> {
                startActivity(new Intent(this, StudentActivity.class)
                        .putExtra(Constants.KEY, model.getName())
                );
            });

            fabWallet.setOnClickListener(view -> {
                startActivity(new Intent(this, WalletActivity.class)
                        .putExtra(Constants.KEY, model.getName())
                );
            });
        }

        setAdapters();

        imgCamera.setOnClickListener(v ->
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(Constants.REQUEST_GALLERY_CODE)
        );
    }

    private void setAdapters() {
        ArrayAdapter<String> centerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllCenterName());
        etCenter.setAdapter(centerAdapter);

        ArrayAdapter<String> centerBranch = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllBranchName());
        etBranch.setAdapter(centerBranch);

        ArrayAdapter<String> centerAdmin = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAdminsName());
        etAdmin.setAdapter(centerAdmin);
    }

    private void initData(Episodes model) {
        Glide.with(this).load(model.getPhoto()).placeholder(R.drawable.logo).into(image);
        etName.setText(model.getName());
        etAddress.setText(model.getAddress());
        etCount.setText(model.getNumberStudents());
        etCenter.setText(model.getCenter_name());
        etBranch.setText(model.getBranch_name());
        etAdmin.setText(model.getAdmin_name());
        etDesc.setText(model.getDescription());
    }

    private void addEpisode() {
        if (isNotEmpty(etName, tvName)
                && isNotEmpty(etAddress, tvAddress)
                && isNotEmpty(etCount, tvCount)
                && isNotEmpty(etCenter, tvCenter)
                && isNotEmpty(etBranch, tvBranch)
                && isNotEmpty(etAdmin, tvAdmin)
                && isNotEmpty(etDesc, tvDesc)
                && isImageNotEmpty(path)
        ) {
            enableElements(false);
            Episodes model = new Episodes(
                    etName.getText().toString().trim(),
                    etAdmin.getText().toString().trim(),
                    etCenter.getText().toString().trim(),
                    etBranch.getText().toString().trim(),
                    etCount.getText().toString().trim(),
                    etDesc.getText().toString().trim(),
                    etAddress.getText().toString().trim(),
                    path
            );
            long isInsert = viewModel.insertEpisodes(model);
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

    private void editEpisode(Episodes episodes) {
        if (isNotEmpty(etName, tvName)
                && isNotEmpty(etAddress, tvAddress)
                && isNotEmpty(etCount, tvCount)
                && isNotEmpty(etCenter, tvCenter)
                && isNotEmpty(etBranch, tvBranch)
                && isNotEmpty(etAdmin, tvAdmin)
                && isNotEmpty(etDesc, tvDesc)
                && isImageNotEmpty(path)
        ) {
            enableElements(false);
            Episodes model = new Episodes(
                    episodes.getId(),
                    etName.getText().toString().trim(),
                    etAdmin.getText().toString().trim(),
                    etCenter.getText().toString().trim(),
                    etBranch.getText().toString().trim(),
                    etCount.getText().toString().trim(),
                    etDesc.getText().toString().trim(),
                    etAddress.getText().toString().trim(),
                    path
            );
            int isInsert = viewModel.updateEpisodes(model);
            new Handler().postDelayed(() -> {
                if (isInsert > -1) {
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.error),
                            Toast.LENGTH_SHORT).show();
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
        etAddress.setEnabled(enable);
        etCount.setEnabled(enable);
        etCenter.setEnabled(enable);
        etDesc.setEnabled(enable);
        btnAddAdmin.setEnabled(enable);
        fabStudent.setEnabled(enable);
        fabWallet.setEnabled(enable);
    }

    private void dialogDelete(Context context, Episodes model) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_item)
                .setMessage(R.string.delete_item_sure)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    int isDeleted = viewModel.deleteEpisodes(model);
                    if (isDeleted > 0) {
                        dialog.dismiss();
                        onBackPressed();
                    } else {
                        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, (dialog, i) -> {
                    dialog.dismiss();
                })
                .setNeutralButton(R.string.cancel, (dialog, i) -> {
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