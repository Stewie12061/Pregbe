package com.example.pregbe.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.ItemClickListener;
import com.example.pregbe.R;

public class SoTuanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView soTuan;
    private ItemClickListener itemClickListener;

    public SoTuanViewHolder(@NonNull View itemView) {
        super(itemView);
        soTuan = itemView.findViewById(R.id.soTuan);
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
