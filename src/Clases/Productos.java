package Clases;

import java.io.Serializable;

public class Productos implements Serializable {
    private long codigoProducto, cantidadProducto;
    private double precioProducto;
    private String nombreProducto,descripcionProducto;

    public Productos(long codigoProducto, String nombreProducto,String descripcionProducto, long cantidadProducto, double precioProducto) {
        this.codigoProducto = codigoProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
    }

    public long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public long getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(long cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }
}