package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        TextView logo = findViewById(R.id.Logo);
        String logoText = "HackED 2020";
        SpannableString modifiedLogoText = new SpannableString(logoText);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);
        modifiedLogoText.setSpan(fcsRed, 4, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        logo.setText(modifiedLogoText);
        final AppCompatEditText usernameInfo = findViewById(R.id.username);
        final Editable usernameParsed = usernameInfo.getText();
        final AppCompatEditText passwordInfo = findViewById(R.id.password);
        final Editable pwParsed = passwordInfo.getText();

        final AppCompatButton loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User newUser = new User(usernameParsed.toString(), pwParsed.toString());
                if (pwParsed.toString().length() == 0 || usernameParsed.toString().length() == 0) {
                    Toast.makeText(Login.this, "Error: Please add your information", Toast.LENGTH_SHORT).show();
                } else if (newUser.getUsername().length() > 1) {
                    final HashMap<String, Object> user = new HashMap<>();
                    user.put("username", newUser.getUsername());
                    user.put("password", newUser.getPassword());

                    final CollectionReference collectionReference = db.collection("User");
                    final ArrayList userIdList = new ArrayList();
                    final ArrayList userPWList = new ArrayList();
                    collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                Log.d("TEST", String.valueOf(doc.getData().get("User")));
                                String UserID = doc.getId();
                                String UserPW = (String) doc.getData().get("password");

                                userIdList.add(UserID);
                                userPWList.add(UserPW);

                                if (!userIdList.contains(newUser.getUsername()) || !userPWList.contains(newUser.getPassword())) {
                                    Toast.makeText(Login.this, "Wrong Login Information", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    Intent userInputIntent = new Intent(Login.this, UserInputActivity.class);
                                    startActivity(userInputIntent);
                                    finish();
                                }
                            }
                        }
                    });
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
