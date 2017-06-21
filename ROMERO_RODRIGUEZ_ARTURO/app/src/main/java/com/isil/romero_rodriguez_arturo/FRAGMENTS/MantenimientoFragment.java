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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.isil.romero_rodriguez_arturo.R;

/**
 * Created by User on 16/06/2017.
 */

public class MantenimientoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient
        .OnConnectionFailedListener, LocationListener, OnMapReadyCallback, CallbackEditar {

    private final int REQUEST_LOCATION_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private EditText etNombre, etApellido, etDireccion, etEdad, etDNI, etTipoDocumento, etFechaCumpleaños;
    private CheckBox chkActivo;
    private Personal mPersonal;
    private ImageButton btnGuardar;
    private CallbackEditar mcallbackEditar;
    private long idPersonal=0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mantenimiento, container, false);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(MantenimientoFragment.this)
                    .addOnConnectionFailedListener(MantenimientoFragment.this)
                    .build();
        }

        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(MantenimientoFragment.this);
        getFragmentManager().beginTransaction().replace(R.id.flMap, mapFragment).commit();

        etNombre = (EditText) view.findViewById(R.id.etNombre);
        etApellido = (EditText) view.findViewById(R.id.etApellido);
        etDireccion = (EditText) view.findViewById(R.id.etDireccion);
        etEdad = (EditText) view.findViewById(R.id.etEdad);
        etDNI = (EditText) view.findViewById(R.id.etDNI);
        etTipoDocumento = (EditText) view.findViewById(R.id.etTipoDocumento);
        etFechaCumpleaños = (EditText) view.findViewById(R.id.etFechaCumpleaños);
        chkActivo = (CheckBox) view.findViewById(R.id.chkActivo);
//        btnEliminar = (Button)getActivity().findViewById(R.id.btnEliminar);
//        btnEliminar.setOnClickListener(onClickListenerBtnEliminar);

        btnGuardar = (ImageButton)getActivity().findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(onClickListenerBtnGuardar);

        setTextMantenimiento();
        return view;
    }

//    private View.OnClickListener onClickListenerBtnEliminar = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Eliminar();
//        }
//    };

    private View.OnClickListener onClickListenerBtnGuardar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Guardar();
        }
    };

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
    public void editarPersonal(Personal personal) {
        //setTextMantenimiento(personal);
    }

    public void setTextMantenimiento(){

        etNombre.setText(getArguments() != null ? getArguments().getString("nombre"): "Nombre");
        etApellido.setText(getArguments() != null ? getArguments().getString("apellido"): "Apellido");
        etDireccion.setText(getArguments() != null ? getArguments().getString("direccion"): "Dirección");
        etEdad.setText(getArguments() != null ? getArguments().getString("edad"): "Edad");
        etDNI.setText(getArguments() != null ? getArguments().getString("dni"): "Número doc.");
        etTipoDocumento.setText(getArguments() != null ? getArguments().getString("tipoDNI"): "Tipo doc.");
        etFechaCumpleaños.setText(getArguments() != null ? getArguments().getString("fechaCumpleaños"): "Cumpleaños");
        String flag = getArguments() != null ? getArguments().getString("flagActivo"): "0";
        if(flag.equals("0")){chkActivo.setChecked(false);}
        if(flag.equals("1")){chkActivo.setChecked(true);}
        idPersonal = getArguments() != null ? getArguments().getLong("id"): 0;
//        double latitudMap;
//        double longitudMap;
    }

    public void Guardar(){
        Personal personal = new Personal();
        if(idPersonal==0){
            personal.setNomPersonal(etNombre.getText().toString());
            personal.setApePersonal(etApellido.getText().toString());
            personal.setDireccionPersonal(etDireccion.getText().toString());
            personal.setEdadPersonal(etEdad.getText().toString());
            personal.setNumDoc(etDNI.getText().toString());
            personal.setTipoDoc(etTipoDocumento.getText().toString());
            personal.setFechaCumple(etFechaCumpleaños.getText().toString());
            personal.setFlagActivo(chkActivo.isChecked()?"1":"0");
            PersonalDAO personalDAO = new PersonalDAO(getActivity());
            personalDAO.insert(personal);
        }else{
            personal.setIdPersonal((idPersonal));
            personal.setNomPersonal(etNombre.getText().toString());
            personal.setApePersonal(etApellido.getText().toString());
            personal.setDireccionPersonal(etDireccion.getText().toString());
            personal.setEdadPersonal(etEdad.getText().toString());
            personal.setNumDoc(etDNI.getText().toString());
            personal.setTipoDoc(etTipoDocumento.getText().toString());
            personal.setFechaCumple(etFechaCumpleaños.getText().toString());
            personal.setFlagActivo(chkActivo.isChecked()?"1":"0");
            PersonalDAO personalDAO = new PersonalDAO(getActivity());
            personalDAO.update(personal);
        }
//        double latitudMap;
//        double longitudMap;
    }

//    public void Eliminar(){
//        Personal personal = new Personal();
//        if(idPersonal!=0){
//            personal.setIdPersonal((idPersonal));
//            PersonalDAO personalDAO = new PersonalDAO(getActivity());
//            personalDAO.delete(personal);
//        }else{
//            Toast.makeText(getActivity(), "No existe registro a eliminar",
//                    Toast.LENGTH_SHORT).show();
//        }
////        double latitudMap;
////        double longitudMap;
//    }

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MantenimientoFragment.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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


}
