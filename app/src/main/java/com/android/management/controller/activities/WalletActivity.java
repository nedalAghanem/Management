package com.android.management.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.management.R;
import com.android.management.controller.adapter.WalletAdapter;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.User;
import com.android.management.model.Validity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class WalletActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private TextView tvTool;
    private ImageView imgBackTool;
    private SwipeRefreshLayout swipeToRefresh;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private TextView tv_empty;

    private ArrayList<User> list = new ArrayList<>();
    private WalletAdapter adapter;
    private ViewModel viewModel;
    private String episode_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initView();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        episode_name = getIntent().getStringExtra(Constants.KEY);

        toolbar = findViewById(R.id.toolbar);
        tvTool = findViewById(R.id.tv_tool);
        imgBackTool = findViewById(R.id.img_back_tool);
        swipeToRefresh = findViewById(R.id.swipe_to_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab);
        tv_empty = findViewById(R.id.textView_empty);
        swipeToRefresh.setOnRefreshListener(this);

        tvTool.setText(getString(R.string.wallets));
        imgBackTool.setOnClickListener(view -> onBackPressed());

        adapter = new WalletAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initWallet(episode_name);

        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, WalletDetailActivity.class)
                    .putExtra(Constants.KEY, Constants.TYPE_ADD)
                    .putExtra(Constants.TYPE, episode_name)
            );
        });

    }

    private void initWallet(String episode_name) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getWalletsByEpisodes(episode_name).observe(this, episodes -> {
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
        initWallet(episode_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}