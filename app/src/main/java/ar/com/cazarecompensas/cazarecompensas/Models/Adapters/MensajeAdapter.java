package ar.com.cazarecompensas.cazarecompensas.Models.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class MensajeAdapter extends ArrayAdapter<Comentario> {
    Comentario[] comentario;
    Context context;
    int idUsuario;
    private LayoutInflater mInflater;

    // Constructors
    public MensajeAdapter(Context context, Comentario[] objects,int idUsuario2) {
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
        final MensajeAdapter.ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.content_bandeja_entrada, parent, false);
            vh = MensajeAdapter.ViewHolder.create((CardView) view);
            view.setTag(vh);
        } else {
            vh = (MensajeAdapter.ViewHolder) convertView.getTag();
        }

        final Comentario item = getItem(position);

        String nombre;

        if(item.getUsuarioEmisor().getIdUsuario() == idUsuario){
            nombre = item.getUsuarioReceptor().getNombre()+" "+item.getUsuarioReceptor().getApellido();
            Picasso.with(context).load(item.getUsuarioReceptor().getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.fotoUser);
        }else{
            nombre = item.getUsuarioEmisor().getNombre()+" "+item.getUsuarioEmisor().getApellido();
            Picasso.with(context).load(item.getUsuarioEmisor().getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.fotoUser);
        }
        vh.nombreUser.setText(nombre);
        String mensaje = item.getComentario();
        if(mensaje.length() > 30){
            mensaje = mensaje.substring(0,29);
        }
        vh.mensajeUser.setText(mensaje);
        String fecha = item.getFechaCarga().substring(0,10);
        vh.fechaCarga.setText(fecha);

        vh.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(),Conversacion.class);
                intent.putExtra("Id",item.getIdComentario());
                intent.putExtra("IdUsuario",idUsuario);
                intent.putExtra("numeroConversacion",item.getNumeroConversacion());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return vh.rootView;
    }

    private static class ViewHolder {
        public final CardView rootView;
        public final CircleImageView fotoUser;
        public final TextView nombreUser;
        public final TextView mensajeUser;
        public final TextView fechaCarga;


        private ViewHolder(CardView rootView,CircleImageView fotoUser,TextView nombreUser,TextView mensajeUser,TextView fechaCarga) {
            this.rootView = rootView;
            this.fotoUser = fotoUser;
            this.nombreUser = nombreUser;
            this.mensajeUser = mensajeUser;
            this.fechaCarga = fechaCarga;
        }

        public static MensajeAdapter.ViewHolder create(CardView rootView) {
            CircleImageView fotoUser = (CircleImageView) rootView.findViewById(R.id.fotoUser);
            TextView nombreUser = (TextView) rootView.findViewById(R.id.nombreUser);
            TextView mensajeUser = (TextView) rootView.findViewById(R.id.mensajeUser);
            TextView fechaCarga = (TextView) rootView.findViewById(R.id.fechaCarga);

            return new MensajeAdapter.ViewHolder(rootView,fotoUser,nombreUser,mensajeUser,fechaCarga);
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
}
