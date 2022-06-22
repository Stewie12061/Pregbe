package com.example.pregbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class ThongTinMeActivity extends AppCompatActivity {

    private EditText UPfullname, UPphone, UPcurrentEmail, UPcurrnetPass;
    private RadioGroup UPradiogr;
    private RadioButton Upradiobtnseletected;
    private Button btnUpdate;

    private String fullname, email, phonenumber, gender, password;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_me);

        UPfullname = findViewById(R.id.UPedtFullname);
        UPphone = findViewById(R.id.UPedtPhoneNumber);
        UPcurrentEmail = findViewById(R.id.UPedtEmailLogIn);
        UPcurrnetPass = findViewById(R.id.UPedtPasswordLogIn);
        UPradiogr = findViewById(R.id.UPrdgr);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        showProfile(firebaseUser);

        btnUpdate = findViewById(R.id.updatebtn);
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

        reference.child(userID).child("Parent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null){
                    fullname = readUserDetails.fullName;
                    email = firebaseUser.getEmail();
                    phonenumber = readUserDetails.phoneNumber;
                    gender = readUserDetails.gender;

                    UPfullname.setText(fullname);
                    UPcurrentEmail.setText(email);
                    UPphone.setText(phonenumber);
                    if (gender.equals("Nam")){
                        Upradiobtnseletected = findViewById(R.id.UPradioMen);
                    }else {
                        Upradiobtnseletected = findViewById(R.id.UPradioWomen);
                    }
                    Upradiobtnseletected.setChecked(true);
                }else {
                    Toast.makeText(ThongTinMeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThongTinMeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        int selectedGenderID = UPradiogr.getCheckedRadioButtonId();
        Upradiobtnseletected = findViewById(selectedGenderID);

        String email = UPcurrentEmail.getText().toString().trim();
        String password = UPcurrnetPass.getText().toString().trim();

        String mobileRegex = "(0[3|5|7|8|9])+([0-9]{8})";
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(phonenumber);

        if (TextUtils.isEmpty(fullname)){
            UPfullname.setError("You have to fill this information!");
            UPfullname.requestFocus();
        }else if (TextUtils.isEmpty(email)){
            UPcurrentEmail.setError("You have to fill this information!");
            UPcurrentEmail.requestFocus();
        }else if (UPradiogr.getCheckedRadioButtonId() == -1){
            Upradiobtnseletected.setError("You have to select your gender!");
            Upradiobtnseletected.requestFocus();
        }else if (TextUtils.isEmpty(phonenumber)){
            UPphone.setError("You have to fill this information!");
            UPphone.requestFocus();
        }else if (phonenumber.length() != 10){
            UPphone.setError("Mobile number should be 10 digits!");
            UPphone.requestFocus();
        }else if (!mobileMatcher.find()){
            UPphone.setError("Mobile is not valid!");
            UPphone.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            UPcurrnetPass.setError("You have to fill this information!");
            UPcurrnetPass.requestFocus();
        }else {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(ThongTinMeActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                gender = Upradiobtnseletected.getText().toString();
                                fullname = UPfullname.getText().toString();
                                phonenumber = UPphone.getText().toString();

                                //enter data into firebase
                                ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(fullname,email,gender,phonenumber);

                                //extract user reference from database
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

                                String userID = firebaseUser.getUid();
                                reference.child(userID).child("Parent").setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            //Stop user from returning to UpdateProfileActivily
                                            Intent intent = new Intent(ThongTinMeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        }else {
                                            try{
                                                throw task.getException();
                                            }catch (Exception e){
                                                Toast.makeText(ThongTinMeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ThongTinMeActivity.this, "Wrong Email or Password!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}