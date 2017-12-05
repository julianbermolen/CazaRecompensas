package ar.com.cazarecompensas.cazarecompensas.services;

import android.support.annotation.Nullable;

import com.google.gson.internal.LinkedTreeMap;

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
    Call<List<LinkedTreeMap<Integer,List<Comentario>>>> getBandejaEntrada(@Path("idUsuario") int idUsuario);
    @FormUrlEncoded
    @POST("Comentarios/guardar")
    Call<ModelResponse> postMessage(@Field("IdPublicacion") int idPublicacion, @Field("IdUsuarioEmisor") int idUsuarioEmisor,@Field("IdUsuarioReceptor") int idUsuarioReceptor, @Field("Detalle") String detalle,@Nullable @Field("Imagen") String imagen, @Field("MensajeLeido") boolean mensajeLeido);
}
