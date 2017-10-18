package ar.com.cazarecompensas.cazarecompensas.Models;

/**
 * Created by jbermolen on 9/10/2017.
 */

public class Usuario {
    private Long idUsuario;
    private String Nombre;
    private String Apellido;
    private String UrlFoto;
    private String Email;
<<<<<<< HEAD
    private long IdFacebook;
=======
    private Long IdFacebook;
>>>>>>> 2b81f8fee2d346d2f36d7438e84d57484107bafd

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

<<<<<<< HEAD
    public long getIdFacebook() {
        return IdFacebook;
    }

    public void setIdFacebook(long idFacebook) {
=======
    public Long getIdFacebook() {
        return IdFacebook;
    }

    public void setIdFacebook(Long idFacebook) {
>>>>>>> 2b81f8fee2d346d2f36d7438e84d57484107bafd
        IdFacebook = idFacebook;
    }
}
