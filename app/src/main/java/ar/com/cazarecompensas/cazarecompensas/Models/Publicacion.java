package ar.com.cazarecompensas.cazarecompensas.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by julia on 30/10/2017.
 */

public class Publicacion {
    @SerializedName("idPublicacion")
    private int IdPublicacion;
    @SerializedName("idTesoro")
    private int IdTesoro;
    @SerializedName("tesoro")
    private Tesoro tesoro;

    public int getIdPublicacion() {
        return IdPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        IdPublicacion = idPublicacion;
    }

    public int getIdTesoro() {
        return IdTesoro;
    }

    public void setIdTesoro(int idTesoro) {
        IdTesoro = idTesoro;
    }

    public Tesoro getTesoro() {
        return tesoro;
    }

    public void setTesoro(Tesoro tesoro) {
        this.tesoro = tesoro;
    }
}
