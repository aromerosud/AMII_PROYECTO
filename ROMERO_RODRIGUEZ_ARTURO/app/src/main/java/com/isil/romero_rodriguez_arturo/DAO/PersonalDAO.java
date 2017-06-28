package com.isil.romero_rodriguez_arturo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.isil.romero_rodriguez_arturo.CONEXION.DataBaseHelper;
import com.isil.romero_rodriguez_arturo.ENTIDADES.Personal;

import java.util.ArrayList;

/**
 * Created by User on 16/06/2017.
 */

public class PersonalDAO {

    private final static String TABLE = "PERSONAL";
    private final static String COL_IDPERSONAL = "idPersonal";
    private final static String COL_NOMPERSONAL = "nomPersonal";
    private final static String COL_APEPERSONAL = "apePersonal";
    private final static String COL_DIRECCIONPERSONAL = "direccionPersonal";
    private final static String COL_EDADPERSONAL = "edadPersonal";
    private final static String COL_FLAGACTIVO= "flagActivo";
    private final static String COL_LATITUDMAP = "latitudMap";
    private final static String COL_LONGITUDMAP = "longitudMap";
    private final static String COL_NUMDOC = "numDoc";
    private final static String COL_TIPODOC = "tipoDoc";
    private final static String COL_FECHACUMPLE = "fechaCumple";
    private final static String COL_NOMFOTO = "nomFoto";

    private Context mContext;
    private DataBaseHelper mDataBaseHelper;

    public PersonalDAO(Context context){
        mContext = context;
        mDataBaseHelper = new DataBaseHelper(context);
    }


    public ArrayList<Personal> getAll(){
        Cursor cursor = mDataBaseHelper.openDataBase().query(TABLE,null,null,null,null,null,null);
        ArrayList<Personal>lstPersonal = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                lstPersonal.add(transformCursorToPersonal(cursor));
            }while (cursor.moveToNext());
        }
        if(cursor!=null && !cursor.isClosed()){
            cursor.close();
        }
        return lstPersonal;
    }

    public void insert(Personal personal) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMPERSONAL, personal.getNomPersonal());
        cv.put(COL_APEPERSONAL, personal.getApePersonal());
        cv.put(COL_DIRECCIONPERSONAL, personal.getDireccionPersonal());
        cv.put(COL_EDADPERSONAL, personal.getEdadPersonal());
        cv.put(COL_FLAGACTIVO, personal.getFlagActivo());
        cv.put(COL_LATITUDMAP, personal.getLatitudMap());
        cv.put(COL_LONGITUDMAP, personal.getLongitudMap());
        cv.put(COL_NUMDOC, personal.getNumDoc());
        cv.put(COL_TIPODOC, personal.getTipoDoc());
        cv.put(COL_FECHACUMPLE, personal.getFechaCumple());
        long id = mDataBaseHelper.openDataBase().insert(TABLE, null, cv);
        personal.setIdPersonal(id);
    }

    public void update (Personal personal)
    {
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMPERSONAL, personal.getNomPersonal());
        cv.put(COL_APEPERSONAL, personal.getApePersonal());
        cv.put(COL_DIRECCIONPERSONAL, personal.getDireccionPersonal());
        cv.put(COL_EDADPERSONAL, personal.getEdadPersonal());
        cv.put(COL_FLAGACTIVO, personal.getFlagActivo());
        cv.put(COL_LATITUDMAP, personal.getLatitudMap());
        cv.put(COL_LONGITUDMAP, personal.getLongitudMap());
        cv.put(COL_NUMDOC, personal.getNumDoc());
        cv.put(COL_TIPODOC, personal.getTipoDoc());
        cv.put(COL_FECHACUMPLE, personal.getFechaCumple());
        mDataBaseHelper.openDataBase().update(TABLE,cv,COL_IDPERSONAL + " = ? ", new String[]{String.valueOf((personal.getIdPersonal()))});
    }

    public void delete (Personal personal)
    {
        mDataBaseHelper.openDataBase().delete(TABLE,COL_IDPERSONAL + " = ? ", new String[]{String.valueOf((personal.getIdPersonal()))});
    }

    private Personal transformCursorToPersonal(Cursor cursor){
        Personal personal = new Personal();
        personal.setIdPersonal(cursor.isNull(cursor.getColumnIndex(COL_IDPERSONAL))? 0 : cursor.getLong(cursor.getColumnIndex(COL_IDPERSONAL)));
        personal.setNomPersonal(cursor.isNull(cursor.getColumnIndex(COL_NOMPERSONAL))? null : cursor.getString(cursor.getColumnIndex(COL_NOMPERSONAL)));
        personal.setApePersonal(cursor.isNull(cursor.getColumnIndex(COL_APEPERSONAL))? null : cursor.getString(cursor.getColumnIndex(COL_APEPERSONAL)));
        personal.setDireccionPersonal(cursor.isNull(cursor.getColumnIndex(COL_DIRECCIONPERSONAL))? null : cursor.getString(cursor.getColumnIndex(COL_DIRECCIONPERSONAL)));
        personal.setEdadPersonal(cursor.isNull(cursor.getColumnIndex(COL_EDADPERSONAL))? null : cursor.getString(cursor.getColumnIndex(COL_EDADPERSONAL)));
        personal.setFlagActivo(cursor.isNull(cursor.getColumnIndex(COL_FLAGACTIVO))? null : cursor.getString(cursor.getColumnIndex(COL_FLAGACTIVO)));
        personal.setLatitudMap(cursor.isNull(cursor.getColumnIndex(COL_LATITUDMAP))? 0.0 : cursor.getDouble(cursor.getColumnIndex(COL_LATITUDMAP)));
        personal.setLongitudMap(cursor.isNull(cursor.getColumnIndex(COL_LONGITUDMAP))? 0.0 : cursor.getDouble(cursor.getColumnIndex(COL_LONGITUDMAP)));
        personal.setNumDoc(cursor.isNull(cursor.getColumnIndex(COL_NUMDOC))? null : cursor.getString(cursor.getColumnIndex(COL_NUMDOC)));
        personal.setTipoDoc(cursor.isNull(cursor.getColumnIndex(COL_TIPODOC))? null : cursor.getString(cursor.getColumnIndex(COL_TIPODOC)));
        personal.setFechaCumple(cursor.isNull(cursor.getColumnIndex(COL_FECHACUMPLE))? null : cursor.getString(cursor.getColumnIndex(COL_FECHACUMPLE)));
        personal.setNomFoto(cursor.isNull(cursor.getColumnIndex(COL_NOMFOTO))? null : cursor.getString(cursor.getColumnIndex(COL_NOMFOTO)));
        return personal;
    }
}
