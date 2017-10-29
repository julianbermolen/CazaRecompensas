package ar.com.cazarecompensas.cazarecompensas.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by julia on 29/10/2017.
 */

public class Comentario {
    @SerializedName("idComentario")
    private int IdComentario;
    @SerializedName("idPublicacion")
    private int IdPublicacion;
    @SerializedName("idUsuario")
    private int IdUsuario;
    @SerializedName("comentario")
    private int Comentario;
    @SerializedName("idRespuesta")
    private int IdRespuesta;

    public int getIdComentario() {
        return IdComentario;
    }

    public void setIdComentario(int idComentario) {
        IdComentario = idComentario;
    }

    public int getIdPublicacion() {
        return IdPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        IdPublicacion = idPublicacion;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getComentario() {
        return Comentario;
    }

    public void setComentario(int comentario) {
        Comentario = comentario;
    }

    public int getIdRespuesta() {
        return IdRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        IdRespuesta = idRespuesta;
    }
}
