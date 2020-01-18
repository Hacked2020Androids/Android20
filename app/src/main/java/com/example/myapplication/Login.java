package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
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
        String logoText = "HackED 2020";
        SpannableString modifiedLogoText = new SpannableString(logoText);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);
        modifiedLogoText.setSpan(fcsRed, 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        logo.setText(modifiedLogoText);

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
