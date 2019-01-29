package com.androidtan.www.aplikasi_patrol;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.androidtan.www.aplikasi_patrol.Activity.Main.JSONResponePatrol;
import com.androidtan.www.aplikasi_patrol.Helpers.SharedPrefManager;
import com.androidtan.www.aplikasi_patrol.Models.PatrolModel;
import com.androidtan.www.aplikasi_patrol.Network.ApiServices;
import com.androidtan.www.aplikasi_patrol.Network.InitRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //get current location
    private FusedLocationProviderClient client;
    //get from the DB
    private ArrayList<PatrolModel> patrol;
    private SharedPrefManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get current location
        requestPermission();

        //session
        session = new SharedPrefManager(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //getLokasi
        client = LocationServices.getFusedLocationProviderClient(this);
        fetchData();

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    // Add a marker and move the camera
                    LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentPosition).title("Posisi Sekarang").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(currentPosition));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 13));
                } else {
                    new AlertDialog.Builder(MapsActivity.this)
                            .setTitle("Information")
                            .setMessage("location = null")
                            .setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            }).show();
                    LatLng currentPosition = new LatLng(-6.9992029,110.416024);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(currentPosition));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 13));
                }
            }
        });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    public void fetchData(){
        ApiServices api = InitRetrofit.getInstance();
        Call<JSONResponePatrol> call = api.getPatrol(session.getSpIduser());
        call.enqueue(new Callback<JSONResponePatrol>() {
            @Override
            public void onResponse(Call<JSONResponePatrol> call, retrofit2.Response<JSONResponePatrol> response) {
                JSONResponePatrol jsonResponePatrol = response.body();
                patrol = new ArrayList<>(Arrays.asList(jsonResponePatrol.getPatrol()));
                int lokasiKosong=0;
                for (int i = 0; i < patrol.size(); i++) {

                    if(patrol.get(i).getLATITUDE()==null) {
                        lokasiKosong++;
                    }
                    else if (patrol.get(i).getLONGITUDE()==null){
                        lokasiKosong++;
                    }
                    else{
                        String status = patrol.get(i).getSTATE();
                        double latitude = Double.parseDouble(patrol.get(i).getLATITUDE());
                        double longitude = Double.parseDouble(patrol.get(i).getLONGITUDE());
                        String keterangan = patrol.get(i).getRUAS();
                        addMarkerToMap(latitude, longitude, status, keterangan);
                    }
                }
                if(lokasiKosong!=0){
                    new AlertDialog.Builder(MapsActivity.this)
                            .setTitle("Information")
                            .setMessage("Jumlah Patrol yang Tidak Ditampilkan: "+lokasiKosong)
                            .setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponePatrol> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    public void addMarkerToMap(double latitude, double longitude, String status, String description)
    {
        double lat = latitude;
        double lng = longitude;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                .title(description)
                .snippet(status);

        // Marker icon
        if(status.matches("EXPIRED")) {
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        else if (status.matches("OPEN")){
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }
        else {
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }

        // Add marker to map
        mMap.addMarker(marker);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
