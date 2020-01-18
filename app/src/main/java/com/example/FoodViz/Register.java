package com.example.FoodViz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        final AppCompatEditText usernameInfo = findViewById(R.id.userName);
        final Editable usernameParsed = usernameInfo.getText();
        final AppCompatEditText passwordInfo = findViewById(R.id.passWord);
        final Editable pwParsed = passwordInfo.getText();
        final EditText confirmPW = findViewById(R.id.password_veri);
        final Editable pwVeriParsed = confirmPW.getText();

        final AppCompatButton registerBtn = findViewById(R.id.signup_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pwParsed.toString().isEmpty() || usernameParsed.toString().isEmpty() || pwVeriParsed.toString().isEmpty()) {
                    Toast.makeText(Register.this, "Error: Please add your information", Toast.LENGTH_SHORT).show();
                }
                else if (!pwParsed.toString().equals(pwVeriParsed.toString())) {
                    Toast.makeText(Register.this, "Error: Password do not match", Toast.LENGTH_SHORT).show();
                }
//                else {
//                    Toast.makeText(Register.this, usernameParsed.toString(), Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(Register.this, nonEmpty, Toast.LENGTH_SHORT).show();
            }
            });

            final AppCompatTextView signUpLink = findViewById(R.id.login_link);
        signUpLink.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view){
                Intent signUpScreen = new Intent(Register.this, Login.class);
                startActivity(signUpScreen);
                finish();
            }
        });
    }
}