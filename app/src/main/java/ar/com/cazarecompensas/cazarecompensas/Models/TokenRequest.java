package ar.com.cazarecompensas.cazarecompensas.Models;

/**
 * Created by julia on 14/10/2017.
 */

public class TokenRequest {

    private String Nombre;
    private String Apellido;
    private String UrlFoto;
    private String Email;
    private String IdFacebook;

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

    public String getIdFacebook() {
        return IdFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        IdFacebook = idFacebook;
    }

}
