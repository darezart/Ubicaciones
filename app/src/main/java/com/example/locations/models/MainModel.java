package com.example.locations.models;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.locations.callbacks.MainCallback;
import com.example.locations.objects.Locations;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainModel {

    private Context context;
    private MainCallback callback;

    private DatabaseReference mDatabase;
    private ArrayList<Locations> locations = new ArrayList<>();

    public MainModel(MainCallback callback, Context context){
        this.callback = callback;
        this.context = context;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    public void insertLocations(double longitude, double latitude, String date, String time){

        String id = mDatabase.push().getKey();

        Locations location = new Locations(id, date, time, String.valueOf(longitude), String.valueOf(latitude));
        mDatabase.child("Ubicaciones").push().setValue(location);

        mDatabase.child("Ubicaciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    locations.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                      locations.add(new Locations(
                                ds.child("id").getValue().toString(),
                                ds.child("date").getValue().toString(),
                                ds.child("time").getValue().toString(),
                                ds.child("longitude").getValue().toString(),
                                ds.child("latitude").getValue().toString()));
                    }

                }

                callback.onSuccess(locations);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
