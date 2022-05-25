package com.example.pregbe.Music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.R;

import java.util.List;

public class NhacAdapter extends RecyclerView.Adapter<NhacAdapter.NhacViewHolder> {
  List<com.example.pregbe.Music.Nhac> listNhac;
  Listener listener;

  public NhacAdapter(List<com.example.pregbe.Music.Nhac> listNhac, Listener listener) {
    this.listNhac = listNhac;
    this.listener=listener;
  }
  @NonNull
  @Override
  public NhacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhac, parent,false);



    return new NhacViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull NhacViewHolder holder, int position) {
    com.example.pregbe.Music.Nhac nhac = listNhac.get(position);
    holder.namesongTv.setText(nhac.getSong());
    holder.timeTv.setText(nhac.getTime());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        listener.Listener(nhac);
      }
    });
  }

  @Override
  public int getItemCount( ) {
    return listNhac.size();
  }

  public class NhacViewHolder extends RecyclerView.ViewHolder{
 TextView namesongTv,timeTv;

    public NhacViewHolder(@NonNull View itemView) {
      super(itemView);
      namesongTv = itemView.findViewById(R.id.namesongTv);
     timeTv = itemView.findViewById(R.id.timeTv);
    }
  }

interface Listener {
    void Listener (com.example.pregbe.Music.Nhac nhac);
}

}
