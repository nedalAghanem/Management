package com.android.management.controller.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.management.R;
import com.android.management.controller.activities.EpisodeDetailActivity;
import com.android.management.controller.activities.MainActivity;
import com.android.management.controller.adapter.StudentAdapter;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseFragment;
import com.android.management.helpers.Constants;
import com.android.management.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public AdminsFragment() {
        // Required empty public constructor
    }

    private SwipeRefreshLayout swipeToRefresh;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private TextView tv_empty;

    private ArrayList<User> list = new ArrayList<>();
    private StudentAdapter adapter;
    private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_admins, container, false);

        initView(root);

        return root;
    }


    private void initView(View root) {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        MainActivity.tv_toolbar.setText(getString(R.string.centers));

        swipeToRefresh = root.findViewById(R.id.swipe_to_refresh);
        recyclerView = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progressBar);
        fab = root.findViewById(R.id.fab);
        tv_empty = root.findViewById(R.id.textView_empty);
        swipeToRefresh.setOnRefreshListener(this);

        adapter = new StudentAdapter(list, requireActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        initEpisodes();

        fab.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), EpisodeDetailActivity.class)
                    .putExtra(Constants.KEY, Constants.TYPE_ADD)
            );
        });

    }

    private void initEpisodes() {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getStudents().observe(requireActivity(), users -> {
            progressBar.setVisibility(View.GONE);
            swipeToRefresh.setRefreshing(false);
            if (users.isEmpty()) {
                tv_empty.setVisibility(View.VISIBLE);
            } else {
                tv_empty.setVisibility(View.GONE);
                list.clear();
                list.addAll(users);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {
        initEpisodes();
    }
}