package ar.com.cazarecompensas.cazarecompensas.Models;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ar.com.cazarecompensas.cazarecompensas.MainActivity;
import ar.com.cazarecompensas.cazarecompensas.PeticionRecompensa;
import ar.com.cazarecompensas.cazarecompensas.R;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.cazarecompensas.cazarecompensas.R.id.imageView;
import static ar.com.cazarecompensas.cazarecompensas.R.id.nombreUser;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.Q;

/**
 * Created by Laucha on 15/11/2017.
 */

public class PeticionRecompensaAdapter extends ArrayAdapter<PeticionRecompensaModel> {

    PeticionRecompensaModel[] peticionRecompensasList;

    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public PeticionRecompensaAdapter(Context context, PeticionRecompensaModel[] objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        peticionRecompensasList = objects;

    }

    @Override
    public PeticionRecompensaModel getItem(int position) {
        return peticionRecompensasList[position];
    }

    private static class ViewHolder {
        public final CardView rootView;
        public final TextView TituloAdapterPR;
        public final TextView TextoAdapterPR;
        public final CircleImageView imagenTesoroPR;
        public final Button validarPeticion;
        public final Button rechazarPeticion;


        private ViewHolder(CardView rootView, TextView TextoAdapterPR,TextView TituloAdapterPR, CircleImageView imagenTesoroPR,Button validarPeticion,Button rechazarPeticion) {
            this.rootView = rootView;
            this.TituloAdapterPR = TituloAdapterPR;
            this.imagenTesoroPR = imagenTesoroPR;
            this.TextoAdapterPR = TextoAdapterPR;
            this.validarPeticion = validarPeticion;
            this.rechazarPeticion = rechazarPeticion;

        }

        public static ViewHolder create(CardView rootView) {
            CircleImageView imagenTesoroPR = (CircleImageView) rootView.findViewById(R.id.imagenTesoroPR);
            TextView TextoAdapterPR = (TextView) rootView.findViewById(R.id.TextoAdapterPR);
            TextView TituloAdapterPR = (TextView) rootView.findViewById(R.id.TituloAdapterPR);
            Button validarPeticion = (Button) rootView.findViewById(R.id.validarPeticion);
            Button rechazarPeticion = (Button) rootView.findViewById(R.id.rechazarPeticion);
            return new ViewHolder(rootView,TextoAdapterPR,TituloAdapterPR,imagenTesoroPR,validarPeticion,rechazarPeticion);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.adapter_peticion_recompensa, parent, false);
            vh = ViewHolder.create((CardView) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final PeticionRecompensaModel item = getItem(position);

        String titulo = item.getTesoro().getNombre();
        vh.TituloAdapterPR.setText(titulo);


        String texto = item.getUsuario().getApellido()+" quiere reclamar su recompensa.";
        vh.TextoAdapterPR.setText(texto);


        String urlImagen = item.getTesoro().getImagen1();

       if(urlImagen == "")
        {Picasso.with(context).load(R.mipmap.ic_launcher).into(vh.imagenTesoroPR);
        }else{
            Picasso.with(context).load(urlImagen).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imagenTesoroPR);
        }



        vh.validarPeticion.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     goValidarPeticionPositiva(item);

                                                 }
                                             }
        );

        vh.rechazarPeticion.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     goValidarPeticionNegativa(item);
                                                 }
                                             }
        );



        return vh.rootView;
    }

    private void goValidarPeticionPositiva(PeticionRecompensaModel PeticionRecompensa)  {



      //Creo el llamado asincronico
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://li1166-116.members.linode.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TesoroService service = retrofit.create(TesoroService.class);


        Call<ModelResponse> modelResponseCall = service.postValidarPeticionRecompensa(PeticionRecompensa.getIdUsuario(),PeticionRecompensa.getIdTesoro(),2);
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


        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://li1166-116.members.linode.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TesoroService service2 = retrofit2.create(TesoroService.class);

        Call<ModelResponse> modelResponseCall2 = service2.postCambiarEstadoTesoro(PeticionRecompensa.getIdTesoro(),2);
        modelResponseCall2.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {

                int statusCode = response.code();

                ModelResponse modelResponse = response.body();
                Log.d("EstadoTesorox","onResponse:" +statusCode);

                Intent intent = new Intent(getContext().getApplicationContext(),MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {

            }
        });


    }

    private void goValidarPeticionNegativa(PeticionRecompensaModel PeticionRecompensa)  {

        //Creo el llamado asincronico
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://li1166-116.members.linode.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TesoroService service = retrofit.create(TesoroService.class);


        Call<ModelResponse> modelResponseCall = service.postValidarPeticionRecompensa(PeticionRecompensa.getIdUsuario(),PeticionRecompensa.getIdTesoro(),3);
        modelResponseCall.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {

                int statusCode = response.code();

                ModelResponse modelResponse = response.body();
                Log.d("NuevoTesorox","onResponse:" +statusCode);

                Intent intent = new Intent(getContext().getApplicationContext(),MainActivity.class);
                context.startActivity(intent);

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {

            }
        });


    }



}
