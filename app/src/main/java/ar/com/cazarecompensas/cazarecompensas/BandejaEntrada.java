package ar.com.cazarecompensas.cazarecompensas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.facebook.Profile;

import java.io.IOException;
import java.util.zip.Inflater;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.MensajeAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.cazarecompensas.cazarecompensas.R.id.listView;
import static java.security.AccessController.getContext;

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
            int idUsuario = model.getValor();
            Call<Comentario[]> comentarioResponse = service.getBandejaEntrada(idUsuario);
            comentarioResponse.enqueue(new Callback<Comentario[]>() {
                @Override
                public void onResponse(Call<Comentario[]> call, Response<Comentario[]> response) {
                    progressDialog.dismiss();
                    listview = (ListView) findViewById(R.id.listViewMensajes);

                    for(int i = 0;i<response.body().length;i++) {
                        if(response.body()[i].getIdRespuesta() == 0) {
                            adapter = new MensajeAdapter(getApplicationContext(), response.body());
                            listview.setAdapter(adapter);
                        }
                    }

                }

                @Override
                public void onFailure(Call<Comentario[]> call, Throwable t) {
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
