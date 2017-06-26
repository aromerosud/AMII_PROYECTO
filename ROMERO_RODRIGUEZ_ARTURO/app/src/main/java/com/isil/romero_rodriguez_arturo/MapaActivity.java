package com.isil.romero_rodriguez_arturo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.isil.romero_rodriguez_arturo.ENTIDADES.Personal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 16/06/2017.
 */

public class MapaActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient
        .OnConnectionFailedListener, LocationListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private final int REQUEST_LOCATION_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private Location mLocation;

    private List<Marker> mlsMarker = new ArrayList<>();
    private ArrayList<Personal> lista = new ArrayList<>();
    private Marker mMarker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mapa);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(MapaActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(MapaActivity.this)
                    .addOnConnectionFailedListener(MapaActivity.this)
                    .build();
        }
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(MapaActivity.this);
        getFragmentManager().beginTransaction().replace(R.id.flMapPoligono, mapFragment).commit();

        mLocation = new Location(String.valueOf(MapaActivity.this));
    }

    public void lstLatLong() {
//        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude(),mLocation
//                .getLongitude())).title("Mi ubicacion").flat(true).icon(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        lista = (ArrayList<Personal>) getIntent().getSerializableExtra("miLista");

        for (int i = 0; i < lista.size(); i++) {

            double latitud = lista.get(i).getLatitudMap();
            double longitud = lista.get(i).getLongitudMap();

            mlsMarker.add(mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                    .title(String.valueOf(mlsMarker.size())).draggable(true).flat(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
        }

        mGoogleMap.clear();
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.strokeColor(Color.RED);
        polygonOptions.fillColor(0X3300FF00);

        LatLngBounds.Builder builder = LatLngBounds.builder();

        for (Marker marker : mlsMarker) {
            builder.include(marker.getPosition());
            polygonOptions.add(marker.getPosition());
        }

        mGoogleMap.addPolygon(polygonOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 32));

        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mGoogleMap.getMyLocation().getLatitude(), mGoogleMap
        .getMyLocation().getLongitude())).title("Mi ubicacion").flat(true).icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        creatLocationRequest();
    }

    private void creatLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MapaActivity.this, REQUEST_LOCATION_CODE);
                            startLocationUpdates();
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MapaActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapLoaded() {
        lstLatLong();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLoadedCallback(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(false);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
