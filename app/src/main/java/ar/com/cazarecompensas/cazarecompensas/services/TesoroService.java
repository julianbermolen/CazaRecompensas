package ar.com.cazarecompensas.cazarecompensas.services;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by julia on 16/10/2017.
 */

public interface TesoroService {
    @POST("Tesoros/guardar")
     Call<ModelResponse> postTesoro(@Query("Nombre") String nombre, @Query("Descripcion") String descripcion, @Query("IdTesoroCategoria") Long IdTesoroCategoria,@Query("IdUsuario") int idUsuario,@Query("Recompensa") int recompensa, @Query("IdTesoroEstado") int idTesoroEstado);
/*, @Query("Imagen1") String imagen1, @Query("Imagen2") String imagen2, @Query("Imagen3") String imagen3*/
}
