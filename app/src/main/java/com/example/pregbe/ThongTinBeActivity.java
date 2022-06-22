package com.example.pregbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pregbe.GioiThieu.ReadWriteUserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThongTinBeActivity extends AppCompatActivity {

    private EditText UPtenBe, UPtuanTuoi, UPcurrentEmail, UPcurrnetPass;
    private RadioButton Upradiobtnseletected;
    private Button btnUpdate;

    private String tenbe, email, tuantuoi, password;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_be);

        UPtenBe = findViewById(R.id.UPedtTenBe);
        UPtuanTuoi = findViewById(R.id.UPedtTuanTuoi);
        UPcurrentEmail = findViewById(R.id.UPedtEmailLogInBe);
        UPcurrnetPass = findViewById(R.id.UPedtPasswordLogInBe);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        showProfile(firebaseUser);

        btnUpdate = findViewById(R.id.updatebtnBe);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    //fetch data and display
    private void showProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(userID).child("Baby").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null){
                    tenbe = readUserDetails.tenBaby;
                    email = firebaseUser.getEmail();
                    tuantuoi = readUserDetails.soTuan;

                    UPtenBe.setText(tenbe);
                    UPcurrentEmail.setText(email);
                    UPtuanTuoi.setText(tuantuoi);

                }else {
                    Toast.makeText(ThongTinBeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThongTinBeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {

        if (TextUtils.isEmpty(tenbe)) {
            UPtenBe.setError("You have to fill this information!");
            UPtenBe.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            UPcurrentEmail.setError("You have to fill this information!");
            UPcurrentEmail.requestFocus();
        }  else if (TextUtils.isEmpty(tuantuoi)) {
            UPtuanTuoi.setError("You have to fill this information!");
            UPtuanTuoi.requestFocus();
        }  else if (TextUtils.isEmpty(password)) {
            UPcurrnetPass.setError("You have to fill this information!");
            UPcurrnetPass.requestFocus();
        } else {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(ThongTinBeActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                tenbe = UPtenBe.getText().toString();
                                tuantuoi = UPtuanTuoi.getText().toString();

                                //enter data into firebase
                                ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(tenbe,tuantuoi);

                                //extract user reference from database
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

                                String userID = firebaseUser.getUid();
                                reference.child(userID).child("Baby").setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //Stop user from returning to UpdateProfileActivily
                                            Intent intent = new Intent(ThongTinBeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        } else {
                                            try {
                                                throw task.getException();
                                            } catch (Exception e) {
                                                Toast.makeText(ThongTinBeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ThongTinBeActivity.this, "Wrong Email or Password!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}