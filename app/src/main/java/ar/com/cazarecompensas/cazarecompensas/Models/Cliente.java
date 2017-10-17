package ar.com.cazarecompensas.cazarecompensas.Models;

/**
 * Created by pablo on 9/19/17.
 */

public class Cliente {
    private int idUsuario;
    private String nombre;
    private String apellido;

    @Override
    public String toString() {
        return "Cliente{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
}
