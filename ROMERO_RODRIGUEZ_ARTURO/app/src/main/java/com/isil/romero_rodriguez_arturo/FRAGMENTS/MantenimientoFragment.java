package com.isil.romero_rodriguez_arturo.FRAGMENTS;

import android.Manifest;
import android.app.Fragment;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.isil.romero_rodriguez_arturo.ADAPTER.RVAdapter;
import com.isil.romero_rodriguez_arturo.DAO.PersonalDAO;
import com.isil.romero_rodriguez_arturo.ENTIDADES.Personal;
import com.isil.romero_rodriguez_arturo.INTERFACE.CallbackPersonal;
import com.isil.romero_rodriguez_arturo.R;

/**
 * Created by User on 16/06/2017.
 */

public class MantenimientoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient
        .OnConnectionFailedListener, LocationListener, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, CallbackPersonal {

    private final int REQUEST_LOCATION_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private EditText etNombre, etApellido, etDireccion, etEdad, etDNI, etTipoDocumento, etFechaCumpleaños;
    private CheckBox chkActivo;
    private Personal mPersonal;
    private ImageButton btnGuardar;
    private long idPersonal=0;
    double vlatitud = 0.0;
    double vlongitud = 0.0;
    private RVAdapter mRVAdapter;
    private PersonalDAO mPersonalDAO;
    RecyclerView rvPersonal;
    private DetalleFragment mDetalleFragment;

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
        btnGuardar = (ImageButton)getActivity().findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(onClickListenerBtnGuardar);
        mPersonalDAO = new PersonalDAO(getActivity());
        rvPersonal = (RecyclerView) getActivity().findViewById(R.id.rvPersonal);
        mRVAdapter = new RVAdapter(MantenimientoFragment.this);
        mRVAdapter.clearAllAddAll(mPersonalDAO.getAll());
        rvPersonal.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPersonal.setAdapter(mRVAdapter);

        mDetalleFragment = (DetalleFragment) Fragment.instantiate(getActivity(),DetalleFragment.class.getName());

        return view;
    }



    private View.OnClickListener onClickListenerBtnGuardar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Guardar();
            mRVAdapter.clearAllAddAll(mPersonalDAO.getAll());
        }
    };


    public void setTextMantenimiento(){
        String accion = getArguments() != null ? getArguments().getString("accion"): "0";
        etNombre.setText(getArguments() != null ? getArguments().getString("nombre"): "");
        etApellido.setText(getArguments() != null ? getArguments().getString("apellido"): "");
        etDireccion.setText(getArguments() != null ? getArguments().getString("direccion"): "");
        etEdad.setText(getArguments() != null ? getArguments().getString("edad"): "");
        etDNI.setText(getArguments() != null ? getArguments().getString("dni"): "");
        etTipoDocumento.setText(getArguments() != null ? getArguments().getString("tipoDNI"): "");
        etFechaCumpleaños.setText(getArguments() != null ? getArguments().getString("fechaCumpleaños"): "");
        String flag = getArguments() != null ? getArguments().getString("flagActivo"): "0";
        if(flag.equals("0")){chkActivo.setChecked(false);}
        if(flag.equals("1")){chkActivo.setChecked(true);}
        idPersonal = getArguments() != null ? getArguments().getLong("id"): 0;
        vlatitud = getArguments() != null ? getArguments().getDouble("latitud"): 0.0;
        vlongitud = getArguments() != null ? getArguments().getDouble("longitud"): 0.0;

        if(accion.equals("0")){
            cargarMapaInsert();
        }
        if(accion.equals("1")){
            cargarMapasEdit();
        }


    }

    public void cargarMapaInsert(){

        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mGoogleMap.getMyLocation().getLatitude(), mGoogleMap
                .getMyLocation().getLongitude())).title("Mi ubicacion").flat(true).draggable(true).icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mGoogleMap.getMyLocation().getLatitude(),mGoogleMap
                .getMyLocation().getLongitude()),17f));

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
    }

    public void cargarMapasEdit(){


        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(vlatitud,vlongitud))
                .title("Mi ubicacion").flat(true).draggable(true).icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(vlatitud,vlongitud),17f));


        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);

    }

    private boolean validaCamposVacios() {
        String nombres = etNombre.getText().toString().trim();
        String apellidos = etApellido.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();
        String dni = etDNI.getText().toString().trim();
        String tipoDoc = etTipoDocumento.getText().toString().trim();
        String fechaNac = etFechaCumpleaños.getText().toString().trim();

        if (nombres.isEmpty()) {
            etNombre.setError("Ingresar nombre");
            return false;
        }
        if (apellidos.isEmpty()) {
            etApellido.setError("Ingresar apellidos");
            return false;
        }

        if (direccion.isEmpty()) {
            etDireccion.setError("Ingresar dirección");
            return false;
        }
        if (edad.isEmpty()) {
            etEdad.setError("Ingresar edad");
            return false;
        }
        if (dni.isEmpty()) {
            etDNI.setError("Ingresar documento");
            return false;
        }
        if (tipoDoc.isEmpty()) {
            etTipoDocumento.setError("Ingresar tipo de documento");
            return false;
        }
        if (fechaNac.isEmpty()) {
            etFechaCumpleaños.setError("Ingresar cumpleaños");
            return false;
        }

        if (nombres.isEmpty()) return false;
        if (apellidos.isEmpty()) return false;
        if (direccion.isEmpty()) return false;
        if (edad.isEmpty()) return false;
        if (dni.isEmpty()) return false;
        if (tipoDoc.isEmpty()) return false;
        if (fechaNac.isEmpty()) return false;

        return true;
    }

    public void Guardar(){
            if(validaCamposVacios()){
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
                personal.setLatitudMap(vlatitud);
                personal.setLongitudMap(vlongitud);
                PersonalDAO personalDAO = new PersonalDAO(getActivity());
                personalDAO.insert(personal);
                long newID = personal.getIdPersonal();
                if(newID!=0){
                    btnGuardar.setVisibility(View.GONE);
                    getFragmentManager().beginTransaction().replace(R.id.contenedor_detalle,mDetalleFragment).commit();
                    Toast.makeText(getActivity(),"Se agregaron los datos del personal correctamente",Toast.LENGTH_SHORT).show();
                }

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
                personal.setLatitudMap(vlatitud);
                personal.setLongitudMap(vlongitud);
                PersonalDAO personalDAO = new PersonalDAO(getActivity());
                personalDAO.update(personal);
                getFragmentManager().beginTransaction().replace(R.id.contenedor_detalle,mDetalleFragment).commit();
                Toast.makeText(getActivity(),"Se actualizaron los datos del personal correctamente",Toast.LENGTH_SHORT).show();
                btnGuardar.setVisibility(View.GONE);
            }

        }
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

    }

    @Override
    public void onMapLoaded() {
        setTextMantenimiento();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLoadedCallback(this);
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
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),17f));
                vlatitud = marker.getPosition().latitude;
                vlongitud = marker.getPosition().longitude;
            }
        });
    }

    @Override
    public void onSelectPersonal(Personal personal) {

    }
}