package com.isil.romero_rodriguez_arturo.ENTIDADES;

/**
 * Created by User on 15/06/2017.
 */

public class User {

    private Long idUser;
    private String user;
    private String password;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
