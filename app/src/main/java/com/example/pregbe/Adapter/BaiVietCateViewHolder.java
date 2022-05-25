package com.example.pregbe.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.ItemClickListener;
import com.example.pregbe.R;

public class BaiVietCateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView name, description;
    public ImageView anhminhhoa;
    private ItemClickListener itemClickListener;


    public BaiVietCateViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.label);
        description= itemView.findViewById(R.id.description);
        anhminhhoa = itemView.findViewById(R.id.anhminhhoa);
        itemView.setOnClickListener(this);

    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getBindingAdapterPosition(),false);
    }
}
