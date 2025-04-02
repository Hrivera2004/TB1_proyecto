/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

/**
 *
 * @author micha
 */
public class Proveedores {
   private  int id_proveedor;
   private String nombre,pais_origen,contacto_nombre,contacto_email,contacto_telefono,direccion;
   private float tiempo_entrega_promedio;

    public Proveedores(int id_proveedor, String nombre, String pais_origen, String contacto_nombre, String contacto_email, String contacto_telefono, String direccion, float tiempo_entrega_promedio) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.pais_origen = pais_origen;
        this.contacto_nombre = contacto_nombre;
        this.contacto_email = contacto_email;
        this.contacto_telefono = contacto_telefono;
        this.direccion = direccion;
        this.tiempo_entrega_promedio = tiempo_entrega_promedio;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais_origen() {
        return pais_origen;
    }

    public void setPais_origen(String pais_origen) {
        this.pais_origen = pais_origen;
    }

    public String getContacto_nombre() {
        return contacto_nombre;
    }

    public void setContacto_nombre(String contacto_nombre) {
        this.contacto_nombre = contacto_nombre;
    }

    public String getContacto_email() {
        return contacto_email;
    }

    public void setContacto_email(String contacto_email) {
        this.contacto_email = contacto_email;
    }

    public String getContacto_telefono() {
        return contacto_telefono;
    }

    public void setContacto_telefono(String contacto_telefono) {
        this.contacto_telefono = contacto_telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getTiempo_entrega_promedio() {
        return tiempo_entrega_promedio;
    }

    public void setTiempo_entrega_promedio(float tiempo_entrega_promedio) {
        this.tiempo_entrega_promedio = tiempo_entrega_promedio;
    }

    @Override
    public String toString() {
        return "Proveedores{" + "id_proveedor=" + id_proveedor + ", nombre=" + nombre + ", pais_origen=" + pais_origen + ", contacto_nombre=" + contacto_nombre + ", contacto_email=" + contacto_email + ", contacto_telefono=" + contacto_telefono + ", direccion=" + direccion + ", tiempo_entrega_promedio=" + tiempo_entrega_promedio + '}';
    }
}
