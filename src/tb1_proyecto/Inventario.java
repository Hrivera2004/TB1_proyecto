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
public class Inventario {
    private int id_inventario,id_producto;
    private int cantidad_disponible;
    private String ubicacion_almacen,lote;
    private Date fecha_ingreso,fecha_caducidad;

    public Inventario(int id_inventario, int id_producto, int cantidad_disponible, String ubicacion_almacen, String lote, Date fecha_ingreso, Date fecha_caducidad) {
        this.id_inventario = id_inventario;
        this.id_producto = id_producto;
        this.cantidad_disponible = cantidad_disponible;
        this.ubicacion_almacen = ubicacion_almacen;
        this.lote = lote;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_caducidad = fecha_caducidad;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(int cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }

    public String getUbicacion_almacen() {
        return ubicacion_almacen;
    }

    public void setUbicacion_almacen(String ubicacion_almacen) {
        this.ubicacion_almacen = ubicacion_almacen;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    @Override
    public String toString() {
        return "Inventario{" + "id_inventario=" + id_inventario + ", id_producto=" + id_producto + ", cantidad_disponible=" + cantidad_disponible + ", ubicacion_almacen=" + ubicacion_almacen + ", lote=" + lote + ", fecha_ingreso=" + fecha_ingreso + ", fecha_caducidad=" + fecha_caducidad + '}';
    }
    
}