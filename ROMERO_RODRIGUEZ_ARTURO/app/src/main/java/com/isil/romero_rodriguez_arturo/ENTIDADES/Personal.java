package com.isil.romero_rodriguez_arturo.ENTIDADES;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 16/06/2017.
 */

public class Personal {

    private long idPersonal;
    private String nomPersonal;
    private String apePersonal;
    private String direccionPersonal;
    private String edadPersonal;
    private String flagActivo;
    private double latitudMap;
    private double longitudMap;
    private String numDoc;
    private String tipoDoc;
    private String fechaCumple;
    private String nomFoto;

    public String getNomFoto() {
        return nomFoto;
    }

    public void setNomFoto(String nomFoto) {
        this.nomFoto = nomFoto;
    }

    public long getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(long idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNomPersonal() {
        return nomPersonal;
    }

    public void setNomPersonal(String nomPersonal) {
        this.nomPersonal = nomPersonal;
    }

    public String getApePersonal() {
        return apePersonal;
    }

    public void setApePersonal(String apePersonal) {
        this.apePersonal = apePersonal;
    }

    public String getDireccionPersonal() {
        return direccionPersonal;
    }

    public void setDireccionPersonal(String direccionPersonal) {
        this.direccionPersonal = direccionPersonal;
    }

    public String getEdadPersonal() {
        return edadPersonal;
    }

    public void setEdadPersonal(String edadPersonal) {
        this.edadPersonal = edadPersonal;
    }

    public String getFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(String flagActivo) {
        this.flagActivo = flagActivo;
    }

    public double getLatitudMap() {
        return latitudMap;
    }

    public void setLatitudMap(double latitudMap) {
        this.latitudMap = latitudMap;
    }

    public double getLongitudMap() {
        return longitudMap;
    }

    public void setLongitudMap(double longitudMap) {
        this.longitudMap = longitudMap;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getFechaCumple() {
        return fechaCumple;
    }

    public void setFechaCumple(String fechaCumple) {
        this.fechaCumple = fechaCumple;
    }
}
