package com.isil.romero_rodriguez_arturo.FRAGMENTS;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.isil.romero_rodriguez_arturo.ADAPTER.RVAdapter;
import com.isil.romero_rodriguez_arturo.DAO.PersonalDAO;
import com.isil.romero_rodriguez_arturo.ENTIDADES.Personal;
import com.isil.romero_rodriguez_arturo.INTERFACE.CallbackPersonal;
import com.isil.romero_rodriguez_arturo.MainActivity;
import com.isil.romero_rodriguez_arturo.R;

/**
 * Created by User on 15/06/2017.
 */

public class RVFragment extends Fragment implements CallbackPersonal {

    RecyclerView rvPersonal;
    private PersonalDAO mPersonalDAO;
    private RVAdapter mrvAdapter;
    private DetalleFragment mDetalleFragment;
    private MainActivity mMainActivity;
    private ImageButton btnEditar, btnEliminar;
    int selection = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater    , @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv,container,false);

        mPersonalDAO = new PersonalDAO(getActivity());

        rvPersonal = (RecyclerView) view.findViewById(R.id.rvPersonal);
        mrvAdapter = new RVAdapter(RVFragment.this);
        mrvAdapter.clearAllAddAll(mPersonalDAO.getAll());
        rvPersonal.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPersonal.setAdapter(mrvAdapter);

        mDetalleFragment = (DetalleFragment) Fragment.instantiate(getActivity(),DetalleFragment.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.contenedor_detalle,mDetalleFragment).commit();

        mMainActivity = new MainActivity();
        btnEditar = (ImageButton) getActivity().findViewById(R.id.btnEditar);
        btnEliminar = (ImageButton) getActivity().findViewById(R.id.btnEliminar);

        return  view;
    }

    @Override
    public void onSelectPersonal(Personal personal) {

        mDetalleFragment.setText(personal);
        btnEditar.setVisibility(View.VISIBLE);
        btnEliminar.setVisibility(View.VISIBLE);
    }
}
