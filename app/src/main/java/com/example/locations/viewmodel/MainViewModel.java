package com.example.locations.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.locations.callbacks.MainCallback;
import com.example.locations.models.MainModel;
import com.example.locations.objects.Locations;

import java.util.ArrayList;

public class MainViewModel extends ViewModel implements MainCallback {

    private MainModel model;

    private MutableLiveData<ArrayList<Locations>> locations;

    public MainViewModel(){
        locations = new MutableLiveData<>();
    }

    public void init(Context context){
        model = new MainModel(this,  context);
    }

    public LiveData<ArrayList<Locations>> getLocations (){
        return locations;
    }

    public void inserLocations(double longitude, double latitude, String date, String time){
        model.insertLocations(longitude, latitude, date, time);
    }

    @Override
    public void onSuccess(ArrayList<Locations> locations) {
       this.locations.setValue(locations);
    }
}
