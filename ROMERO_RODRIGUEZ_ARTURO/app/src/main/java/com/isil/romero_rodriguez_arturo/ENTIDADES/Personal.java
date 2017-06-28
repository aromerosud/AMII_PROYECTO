package com.isil.romero_rodriguez_arturo.ENTIDADES;

import com.isil.romero_rodriguez_arturo.R;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 16/06/2017.
 */

public class Personal implements Serializable {

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
    private int foto;

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

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

    public void RepositorioFotos(long id){

        if(id==1){
            foto =  R.drawable.emp1;
        }
        if(id==2){
            foto =  R.drawable.emp2;
        }
        if(id==3){
            foto =  R.drawable.emp3;
        }
        if(id==4){
            foto =  R.drawable.emp4;
        }
        if(id==5){
            foto =  R.drawable.emp5;
        }
        if(id==6){
            foto =  R.drawable.emp6;
        }
        if(id==7){
            foto =  R.drawable.emp7;
        }
        if(id==8){
            foto =  R.drawable.emp8;
        }
        if(id==9){
            foto =  R.drawable.emp9;
        }
        if(id==10){
            foto =  R.drawable.emp10;
        }
        if(id==11){
            foto =  R.drawable.emp11;
        }
        if(id==12){
            foto =  R.drawable.emp12;
        }
        if(id==13){
            foto =  R.drawable.emp13;
        }
        if(id==14){
            foto =  R.drawable.emp14;
        }
        if(id==15){
            foto =  R.drawable.emp15;
        }
        if(id==16){
            foto =  R.drawable.emp16;
        }
        if(id==17){
            foto =  R.drawable.emp17;
        }
        if(id==18){
            foto =  R.drawable.emp18;
        }
        if(id==19){
            foto =  R.drawable.emp19;
        }
        if(id==20){
            foto =  R.drawable.emp20;
        }
        if(id==21){
            foto =  R.drawable.emp21;
        }
        if(id==22){
            foto =  R.drawable.emp22;
        }
        if(id==23){
            foto =  R.drawable.emp23;
        }
        if(id==24){
            foto =  R.drawable.emp24;
        }
        if(id==25){
            foto =  R.drawable.emp25;
        }
        if(id==26){
            foto =  R.drawable.emp26;
        }
        if(id==27){
            foto =  R.drawable.emp27;
        }
        if(id==28){
            foto =  R.drawable.emp28;
        }
        if(id==29){
            foto =  R.drawable.emp29;
        }
        if(id==30){
            foto =  R.drawable.emp30;
        }
    }
}
