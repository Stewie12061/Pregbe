package com.example.pregbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pregbe.Model.DatLich;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Update_lich_Activity extends AppCompatActivity {

    EditText edtTieuDe, edtMota, edtNgayDatLich;
    TextView edtTime;
    LinearLayout settime;
    private int mHour, mMinute;
    String idLich;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference datLichRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lich);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        datLichRef = firebaseDatabase.getReference("Dat Lich");

        settime = findViewById(R.id.addTime);
        edtTime = findViewById(R.id.edtTime);
        edtTieuDe = findViewById(R.id.edtTieuDe);
        edtMota = findViewById(R.id.edtMota);
        edtNgayDatLich = findViewById(R.id.edtNgayDatLich);

        idLich = getIntent().getStringExtra("idLich");
        getDatLich(idLich);

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Update_lich_Activity.this,
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

        Button xacNhan = findViewById(R.id.addLich);
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


                    datLichRef.child(currentUserId).child(idLich).setValue(datLich);

                    Toast.makeText(getApplicationContext(),"Cập nhật lịch thành công",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getDatLich(String idLich) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();
        datLichRef.child(currentUserId).child(idLich).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtMota.setText(snapshot.child("moTa").getValue().toString());
                edtNgayDatLich.setText(snapshot.child("ngayDat").getValue().toString());
                edtTieuDe.setText(snapshot.child("tieuDe").getValue().toString());
                edtTime.setText(snapshot.child("tgDat").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}