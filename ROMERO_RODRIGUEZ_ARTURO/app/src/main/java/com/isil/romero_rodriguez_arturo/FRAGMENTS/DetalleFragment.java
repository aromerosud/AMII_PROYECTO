package com.isil.romero_rodriguez_arturo.FRAGMENTS;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.isil.romero_rodriguez_arturo.DAO.PersonalDAO;
import com.isil.romero_rodriguez_arturo.ENTIDADES.Personal;
import com.isil.romero_rodriguez_arturo.INTERFACE.CallbackEditar;
import com.isil.romero_rodriguez_arturo.INTERFACE.CallbackPersonal;
import com.isil.romero_rodriguez_arturo.R;

import java.io.Serializable;

import static com.isil.romero_rodriguez_arturo.R.id.btnGuardar;
import static com.isil.romero_rodriguez_arturo.R.id.tvNomApeDetalle;
import static com.isil.romero_rodriguez_arturo.R.id.visible;

/**
 * Created by User on 15/06/2017.
 */

public class DetalleFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient
        .OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    private final int REQUEST_LOCATION_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private TextView tvNombre, tvApellido, tvDireccion, tvEdad, tvDNI, tvTipoDocumento, tvFechaCumpleaños;
    private LinearLayout lyBienvenida ;
    private CallbackEditar mcallbackEditar;
    private ImageButton btnEditar, btnGuardar, btnEliminar;
    private MantenimientoFragment mMantenimientoFragment;
    private String flagActivo;
    private long idPersonal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(DetalleFragment.this)
                    .addOnConnectionFailedListener(DetalleFragment.this)
                    .build();
        }

        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(DetalleFragment.this);
        getFragmentManager().beginTransaction().replace(R.id.flMap, mapFragment).commit();

        tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        tvApellido = (TextView) view.findViewById(R.id.tvApellido);
        tvDireccion = (TextView) view.findViewById(R.id.tvDireccion);
        tvEdad = (TextView) view.findViewById(R.id.tvEdad);
        tvDNI = (TextView) view.findViewById(R.id.tvDNI);
        tvTipoDocumento = (TextView) view.findViewById(R.id.tvTipoDocumento);
        tvFechaCumpleaños = (TextView) view.findViewById(R.id.tvFechaCumpleaños);
        lyBienvenida = (LinearLayout) view.findViewById(R.id.lyBienvenida);
        btnEditar = (ImageButton)getActivity().findViewById(R.id.btnEditar);
        btnGuardar = (ImageButton)getActivity().findViewById(R.id.btnGuardar);
        btnEditar.setOnClickListener(onClickListenerBtnEditar);
        mMantenimientoFragment = (MantenimientoFragment) Fragment.instantiate(getActivity(),MantenimientoFragment.class.getName());

        btnEliminar = (ImageButton)getActivity().findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(onClickListenerBtnEliminar);

        return view;
    }

    private View.OnClickListener onClickListenerBtnEliminar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Eliminar();
        }
    };

    private View.OnClickListener onClickListenerBtnEditar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putString("nombre", tvNombre.getText().toString());
            args.putString("apellido", tvApellido.getText().toString());
            args.putString("direccion", tvDireccion.getText().toString());
            args.putString("edad", tvEdad.getText().toString());
            args.putString("dni", tvDNI.getText().toString());
            args.putString("tipoDNI", tvTipoDocumento.getText().toString());
            args.putString("fechaCumpleaños", tvFechaCumpleaños.getText().toString());
            args.putString("flagActivo", flagActivo.toString());
            args.putLong("id", idPersonal);
            mMantenimientoFragment.setArguments(args);

            btnEditar.setVisibility(v.GONE);
            btnGuardar.setVisibility(v.VISIBLE);

            getFragmentManager().beginTransaction().replace(R.id.contenedor_detalle,mMantenimientoFragment).commit();
        }
    };

    public void Eliminar(){
        Personal personal = new Personal();
        if(idPersonal!=0){
            personal.setIdPersonal((idPersonal));
            PersonalDAO personalDAO = new PersonalDAO(getActivity());
            personalDAO.delete(personal);
        }else{
            Toast.makeText(getActivity(), "No existe registro a eliminar",
                    Toast.LENGTH_SHORT).show();
        }
//        double latitudMap;
//        double longitudMap;
    }

    public void setText(Personal personal){
        tvNombre.setText(personal.getNomPersonal());
        tvApellido.setText(personal.getApePersonal());
        tvDireccion.setText(personal.getDireccionPersonal());
        tvEdad.setText(personal.getEdadPersonal());
        tvDNI.setText(personal.getNumDoc());
        tvTipoDocumento.setText(personal.getTipoDoc());
        tvFechaCumpleaños.setText(personal.getFechaCumple());
        flagActivo = personal.getFlagActivo();
        idPersonal = personal.getIdPersonal();
        lyBienvenida.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof CallbackEditar)
            mcallbackEditar = (CallbackEditar) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CallbackEditar)
            mcallbackEditar = (CallbackEditar) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createLocationRequest();
    }

    private void createLocationRequest() {
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
                            status.startResolutionForResult(getActivity(), REQUEST_LOCATION_CODE);
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
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, DetalleFragment.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.d("location", location.getLatitude() + ", " + location.getLongitude());
            if(mGoogleMap!=null){
                if(mMarker==null){
//                    mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location
//                            .getLongitude())).title("Mi ubicacion").flat(true));
                    mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location
                            .getLongitude())).title("Mi ubicacion").flat(true).icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }
                else{
                    mMarker.setPosition(new LatLng(location.getLatitude(),location
                            .getLongitude()));
                }
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location
                        .getLongitude()),17f));
//                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location
//                        .getLongitude()),17f));
            }
        } else {
            Log.d("location", "null");
        }
    }
}