package ar.com.cazarecompensas.cazarecompensas.Models;

/**
 * Created by julia on 16/10/2017.
 */

public class Tesoro {
    private int IdTesoro;
    private String Nombre;
    private String Descripcion;
    private float Recompensa;
    private String Latitud;
    private String Longitud;
    private String Imagen1;
    private String Imagen2;
    private String Imagen3;
    private int IdTesoroCategoria;

    public int getIdTesoro() {
        return IdTesoro;
    }

    public void setIdTesoro(int idTesoro) {
        IdTesoro = idTesoro;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public float getRecompensa() {
        return Recompensa;
    }

    public void setRecompensa(float recompensa) {
        Recompensa = recompensa;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getImagen1() {
        return Imagen1;
    }

    public void setImagen1(String imagen1) {
        Imagen1 = imagen1;
    }

    public String getImagen2() {
        return Imagen2;
    }

    public void setImagen2(String imagen2) {
        Imagen2 = imagen2;
    }

    public String getImagen3() {
        return Imagen3;
    }

    public void setImagen3(String imagen3) {
        Imagen3 = imagen3;
    }

    public int getIdTesoroCategoria() {
        return IdTesoroCategoria;
    }

    public void setIdTesoroCategoria(int idTesoroCategoria) {
        IdTesoroCategoria = idTesoroCategoria;
    }





}
