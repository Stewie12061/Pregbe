package com.example.pregbe.GioiThieu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pregbe.ForgetPasswordActivity;
import com.example.pregbe.MainActivity;
import com.example.pregbe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {
    private Button signupBtn2, loginBtn, FBLoginbtn, forgetPass;
    private TextInputLayout edtmail, edtpassword;
    private EditText inputEmail, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        signupBtn2 = findViewById(R.id.signupbtn2);

        edtmail = findViewById(R.id.EmailLogIn);
        edtpassword = findViewById(R.id.passwordLogIn);
        loginBtn = findViewById(R.id.loginbtn);
        FBLoginbtn = findViewById(R.id.btnFB);

        forgetPass = findViewById(R.id.forgetpass);

        inputEmail = findViewById(R.id.edtEmailLogIn);
        inputPassword = findViewById(R.id.edtPasswordLogIn);

        signupBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);

                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(edtmail, "email");
                pairs[1] = new Pair<View, String>(edtpassword, "password");
                pairs[2] = new Pair<View, String>(loginBtn, "button");
                pairs[3] = new Pair<View, String>(signupBtn2, "button2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DangNhap.this,
                        pairs);
                startActivity(intent, options.toBundle());
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }

            private void onClickSignIn() {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Email can't be blank!");
                    inputEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Password required!");
                    inputPassword.requestFocus();
                } else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {


                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(DangNhap.this, MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(DangNhap.this, "Wrong Email or Password!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhap.this, ForgetPasswordActivity.class));
            }
        });

        FBLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}