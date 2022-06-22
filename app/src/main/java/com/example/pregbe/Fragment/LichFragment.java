package com.example.pregbe.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.Adapter.DanhSachViewHolder;
import com.example.pregbe.Adapter.DatLichViewHolder;
import com.example.pregbe.Model.DatLich;
import com.example.pregbe.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;


public class LichFragment extends Fragment {

    String mDay, mMonth, mYear;
    private int mHour, mMinute;

    Button themLichKham;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference datLichRef;

    RecyclerView rvDatLich;
    Boolean isPickDay=false;

    public LichFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        datLichRef = firebaseDatabase.getReference("Dat Lich");

        CalendarView simpleCalendarView = (CalendarView) view.findViewById(R.id.calendarView); // get the reference of CalendarView

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 1, 1);
        simpleCalendarView.setMinDate(System.currentTimeMillis());
        simpleCalendarView.setMaxDate(calendar.getTimeInMillis());

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mDay = String.valueOf(dayOfMonth);
                mMonth = String.valueOf(month+1);
                mYear = String.valueOf(year);
                isPickDay=true;
            }
        });

        themLichKham = view.findViewById(R.id.themLichKham);
        themLichKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPickDay==true){
                    displayDialog();
                }
                else {
                    Toast.makeText(getContext(),"You have to pick day first",Toast.LENGTH_SHORT).show();
                }
            }

        });

        rvDatLich = view.findViewById(R.id.rvDatLich);
        rvDatLich.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return (view);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDatLich();
    }

    private void getDatLich() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();

        Query query = datLichRef.child(currentUserId);
        FirebaseRecyclerOptions<DatLich> options = new FirebaseRecyclerOptions.Builder<DatLich>().setQuery(query,DatLich.class).build();
        FirebaseRecyclerAdapter<DatLich,DatLichViewHolder> adapter = new FirebaseRecyclerAdapter<DatLich, DatLichViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DatLichViewHolder holder, int position, @NonNull DatLich model) {
                String idDatLich = getRef(position).getKey();

                datLichRef.child(currentUserId).child(idDatLich).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String tieude = snapshot.child("tieuDe").getValue().toString();
                        String ngaydat = snapshot.child("ngayDat").getValue().toString();
                        String mota = snapshot.child("moTa").getValue().toString();
                        String tgDat = snapshot.child("tgDat").getValue().toString();

                        holder.tieuDe.setText(tieude);
                        holder.ngayDat.setText(ngaydat);
                        holder.moTa.setText(mota);
                        holder.thoiGian.setText(tgDat);

                        holder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datLichRef.child(currentUserId).child(idDatLich).removeValue();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public DatLichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dat_lich,parent,false);
                DatLichViewHolder viewHolder = new DatLichViewHolder(v);
                return viewHolder;
            }
        };
        rvDatLich.setAdapter(adapter);
        adapter.startListening();
    }

    private void displayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_custom_dialog, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        LinearLayout settime = dialogView.findViewById(R.id.addTime);
        TextView edtTime = dialogView.findViewById(R.id.edtTime);
        EditText edtTieuDe = dialogView.findViewById(R.id.edtTieuDe);
        EditText edtMota = dialogView.findViewById(R.id.edtMota);
        EditText edtNgayDatLich = dialogView.findViewById(R.id.edtNgayDatLich);

        edtNgayDatLich.setText(mDay + "-" + mMonth + "-" + mYear);

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                edtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        Button xacNhan = dialogView.findViewById(R.id.addLich);
        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtTieuDe.getText().toString().trim())){
                    edtTieuDe.setError("You have to fill this information!");
                    edtTieuDe.requestFocus();
                }else if(TextUtils.isEmpty(edtMota.getText().toString().trim())){
                    edtMota.setError("You have to fill this information!");
                    edtMota.requestFocus();
                }else if(TextUtils.isEmpty(edtMota.getText().toString().trim())){
                    edtMota.setError("You have to fill this information!");
                    edtMota.requestFocus();
                }else if(TextUtils.isEmpty(edtTime.getText().toString().trim())){
                    edtTime.setError("You have to fill this information!");
                    edtTime.requestFocus();
                }else {
                    firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
                    datLichRef = firebaseDatabase.getReference("Dat Lich");

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String currentUserId = user.getUid();

                    DatLich datLich = new DatLich(edtNgayDatLich.getText().toString(),edtTime.getText().toString(),edtTieuDe.getText().toString(),edtMota.getText().toString());

                    datLichRef.child(currentUserId).push().setValue(datLich);

                    Toast.makeText(getContext(),"Đặt lịch thành công",Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }
            }
        });

        alertDialog.show();
    }

}