package ar.com.cazarecompensas.cazarecompensas.services;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by julia on 30/10/2017.
 */

public interface ComentarioService {
    @GET("Comentarios/obtener/bandejaEntrada/{idUsuario}")
    Call<Comentario[]> getBandejaEntrada(@Path("idUsuario") int idUsuario);
}
