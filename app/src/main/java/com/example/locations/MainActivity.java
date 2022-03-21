package com.example.locations;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.locations.adapters.MainAdapter;
import com.example.locations.databinding.ActivityMainBinding;
import com.example.locations.objects.Locations;
import com.example.locations.viewmodel.MainViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<Locations> locations = new ArrayList<>();
    private MainAdapter adapter;
    private MainViewModel viewModel;
    private View.OnClickListener onClickListener = view -> {

        Locations location = (Locations) view.getTag();

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("LON", Double.parseDouble(location.getLongitude()));
        intent.putExtra("LAT", Double.parseDouble(location.getLatitude()));
        startActivity(intent);

    };

    private double lon = 0.0;
    private double lat = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Ubicaciones");

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.init(this);
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            miUbicacion();
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }

        adapter = new MainAdapter(onClickListener, this);

        binding.actMainRecyler.setLayoutManager(new LinearLayoutManager(this));
        binding.actMainRecyler.setHasFixedSize(false);
        binding.actMainRecyler.setAdapter(adapter);


        final Observer<ArrayList<Locations>> onSucess = new Observer<ArrayList<Locations>>() {
            @Override
            public void onChanged(ArrayList<Locations> s) {
                adapter.setUpdate(s);
            }
        };

        viewModel.getLocations().observe(this, onSucess);

    }

    public void getLonLatiude(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
            String date = df.format(c.getTime());

            SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            String time = formatter.format(curDate);

            viewModel.inserLocations(lon, lat, date, time);
        }

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            getLonLatiude(location);
        }
    };

    public void miUbicacion() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        getLonLatiude(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    miUbicacion();
                } else {
                    Toast.makeText(this, "Sin permisos de ubicación", Toast.LENGTH_LONG).show();
                }
            });
}