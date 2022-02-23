package com.android.management.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.management.R;
import com.android.management.controller.activities.TaskDetailActivity;
import com.android.management.helpers.Constants;
import com.android.management.helpers.DateConverter;
import com.android.management.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context mContext;
    private ArrayList<Task> list;

    public TaskAdapter(ArrayList<Task> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView customTaskTvDate;
        private TextView customTaskTvHostName;
        private TextView customTaskTvNotes;
        private TextView customTaskTvRate;

        private TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            customTaskTvDate = itemView.findViewById(R.id.customTask_tv_date);
            customTaskTvHostName = itemView.findViewById(R.id.customTask_tv_host_name);
            customTaskTvNotes = itemView.findViewById(R.id.customTask_tv_notes);
            customTaskTvRate = itemView.findViewById(R.id.customTask_tv_rate);

        }

        @SuppressLint("SetTextI18n")
        private void bind(Task model) {
            customTaskTvDate.setText(DateConverter.toDate(model.getTask_end().getTime()) + "");
            customTaskTvHostName.setText(model.getHost_name());
            customTaskTvNotes.setText(model.getNotes());
            customTaskTvRate.setText(model.getEvaluation() + "");

            itemView.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, TaskDetailActivity.class)
                        .putExtra(Constants.KEY, Constants.TYPE_EDIT)
                        .putExtra(Constants.TYPE_MODEL, model)
                );
            });

        }
    }

}

