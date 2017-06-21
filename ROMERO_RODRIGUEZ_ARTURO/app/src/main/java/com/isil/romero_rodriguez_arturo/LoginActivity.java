package com.isil.romero_rodriguez_arturo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.isil.romero_rodriguez_arturo.CONEXION.DataBaseHelper;
import com.isil.romero_rodriguez_arturo.DAO.UserDAO;
import com.isil.romero_rodriguez_arturo.ENTIDADES.User;

import java.io.IOException;

/**
 * Created by User on 15/06/2017.
 */

public class LoginActivity extends Activity {

    private EditText etUser, etPassword;
    private Button btnLogin;
    private UserDAO mUserDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(OnclickLogin);

        mUserDAO = new UserDAO(LoginActivity.this);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginActivity.this);
        try {
            dataBaseHelper.createDataBase();
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    private View.OnClickListener OnclickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User user = new User();
            user.setUser(etUser.getText().toString());
            user.setPassword(etPassword.getText().toString());
            if(validaCamposVacios()){
                if(validar(user)){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(),"HOLA MUNDO",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Usuario incorrecto",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private boolean validar(User user){
        boolean result;
        if(mUserDAO.ValidarLogin(user)){
            result = true;
        }else{result = false;}
        return result;
    }

    private boolean validaCamposVacios() {
        String email = etUser.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etUser.setError("Ingrese su usuario");
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Ingrese su contraseña");
            return false;
        }
        if (email.isEmpty()) return false;
        if (password.isEmpty()) return false;

        return true;
    }
}
