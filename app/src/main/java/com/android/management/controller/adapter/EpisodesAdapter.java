package com.android.management.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.management.R;
import com.android.management.controller.activities.EpisodeDetailActivity;
import com.android.management.controller.activities.StudentActivity;
import com.android.management.controller.activities.WalletActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.Episodes;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder> {

    private Context mContext;
    private ArrayList<Episodes> list;

    public EpisodesAdapter(ArrayList<Episodes> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_episode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {
        Episodes model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class EpisodesViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private ImageView imgLocation;
        private TextView tvName;
        private TextView tvAdmin;
        private TextView tvCenter;
        private TextView tvBranch;
        private TextView tvStudents;
        private TextView tvWallets;

        private EpisodesViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.customEpisode_image);
            imgLocation = itemView.findViewById(R.id.customEpisode_img_location);
            tvName = itemView.findViewById(R.id.customEpisode_tv_name);
            tvAdmin = itemView.findViewById(R.id.customEpisode_tv_admin);
            tvCenter = itemView.findViewById(R.id.customEpisode_tv_center);
            tvBranch = itemView.findViewById(R.id.customEpisode_tv_branch);
            tvStudents = itemView.findViewById(R.id.customEpisode_tv_students);
            tvWallets = itemView.findViewById(R.id.customEpisode_tv_wallets);

        }

        @SuppressLint("SetTextI18n")
        private void bind(Episodes model) {
            Glide.with(mContext).load(model.getPhoto()).placeholder(R.drawable.logo).into(image);
            tvName.setText(model.getName());
            tvAdmin.setText(model.getAdmin_name());
            tvCenter.setText(model.getCenter_name());
            tvBranch.setText(model.getBranch_name());

            itemView.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, EpisodeDetailActivity.class)
                        .putExtra(Constants.KEY, Constants.TYPE_EDIT)
                        .putExtra(Constants.TYPE_MODEL, model)
                );
            });

            tvStudents.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, StudentActivity.class)
                        .putExtra(Constants.KEY, model.getName())
                );
            });

            tvWallets.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, WalletActivity.class)
                        .putExtra(Constants.KEY, model.getName())
                );
            });
        }
    }

}

