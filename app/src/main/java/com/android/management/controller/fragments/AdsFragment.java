package com.android.management.controller.fragments;

import android.app.AlertDialog;
import android.content.Context;
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
import com.android.management.controller.activities.AdsDetailActivity;
import com.android.management.controller.activities.EpisodeDetailActivity;
import com.android.management.controller.activities.MainActivity;
import com.android.management.controller.adapter.AdsAdapter;
import com.android.management.controller.adapter.WalletAdapter;
import com.android.management.databeas.other.ViewModel;
import com.android.management.helpers.BaseFragment;
import com.android.management.helpers.Constants;
import com.android.management.model.Ads;
import com.android.management.model.Center;
import com.android.management.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public AdsFragment() {
        // Required empty public constructor
    }

    private SwipeRefreshLayout swipeToRefresh;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private TextView tv_empty;

    private ArrayList<Ads> list = new ArrayList<>();
    private AdsAdapter adapter;
    private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ads, container, false);

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

        adapter = new AdsAdapter(list, requireActivity(), model -> {
            dialogDelete(requireActivity(), model);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        initAds();

//        fab.setOnClickListener(view -> {
//            startActivity(new Intent(requireActivity(), AdsDetailActivity.class)
//                    .putExtra(Constants.KEY, Constants.TYPE_ADD)
//            );
//        });

    }

    private void initAds() {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getAllAds().observe(requireActivity(), ads -> {
            progressBar.setVisibility(View.GONE);
            swipeToRefresh.setRefreshing(false);
            if (ads.isEmpty()) {
                tv_empty.setVisibility(View.VISIBLE);
            } else {
                tv_empty.setVisibility(View.GONE);
                list.clear();
                list.addAll(ads);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void dialogDelete(Context context, Ads model) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_item)
                .setMessage(R.string.delete_item_sure)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    int isDeleted = viewModel.deleteAds(model);
                    if (isDeleted > 0) {
                        dialog.dismiss();
                    } else {
                        Toast.makeText(requireActivity(), getString(R.string.error),
                                Toast.LENGTH_SHORT).show();
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
    public void onRefresh() {
        initAds();
    }

}