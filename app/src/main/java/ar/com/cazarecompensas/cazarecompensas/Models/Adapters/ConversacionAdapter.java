package ar.com.cazarecompensas.cazarecompensas.Models.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.Conversacion;
import ar.com.cazarecompensas.cazarecompensas.EncontreTesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import ar.com.cazarecompensas.cazarecompensas.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by julia on 29/10/2017.
 */

public class ConversacionAdapter extends ArrayAdapter<Comentario> {
    Comentario[] comentario;
    Context context;
    int idUsuario;
    private LayoutInflater mInflater;

    // Constructors
    public ConversacionAdapter(Context context, Comentario[] objects,int idUsuario2) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        comentario = objects;
        idUsuario = idUsuario2;

    }
    @Override
    public Comentario getItem(int position) {
        return comentario[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ConversacionAdapter.ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.content_conversacion, parent, false);
            vh = ConversacionAdapter.ViewHolder.create((CardView) view);
            view.setTag(vh);
        } else {
            vh = (ConversacionAdapter.ViewHolder) convertView.getTag();
        }

        final Comentario item = getItem(position);
            if(item.getIdUsuarioEmisor() == idUsuario){
                vh.textoRecibe.setText(item.getComentario());
            }else{
                vh.textoDa.setText(item.getComentario());
                if(item.getImagen() != null){
                    vh.textoRecibe.invalidate();
                    Picasso.with(context).load(item.getImagen()).into(vh.fotoCom);
                    vh.fotoCom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.acercar);
                            vh.fotoCom.startAnimation(animation);
                        }
                    });
                }

            }

        return vh.rootView;
    }

    private static class ViewHolder {
        public final CardView rootView;
        public final TextView textoRecibe;
        public final TextView textoDa;
        public final ImageView fotoCom;


        private ViewHolder(CardView rootView,TextView textoDa,TextView textoRecibe,ImageView fotoCom) {
            this.rootView = rootView;
            this.textoDa = textoDa;
            this.textoRecibe = textoRecibe;
            this.fotoCom = fotoCom;
        }

        public static ConversacionAdapter.ViewHolder create(CardView rootView) {
            TextView textoDa = (TextView) rootView.findViewById(R.id.textoDa);
            TextView textoRecibe = (TextView) rootView.findViewById(R.id.textoRecibe);
            ImageView fotoCom = (ImageView) rootView.findViewById(R.id.fotoCom);


            return new ConversacionAdapter.ViewHolder(rootView,textoDa,textoRecibe,fotoCom);
        }

    }
    private void goLoEncontreScreen(Tesoro tesoro)  {
        Intent intent = new Intent(getContext().getApplicationContext(),EncontreTesoro.class);
        Usuario usuario = tesoro.getUsuario();
        intent.putExtra("Tesoro",(Parcelable) tesoro);
        intent.putExtra("Usuario",(Serializable) usuario);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
