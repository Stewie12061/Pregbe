package com.example.pregbe.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.R;

import java.util.List;

public class TuanThaiAdapter extends RecyclerView.Adapter<TuanThaiAdapter.TuanThaiViewHolder>{
    //cai nay chi can list la du
    List<String> arrayList;
    Listener listener;

    public TuanThaiAdapter(List<String> arrayList, Listener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TuanThaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tuan_thai, parent, false);
        //sua moi cho return, cai tren k sua
        return new TuanThaiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TuanThaiViewHolder holder, int position) {
        String baiviet = arrayList.get(position);
        if(baiviet == null){
            return;
        }
        //neu la so thi convert no ra string
        holder.tuanthai.setText(String.valueOf(baiviet));
        holder.tuanthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickListener(baiviet);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList == null) return 0;
        return arrayList.size();
    }
    //cho nay luon luon return ve array size

    public class TuanThaiViewHolder extends RecyclerView.ViewHolder {
        public TextView tuanthai;
        public TuanThaiViewHolder(@NonNull View view) {
            super(view);

            tuanthai = view.findViewById(R.id.textView19);

        }
    }
    public interface Listener{
        void OnClickListener(String string);
    }
}
