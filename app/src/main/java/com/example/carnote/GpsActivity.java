package com.example.carnote;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class GpsActivity extends AppCompatActivity implements LocationListener {

    private TextView bestSpeed;
    private TextView currentSpeed;
    private Button button;
    private LocationManager locationManager;
    private Date startTime;
    private boolean wasCounted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gps_layout);

        bestSpeed = findViewById(R.id.best_speed);
        currentSpeed = findViewById(R.id.current_speed);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startTime = (Date) new java.util.Date();
               wasCounted = false;
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (savedInstanceState != null) {
            //by odzyskać ostatnie odczyty z okna po zabiciu
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume()
    {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500L,2.0f, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates( this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        float speed = location.getSpeed();
        float kmhspeed = speed * 3600 / 1000;
        currentSpeed.setText("Twoja bieżąca prędkość " + kmhspeed+ " km/h");
        if (kmhspeed >= 100 && !wasCounted)
        {
            long diffInMs = new java.util.Date().getTime() - startTime.getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
            bestSpeed.setText("Rekord ostatni " + diffInSec + " sekund");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
