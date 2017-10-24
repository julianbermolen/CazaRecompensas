package ar.com.cazarecompensas.cazarecompensas.Models;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.R;
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

        Tesoro item = getItem(position);

        vh.NombreTesoro.setText(item.getNombre());
        vh.DescripcionTesoro.setText(item.getDescripcion());
        String recompensa = "$"+ Float.toString(item.getRecompensa());
        vh.RecompensaTesoro.setText(recompensa);
        Profile profile = Profile.getCurrentProfile();
        vh.NombreUsuario.setText(profile.getFirstName());


        Picasso.with(context).load(item.getUsuario().getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageViewUser);

        String urlImagen = "C://wamp//www//ApiCazaRecompensa//api"+item.getImagen1();
        Picasso.with(context).load(urlImagen).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);

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

        private ViewHolder(CardView rootView, ImageView imageView, TextView textViewName,TextView textViewEmail,TextView textViewRecompensa, TextView textViewNameUser, CircleImageView imageViewUser) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.imageViewUser = imageViewUser;
            this.NombreTesoro = textViewName;
            this.DescripcionTesoro = textViewEmail;
            this.RecompensaTesoro = textViewRecompensa;
            this.NombreUsuario = textViewNameUser;
        }

        public static ViewHolder create(CardView rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            CircleImageView ImageViewUser = (CircleImageView) rootView.findViewById(R.id.imageUser);
            TextView textViewName = (TextView) rootView.findViewById(R.id.NombreTesoro);
            TextView textViewEmail = (TextView) rootView.findViewById(R.id.DescripcionTesoro);
            TextView textViewRecompense = (TextView) rootView.findViewById(R.id.RecompensaTesoro);
            TextView textViewNameUser = (TextView) rootView.findViewById(R.id.NombreUsuario);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail,textViewRecompense,textViewNameUser,ImageViewUser);
        }


    }
}
