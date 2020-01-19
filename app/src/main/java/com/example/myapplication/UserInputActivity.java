package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserInputActivity extends AppCompatActivity {
    EditText location, stuff;
    Button saveButton;
    private FirebaseFirestore db;
    // final CollectionReference collectionReference;

    public UserInputActivity(){
        // db = FirebaseFirestore.getInstance();

        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        // this.collectionReference  = db.collection("Location");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        location = findViewById(R.id.locationName);
        stuff = findViewById(R.id.stuff);
        saveButton = findViewById(R.id.save_button);

        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                String locationName = location.getText().toString();
                String otherStuff = stuff.getText().toString();
                data.put("placeName", locationName);
                db.collection("Location").document("LA")
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("test", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("test", "Error writing document", e);
                            }
                        });
            }
        });


    }

}
