package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
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

import static android.app.ProgressDialog.show;
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
                finish();
            }
        });


        songCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                songs.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Log.d("TEST", String.valueOf(doc.getData().get("Province")));
                    String songId = (String) doc.getData().get("SongId");
                    String songName = (String) doc.getData().get("SongName");
                    String description = (String) doc.getData().get("Description");
                    String songType = (String) doc.getData().get("SongType");
                    songs.add(new Song(songName, description, songType,songId));
                }
                songAdapter.notifyDataSetChanged();
            }

        });
//        songList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                songAdapter.remove(position);
//                songCollectionReference.document(songs.get(position).getID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("test", "Mood successfully deleted!");
//
//                    }
//                })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("Test", "Error deleting mood", e);
//                            }
//                        });
//                return false;
//            }
//        });

        songList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                final int selectedSong = position;
                final Song song = songs.get(selectedSong);

                new Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("Would you like to delete this song?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("Videos").document(song.getID()).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Okay", "DocumentSnapshot sucessfully deleted");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Error", "Error deleting song");
                                            }
                                        });
                                songs.remove(selectedSong);
                                songAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return false;
            }
        });


    }
}
