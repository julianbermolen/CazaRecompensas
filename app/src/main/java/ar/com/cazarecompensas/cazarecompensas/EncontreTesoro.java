package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Publicacion;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.PublicacionService;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EncontreTesoro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontre_tesoro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Obtengo el Tesoro del intent
        Intent i = getIntent();

        final Tesoro tesoro = i.getParcelableExtra("Tesoro");
        final Usuario usuario = (Usuario) i.getSerializableExtra("Usuario");
        ImageView fotoTesoro = (ImageView) findViewById(R.id.fotoTesoro);
        TextView nombreTesoro = (TextView) findViewById(R.id.nombreTesoro);
        TextView descripcionTesoro = (TextView) findViewById(R.id.descripcionTesoro);
        CircleImageView imageUser = (CircleImageView) findViewById(R.id.imageUser);
        TextView nombreUser = (TextView) findViewById(R.id.nombreUsuario);
        String nombre = usuario.getNombre()+" "+usuario.getApellido();
        nombreUser.setText(nombre);
        //Imagen del Usuario.
        Picasso.with(getApplicationContext()).load(usuario.getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageUser);
        //Imagen del Tesoro.
        String imagen1 = tesoro.getImagen1();
        Picasso.with(getApplicationContext()).load(imagen1).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(fotoTesoro);
        nombreTesoro.setText(tesoro.getNombre());
        descripcionTesoro.setText(tesoro.getDescripcion());


        Button enviar = (Button) findViewById(R.id.enviarMensaje);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mensaje = (EditText) findViewById(R.id.mensaje);

                //Creo el llamado asincronico
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.apiUrl))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ComentarioService service = retrofit.create(ComentarioService.class);
                TesoroService service2 = retrofit.create(TesoroService.class);
                UsuarioService service3 = retrofit.create(UsuarioService.class);
                PublicacionService service4 = retrofit.create(PublicacionService.class);

                Comentario comentario = new Comentario();
                comentario.setComentario(mensaje.getText().toString());
 //comentario.setIdUsuario(usuario.getIdUsuario());
                int idTesoro = tesoro.getIdTesoro();
                int idUsuarioReceptor = 0;
                int idPublicacion = 0;
                Call<Publicacion> call2 = service2.getIdPublicacion(idTesoro);
                try {
                    Publicacion publicacion2 = call2.execute().body();
                     idPublicacion = publicacion2.getIdPublicacion();
                    comentario.setIdPublicacion(idPublicacion);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Call<Publicacion> call3 = service4.getPublicacion(idPublicacion);
                try {
                    Publicacion publicacion = call3.execute().body();
                    idUsuarioReceptor = publicacion.getTesoro().getIdUsuario();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                int idUsuario2 = 0;
                Profile profile = Profile.getCurrentProfile();
                String idFacebook = profile.getId().toString();
                Call<ModelResponse> callModel = service3.getUserId(idFacebook);
                try {
                    ModelResponse model = callModel.execute().body();
                    idUsuario2 = model.getValor();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                int idUsuarioEmisor = idUsuario2;
                String detalle = comentario.getComentario();
                String imagen = comentario.getImagen();
                comentario.setMensajeLeido(false);
                boolean mensajeLeido = comentario.getMensajeLeido();

                Call<ModelResponse> call = service.postMessage(idPublicacion,idUsuarioEmisor,idUsuarioReceptor,detalle,imagen,mensajeLeido);
                call.enqueue(new Callback<ModelResponse>() {
                    @Override
                    public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                        goMainScreen();
                    }

                    @Override
                    public void onFailure(Call<ModelResponse> call, Throwable t) {
                        Toast.makeText(EncontreTesoro.this, "Error en el envío del mensaje", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


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

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
