package com.example.pregbe.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.ItemClickListener;
import com.example.pregbe.R;

public class DanhSachViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView img;
    public TextView tieude, chuthich, tgian;
    private ItemClickListener itemClickListener;
    public DanhSachViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);

        tieude = itemView.findViewById(R.id.tieude);

        chuthich = itemView.findViewById(R.id.chuthich);

        tgian = itemView.findViewById(R.id.tgian);
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
