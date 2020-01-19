package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class songAdapter extends ArrayAdapter<Song> {

    private ArrayList<Song> songs;
    private Context context;

    public songAdapter(Context context, ArrayList<Song> songs){
        super(context,0, songs);
        this.songs = songs;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_music, parent,false);
        }

        Song song = songs.get(position);

        TextView songTitle = view.findViewById(R.id.songTitle);
        TextView songType = view.findViewById(R.id.songType);
        TextView description = view.findViewById(R.id.description);

        songTitle.setText(song.getName());
        songType.setText(song.getType());
        description.setText(song.getDescription());
        return view;

    }
}
