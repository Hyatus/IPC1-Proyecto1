package Clases;

import java.io.Serializable;

public class Sucursales implements Serializable {
    private long codigoSucursal,telefonoSucursal;
    private String nombreSucursal,direccionSucursal,correoSucursal;

    public Sucursales(){

    }

    public Sucursales(long codigoSucursal, String nombreSucursal, String direccionSucursal, String correoSucursal,long telefonoSucursal) {
        this.codigoSucursal = codigoSucursal;
        this.telefonoSucursal = telefonoSucursal;
        this.nombreSucursal = nombreSucursal;
        this.direccionSucursal = direccionSucursal;
        this.correoSucursal = correoSucursal;
    }

    public long getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(long codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public long getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(long telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public String getCorreoSucursal() {
        return correoSucursal;
    }

    public void setCorreoSucursal(String correoSucursal) {
        this.correoSucursal = correoSucursal;
    }

    @Override
    public String toString() {
        return codigoSucursal +" "+ nombreSucursal;
    }
}

