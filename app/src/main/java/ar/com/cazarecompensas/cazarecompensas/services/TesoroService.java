package ar.com.cazarecompensas.cazarecompensas.services;

import android.graphics.Bitmap;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by julia on 16/10/2017.
 */

public interface TesoroService {
    @FormUrlEncoded
    @POST("Tesoros/guardar")
    Call<ModelResponse> postTesoro(@Field("Nombre") String nombre, @Field("Descripcion") String descripcion, @Field("IdTesoroCategoria") int IdTesoroCategoria, @Field("IdUsuario") int idUsuario, @Field("Recompensa") int recompensa, @Field("IdTesoroEstado") int idTesoroEstado,@Field("Imagen1") String imageInByte1,@Field("Imagen2") String imageInByte2,@Field("Imagen3") String imageInByte3);
/*, @Query("Imagen1") String imagen1, @Query("Imagen2") String imagen2, @Query("Imagen3") String imagen3*/
}
