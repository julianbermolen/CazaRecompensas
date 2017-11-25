package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                List<LinkedTreeMap<Integer,List<Comentario>>> list = response.body();
                List<Comentario> comentarios = null;
                for(LinkedTreeMap<Integer,List<Comentario>> dic : list){
                    for(Map.Entry<Integer,List<Comentario>> e : dic.entrySet()){

                        comentarios = (List) e.getValue();

                    }
                }
                Comentario[] comentarios1 = new Comentario[comentarios.size()];
                int cont = 0;
                for(LinkedTreeMap<Integer,List<Comentario>> dic : list){
                    for(Map.Entry<Integer,List<Comentario>> e : dic.entrySet()){

                        comentarios = (List) e.getValue();
                        if(comentarios.get(0).getNumeroConversacion() == numeroConversacion){
                            int cont2 = 0;
                            int i2 = comentarios.size()-1;
                            for(int  i= i2;i>-1;i--) {
                                comentarios1[cont2] = comentarios.get(i);
                                cont2++;
                            }
                        }

                        cont++;
                    }
                }

                CircleImageView imageUser = (CircleImageView) findViewById(R.id.fotoUser);
                TextView nombreUser = (TextView) findViewById(R.id.nombreUser);

                if(idUsuario == comentarios1[0].getIdUsuarioEmisor()){
                    Picasso.with(getApplicationContext()).load(comentarios1[0].getUsuarioReceptor().getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageUser);
                    String nombre = comentarios1[0].getUsuarioReceptor().getNombre()+" "+comentarios1[0].getUsuarioReceptor().getApellido();
                    nombreUser.setText(nombre);
                }else{
                    Picasso.with(getApplicationContext()).load(comentarios1[0].getUsuarioEmisor().getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageUser);
                    String nombre = comentarios1[0].getUsuarioEmisor().getNombre()+" "+comentarios1[0].getUsuarioEmisor().getApellido();
                    nombreUser.setText(nombre);
                }

                adapter = new ConversacionAdapter(getApplicationContext(), comentarios1,idUsuario);
                listview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<LinkedTreeMap<Integer,List<Comentario>>>> call, Throwable t) {

            }
        });




    }

}
