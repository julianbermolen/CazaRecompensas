package ar.com.cazarecompensas.cazarecompensas.services;

import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.Models.Cliente;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by pablo on 9/19/17.
 */

public interface ClienteService {
    @GET("/clientes")
    Call<List<Cliente>> getCliente();
}
