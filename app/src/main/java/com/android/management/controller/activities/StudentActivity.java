package com.android.management.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.management.R;
import com.android.management.controller.adapter.StudentAdapter;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tvTool;
    private ImageView imgBackTool;
    private SwipeRefreshLayout swipeToRefresh;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tv_empty;
    private FloatingActionButton fab;

    private ArrayList<User> list = new ArrayList<>();
    private StudentAdapter adapter;
    private ViewModel viewModel;
    private String episode_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        initView();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        episode_name = getIntent().getStringExtra(Constants.KEY);

        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        swipeToRefresh = findViewById(R.id.swipe_to_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        tv_empty = findViewById(R.id.textView_empty);
        fab = findViewById(R.id.fab);
        swipeToRefresh.setOnRefreshListener(this);

        tvTool.setText(getString(R.string.students));
        imgBackTool.setOnClickListener(view -> onBackPressed());

        adapter = new StudentAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        initStudent(episode_name);

        fab.setOnClickListener(view ->
                startActivity(new Intent(this, EpisodeDetailActivity.class)
                        .putExtra(Constants.KEY, Constants.TYPE_ADD)
                        .putExtra(Constants.TYPE, episode_name)
                ));

    }

    private void initStudent(String episode_name) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getStudentsByEpisodes(episode_name).observe(this, episodes -> {
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
        initStudent(episode_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}