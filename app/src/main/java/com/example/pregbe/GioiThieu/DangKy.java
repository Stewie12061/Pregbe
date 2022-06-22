package com.example.pregbe.GioiThieu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pregbe.DangKyThanhCongActivity2;
import com.example.pregbe.MainActivity;
import com.example.pregbe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DangKy extends AppCompatActivity {
    private Button signupBtn, loginBtn2;


    private EditText edtmail, edtpassword, edtfullname, edtphonenumber, edtpasswordconfirm;
    private RadioButton radioButtonselected;
    private RadioGroup radioGroup;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        loginBtn2 = findViewById(R.id.loginbtn2);
        signupBtn =  findViewById(R.id.signupbtn);

        edtmail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPassword);
        edtfullname = findViewById(R.id.edtFullname);
        edtphonenumber = findViewById(R.id.edtPhoneNumber);
        edtpasswordconfirm = findViewById(R.id.edtPasswordConfirm);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
        referenceProfile = firebaseDatabase.getReference(
                "Users");
        radioGroup = findViewById(R.id.rdgr);
        radioGroup.clearCheck();

        loginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);

                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View,String>(edtmail, "email");
                pairs[1] = new Pair<View,String>(edtpassword, "password");
                pairs[2] = new Pair<View,String >(loginBtn2, "button2");
                pairs[3] = new Pair<View,String >(signupBtn, "button");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DangKy.this,
                        pairs);
                startActivity(intent, options.toBundle());
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedGenderId = radioGroup.getCheckedRadioButtonId();
                radioButtonselected = findViewById(selectedGenderId);

                String email = edtmail.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();
                String fullname = edtfullname.getText().toString().trim();
                String phonenumber = edtphonenumber.getText().toString().trim();
                String passwordconfirm = edtpasswordconfirm.getText().toString().trim();
                String gender;

                //Validate Mobile Number using Matcher and Patter
                String mobileRegex = "(0[3|5|7|8|9])+([0-9]{8})";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(phonenumber);

                if (TextUtils.isEmpty(fullname)){
                    edtfullname.setError("You have to fill this information!");
                    edtfullname.requestFocus();
                }else if (TextUtils.isEmpty(email)){
                    edtmail.setError("You have to fill this information!");
                    edtmail.requestFocus();
                }else if (radioGroup.getCheckedRadioButtonId() == -1){
                    radioButtonselected.setError("You have to select your gender!");
                    radioButtonselected.requestFocus();
                }else if (TextUtils.isEmpty(phonenumber)){
                    edtphonenumber.setError("You have to fill this information!");
                    edtphonenumber.requestFocus();
                }else if (phonenumber.length() != 10){
                    edtphonenumber.setError("Mobile number should be 10 digits!");
                    edtphonenumber.requestFocus();
                }else if (!mobileMatcher.find()){
                    edtphonenumber.setError("Mobile is not valid!");
                    edtphonenumber.requestFocus();
                }else if (TextUtils.isEmpty(password)){
                    edtpassword.setError("You have to fill this information!");
                    edtpassword.requestFocus();
                }else if (password.length() < 6){
                    edtpassword.setError("Password should be at least 6 digits!");
                    edtpassword.requestFocus();
                }else if (TextUtils.isEmpty(passwordconfirm)){
                    edtpasswordconfirm.setError("You have to fill this information!");
                    edtpasswordconfirm.requestFocus();
                }else if (!password.equals(passwordconfirm)){
                    edtpasswordconfirm.setError("Password confirm incorrect!");
                    edtpasswordconfirm.requestFocus();
                }else {
                    gender = radioButtonselected.getText().toString();
                    //load progressbar

                    registerUser(fullname, email, gender, phonenumber, password, passwordconfirm);
                }
            }
        });

    }

    void registerUser(String fullname, String email, String gender, String phonenumber, String password,String passwordconfirm) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(fullname, email, gender, phonenumber);

                            //extracting user reference from database for "registered users"
                            referenceProfile.child(firebaseUser.getUid()).child("Parent").setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent i = new Intent(DangKy.this,
                                                DangKyThanhCongActivity2.class);
                                        startActivity(i);
                                    }else {
                                        Toast.makeText(DangKy.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(DangKy.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }}