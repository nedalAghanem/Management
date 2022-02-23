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
import com.android.management.controller.activities.CenterDetailActivity;
import com.android.management.controller.activities.EpisodesActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.Center;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.CenterViewHolder> {

    private Context mContext;
    private ArrayList<Center> list;

    public CenterAdapter(ArrayList<Center> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CenterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_center, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CenterViewHolder holder, final int position) {
        final Center model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class CenterViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView imgLocation;
        TextView tvName;
        TextView tvAddress;
        TextView tvBranch;
        TextView tvEpisodes;

        private CenterViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.customCenter_image);
            imgLocation = itemView.findViewById(R.id.customCenter_img_location);
            tvName = itemView.findViewById(R.id.customCenter_tv_name);
            tvAddress = itemView.findViewById(R.id.customCenter_tv_address);
            tvBranch = itemView.findViewById(R.id.customCenter_tv_branch);
            tvEpisodes = itemView.findViewById(R.id.customCenter_tv_episodes);

        }

        @SuppressLint("SetTextI18n")
        private void bind(Center model) {
            Glide.with(mContext).load(model.getLogo()).placeholder(R.drawable.logo).into(image);

            tvName.setText(model.getName());
            tvAddress.setText(model.getAddress());
            tvBranch.setText(model.getBra_name());

            itemView.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, CenterDetailActivity.class)
                        .putExtra(Constants.KEY, Constants.TYPE_EDIT)
                        .putExtra(Constants.TYPE_MODEL, model)
                );
            });

            tvEpisodes.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, EpisodesActivity.class)
                        .putExtra(Constants.KEY, tvName.getText().toString().trim())
                );
            });
        }
    }

}

