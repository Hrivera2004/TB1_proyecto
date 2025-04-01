/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

/**
 *
 * @author micha
 */
public class Rutas {
    private int id_ruta, id_empleado;
    private String nombre,zona_geografica,dia_semana;

    public Rutas(int id_ruta, int id_empleado, String nombre, String zona_geografica, String dia_semana) {
        this.id_ruta = id_ruta;
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.zona_geografica = zona_geografica;
        this.dia_semana = dia_semana;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
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

    public String getZona_geografica() {
        return zona_geografica;
    }

    public void setZona_geografica(String zona_geografica) {
        this.zona_geografica = zona_geografica;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    @Override
    public String toString() {
        return "Rutas{" + "id_ruta=" + id_ruta + ", id_empleado=" + id_empleado + ", nombre=" + nombre + ", zona_geografica=" + zona_geografica + ", dia_semana=" + dia_semana + '}';
    }
}
