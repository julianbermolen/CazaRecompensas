package ar.com.cazarecompensas.cazarecompensas.services;

import org.json.JSONArray;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.PeticionRecompensaModel;
import ar.com.cazarecompensas.cazarecompensas.Models.Publicacion;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.TesoroCategoria;
import ar.com.cazarecompensas.cazarecompensas.PeticionRecompensa;
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
    Call<ModelResponse> postTesoro(@Field("Nombre") String nombre, @Field("Descripcion") String descripcion, @Field("IdTesoroCategoria") int IdTesoroCategoria, @Field("IdUsuario") int idUsuario, @Field("Recompensa") int recompensa, @Field("IdTesoroEstado") int idTesoroEstado,@Field("Imagen1") String imageInByte1,@Field("Imagen2") String imageInByte2,@Field("Imagen3") String imageInByte3,@Field("Latitud") String latitud,@Field("Longitud") String longitud);
/*, @Query("Imagen1") String imagen1, @Query("Imagen2") String imagen2, @Query("Imagen3") String imagen3*/

    @GET("Tesoros/ObtenerCategoria")
    Call<TesoroCategoria[]> getCategoria();

    @GET("Tesoros/obtener")
    Call<Tesoro[]> getTesoros();

    @GET("Tesoros/ObtenerIdPublicacionPorIdTesoro/{id}")
    Call<Publicacion> getIdPublicacion(@Path("id") int idTesoro);


    @FormUrlEncoded
    @POST("Tesoros/CambiarEstadoTesoro")
    Call<ModelResponse> postCambiarEstadoTesoro(@Field("IdTesoro") int idTesoro,@Field("IdEstado") int idEstado);


    @FormUrlEncoded
    @POST("PeticionRecompensa/guardar")
    Call<ModelResponse> postPeticionRecompensa(@Field("IdUsuario") int idUsuario, @Field("IdTesoro") int idTesoro,@Field("Estado") int estado);

    @GET("PeticionRecompensa/obtenerPorIdUsuario/{id}")
    Call<PeticionRecompensaModel[]> getPeticionRecompensaPorIdUsuario(@Path("id") int idUsuario);

    @GET("PeticionRecompensa/obtenerPorIdUsuarioQueSolicitoRecompensa/{id}")
    Call<PeticionRecompensaModel[]> getMisRecompensasPorIdUsuarioQueSolicitoRecompensa(@Path("id") int idUsuario);

    @FormUrlEncoded
    @POST("PeticionRecompensa/actualizarEstado")
    Call<ModelResponse> postValidarPeticionRecompensa(@Field("IdUsuario") int idUsuario, @Field("IdTesoro") int idTesoro,@Field("Estado") int estado);



}

