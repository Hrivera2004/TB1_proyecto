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
public class Clientes {
    private int id_cliente;
    private String nombre_tienda,tipo_tienda,propietario,email,telefono,direccion,ciudad,codigo_postal;
    private Date fecha_registro,limite_credito;
    private String condiciones_pago;

    public Clientes(int id_cliente, String nombre_tienda, String tipo_tienda, String propietario, String email, String telefono, String direccion, String ciudad, String codigo_postal, Date fecha_registro, Date limite_credito, String condiciones_pago) {
        this.id_cliente = id_cliente;
        this.nombre_tienda = nombre_tienda;
        this.tipo_tienda = tipo_tienda;
        this.propietario = propietario;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigo_postal = codigo_postal;
        this.fecha_registro = fecha_registro;
        this.limite_credito = limite_credito;
        this.condiciones_pago = condiciones_pago;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_tienda() {
        return nombre_tienda;
    }

    public void setNombre_tienda(String nombre_tienda) {
        this.nombre_tienda = nombre_tienda;
    }

    public String getTipo_tienda() {
        return tipo_tienda;
    }

    public void setTipo_tienda(String tipo_tienda) {
        this.tipo_tienda = tipo_tienda;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getLimite_credito() {
        return limite_credito;
    }

    public void setLimite_credito(Date limite_credito) {
        this.limite_credito = limite_credito;
    }

    public String getCondiciones_pago() {
        return condiciones_pago;
    }

    public void setCondiciones_pago(String condiciones_pago) {
        this.condiciones_pago = condiciones_pago;
    }

    @Override
    public String toString() {
        return "Clientes{" + "id_cliente=" + id_cliente + ", nombre_tienda=" + nombre_tienda + ", tipo_tienda=" + tipo_tienda + ", propietario=" + propietario + ", email=" + email + ", telefono=" + telefono + ", direccion=" + direccion + ", ciudad=" + ciudad + ", codigo_postal=" + codigo_postal + ", fecha_registro=" + fecha_registro + ", limite_credito=" + limite_credito + ", condiciones_pago=" + condiciones_pago + '}';
    }
    
}
