package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;

public class EncontreTesoro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontre_tesoro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Obtengo el Tesoro del intent
        Intent i = getIntent();
        Tesoro tesoro = i.getParcelableExtra("Tesoro");

        ImageView fotoTesoro = (ImageView) findViewById(R.id.fotoTesoro);
        TextView nombreTesoro = (TextView) findViewById(R.id.nombreTesoro);
        TextView descripcionTesoro = (TextView) findViewById(R.id.descripcionTesoro);

        Picasso.with(getApplicationContext()).load(tesoro.getImagen1()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(fotoTesoro);
        nombreTesoro.setText(tesoro.getNombre());
        descripcionTesoro.setText(tesoro.getDescripcion());
    }

}
