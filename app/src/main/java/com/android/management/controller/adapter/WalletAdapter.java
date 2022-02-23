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
import com.android.management.controller.activities.EpisodesActivity;
import com.android.management.controller.activities.WalletDetailActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder> {

    private Context mContext;
    private ArrayList<User> list;

    public WalletAdapter(ArrayList<User> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_wallet, parent, false);
        return new WalletViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, final int position) {
        final User model = list.get(position);
        holder.bind(model);

    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private ImageView imgLocation;
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvBranch;
        private TextView tvEpisodes;

        private WalletViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.customWallet_image);
            imgLocation = itemView.findViewById(R.id.customWallet_img_location);
            tvName = itemView.findViewById(R.id.customWallet_tv_name);
            tvPhone = itemView.findViewById(R.id.customWallet_tv_phone);
            tvBranch = itemView.findViewById(R.id.customWallet_tv_branch);
            tvEpisodes = itemView.findViewById(R.id.customWallet_tv_episodes);

        }

        @SuppressLint("SetTextI18n")
        private void bind(User model) {
            Glide.with(mContext).load(model.getPhoto()).placeholder(R.drawable.logo).into(image);
            tvName.setText(model.getFullName());
            tvPhone.setText(model.getPhone());
            tvBranch.setText(model.getBranch_name());

            itemView.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, WalletDetailActivity.class)
                        .putExtra(Constants.KEY, Constants.TYPE_EDIT)
                        .putExtra(Constants.TYPE_MODEL, model)
                );
            });
//
//            tvEpisodes.setOnClickListener(view -> {
//                mContext.startActivity(new Intent(mContext, EpisodesActivity.class)
//                        .putExtra(Constants.KEY, Constants.TYPE_EDIT)
//                        .putExtra(Constants.TYPE, model)
//                );
//            });
        }
    }

}

