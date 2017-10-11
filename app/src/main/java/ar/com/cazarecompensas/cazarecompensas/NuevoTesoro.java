package ar.com.cazarecompensas.cazarecompensas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

public class NuevoTesoro extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE2 = 2;
    static final int REQUEST_IMAGE_CAPTURE3 = 3;
    static final int REQUEST_IMAGE_CAPTURE4 = 4;
    static final int REQUEST_IMAGE_CAPTURE5 = 5;
    static final int REQUEST_IMAGE_CAPTURE6 = 6;
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


    //Intentos para poder capturar imagen o buscarla en la galeria -- ImageButton1
    private void dispatchTakePictureIntent() {

        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(NuevoTesoro.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                       startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }else if(option[which] == "Elegir de galeria"){
                    Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    takePictureIntent.setType("image/*");
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE4);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    //Intentos para poder capturar imagen o buscarla en la galeria -- ImageButton2
    private void dispatchTakePictureIntent2() {

        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(NuevoTesoro.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE2);
                    }
                }else if(option[which] == "Elegir de galeria"){
                    Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    takePictureIntent.setType("image/*");
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE5);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    //Intentos para poder capturar imagen o buscarla en la galeria -- ImageButton3
    private void dispatchTakePictureIntent3() {

        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(NuevoTesoro.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE3);
                    }
                }else if(option[which] == "Elegir de galeria"){
                    Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    takePictureIntent.setType("image/*");
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE6);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    //Metodo para ajustar la resolucion de la imagen ingresada
    public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

    //Metodo que sirve para reemplazar lo obtenido dentro del imagebutton
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = getResizedBitmap(imageBitmap,400,300);

            botonImagen1.setImageBitmap(imageBitmap);

        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = getResizedBitmap(imageBitmap,400,300);

            botonImagen2.setImageBitmap(imageBitmap);
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = getResizedBitmap(imageBitmap,400,300);

            botonImagen3.setImageBitmap(imageBitmap);
        }

        if (requestCode == 4 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = getResizedBitmap(bitmap,400,300);
                botonImagen1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 5 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = getResizedBitmap(bitmap,400,300);
                botonImagen2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 6 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = getResizedBitmap(bitmap,400,300);
                botonImagen3.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}