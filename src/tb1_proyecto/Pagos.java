/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

import java.util.Date;

/**
 *
 * @author micha
 */
public class Pagos {
    private int id_pago,id_pedido;
    private Date fecha_pago;
    private double monto;
    private String metodo_pago,referencia,estado;

    public Pagos(int id_pago, int id_pedido, Date fecha_pago, double monto, String metodo_pago, String referencia, String estado) {
        this.id_pago = id_pago;
        this.id_pedido = id_pedido;
        this.fecha_pago = fecha_pago;
        this.monto = monto;
        this.metodo_pago = metodo_pago;
        this.referencia = referencia;
        this.estado = estado;
    }

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pagos{" + "id_pago=" + id_pago + ", id_pedido=" + id_pedido + ", fecha_pago=" + fecha_pago + ", monto=" + monto + ", metodo_pago=" + metodo_pago + ", referencia=" + referencia + ", estado=" + estado + '}';
    }
    
}
