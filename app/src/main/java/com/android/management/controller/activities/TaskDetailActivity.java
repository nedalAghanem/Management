package com.android.management.controller.activities;

import android.annotation.SuppressLint;
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
import com.android.management.helpers.DateConverter;
import com.android.management.model.Episodes;
import com.android.management.model.Task;
import com.android.management.model.User;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskDetailActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;
    private TextInputLayout tvType;
    private AutoCompleteTextView etType;
    private TextInputLayout tvFrom;
    private TextInputEditText etFrom;
    private TextInputLayout tvTo;
    private TextInputEditText etTo;
    private TextInputLayout tvRate;
    private TextInputEditText etRate;
    private TextInputLayout tvHostName;
    private TextInputEditText etHostName;
    private TextInputLayout tvTester;
    private AutoCompleteTextView etTester;
    private TextInputLayout tvEpisode;
    private AutoCompleteTextView etEpisode;
    private TextInputLayout tvCenter;
    private AutoCompleteTextView etCenter;
    private TextInputLayout tvEndDate;
    private TextInputEditText etEndDate;
    private TextInputLayout tvNotes;
    private TextInputEditText etNotes;
    private AppCompatButton btn_save;
    private SpinKitView progressBar;
    private FloatingActionButton fab;

    private Calendar calendar;
    private String type = "";
    private String student_name = "";
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        initView();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        type = getIntent().getStringExtra(Constants.KEY);
        student_name = getIntent().getStringExtra(Constants.KEY_2);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        tvType = findViewById(R.id.taskDetail_tv_type);
        etType = findViewById(R.id.taskDetail_et_type);
        tvFrom = findViewById(R.id.taskDetail_tv_from);
        etFrom = findViewById(R.id.taskDetail_et_from);
        tvTo = findViewById(R.id.taskDetail_tv_to);
        etTo = findViewById(R.id.taskDetail_et_to);
        tvRate = findViewById(R.id.taskDetail_tv_rate);
        etRate = findViewById(R.id.taskDetail_et_rate);
        tvHostName = findViewById(R.id.taskDetail_tv_host_name);
        etHostName = findViewById(R.id.taskDetail_et_host_name);
        tvTester = findViewById(R.id.taskDetail_tv_tester);
        etTester = findViewById(R.id.taskDetail_et_tester);
        tvEpisode = findViewById(R.id.taskDetail_tv_episode);
        etEpisode = findViewById(R.id.taskDetail_et_episode);
        tvCenter = findViewById(R.id.taskDetail_tv_center);
        etCenter = findViewById(R.id.taskDetail_et_center);
        tvEndDate = findViewById(R.id.taskDetail_tv_end_date);
        etEndDate = findViewById(R.id.taskDetail_et_end_date);
        tvNotes = findViewById(R.id.taskDetail_tv_notes);
        etNotes = findViewById(R.id.taskDetail_et_notes);
        btn_save = findViewById(R.id.taskDetail_btn);
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab_delete);

        imgBackTool.setOnClickListener(view -> onBackPressed());

        if (type.equals(Constants.TYPE_ADD)) {
            fab.setVisibility(View.GONE);
            tvTool.setText("اضافة حلقة جديدة");
            btn_save.setOnClickListener(view -> addTask());
            User user = Hawk.get(Constants.USER, null);
            etHostName.setText(user.getFullName() + "");
        } else {
            tvTool.setText("تعديل بيانات الحلقة");
            Task model = (Task) getIntent().getSerializableExtra(Constants.TYPE_MODEL);
            initData(model);
            btn_save.setOnClickListener(view -> editTask(model));
            fab.setOnClickListener(view -> dialogDelete(this, model));
        }

        etEndDate.setOnClickListener(view -> {
            DatePickerDialog dialog = DatePickerDialog.newInstance((view1, year, monthOfYear, dayOfMonth) -> {
                etEndDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
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

        ArrayAdapter<String> episodeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllEpisodesName());
        etEpisode.setAdapter(episodeAdapter);

        ArrayAdapter<String> testerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, viewModel.getAllWalletsName());
        etTester.setAdapter(testerAdapter);

        ArrayList<String> listType = new ArrayList<>();
        listType.add("جزء");
        listType.add("صفحة");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listType);
        etType.setAdapter(typeAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void initData(Task model) {
        etType.setText(model.getType());
        etFrom.setText(model.getFrom());
        etTo.setText(model.getFrom());
        etRate.setText(model.getEvaluation() + "");
        etHostName.setText(model.getHost_name());
        etTester.setText(model.getTester_name());
        etEpisode.setText(model.getEpisodes_name());
        etCenter.setText(model.getCenter_name());
        etEndDate.setText(DateConverter.toDate(model.getTask_end().getTime()) + "");
        etNotes.setText(model.getNotes());
    }

    private void addTask() {
        if (isNotEmpty(etType, tvType)
                && isNotEmpty(etFrom, tvFrom)
                && isNotEmpty(etTo, tvTo)
                && isNotEmpty(etRate, tvRate)
                && isNotEmpty(etHostName, tvHostName)
                && isNotEmpty(etTester, tvTester)
                && isNotEmpty(etEpisode, tvEpisode)
                && isNotEmpty(etCenter, tvCenter)
                && isNotEmpty(etEndDate, tvEndDate)
                && isNotEmpty(etNotes, tvNotes)
        ) {
            enableElements(false);
            Task model = new Task(
                    student_name,
                    etEpisode.getText().toString().trim(),
                    etCenter.getText().toString().trim(),
                    etHostName.getText().toString().trim(),
                    etTester.getText().toString().trim(),
                    calendar.getTime(),
                    etFrom.getText().toString().trim(),
                    etTo.getText().toString().trim(),
                    etType.getText().toString().trim(),
                    Integer.parseInt(etRate.getText().toString().trim()),
                    etNotes.getText().toString().trim()
            );
            long isInsert = viewModel.insertTask(model);
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

    private void editTask(Task task) {
        if (isNotEmpty(etType, tvType)
                && isNotEmpty(etFrom, tvFrom)
                && isNotEmpty(etTo, tvTo)
                && isNotEmpty(etRate, tvRate)
                && isNotEmpty(etHostName, tvHostName)
                && isNotEmpty(etTester, tvTester)
                && isNotEmpty(etEpisode, tvEpisode)
                && isNotEmpty(etCenter, tvCenter)
                && isNotEmpty(etEndDate, tvEndDate)
                && isNotEmpty(etNotes, tvNotes)
        ) {
            enableElements(false);
            Task model = new Task(
                    task.getId(),
                    task.getStudent_name(),
                    etEpisode.getText().toString().trim(),
                    etCenter.getText().toString().trim(),
                    etHostName.getText().toString().trim(),
                    etTester.getText().toString().trim(),
                    calendar.getTime(),
                    etFrom.getText().toString().trim(),
                    etTo.getText().toString().trim(),
                    etType.getText().toString().trim(),
                    Integer.parseInt(etRate.getText().toString().trim()),
                    etNotes.getText().toString().trim()
            );
            int isInsert = viewModel.updateTask(model);
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
        etType.setEnabled(enable);
        etFrom.setEnabled(enable);
        etTo.setEnabled(enable);
        etRate.setEnabled(enable);
        etTester.setEnabled(enable);
        etEpisode.setEnabled(enable);
        etCenter.setEnabled(enable);
        etEndDate.setEnabled(enable);
        etNotes.setEnabled(enable);
    }

    private void dialogDelete(Context context, Task model) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_item)
                .setMessage(R.string.delete_item_sure)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    int isDeleted = viewModel.deleteTask(model);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}