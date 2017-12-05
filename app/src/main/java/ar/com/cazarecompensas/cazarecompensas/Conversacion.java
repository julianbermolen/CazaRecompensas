package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.mercadopago.model.Item;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.com.cazarecompensas.cazarecompensas.Models.Adapters.ConversacionAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.Adapters.MensajeAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conversacion extends AppCompatActivity {
    private ConversacionAdapter adapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBandejaEntrada();
            }
        });

    Intent intent = getIntent();
        int idMensaje = intent.getIntExtra("Id",0);
        final int idUsuario = intent.getIntExtra("IdUsuario",0);
        final int numeroConversacion = intent.getIntExtra("numeroConversacion",0);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ComentarioService service2 = retrofit.create(ComentarioService.class);

        Call<List<LinkedTreeMap<Integer,List<Comentario>>>> comentarioResponse = service2.getBandejaEntrada(idUsuario);
        comentarioResponse.enqueue(new Callback<List<LinkedTreeMap<Integer,List<Comentario>>>>() {
            @Override
            public void onResponse(Call<List<LinkedTreeMap<Integer,List<Comentario>>>> call, Response<List<LinkedTreeMap<Integer,List<Comentario>>>> response) {

                listview = (ListView) findViewById(R.id.listViewMensajes);
                List<LinkedTreeMap<Integer, List<Comentario>>> list = response.body();
                List<Comentario> comentarios = null;
                for (LinkedTreeMap<Integer, List<Comentario>> dic : list) {
                    for (Map.Entry<Integer, List<Comentario>> e : dic.entrySet()) {

                        comentarios = (List) e.getValue();

                    }
                }

                int cont = 0;

                Comentario[] comentarios1 = null;


                for (LinkedTreeMap<Integer, List<Comentario>> dic : list) {
                    for (Map.Entry<Integer, List<Comentario>> e : dic.entrySet()) {

                        comentarios = (List) e.getValue();

                        if (comentarios.get(0).getNumeroConversacion() == numeroConversacion) {
                            comentarios1 = new Comentario[comentarios.size()];
                            int cont2 = 0;
                            int i2 = comentarios.size() - 1;
                            for (int i = i2; i > -1; i--) {
                                comentarios1[cont2] = e.getValue().get(i);
                                cont2++;
                            }
                        }

                        cont++;
                    }
                }

                CircleImageView imageUser = (CircleImageView) findViewById(R.id.fotoUser);
                TextView nombreUser = (TextView) findViewById(R.id.nombreUser);

                if (idUsuario == comentarios1[0].getIdUsuarioEmisor()) {
                    Picasso.with(getApplicationContext()).load(comentarios1[0].getUsuarioReceptor().getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageUser);
                    String nombre = comentarios1[0].getUsuarioReceptor().getNombre() + " " + comentarios1[0].getUsuarioReceptor().getApellido();
                    nombreUser.setText(nombre);
                    toolbar.setTitle(nombre);
                } else {

                    String nombre = comentarios1[0].getUsuarioEmisor().getNombre() + " " + comentarios1[0].getUsuarioEmisor().getApellido();
                    nombreUser.setText(nombre);
                    toolbar.setTitle("  "+nombre);
                    Drawable bitmap = getDrawable(comentarios1[0].getUsuarioEmisor().getUrlFoto());
                    toolbar.setLogo(bitmap);

                }

                adapter = new ConversacionAdapter(getApplicationContext(), comentarios1, idUsuario);
                listview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<LinkedTreeMap<Integer,List<Comentario>>>> call, Throwable t) {

            }
        });




    }
    private void goBandejaEntrada() {
        Intent intent = new Intent(this,BandejaEntrada.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public Drawable getDrawable(String bitmapUrl) {
        try {
            URL url = new URL(bitmapUrl);
            Drawable d =new BitmapDrawable(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
            return d;
        }
        catch(Exception ex) {return null;}
    }
}
