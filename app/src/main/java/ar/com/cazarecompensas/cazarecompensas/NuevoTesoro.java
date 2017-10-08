package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

public class NuevoTesoro extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE2 = 2;
    static final int REQUEST_IMAGE_CAPTURE3 = 3;
    ImageButton botonImagen1;
    ImageButton botonImagen2;
    ImageButton botonImagen3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tesoro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        botonImagen1 = (ImageButton) findViewById(R.id.imageButton);
        botonImagen2 = (ImageButton) findViewById(R.id.imageButton2);
        botonImagen3 = (ImageButton) findViewById(R.id.imageButton3);


        //Cuando se clickea el primer boton de imagen (ImageButton)
        botonImagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        //Cuando se clickea el segundo boton de imagen (ImageButton)
        botonImagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent2();
            }
        });

        //Cuando se clickea el tercero boton de imagen (ImageButton)
        botonImagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent3();
            }
        });


            //Logica del Spinner (DropDownList)

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //get the spinner from the xml.
            Spinner dropdown = (Spinner)findViewById(R.id.spinner);
            //create a list of items for the spinner.
            String[] items = new String[]{"Mascota", "Celular", "Mochila"};
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
            //set the spinners adapter to the previously created one.
            dropdown.setAdapter(adapter);


    }


    //Intentos para poder capturar imagen -- ImageButton1
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }


    }

    //Intentos para poder capturar imagen -- ImageButton2
    private void dispatchTakePictureIntent2() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE2);
        }


    }

    //Intentos para poder capturar imagen -- ImageButton3
    private void dispatchTakePictureIntent3() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE3);
        }


    }

    //Metodo que sirve para reemplazar lo capturado en el imagebutton
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            botonImagen1.setImageBitmap(imageBitmap);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            botonImagen2.setImageBitmap(imageBitmap);
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            botonImagen3.setImageBitmap(imageBitmap);
        }
    }


}