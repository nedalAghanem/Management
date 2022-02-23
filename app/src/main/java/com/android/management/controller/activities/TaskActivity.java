package com.android.management.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.management.R;
import com.android.management.controller.adapter.EpisodesAdapter;
import com.android.management.controller.adapter.TaskAdapter;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;
    private SwipeRefreshLayout swipeToRefresh;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private TextView tv_empty;

    private ArrayList<Task> list = new ArrayList<>();
    private TaskAdapter adapter;
    private ViewModel viewModel;
    private String student_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initView();

    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        student_name = getIntent().getStringExtra(Constants.KEY);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        swipeToRefresh = findViewById(R.id.swipe_to_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab);
        tv_empty = findViewById(R.id.textView_empty);
        swipeToRefresh.setOnRefreshListener(this);

        tvTool.setText(getString(R.string.episodes));
        imgBackTool.setOnClickListener(view -> onBackPressed());

        adapter = new TaskAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        initTask(student_name);

        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, TaskDetailActivity.class)
                    .putExtra(Constants.KEY, Constants.TYPE_ADD)
                    .putExtra(Constants.KEY_2, student_name)
            );
        });

    }

    private void initTask(String student_name) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getAllTaskByName(student_name).observe(this, episodes -> {
            progressBar.setVisibility(View.GONE);
            swipeToRefresh.setRefreshing(false);
            if (episodes.isEmpty()) {
                tv_empty.setVisibility(View.VISIBLE);
            } else {
                tv_empty.setVisibility(View.GONE);
                list.clear();
                list.addAll(episodes);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {
        initTask(student_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}