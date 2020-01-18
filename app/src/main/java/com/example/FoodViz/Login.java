package com.example.FoodViz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        TextView logo = findViewById(R.id.Logo);
        String logoText = "Food Viz";
        SpannableString modifiedLogoText = new SpannableString(logoText);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);
        modifiedLogoText.setSpan(fcsRed, 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        logo.setText(modifiedLogoText);
        final AppCompatEditText usernameInfo = findViewById(R.id.username);
        final Editable usernameParsed = usernameInfo.getText();
        final AppCompatEditText passwordInfo = findViewById(R.id.password);
        final Editable pwParsed = passwordInfo.getText();

        final AppCompatButton loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwParsed.toString().length() == 0 || usernameParsed.toString().length() == 0) {
                    Toast.makeText(Login.this, "Error: Please add your information", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                    Intent mainActivityIntent = new Intent(Login.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
            }
        });

        final AppCompatTextView signUpLink = findViewById(R.id.signup_link);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpScreen = new Intent(Login.this, Register.class);
                startActivity(signUpScreen);
                finish();
            }
        });
        }
    }