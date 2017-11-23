package ar.com.cazarecompensas.cazarecompensas.services;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by julia on 30/10/2017.
 */

public interface ComentarioService {
    @GET("Comentarios/obtener/bandejaEntrada/{idUsuario}")
    Call<ArrayList> getBandejaEntrada(@Path("idUsuario") int idUsuario);

    @POST("Comentarios/guardar")
    Call<ModelResponse> postMessage(@Query("IdPublicacion") int idPublicacion, @Query("IdUsuario") int idUsuario, @Query("Detalle") String detalle, @Query("IdRespuestaComentario") int idComentarioRespuesta, @Nullable @Query("Imagen") String imagen, @Query("MensajeLeido") boolean mensajeLeido);
}
