package com.isil.romero_rodriguez_arturo.DAO;

import android.content.Context;
import android.database.Cursor;

import com.isil.romero_rodriguez_arturo.CONEXION.DataBaseHelper;
import com.isil.romero_rodriguez_arturo.ENTIDADES.User;

/**
 * Created by User on 15/06/2017.
 */

public class UserDAO {

    private final static String TABLE = "USUARIO";
    private final static String COL_IDUSER = "idUser";
    private final static String COL_USER = "user";
    private final static String COL_PASSWORD = "password";

    private Context mContext;
    private DataBaseHelper mDataBaseHelper;

    public UserDAO(Context context){
        mContext = context;
        mDataBaseHelper = new DataBaseHelper(context);
    }

    public Boolean ValidarLogin(User user){
        boolean resultado = false;
        Cursor cursor = mDataBaseHelper.openDataBase().rawQuery("Select " + COL_USER + " , " + COL_PASSWORD +
                " from " + TABLE + " where " + COL_USER + " = '" + user.getUser() + "' and " +
                COL_PASSWORD + " = '" + user.getPassword() + "'",null);
        if(cursor.moveToFirst()){
            String u = cursor.getString(0);
            String p = cursor.getString(1);
            if (user.getUser().equals(u) && user.getPassword().equals(p)){
                resultado = true;
            }
            else {resultado = false;}
        }
        if(cursor!= null && !cursor.isClosed())
        {cursor.close();}
        return  resultado;
    }
}
