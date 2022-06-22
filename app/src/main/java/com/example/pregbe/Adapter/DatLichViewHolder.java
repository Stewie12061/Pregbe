package com.example.pregbe.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.ItemClickListener;
import com.example.pregbe.R;

public class DatLichViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView tieuDe, moTa, ngayDat, thoiGian;
    public LinearLayout delete;
    private ItemClickListener itemClickListener;
    public DatLichViewHolder(@NonNull View itemView) {
        super(itemView);

        tieuDe = itemView.findViewById(R.id.txtTieuDe);
        moTa = itemView.findViewById(R.id.txtDes);
        ngayDat = itemView.findViewById(R.id.txtLich);
        thoiGian = itemView.findViewById(R.id.txtTime);

        delete = itemView.findViewById(R.id.deleteLich);
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
