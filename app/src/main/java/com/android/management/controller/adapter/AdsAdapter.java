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
import com.android.management.controller.interfaces.AdsInterface;
import com.android.management.helpers.Constants;
import com.android.management.helpers.DateConverter;
import com.android.management.model.Ads;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {

    private Context mContext;
    private ArrayList<Ads> list;
    private AdsInterface adsInterface;

    public AdsAdapter(ArrayList<Ads> list, Context mContext, AdsInterface adsInterface) {
        this.list = list;
        this.mContext = mContext;
        this.adsInterface = adsInterface;
    }

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ads, parent, false);
        return new AdsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        Ads model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class AdsViewHolder extends RecyclerView.ViewHolder {

        private ImageView customAdsImage;
        private ImageView customAdsImgDelete;
        private TextView customAdsTvPublisherName;
        private TextView customAdsTvDate;
        private TextView customAdsTvContent;

        private AdsViewHolder(@NonNull View itemView) {
            super(itemView);

            customAdsImage = itemView.findViewById(R.id.customAds_image);
            customAdsImgDelete = itemView.findViewById(R.id.customAds_img_delete);
            customAdsTvPublisherName = itemView.findViewById(R.id.customAds_tv_publisher_name);
            customAdsTvDate = itemView.findViewById(R.id.customAds_tv_date);
            customAdsTvContent = itemView.findViewById(R.id.customAds_tv_content);

        }

        @SuppressLint("SetTextI18n")
        private void bind(Ads model) {
            Glide.with(mContext).load(model.getPhoto()).placeholder(R.drawable.logo).into(customAdsImage);

            customAdsTvPublisherName.setText(model.getPublisher_name());
            customAdsTvDate.setText(DateConverter.toDate(model.getDate().getTime()) + "");
            customAdsTvContent.setText(model.getContent());

//            itemView.setOnClickListener(view -> {
//                mContext.startActivity(new Intent(mContext, CenterDetailActivity.class)
//                        .putExtra(Constants.KEY, Constants.TYPE_EDIT)
//                        .putExtra(Constants.TYPE_MODEL, model)
//                );
//            });

            customAdsImgDelete.setOnClickListener(view -> {
                adsInterface.onClick(model);
            });

        }
    }

}

