package ar.com.cazarecompensas.cazarecompensas.services;

import java.util.ArrayList;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by julia on 16/10/2017.
 */

public interface TesoroService {
    @FormUrlEncoded
    @POST("Tesoros/guardar")
    Call<ModelResponse> postTesoro(@Field("Nombre") String nombre, @Field("Descripcion") String descripcion, @Field("IdTesoroCategoria") int IdTesoroCategoria, @Field("IdUsuario") int idUsuario, @Field("Recompensa") int recompensa, @Field("IdTesoroEstado") int idTesoroEstado,@Field("Imagen1") String imageInByte1,@Field("Imagen2") String imageInByte2,@Field("Imagen3") String imageInByte3);
/*, @Query("Imagen1") String imagen1, @Query("Imagen2") String imagen2, @Query("Imagen3") String imagen3*/

    @GET("Tesoros/ObtenerCategoria")
    Call<ArrayList<String>> getCategory();

    @GET("Tesoros/obtener")
    Call<Tesoro[]> getTesoros();
    
}

