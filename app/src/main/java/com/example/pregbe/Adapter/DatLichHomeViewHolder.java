package com.example.pregbe.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.ItemClickListener;
import com.example.pregbe.R;

public class DatLichHomeViewHolder extends RecyclerView.ViewHolder {
    public TextView tieuDe, moTa, ngayDat, thoiGian;

    public DatLichHomeViewHolder(@NonNull View itemView) {
        super(itemView);
        tieuDe = itemView.findViewById(R.id.txtTieuDe);
        moTa = itemView.findViewById(R.id.txtDes);
        ngayDat = itemView.findViewById(R.id.txtLich);
        thoiGian = itemView.findViewById(R.id.txtTime);
    }
}
