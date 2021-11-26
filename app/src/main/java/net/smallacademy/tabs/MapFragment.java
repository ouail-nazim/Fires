package net.smallacademy.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class MapFragment extends Fragment {


    private ArrayList<MarkerOptions> markers = new ArrayList();
    private ArrayList<Float> markersColors = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mapLoaded(googleMap);
            }
        });
        return view;
    }

    private void getMapData() {
        for (int i = 0; i < 4; i++) {
            double longitude = Math.random() * Math.PI * 2;
            double latitude = Math.acos(Math.random() * 2 - 1);
            // get list of markers
            MarkerOptions mr = new MarkerOptions();
            mr.position(new LatLng(longitude + 30, latitude));
            mr.title(longitude + " : " + latitude);
            markers.add(mr);
            // depande on state of fire add color to marker
            switch (i) {
                case 1:
                    markersColors.add(BitmapDescriptorFactory.HUE_ORANGE);
                    break;
                case 2:
                    markersColors.add(BitmapDescriptorFactory.HUE_YELLOW);
                    break;
                default:
                    markersColors.add(BitmapDescriptorFactory.HUE_RED);
            }
        }
    }

    private void mapLoaded(GoogleMap googleMap) {

        for (MarkerOptions m : markers) {
            Float markerColor = markersColors.get(markers.indexOf(m));
            m.icon(BitmapDescriptorFactory.defaultMarker(markerColor));
            googleMap.addMarker(m);
        }
        // on click on map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                // zoom on click on map
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            }
        });
        // on click on marker
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.get_dir_msg)
                        .setCancelable(true)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(R.string.get_dir, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                return false;
            }
        });

    }
}