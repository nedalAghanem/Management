package com.android.management.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.management.R;
import com.android.management.controller.activities.CenterDetailActivity;
import com.android.management.controller.activities.StudentDetailActivity;
import com.android.management.controller.activities.TaskActivity;
import com.android.management.helpers.Constants;
import com.android.management.model.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context mContext;
    private ArrayList<User> list;

    public StudentAdapter(ArrayList<User> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_student, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
        final User model = list.get(position);
        holder.bind(model);

    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private ImageView imgLocation;
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvBranch;
        private TextView tvEpisode;
        private TextView tvCenter;
        private TextView tvTasks;
        private TextView tvRate;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.customStudent_image);
            imgLocation = itemView.findViewById(R.id.customStudent_img_location);
            tvName = itemView.findViewById(R.id.customStudent_tv_name);
            tvPhone = itemView.findViewById(R.id.customStudent_tv_phone);
            tvBranch = itemView.findViewById(R.id.customStudent_tv_branch);
            tvEpisode = itemView.findViewById(R.id.customStudent_tv_episode);
            tvCenter = itemView.findViewById(R.id.customStudent_tv_center);
            tvTasks = itemView.findViewById(R.id.customStudent_tv_tasks);
            tvRate = itemView.findViewById(R.id.customStudent_tv_rate);

        }

        @SuppressLint("SetTextI18n")
        private void bind(User model) {
            Glide.with(mContext).load(model.getPhoto()).placeholder(R.drawable.logo).into(image);

            tvName.setText(model.getFullName());
            tvPhone.setText(model.getPhone());
            tvBranch.setText(model.getBranch_name());
            tvEpisode.setText(model.getEpisode_name());
            tvCenter.setText(model.getCenter_name());

            itemView.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, StudentDetailActivity.class)
                        .putExtra(Constants.KEY, Constants.TYPE_EDIT)
                        .putExtra(Constants.TYPE_MODEL, model)
                );
            });

            tvTasks.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, TaskActivity.class)
                        .putExtra(Constants.KEY, model.getFullName()));
            });
        }
    }

}

