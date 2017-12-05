package ar.com.cazarecompensas.cazarecompensas.Models.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.*;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

import static ar.com.cazarecompensas.cazarecompensas.R.color.white;

/**
 * Created by julia on 21/10/2017.
 */
public class TesoroAdapter extends ArrayAdapter<Tesoro> {

    Tesoro[] tesoroList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public TesoroAdapter(Context context, Tesoro[] objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        tesoroList = objects;

    }


    @Override
    public Tesoro getItem(int position) {
        return tesoroList[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.content_lista_tesoros, parent, false);
            vh = ViewHolder.create((CardView) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Tesoro item = getItem(position);

        vh.NombreTesoro.setText(item.getNombre());
        vh.DescripcionTesoro.setText(item.getDescripcion());
        int recompensaSinAdicional = Math.round(item.getRecompensa());
        recompensaSinAdicional = recompensaSinAdicional-((recompensaSinAdicional*10)/100);
        String recompensa = "$"+ Integer.toString(recompensaSinAdicional);
        vh.RecompensaTesoro.setText(recompensa);
        String nombreUser = item.getUsuario().getNombre()+" "+item.getUsuario().getApellido();
        vh.NombreUsuario.setText(nombreUser);

        vh.EncontreTesoro.setOnClickListener(new View.OnClickListener() {
                    @Override
                           public void onClick(View v) {
                                 goLoEncontreScreen(item);

                                        }
                                 }
        );

        vh.ReclamarTesoro.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     goReclamarScreen(item);
                                                 }
                                             }
        );

        vh.IrAlMapa.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     goIrAlMapa(item);
                                                 }
                                             }
        );

        String fotoUser = item.getUsuario().getUrlFoto();

        Picasso.with(context).load(item.getUsuario().getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageViewUser);

        String urlImagen = item.getImagen1();
        if(urlImagen == "")
        {Picasso.with(context).load(R.mipmap.ic_launcher).into(vh.imageView);
        }else{
        Picasso.with(context).load(urlImagen).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);
        }
        return vh.rootView;
    }

    private static class ViewHolder {
        public final CardView rootView;
        public final ImageView imageView;
        public final TextView NombreTesoro;
        public final TextView DescripcionTesoro;
        public final TextView RecompensaTesoro;
        public final TextView NombreUsuario;
        public final CircleImageView imageViewUser;
        public final Button EncontreTesoro;
        public final Button ReclamarTesoro;
        public final Button IrAlMapa;

        private ViewHolder(CardView rootView, ImageView imageView, TextView textViewName,TextView textViewEmail,TextView textViewRecompensa, TextView textViewNameUser, CircleImageView imageViewUser,Button encontreTesoro,Button ReclamarTesoro,Button IrAlMapa) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.imageViewUser = imageViewUser;
            this.NombreTesoro = textViewName;
            this.DescripcionTesoro = textViewEmail;
            this.RecompensaTesoro = textViewRecompensa;
            this.NombreUsuario = textViewNameUser;
            this.EncontreTesoro = encontreTesoro;
            this.ReclamarTesoro = ReclamarTesoro;
            this.IrAlMapa = IrAlMapa;
        }

        public static ViewHolder create(CardView rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            CircleImageView ImageViewUser = (CircleImageView) rootView.findViewById(R.id.imageUser);
            TextView textViewName = (TextView) rootView.findViewById(R.id.NombreTesoro);
            TextView textViewEmail = (TextView) rootView.findViewById(R.id.DescripcionTesoro);
            TextView textViewRecompense = (TextView) rootView.findViewById(R.id.RecompensaTesoro);
            TextView textViewNameUser = (TextView) rootView.findViewById(R.id.NombreUsuario);
            Button encontreTesoro = (Button) rootView.findViewById(R.id.encontreTesoro);
            Button ReclamarTesoro = (Button) rootView.findViewById(R.id.reclamarTesoro);
            Button IrAlMapa = (Button) rootView.findViewById(R.id.irAlMapa);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail,textViewRecompense,textViewNameUser,ImageViewUser,encontreTesoro,ReclamarTesoro,IrAlMapa);
        }

    }
    private void goLoEncontreScreen(Tesoro tesoro)  {
        Intent intent = new Intent(getContext().getApplicationContext(),EncontreTesoro.class);
        Usuario usuario = tesoro.getUsuario();
        intent.putExtra("Tesoro",(Parcelable) tesoro);
        intent.putExtra("Usuario",(Serializable) usuario);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void goReclamarScreen(Tesoro tesoro)  {
        Intent intent = new Intent(getContext().getApplicationContext(),PeticionRecompensa.class);
        Usuario usuario = tesoro.getUsuario();
        intent.putExtra("Tesoro",(Parcelable) tesoro);
        intent.putExtra("Usuario",(Serializable) usuario);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void goIrAlMapa(Tesoro tesoro)  {
        Intent intent = new Intent(getContext().getApplicationContext(),IrAlMapa.class);
        Usuario usuario = tesoro.getUsuario();
        intent.putExtra("Tesoro",(Parcelable) tesoro);
        intent.putExtra("Usuario",(Serializable) usuario);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
