package ar.com.cazarecompensas.cazarecompensas.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Laucha on 23/10/2017.
 */

public class TesoroCategoria {
    @SerializedName("idTesoroCategoria")
    private Integer IdTesoroCategoria;
    @SerializedName("nombre")
    private String Nombre;


    @Override
    public String toString() {
        return "TesoroCategoria{" +
                "IdTesoroCategoria=" + IdTesoroCategoria +
                ", Nombre='" + Nombre + '\'' +
                '}';
    }

    public Integer getIdTesoroCategoria() {
        return IdTesoroCategoria;
    }

    public void setIdTesoroCategoria(Integer idTesoroCategoria) {
        this.IdTesoroCategoria = idTesoroCategoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

}
