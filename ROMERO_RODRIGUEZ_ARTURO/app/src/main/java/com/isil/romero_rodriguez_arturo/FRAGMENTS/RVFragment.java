package com.isil.romero_rodriguez_arturo.FRAGMENTS;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.isil.romero_rodriguez_arturo.MapaActivity;
import com.isil.romero_rodriguez_arturo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15/06/2017.
 */

public class RVFragment extends Fragment implements CallbackPersonal {

    RecyclerView rvPersonal;
    private PersonalDAO mPersonalDAO;
    private RVAdapter mrvAdapter;
    private DetalleFragment mDetalleFragment;
    private MainActivity mMainActivity;
    private ImageButton btnEditar, btnEliminar, btnPolygon;
    private MapaActivity mMapaActivity;
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
        btnPolygon = (ImageButton) getActivity().findViewById(R.id.btnPoligono);
        btnPolygon.setOnClickListener(onClickListenerBtnPolygon);


        MetodolstCount();

        return  view;
    }

    @Override
    public void onSelectPersonal(Personal personal) {
//        mDetalleFragment.setText(personal);
//        btnEditar.setVisibility(View.VISIBLE);
//        btnEliminar.setVisibility(View.VISIBLE);
    }


    private void MetodolstCount(){

       if(mrvAdapter.getItemCount()>=2) {
           btnPolygon.setVisibility(View.VISIBLE);
       }else {
           btnPolygon.setVisibility(View.GONE);
       }
    }

    private View.OnClickListener onClickListenerBtnPolygon = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ArrayList<Personal> milista = new ArrayList<>();
            List<Personal> lsta = mPersonalDAO.getAll();
            milista.addAll(lsta);

            Intent intent = new Intent(getActivity(), MapaActivity.class);
            intent.putExtra("miLista", milista);
            startActivity(intent);
        }
    };
}
