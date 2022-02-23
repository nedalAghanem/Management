package com.android.management.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.management.R;
import com.android.management.controller.activities.MainActivity;
import com.android.management.helpers.BaseFragment;

public class HomeFragment extends BaseFragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private TextView fHomeTvEpisode;
    private TextView fHomeTvWallet;
    private TextView fHomeTvStudent;
    private TextView fHomeTvBestWallet;
    private TextView fHomeTvBestStudent;
    private TextView fHomeTvBestEpisode;
    private TextView fHomeTvRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initView(root);

        return root;
    }

    private void initView(View root) {
        MainActivity.tv_toolbar.setText(getString(R.string.home));

        fHomeTvEpisode = root.findViewById(R.id.fHome_tv_episode);
        fHomeTvWallet = root.findViewById(R.id.fHome_tv_wallet);
        fHomeTvStudent = root.findViewById(R.id.fHome_tv_student);
        fHomeTvBestWallet = root.findViewById(R.id.fHome_tv_best_wallet);
        fHomeTvBestStudent = root.findViewById(R.id.fHome_tv_best_student);
        fHomeTvBestEpisode = root.findViewById(R.id.fHome_tv_best_episode);
        fHomeTvRate = root.findViewById(R.id.fHome_tv_rate);
    }
}