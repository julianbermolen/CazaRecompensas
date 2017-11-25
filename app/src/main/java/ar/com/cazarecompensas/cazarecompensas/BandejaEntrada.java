package ar.com.cazarecompensas.cazarecompensas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.facebook.Profile;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.Adapters.MensajeAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.cazarecompensas.cazarecompensas.R.id.map;

public class BandejaEntrada extends AppCompatActivity {
    private Comentario[] comentario;
    private MensajeAdapter adapter;
    private ListView listview;
    private Inflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_entrada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        obtenerMensajes();

    }

    private void obtenerMensajes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsuarioService service2 = retrofit.create(UsuarioService.class);
        ComentarioService service = retrofit.create(ComentarioService.class);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando ...");
        progressDialog.show();
        Profile profile = Profile.getCurrentProfile();
        String idFacebook = profile.getId().toString();
        Call<ModelResponse> callModel = service2.getUserId(idFacebook);
        try {
            ModelResponse model = callModel.execute().body();
            final int idUsuario = model.getValor();
            Call<List<LinkedTreeMap<Integer,List<Comentario>>>> comentarioResponse = service.getBandejaEntrada(idUsuario);
            comentarioResponse.enqueue(new Callback<List<LinkedTreeMap<Integer,List<Comentario>>>>() {
                @Override
                public void onResponse(Call<List<LinkedTreeMap<Integer,List<Comentario>>>> call, Response<List<LinkedTreeMap<Integer,List<Comentario>>>> response) {
                    progressDialog.dismiss();
                    listview = (ListView) findViewById(R.id.listViewMensajes);

                    List<LinkedTreeMap<Integer,List<Comentario>>> list = response.body();
                    List<Comentario> comentarios = null;

                    for(LinkedTreeMap<Integer,List<Comentario>> dic : list){
                        for(Map.Entry<Integer,List<Comentario>> e : dic.entrySet()){

                            comentarios = (List) e.getValue();
                        }
                    }
                    Comentario[] comentarios1 = new Comentario[list.size()];
                    int cont = 0;
                    for(LinkedTreeMap<Integer,List<Comentario>> dic : list){
                        for(Map.Entry<Integer,List<Comentario>> e : dic.entrySet()){

                            comentarios = (List) e.getValue();
                            comentarios1[cont] = comentarios.get(0);
                            cont++;
                        }
                    }
                            adapter = new MensajeAdapter(getApplicationContext(), comentarios1,idUsuario);
                            listview.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<LinkedTreeMap<Integer,List<Comentario>>>> call, Throwable t) {
                    Log.d("ERROR:","No funciona la llamada");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
