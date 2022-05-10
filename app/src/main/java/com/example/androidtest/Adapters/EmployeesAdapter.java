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
import com.example.androidtest.Models.EmployeeModel;
import com.example.androidtest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.ViewHolder> {

    private List<EmployeeModel> employees;
    private ItemClickListener clickListener;

    public EmployeesAdapter(List<EmployeeModel> list) {
        this.employees = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_employee, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmployeeModel employeeModel = employees.get(position);

        // Set item views based on your views and data model
        holder.text_name.setText(employeeModel.getFirstName() + " " + employeeModel.getLastName());
        holder.text_role.setText(employeeModel.getRole());
        Picasso.get().load(employeeModel.getImageURL()).into(holder.imageView);

        holder.text_id.setText(employeeModel.getEmpId());

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text_name, text_id, text_role;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_id = (TextView) itemView.findViewById(R.id.text_id);
            text_role = (TextView) itemView.findViewById(R.id.text_role);
            imageView = (ImageView) itemView.findViewById(R.id.imageView1);

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
