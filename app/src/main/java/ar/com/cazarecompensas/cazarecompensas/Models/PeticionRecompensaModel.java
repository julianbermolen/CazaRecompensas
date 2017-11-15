package ar.com.cazarecompensas.cazarecompensas.Models;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Laucha on 14/11/2017.
 */

public class PeticionRecompensaModel {

    @SerializedName("idUsuario")
    private int IdUsuario;
    @SerializedName("usuario")
    private Usuario Usuario;
    @SerializedName("idTesoro")
    private int IdTesoro;
    @SerializedName("tesoro")
    private Tesoro Tesoro;
    @SerializedName("estado")
    private int Estado;

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.IdUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario usuario) {
        Usuario = usuario;
    }

    public int getIdTesoro() {
        return IdTesoro;
    }

    public void setIdTesoro(int idTesoro) {
        IdTesoro = idTesoro;
    }

    public Tesoro getTesoro() {
        return Tesoro;
    }

    public void setTesoro(Tesoro tesoro) {
        this.Tesoro = tesoro;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        this.Estado = estado;
    }
}
