package ar.com.cazarecompensas.cazarecompensas;

import android.media.session.MediaSession;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.TokenRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by julian on 14/10/2017.
 */

public interface  LoginApi {

    @POST("Login/registrarUsuario")
    Call<ModelResponse> getExito(@Query("Nombre") String nombre,@Query("Apellido") String apellido, @Query("email") String Email,@Query("idFacebook") String idFacebook, @Query("urlFoto") String urlFoto );


}
