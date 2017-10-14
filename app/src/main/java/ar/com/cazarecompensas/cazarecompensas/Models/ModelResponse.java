package ar.com.cazarecompensas.cazarecompensas.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by julia on 14/10/2017.
 */

public class ModelResponse {

        @SerializedName("exito")
        @Expose
        private Boolean exito;
        @SerializedName("mensaje")
        @Expose
        private String mensaje;

    public Boolean getExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }
}
