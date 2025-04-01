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
public class Productos {
    private String codigo_barras,nombre,descripcion;
    private int id_producto, id_categoria, id_proveedor;
    private double precio_compra,precio_venta_sugerido,peso_gramos;
    String dimensiones;
    private Date fecha_introduccion;

    public Productos(String codigo_barras, String nombre, String descripcion, int id_producto, int id_categoria, int id_proveedor, double precio_compra, double precio_venta_sugerido, double peso_gramos, String dimensiones, Date fecha_introduccion) {
        this.codigo_barras = codigo_barras;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_producto = id_producto;
        this.id_categoria = id_categoria;
        this.id_proveedor = id_proveedor;
        this.precio_compra = precio_compra;
        this.precio_venta_sugerido = precio_venta_sugerido;
        this.peso_gramos = peso_gramos;
        this.dimensiones = dimensiones;
        this.fecha_introduccion = fecha_introduccion;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public double getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precio_compra = precio_compra;
    }

    public double getPrecio_venta_sugerido() {
        return precio_venta_sugerido;
    }

    public void setPrecio_venta_sugerido(double precio_venta_sugerido) {
        this.precio_venta_sugerido = precio_venta_sugerido;
    }

    public double getPeso_gramos() {
        return peso_gramos;
    }

    public void setPeso_gramos(double peso_gramos) {
        this.peso_gramos = peso_gramos;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Date getFecha_introduccion() {
        return fecha_introduccion;
    }

    public void setFecha_introduccion(Date fecha_introduccion) {
        this.fecha_introduccion = fecha_introduccion;
    }

    @Override
    public String toString() {
        return "Productos{" + "codigo_barras=" + codigo_barras + ", nombre=" + nombre + ", descripcion=" + descripcion + ", id_producto=" + id_producto + ", id_categoria=" + id_categoria + ", id_proveedor=" + id_proveedor + ", precio_compra=" + precio_compra + ", precio_venta_sugerido=" + precio_venta_sugerido + ", peso_gramos=" + peso_gramos + ", dimensiones=" + dimensiones + ", fecha_introduccion=" + fecha_introduccion + '}';
    }
    
}
