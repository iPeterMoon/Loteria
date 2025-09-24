/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author rocha
 */
public class JugadaSubject extends Subject {
    private String nombre;
    private boolean estado;
    private int puntos;

    public JugadaSubject() {
    }

    public JugadaSubject(String nombre, boolean estado, int puntos) {
        this.nombre = nombre;
        this.estado = estado;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "JugadaSubject{" + "nombre=" + nombre + ", estado=" + estado + ", puntos=" + puntos + '}';
    }

}
