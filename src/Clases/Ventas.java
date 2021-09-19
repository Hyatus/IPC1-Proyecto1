package Clases;

import java.io.Serializable;

public class Ventas implements Serializable {
    private int noFacturaVenta;
    private long nitVenta;
    private String nombreClienteVenta;
    private String fechaVenta;
    private double totalVenta;

    public Ventas(int noFacturaVenta, long nitVenta, String nombreClienteVenta, String fechaVenta, double totalVenta) {
        this.noFacturaVenta = noFacturaVenta;
        this.nitVenta = nitVenta;
        this.nombreClienteVenta = nombreClienteVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
    }

    public int getNoFacturaVenta() {
        return noFacturaVenta;
    }

    public void setNoFacturaVenta(int noFacturaVenta) {
        this.noFacturaVenta = noFacturaVenta;
    }

    public long getNitVenta() {
        return nitVenta;
    }

    public void setNitVenta(long nitVenta) {
        this.nitVenta = nitVenta;
    }

    public String getNombreClienteVenta() {
        return nombreClienteVenta;
    }

    public void setNombreClienteVenta(String nombreClienteVenta) {
        this.nombreClienteVenta = nombreClienteVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

}
