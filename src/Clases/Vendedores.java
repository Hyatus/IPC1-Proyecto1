package Clases;

import java.io.Serializable;

public class Vendedores implements Serializable {
    private long codigoVendedor;
    private String nombreVendedor;
    private long cajaVendedor;
    public Ventas[] ventas = new Ventas[1000];
    private char generoVendedor;
    private String passwordVendedor;
    private long numeroVentas;
    private int contadorFacturasVend = 0;

    public int getContadorFacturasVend() {
        return contadorFacturasVend;
    }

    public void setContadorFacturasVend(int contadorFacturas) {
        this.contadorFacturasVend += contadorFacturas;
    }

    public Vendedores(long codigoVendedor, String nombreVendedor, long cajaVendedor, Ventas[] ventas, char generoVendedor, String passwordVendedor) {
        this.codigoVendedor = codigoVendedor;
        this.nombreVendedor = nombreVendedor;
        this.cajaVendedor = cajaVendedor;
        this.ventas = ventas;
        this.generoVendedor = generoVendedor;
        this.passwordVendedor = passwordVendedor;
    }

    public Vendedores(long codigoVendedor, String nombreVendedor, long cajaVendedor, long numeroVentas, char generoVendedor, String passwordVendedor) {
        this.codigoVendedor = codigoVendedor;
        this.nombreVendedor = nombreVendedor;
        this.cajaVendedor = cajaVendedor;
        this.generoVendedor = generoVendedor;
        this.passwordVendedor = passwordVendedor;
        this.numeroVentas = numeroVentas;
    }

    public long getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(long codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public long getCajaVendedor() {
        return cajaVendedor;
    }

    public void setCajaVendedor(long cajaVendedor) {
        this.cajaVendedor = cajaVendedor;
    }

    public Ventas[] getVentas() {
        return ventas;
    }

    public void setVentas(Ventas[] ventas) {
        this.ventas = ventas;
    }

    public char getGeneroVendedor() {
        return generoVendedor;
    }

    public void setGeneroVendedor(char generoVendedor) {
        this.generoVendedor = generoVendedor;
    }

    public String getPasswordVendedor() {
        return passwordVendedor;
    }

    public void setPasswordVendedor(String passwordVendedor) {
        this.passwordVendedor = passwordVendedor;
    }

    public long getNumeroVentas() {
        return numeroVentas;
    }

    public void setNumeroVentas(long numeroVentas) {
        this.numeroVentas = numeroVentas;
    }
}
