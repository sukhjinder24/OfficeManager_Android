package com.example.androidtest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.ItemClickListener;
import com.example.androidtest.Models.TaskModel;
import com.example.androidtest.R;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<TaskModel> tasks;
    private ItemClickListener clickListener;

    public TasksAdapter(List<TaskModel> list) {
        this.tasks = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_task, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TaskModel model = tasks.get(position);

        // Set item views based on your views and data model
        holder.text_title.setText(model.getTitle());
        holder.text_task_id.setText(model.getTaskId());
        holder.text_emp_id.setText(model.getEmpId());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text_title, text_task_id, text_emp_id;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_title = (TextView) itemView.findViewById(R.id.text_title);
            text_task_id = (TextView) itemView.findViewById(R.id.text_task_id);
            text_emp_id = (TextView) itemView.findViewById(R.id.text_emp_id);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getAdapterPosition());
        }
    }
}
