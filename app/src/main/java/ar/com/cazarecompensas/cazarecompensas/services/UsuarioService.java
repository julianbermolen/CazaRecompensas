package ar.com.cazarecompensas.cazarecompensas.services;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by pablo on 9/19/17.
 */

public interface UsuarioService {
    @GET("Usuarios/getUserId/{idFacebook}")
    Call<ModelResponse> getUserId(@Path("idFacebook") String idFacebook);
}
