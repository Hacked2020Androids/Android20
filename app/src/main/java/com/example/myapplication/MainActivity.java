package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;
import static com.google.api.MetricDescriptor.MetricKind.DELTA;

public class MainActivity extends AppCompatActivity {
    Button AddMusic;
    private RecyclerView recyclerView;
    private songAdapter adapter;
    ListView songList;
    ArrayList<Song> songDataList;
    private FirebaseFirestore db;

    private ArrayList<Song> songs = new ArrayList<>();
    float historicX = Float.NaN, historicY = Float.NaN;
    final int DELTA = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        final CollectionReference songCollectionReference = db.collection("Videos");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddMusic = findViewById(R.id.addMusic);
        songDataList = new ArrayList<Song>();
        songList = findViewById(R.id.songList);
        final ArrayAdapter songAdapter = new songAdapter(this, songs);
        songList.setAdapter(songAdapter);
        AddMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserInputActivity.class);
                startActivity(intent);
            }
        });



        songCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                songs.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Log.d("TEST", String.valueOf(doc.getData().get("Province")));
                    String songId = doc.getId();
                    String description = (String) doc.getData().get("Description");
                    String place = (String) doc.getData().get("Place");
                    String songType = (String) doc.getData().get("SongType");
                    songs.add(new Song(description, place, songType));
                }
                songAdapter.notifyDataSetChanged();
            }

        });
        songList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                songAdapter.remove(position);
                songCollectionReference.document(songs.get(position).getID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "Mood successfully deleted!");

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Test", "Error deleting mood", e);
                            }
                        });
                return false;
            }
        });




    }

}