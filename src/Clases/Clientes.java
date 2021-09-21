package Clases;

import java.io.Serializable;

public class Clientes implements Serializable {
    private long codigoCliente;
    private String nombreCliente;
    private String nitCliente;
    private String correoCliente;
    private char generoCliente;

    public Clientes(long codigoCliente, String nombreCliente, String nitCliente, String correoCliente, char generoCliente) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.nitCliente = nitCliente;
        this.correoCliente = correoCliente;
        this.generoCliente = generoCliente;
    }

    public long getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(long codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public char getGeneroCliente() {
        return generoCliente;
    }

    public void setGeneroCliente(char generoCliente) {
        this.generoCliente = generoCliente;
    }
}
