package ar.com.cazarecompensas.cazarecompensas.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jbermolen on 9/10/2017.
 */

public class Usuario {
    @SerializedName("idUsuario")
    private Long idUsuario;
    @SerializedName("nombre")
    private String Nombre;
    @SerializedName("apellido")
    private String Apellido;
    @SerializedName("urlFoto")
    private String UrlFoto;
    @SerializedName("email")
    private String Email;
    @SerializedName("idFacebook")
    private long IdFacebook;

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + Nombre + '\'' +
                ", apellido='" + Apellido + '\'' +
                ", UrlFoto='" + UrlFoto + '\'' +
                ", Email='" + Email + '\'' +
                ", IdFacebook='" + IdFacebook + '\'' +
                '}';
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getUrlFoto() {
        return UrlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        UrlFoto = urlFoto;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public long getIdFacebook() {
        return IdFacebook;
    }

    public void setIdFacebook(long idFacebook) {

    }
}
