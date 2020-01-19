package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseFirestore db;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // instance of database
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("User");
        final AppCompatEditText usernameInfo = findViewById(R.id.username);
        final Editable usernameParsed = usernameInfo.getText();
        final AppCompatEditText passwordInfo = findViewById(R.id.password);
        final Editable pwParsed = passwordInfo.getText();
        final EditText confirmPW = findViewById(R.id.password_veri);
        final Editable pwVeriParsed = confirmPW.getText();

        final AppCompatButton registerBtn = findViewById(R.id.signup_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User newUser = new User(usernameParsed.toString(), pwParsed.toString());
                if (pwParsed.toString().isEmpty() || usernameParsed.toString().isEmpty() || pwVeriParsed.toString().isEmpty()) {
                    Toast.makeText(Register.this, "Error: Please add information", Toast.LENGTH_SHORT).show();
                } else if (!(pwParsed.toString().equals(pwVeriParsed.toString()))) {
                    Toast.makeText(Register.this, "Passwords Do not Match", Toast.LENGTH_SHORT).show();
                }

                final HashMap<String, Object> user = new HashMap<>();
                user.put("username", newUser.getUsername());
                user.put("password", newUser.getPassword());

                final CollectionReference collectionReference = db.collection("User");
                final ArrayList userIdList = new ArrayList();
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Log.d("TEST", String.valueOf(doc.getData().get("User")));
                            String UserID = doc.getId();
                            userIdList.add(UserID);

                            if (userIdList.contains(newUser.getUsername())) {
                                Toast.makeText(Register.this, "ERROR: User Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent userInputIntent = new Intent(Register.this, UserInputActivity.class);
                                startActivity(userInputIntent);
                                finish();
                            }
                        }
                    }
                });
            }
        });

        final AppCompatTextView loginLink = findViewById(R.id.login_link);
        loginLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent loginScreen = new Intent(Register.this, Login.class);
                startActivity(loginScreen);
                finish();
            }
            });
    }
}