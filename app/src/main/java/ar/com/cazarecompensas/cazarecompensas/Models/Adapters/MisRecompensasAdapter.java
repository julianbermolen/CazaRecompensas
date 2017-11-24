package ar.com.cazarecompensas.cazarecompensas.Models.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ar.com.cazarecompensas.cazarecompensas.Models.PeticionRecompensaModel;
import ar.com.cazarecompensas.cazarecompensas.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static ar.com.cazarecompensas.cazarecompensas.R.id.TextoAdapterPR;
import static ar.com.cazarecompensas.cazarecompensas.R.id.TituloAdapterPR;
import static ar.com.cazarecompensas.cazarecompensas.R.id.imagenTesoroPR;
import static ar.com.cazarecompensas.cazarecompensas.R.id.rechazarPeticion;
import static ar.com.cazarecompensas.cazarecompensas.R.id.validarPeticion;

/**
 * Created by Laucha on 23/11/2017.
 */

public class MisRecompensasAdapter extends ArrayAdapter<PeticionRecompensaModel> {

    PeticionRecompensaModel[] misRecompensasList;

    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MisRecompensasAdapter(Context context, PeticionRecompensaModel[] objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        misRecompensasList = objects;

    }

    @Override
    public PeticionRecompensaModel getItem(int position) {
        return misRecompensasList[position];
    }


    private static class ViewHolder {
        public final CardView rootView;
        public final TextView TituloAdapterMR;
        public final TextView TextoAdapterMR;
        public final CircleImageView imagenTesoroMR;



        private ViewHolder(CardView rootView, TextView TextoAdapterMR,TextView TituloAdapterMR, CircleImageView imagenTesoroMR) {
            this.rootView = rootView;
            this.TituloAdapterMR = TituloAdapterMR;
            this.imagenTesoroMR = imagenTesoroMR;
            this.TextoAdapterMR = TextoAdapterMR;


        }

        public static ViewHolder create(CardView rootView) {
            CircleImageView imagenTesoroMR = (CircleImageView) rootView.findViewById(R.id.imagenTesoroMR);
            TextView TextoAdapterMR = (TextView) rootView.findViewById(R.id.TextoAdapterMR);
            TextView TituloAdapterMR = (TextView) rootView.findViewById(R.id.TituloAdapterMR);
            return new ViewHolder(rootView,TextoAdapterMR,TituloAdapterMR,imagenTesoroMR);
        }



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.adapter_mis_recompensas, parent, false);
            vh = ViewHolder.create((CardView) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final PeticionRecompensaModel item = getItem(position);

        String titulo = " Felicitaciones !";
        vh.TituloAdapterMR.setText(titulo);


        String texto = "Recibiste : $" + item.getTesoro().getRecompensa() + " de "+ item.getTesoro().getUsuario().getNombre()+ " por el tesoro "+item.getTesoro().getNombre() ;
        vh.TextoAdapterMR.setText(texto);


        String urlImagen = item.getTesoro().getImagen1();

        if(urlImagen == "")
        {
            Picasso.with(context).load(R.mipmap.ic_launcher).into(vh.imagenTesoroMR);
        }else{
            Picasso.with(context).load(urlImagen).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imagenTesoroMR);
        }




        return vh.rootView;
    }


}
