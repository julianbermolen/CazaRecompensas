package ar.com.cazarecompensas.cazarecompensas;

/**
 * Created by jbermolen on 9/10/2017.
 */

public class Usuario {
    private Integer idUsuario;
    private String Nombre;
    private String Apellido;
    private String UrlFoto;
    private String Email;
    private int IdFacebook;

    @Override
    public String toString() {
        return "Cliente{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + Nombre + '\'' +
                ", apellido='" + Apellido + '\'' +
                ", UrlFoto='" + UrlFoto + '\'' +
                ", Email='" + Email + '\'' +
                ", IdFacebook='" + IdFacebook + '\'' +
                '}';
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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

    public int getIdFacebook() {
        return IdFacebook;
    }

    public void setIdFacebook(int idFacebook) {
        IdFacebook = idFacebook;
    }
}
