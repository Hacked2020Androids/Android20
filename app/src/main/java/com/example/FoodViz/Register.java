package com.example.FoodViz;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore db;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // instance of database
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Users");
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
                if (pwParsed.toString().isEmpty() || usernameParsed.toString().isEmpty() || pwVeriParsed.toString().isEmpty()) {
                    Toast.makeText(Register.this, "Error: Please add information", Toast.LENGTH_SHORT).show();
                } else if (!(pwParsed.toString().equals(pwVeriParsed.toString()))) {
                    Toast.makeText(Register.this, "Passwords Do not Match", Toast.LENGTH_SHORT).show();
                }
                else {
                    User newUser = new User(usernameParsed.toString(), pwParsed.toString());

                    HashMap<String, Object> user = new HashMap<>();
                    user.put("username", newUser.getUsername());
                    user.put("password", newUser.getPassword());

                    final CollectionReference collectionReference = db.collection("users");
                    collectionReference.document(newUser.getUsername())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("thisActivity", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Failed to add user", "Error writing document", e);
                                }
                            });

                    Toast.makeText(Register.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                    Intent mainActivityIntent = new Intent(Register.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
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