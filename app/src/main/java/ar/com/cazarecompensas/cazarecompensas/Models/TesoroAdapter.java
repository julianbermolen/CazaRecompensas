package ar.com.cazarecompensas.cazarecompensas.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.R;

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
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Tesoro item = getItem(position);

        vh.NombreTesoro.setText(item.getNombre());
        vh.DescripcionTesoro.setText(item.getDescripcion());
        Picasso.with(context).load(item.getImagen1()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView NombreTesoro;
        public final TextView DescripcionTesoro;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.NombreTesoro = textViewName;
            this.DescripcionTesoro = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView) rootView.findViewById(R.id.NombreTesoro);
            TextView textViewEmail = (TextView) rootView.findViewById(R.id.DescripcionTesoro);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail);
        }
    }
}
