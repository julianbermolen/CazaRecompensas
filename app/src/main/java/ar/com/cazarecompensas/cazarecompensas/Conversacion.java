package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conversacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    Intent intent = getIntent();
        int idMensaje = intent.getIntExtra("Id",0);
        int idUsuario = intent.getIntExtra("IdUsuario",0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ComentarioService service2 = retrofit.create(ComentarioService.class);

        Call<Comentario[]> comentarioResponse = service2.getBandejaEntrada(idUsuario);
        comentarioResponse.enqueue(new Callback<Comentario[]>() {
            @Override
            public void onResponse(Call<Comentario[]> call, Response<Comentario[]> response) {

            }

            @Override
            public void onFailure(Call<Comentario[]> call, Throwable t) {

            }
        });




    }

}
