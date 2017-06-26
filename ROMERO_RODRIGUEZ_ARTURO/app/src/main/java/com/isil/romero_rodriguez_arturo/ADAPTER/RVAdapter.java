package com.isil.romero_rodriguez_arturo.ADAPTER;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isil.romero_rodriguez_arturo.ENTIDADES.Personal;
import com.isil.romero_rodriguez_arturo.INTERFACE.CallbackPersonal;
import com.isil.romero_rodriguez_arturo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 16/06/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVAdapterViewHolder>{

    private List<Personal> mLstPersonal = new ArrayList<>();
    private CallbackPersonal mCallbackPersonal;

    public RVAdapter(CallbackPersonal callbackPersonal) {
        this.mCallbackPersonal = callbackPersonal;
    }

    public void clearAllAddAll(List<Personal> lstPersonal){
        mLstPersonal.clear();
        mLstPersonal.addAll(lstPersonal);
        notifyDataSetChanged();
    }

    private View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Personal personal = (Personal) view.getTag();
            if(mCallbackPersonal!=null)
                mCallbackPersonal.onSelectPersonal(personal);
        }
    };

    @Override
    public RVAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(RVAdapterViewHolder holder, int position) {
        Personal personal = mLstPersonal.get(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.itemView.setTag(personal);
        String nomPersonal = personal.getNomPersonal()==null?"" : personal.getNomPersonal();
        String apePersonal = personal.getApePersonal()==null?"" : personal.getApePersonal();
        holder.tvNomCompleto.setText(nomPersonal + " " + apePersonal);
        holder.tvDireccion.setText(personal.getDireccionPersonal()==null?"" : personal.getDireccionPersonal());
        String flagActivo = personal.getFlagActivo()==null?"" : personal.getFlagActivo();
        if(flagActivo.equals("1")){
            holder.iviFlag.setVisibility(View.VISIBLE);
        }else
        {
            holder.iviFlag.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mLstPersonal.size();
    }

    static class RVAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tvNomCompleto, tvDireccion;
        ImageView iviPlace, iviFlag;
        public RVAdapterViewHolder(View itemView) {
            super(itemView);
            tvNomCompleto = (TextView) itemView.findViewById(R.id.tvNombreCompleto);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            iviPlace = (ImageView) itemView.findViewById(R.id.iviPlace);
            iviFlag = (ImageView) itemView.findViewById(R.id.iviFlag);
        }
    }
}
