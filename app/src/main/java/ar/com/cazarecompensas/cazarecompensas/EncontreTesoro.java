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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
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

        Tesoro tesoro = i.getParcelableExtra("Tesoro");
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
        Picasso.with(getApplicationContext()).load("http://www.seguroautomotor.org/wp-content/uploads/2015/01/auto-s.png").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(fotoTesoro);
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

                Comentario comentario = new Comentario();
                comentario.setComentario(mensaje.getText().toString());
                comentario.setIdUsuario(usuario.getIdUsuario());
                Call<ModelResponse> call = service.postMessage(comentario);
                try {
                   ModelResponse model = call.execute().body();

                } catch (IOException e) {
                    e.printStackTrace();
                }

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
}
