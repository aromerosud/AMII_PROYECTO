package com.isil.romero_rodriguez_arturo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.isil.romero_rodriguez_arturo.DAO.PersonalDAO;
import com.isil.romero_rodriguez_arturo.ENTIDADES.Personal;
import com.isil.romero_rodriguez_arturo.FRAGMENTS.DetalleFragment;
import com.isil.romero_rodriguez_arturo.FRAGMENTS.MantenimientoFragment;
import com.isil.romero_rodriguez_arturo.FRAGMENTS.RVFragment;

/**
 * Created by User on 15/06/2017.
 */

public class MainActivity extends Activity  {

    private RVFragment mRVFragment;
    private DetalleFragment mDetalleFragment;
    private MantenimientoFragment mMantenimientoFragment;
    private ImageButton btnAgregar, btnEditar, btnEliminar, btnGuardar, btnCerrarSesion;
    private TextView tvBienvenida;
    private Personal mPersonal;
    private PersonalDAO mPersonalDAO;
    private EditText tvNombre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(onClickListenerBtnAgregar);

        btnGuardar = (ImageButton) findViewById(R.id.btnGuardar);

        btnEditar = (ImageButton) findViewById(R.id.btnEditar);

        btnEliminar = (ImageButton) findViewById(R.id.btnEliminar);

        btnCerrarSesion = (ImageButton) findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(onClickListenerBtnCerrar);

        mRVFragment = (RVFragment) Fragment.instantiate(MainActivity.this,RVFragment.class.getName());
        mDetalleFragment = (DetalleFragment) Fragment.instantiate(MainActivity.this,DetalleFragment.class.getName());
        mMantenimientoFragment = (MantenimientoFragment) Fragment.instantiate(MainActivity.this,MantenimientoFragment.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.contenedor_lista,mRVFragment).commit();
        getFragmentManager().beginTransaction().replace(R.id.contenedor_detalle,mDetalleFragment).commit();
        tvNombre = (EditText) findViewById(R.id.etNombre);
    }

    private View.OnClickListener onClickListenerBtnAgregar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            getFragmentManager().beginTransaction().replace(R.id.contenedor_detalle,mMantenimientoFragment).commit();
            btnGuardar.setVisibility(View.VISIBLE);
            btnEditar.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);

        }
    };

    private View.OnClickListener onClickListenerBtnCerrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent= new Intent(MainActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
