/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import java.util.Date;

/**
 *
 * @author micha
 */
public class Pedidos {
    private int id_pedido,id_cliente;
    private Date fecha_pedido,fecha_entrega_programada,fecha_entrega_real;
    private int id_empleado_vendedor,id_ruta;
    private String estado,notas;
    private double total;

    public Pedidos(int id_pedido, int id_cliente, Date fecha_pedido, Date fecha_entrega_programada, Date fecha_entrega_real, int id_empleado_vendedor, int id_ruta, String estado, String notas, double total) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.fecha_pedido = fecha_pedido;
        this.fecha_entrega_programada = fecha_entrega_programada;
        this.fecha_entrega_real = fecha_entrega_real;
        this.id_empleado_vendedor = id_empleado_vendedor;
        this.id_ruta = id_ruta;
        this.estado = estado;
        this.notas = notas;
        this.total = total;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public Date getFecha_entrega_programada() {
        return fecha_entrega_programada;
    }

    public void setFecha_entrega_programada(Date fecha_entrega_programada) {
        this.fecha_entrega_programada = fecha_entrega_programada;
    }

    public Date getFecha_entrega_real() {
        return fecha_entrega_real;
    }

    public void setFecha_entrega_real(Date fecha_entrega_real) {
        this.fecha_entrega_real = fecha_entrega_real;
    }

    public int getId_empleado_vendedor() {
        return id_empleado_vendedor;
    }

    public void setId_empleado_vendedor(int id_empleado_vendedor) {
        this.id_empleado_vendedor = id_empleado_vendedor;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedidos{" + "id_pedido=" + id_pedido + ", id_cliente=" + id_cliente + ", fecha_pedido=" + fecha_pedido + ", fecha_entrega_programada=" + fecha_entrega_programada + ", fecha_entrega_real=" + fecha_entrega_real + ", id_empleado_vendedor=" + id_empleado_vendedor + ", id_ruta=" + id_ruta + ", estado=" + estado + ", notas=" + notas + ", total=" + total + '}';
    }
    
}
