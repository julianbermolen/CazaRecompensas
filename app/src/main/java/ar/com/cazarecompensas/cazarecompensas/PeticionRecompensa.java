package ar.com.cazarecompensas.cazarecompensas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.PeticionRecompensaAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.PeticionRecompensaModel;
import ar.com.cazarecompensas.cazarecompensas.Models.Publicacion;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import ar.com.cazarecompensas.cazarecompensas.R;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.cazarecompensas.cazarecompensas.MapaNuevoTesoro.encodeTobase64;
import static ar.com.cazarecompensas.cazarecompensas.R.id.listView;

public class PeticionRecompensa extends AppCompatActivity {

    Button botonPeticionRecompensa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion_recompensa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ObtenerImagenTesoro();

        botonPeticionRecompensa = (Button) findViewById(R.id.botonPeticionRecompensa);

        botonPeticionRecompensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guardarPeticionRecompensa();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //El boton se desabilita al querer obtener la recompensa de tu propio tesoro
        VerificarMismoUsuario();

        //No funciona, rompe en la comparacion
        //VerificarPeticionesRepetida();



    }

    public void ObtenerImagenTesoro(){


        //Obtengo el Tesoro del intent
        Intent i = getIntent();

        final Tesoro tesoro = i.getParcelableExtra("Tesoro");
        final Usuario usuario = (Usuario) i.getSerializableExtra("Usuario");

        ImageView fotoTesoro = (ImageView) findViewById(R.id.imagenPeticionRecompensa);

        String imagen1 = tesoro.getImagen1();
        Picasso.with(getApplicationContext()).load(imagen1).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(fotoTesoro);

    }

    public void VerificarMismoUsuario(){


        //Obtengo el Tesoro del intent
        Intent i = getIntent();

        final Tesoro tesoro = i.getParcelableExtra("Tesoro");

        int idUsuario = 0;

        idUsuario= obtenerUsuarioActual();

        if(idUsuario == tesoro.getIdUsuario()){

            botonPeticionRecompensa.setBackgroundColor(Color.parseColor("#c0c0c0"));
            botonPeticionRecompensa.setEnabled(false);

        }


    }


    private void VerificarPeticionesRepetida() {




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
                Intent intent = getIntent();
                final Tesoro tesoro = intent.getParcelableExtra("Tesoro");
                int a =tesoro.getIdTesoro();
                for(int i = 0; i <= response.body().length; i = i + 1)
                {
                    //rompe aca
                    //int b = response.body()[i].getTesoro().getIdTesoro();
                    //
                    if(tesoro == response.body()[i].getTesoro() ){

/*                        botonPeticionRecompensa.setBackgroundColor(Color.parseColor("#c0c0c0"));
                        botonPeticionRecompensa.setEnabled(false);*/

                    }

                }


            }

            @Override
            public void onFailure(Call<PeticionRecompensaModel[]> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error en la carga de tesoros", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public int ObtenerIdTesoro(){


        //Obtengo el Tesoro del intent
        Intent i = getIntent();

        final Tesoro tesoro = i.getParcelableExtra("Tesoro");

        return tesoro.getIdTesoro();

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


    public void guardarPeticionRecompensa(){

        //Creo el tesoro
        final PeticionRecompensa peticionRecompensa = new PeticionRecompensa();
        int idUsuario = 0;
        int idTesoro = 0;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        idUsuario= obtenerUsuarioActual();
        idTesoro = ObtenerIdTesoro();


        TesoroService service = retrofit.create(TesoroService.class);


        Call<ModelResponse> modelResponseCall = service.postPeticionRecompensa(idUsuario,idTesoro,1);
        modelResponseCall.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                int statusCode = response.code();

                ModelResponse modelResponse = response.body();
                Log.d("NuevoTesorox","onResponse:" +statusCode);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {

            }
        });
    }

}
