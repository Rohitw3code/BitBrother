package com.bit.bitbrothers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText email, password;
    private Button signUpBtn;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        relativeLayout = findViewById(R.id.sign_up_relative);
        progressBar = findViewById(R.id.sign_up_progress_bar);
        email = findViewById(R.id.sign_up_email);
        password = findViewById(R.id.sign_up_password);
        signUpBtn = findViewById(R.id.sign_up_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(v -> {
            relativeLayout.setAlpha(0.5f);
            progressBar.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(email.getText().toString()) || !TextUtils.isEmpty(password.getText().toString())) {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(authResult -> {
                    relativeLayout.setAlpha(1f);
                    Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(e -> {
                    relativeLayout.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                relativeLayout.setAlpha(1f);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "email or password can't be empty", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void alreadyHaveAccount(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}