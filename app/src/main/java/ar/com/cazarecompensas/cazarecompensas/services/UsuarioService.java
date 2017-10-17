package ar.com.cazarecompensas.cazarecompensas.services;

import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by pablo on 9/19/17.
 */

public interface UsuarioService {
    @GET("/usuario")
    Call<Usuario> registrarUsuario();
}
