package com.trimit.android.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationSource;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;
import com.trimit.android.R;
import com.trimit.android.utils.PrefsUtils;

import java.util.List;

import javax.inject.Inject;


public abstract class BaseLocationActivity extends AppCompatActivity implements PermissionsListener {
    private static final String TAG = "BaseLocationActivity";

    private LocationEngine locationEngine;
    private LocationEngineListener locationEngineListener;
    private PermissionsManager permissionsManager;

    @Inject
    public PrefsUtils mPrefsUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox mapbox=Mapbox.getInstance(this, getString(R.string.mapbox_token));
        locationEngine = LocationSource.getLocationEngine(this);
        locationEngine.activate();
    }
    protected abstract void start();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationEngineListener != null) {
            locationEngine.removeLocationEngineListener(locationEngineListener);
        }
    }
    protected void getLocation() {
        Log.d(TAG, "getLocation: ");
        permissionsManager = new PermissionsManager(this);
        if (!PermissionsManager.areLocationPermissionsGranted(this)) {
            permissionsManager.requestLocationPermissions(this);
        } else {
            if(locationEngine.getLastLocation()!=null){
                saveLocation(locationEngine.getLastLocation());
                return;
            }
            locationEngineListener = new LocationEngineListener() {
                @Override
                public void onConnected() {
                    // No action needed here.
                    Log.d(TAG, "onConnected: ");
                    locationEngine.activate();
                }

                @Override public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: ");
                    if (location != null) {
                        mPrefsUtils.setUserLocation(new LatLng(location));
                        Log.d(TAG, "onLocationChanged: "+location);
                        Toast.makeText(BaseLocationActivity.this, "Location updated:"+location, Toast.LENGTH_SHORT).show();
                        start();
                    }else {
                        Log.e(TAG, "onLocationChanged: error" );
                        Toast.makeText(BaseLocationActivity.this, "location update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            locationEngine.addLocationEngineListener(locationEngineListener);
            locationEngine.requestLocationUpdates();
        }
    }
    public void saveLocation(Location location){
        mPrefsUtils.setUserLocation(new LatLng(location));
        Log.d(TAG, "onLocationChanged: "+location);
        Toast.makeText(BaseLocationActivity.this, "Location updated:"+location, Toast.LENGTH_SHORT).show();
        start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            getLocation();
        } else {
            Log.e(TAG, "onPermissionResult: failed" );
            Toast.makeText(this, R.string.user_location_permission_not_granted,
                    Toast.LENGTH_LONG).show();
            //finish();
        }
    }
}
