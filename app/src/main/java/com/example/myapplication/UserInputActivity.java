package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ThemedSpinnerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class UserInputActivity extends AppCompatActivity {
    EditText location, stuff;
    Button saveButton;
    String songName;

    private FirebaseFirestore db;
    Spinner songType;
    // final CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        location = findViewById(R.id.locationName);
        stuff = findViewById(R.id.stuff);
        saveButton = findViewById(R.id.save_button);
        songType = findViewById(R.id.songType);
        final ArrayAdapter<CharSequence> songTypes = ArrayAdapter.createFromResource(this, R.array.edit_songspinner, android.R.layout.simple_list_item_1);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
        songType.setAdapter(songTypes);
        songType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                songName =songTypes.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                String locationName = location.getText().toString();
                String otherStuff = stuff.getText().toString();
                String songID = String.valueOf(Timestamp.now().hashCode());
                Song song = new Song(locationName, otherStuff , songName);
                song.setID(songID);
                // location is song name
                data.put("SongName", locationName);
                data.put("Description", otherStuff);
                data.put("SongType", songName);
                data.put("SongId",songID);
                if (locationName.length() != 0){
                    db.collection("Videos").document(songID)
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
                    Intent MainActivity = new Intent(UserInputActivity.this, com.example.myapplication.MainActivity.class);
                    startActivity(MainActivity);
                }
                else{
                    Toast.makeText(UserInputActivity.this, "NO SONG NAME GIVEN", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
