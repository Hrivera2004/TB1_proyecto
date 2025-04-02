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
public class Empleados {
    //commemts
    private int id_empleado;
    private String nombre,apellido,puesto,email,telefono;
    private Date fecha_contratacion;

    public Empleados(int id_empleado, String nombre, String apellido, String puesto, String email, String telefono, Date fecha_contratacion) {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.email = email;
        this.telefono = telefono;
        this.fecha_contratacion = fecha_contratacion;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
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

    public Date getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(Date fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    @Override
    public String toString() {
        return "Empleados{" + "id_empleado=" + id_empleado + ", nombre=" + nombre + ", apellido=" + apellido + ", puesto=" + puesto + ", email=" + email + ", telefono=" + telefono + ", fecha_contratacion=" + fecha_contratacion + '}';
    }
}
