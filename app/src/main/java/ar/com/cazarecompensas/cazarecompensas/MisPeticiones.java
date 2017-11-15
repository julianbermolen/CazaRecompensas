package ar.com.cazarecompensas.cazarecompensas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.Profile;

import java.io.IOException;
import java.io.Serializable;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.PeticionRecompensaAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.PeticionRecompensaModel;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.TesoroAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.cazarecompensas.cazarecompensas.R.id.listView;
import static java.security.AccessController.getContext;

public class MisPeticiones extends AppCompatActivity {

    private ListView listView;
    private PeticionRecompensaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_peticiones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        descargarPeticionesRecompensa();
        listView = (ListView) findViewById(R.id.listViewMisPeticiones);
        listView.setAdapter(adapter);

    }

    private void descargarPeticionesRecompensa() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando ...");
        progressDialog.show();

        //Creo el llamado asincronico
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TesoroService service = retrofit.create(TesoroService.class);


        final Call<PeticionRecompensaModel[]> PeticionRecompensa = service.getPeticionRecompensaPorIdUsuario(obtenerUsuarioActual());
        PeticionRecompensa.enqueue(new Callback<PeticionRecompensaModel[]>() {
            @Override
            public void onResponse(Call<PeticionRecompensaModel[]> call, Response<PeticionRecompensaModel[]> response) {
                progressDialog.dismiss();
                adapter = new PeticionRecompensaAdapter(getApplicationContext(),response.body());
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PeticionRecompensaModel[]> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error en la carga de tesoros", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public int obtenerUsuarioActual(){
        int idUsuario = 0;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UsuarioService service2 = retrofit.create(UsuarioService.class);

        Profile profile = Profile.getCurrentProfile();
        String idFacebook = profile.getId().toString();
        Call<ModelResponse> callModel = service2.getUserId(idFacebook);
        try {
            ModelResponse model = callModel.execute().body();
            //Aca se obtiene el id del usuario actual
            idUsuario = model.getValor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  idUsuario;
    }





}
