package com.example.mapandscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Objects;

public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
        }

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // When map is loaded
                LatLng latLng = new LatLng(53.385817063571665, -6.258889225956497);
//                LatLng latLng = new LatLng(lastLat, lastLon);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                // Add marker for user location - 11 August
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("My location")
                        .icon(BitmapDescriptorFactory.defaultMarker(35))
                        .flat(true));
                Bundle mapBundle = getArguments();
                if (mapBundle != null) {
                    double[][] bikeLocations = (double[][]) mapBundle.getSerializable("bike_locations");
                    for (int i = 0; i < bikeLocations.length; i++) {
                        LatLng bike = new LatLng(bikeLocations[i][0], bikeLocations[i][1]);
                        System.out.println("Bike lat"+bikeLocations[i][0]);
                        System.out.println("Bike lon"+bikeLocations[i][1]);
                        float iconColor = 0;
                        String iconTitle = "";
                        boolean isAvailable = (bikeLocations[i][2] > 0);
                        if (isAvailable) {
                            iconColor = 130;
                            iconTitle = "Bike " + (i + 1) + " Available...";
                        } else {
                            iconColor = 340;
                            iconTitle = "Bike " + (i + 1) + " Unavailable...";
                        }
                        int tag[] = {(int) i + 1, (int) bikeLocations[i][2]};
                        googleMap.addMarker(new MarkerOptions()
                                .position(bike)
                                .title(iconTitle)
                                .icon(BitmapDescriptorFactory.defaultMarker(iconColor))
                                .flat(true)
                        ).setTag(tag);
                    }
                    double[][] stationLocations = {{53.38527778, -6.25805556}, {53.38444444, -6.25527778}, {53.38583333, -6.25722222}};
                    for (int i = 0; i < stationLocations.length; i++) {
                        LatLng station = new LatLng(stationLocations[i][0], stationLocations[i][1]);
                        CircleOptions circleInner = new CircleOptions()
                                .center(station)
                                .radius(10) // In meters
                                .fillColor(0x99099900)
                                .strokeColor(Color.TRANSPARENT);
                        CircleOptions circleMiddle = new CircleOptions()
                                .center(station)
                                .radius(20) // In meters
                                .fillColor(0x66E5CE11)
                                .strokeColor(Color.TRANSPARENT);
                        googleMap.addCircle(circleInner);
                        googleMap.addCircle(circleMiddle);
                    }
                }
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        // If clicking user location marker, return false - 11 August
                        if (marker.getTitle().equals("My location")) {
                            return false;
                        }
                        int markerTag[] = (int[]) marker.getTag();
                        Intent intent = new Intent(getContext(), MarkerPopActivity.class);
                        intent.putExtra("Bike ID", markerTag[0]);
                        intent.putExtra("Availability", markerTag[1]);
                        System.out.println("Try to start the activity...");
                        startActivity(intent);
                        return true;
                    }
                });
            }
        });

        // Return view
        return view;
    }
}
