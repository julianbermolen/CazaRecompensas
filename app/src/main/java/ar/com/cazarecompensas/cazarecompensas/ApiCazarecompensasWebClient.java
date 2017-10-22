package ar.com.cazarecompensas.cazarecompensas;

import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.Models.Cliente;
import ar.com.cazarecompensas.cazarecompensas.services.ClienteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pablo on 9/29/17.
 */

public class ApiCazarecompensasWebClient {

    private Retrofit retrofit;
    String result;

    public ApiCazarecompensasWebClient() {


    }

    public String Get(String path) {
        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/api" + path + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClienteService servicio = retrofit.create(ClienteService.class);
        servicio.getCliente().enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {

                result =  response.body().toString();

            }
            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {

                result  = t.getMessage();
            }
        });
        return result;
    }

}
