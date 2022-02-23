package com.android.management.controller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.management.R;
import com.android.management.controller.activities.CenterDetailActivity;
import com.android.management.controller.activities.MainActivity;
import com.android.management.controller.adapter.CenterAdapter;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseFragment;
import com.android.management.helpers.Constants;
import com.android.management.model.Center;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CenterFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeToRefresh;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private TextView tv_empty;

    private ArrayList<Center> list = new ArrayList<>();
    private CenterAdapter adapter;
    private ViewModel viewModel;

    public CenterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_center, container, false);

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

        adapter = new CenterAdapter(list, requireActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        initCenter();

        fab.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), CenterDetailActivity.class)
                    .putExtra(Constants.KEY, Constants.TYPE_ADD)
            );
        });

    }

    private void initCenter() { // لعنصر التحميل الذي يمهل المستخدم
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getAllCenter().observe(requireActivity(), centers -> {
            progressBar.setVisibility(View.GONE);
            swipeToRefresh.setRefreshing(false);
            if (centers.isEmpty()) {
                tv_empty.setVisibility(View.VISIBLE);
            } else {
                tv_empty.setVisibility(View.GONE);
                list.clear();
                list.addAll(centers);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh() {
        initCenter();
    }

}