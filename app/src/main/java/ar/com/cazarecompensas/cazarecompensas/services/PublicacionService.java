package ar.com.cazarecompensas.cazarecompensas.services;

import ar.com.cazarecompensas.cazarecompensas.Models.Publicacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

/**
 * Created by julia on 24/11/2017.
 */

public interface PublicacionService {
    @GET("Publicaciones/obtener/{id}")
    Call<Publicacion> getPublicacion(@Path("id") int idPublicacion);
}
