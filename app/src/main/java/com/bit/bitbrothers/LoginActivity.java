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

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private EditText email, password;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        relativeLayout = findViewById(R.id.relative_login);
        progressBar = findViewById(R.id.login_progress_bar);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setAlpha(0.5f);
            if (!TextUtils.isEmpty(email.getText().toString()) || !TextUtils.isEmpty(password.getText().toString())) {
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(authResult -> {
                    Toast.makeText(LoginActivity.this, "welcome back", Toast.LENGTH_SHORT).show();
                    relativeLayout.setAlpha(1f);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(e -> {
                    relativeLayout.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                relativeLayout.setAlpha(1f);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Email or password can't be empty", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void createAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}