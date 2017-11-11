package ar.com.cazarecompensas.cazarecompensas;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.Profile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import ar.com.cazarecompensas.cazarecompensas.Models.LinkedHashMapAdapter;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.TesoroCategoria;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.string.no;
import static ar.com.cazarecompensas.cazarecompensas.R.string.MensajeNuevoTesoro;

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
    Button siguiente;

    private LinkedHashMapAdapter<Integer, String> adapter;
    private LinkedHashMap<Integer, String> mapData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tesoro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        siguiente = (Button) findViewById(R.id.siguientes);

        siguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                //Se hace binding
                EditText nombre = (EditText) findViewById(R.id.NombreTesoro);
                EditText descripcion = (EditText) findViewById(R.id.DescripcionTesoro);
                EditText recompensa = (EditText) findViewById(R.id.RecompensaTesoro);

                Spinner categoria = (Spinner) findViewById(R.id.Categoria);
                ImageView imagen1 = (ImageView) findViewById(R.id.foto);
                ImageView imagen2 = (ImageView) findViewById(R.id.foto2);
                ImageView imagen3 = (ImageView) findViewById(R.id.foto3);

                //Imagen 1
                Bitmap bitmap = ((BitmapDrawable) imagen1.getDrawable()).getBitmap();
                ByteArrayOutputStream imageArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,imageArray);
                byte[] imageInByte1 = imageArray.toByteArray();
                //Imagen 2
                Bitmap bitmap2 = ((BitmapDrawable) imagen1.getDrawable()).getBitmap();
                ByteArrayOutputStream imageArray2 = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,imageArray2);
                byte[] imageInByte2 = imageArray2.toByteArray();
                //Imagen 3
                Bitmap bitmap3 = ((BitmapDrawable) imagen1.getDrawable()).getBitmap();
                ByteArrayOutputStream imageArray3 = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,imageArray3);
                byte[] imageInByte3 = imageArray3.toByteArray();

                String nombre2 = nombre.getText().toString();
                String descripcion2 = descripcion.getText().toString();
                Object categoria2 = categoria.getSelectedItem();
                int categoria3 = 2;

                int idTesoroEstado = 1;

                //Se instancia el intento
                Intent intent = new Intent(getApplicationContext(),MapaNuevoTesoro.class);
                //Se agregan las variables a pasar
                intent.putExtra("nombreTesoro", nombre2);
                intent.putExtra("descripcionTesoro", descripcion2);
                intent.putExtra("categoriaTesoro", categoria3);
                if(recompensa.getText().length() == 0 || recompensa.getText().toString() == ""){
                    recompensa.setError("Recompensa tiene que tener un monto");
                }else {
                    Integer Recompensa = Integer.parseInt(String.valueOf(recompensa.getText()));
                    intent.putExtra("recompensaTesoro", Recompensa);
                }
                intent.putExtra("idEstadoTesoro", idTesoroEstado);
                intent.putExtra("imagen1Tesoro", imageInByte1);
                intent.putExtra("imagen2Tesoro", imageInByte2);
                intent.putExtra("imagen3Tesoro", imageInByte3);

                //Se inicia el intento

                if(nombre.getText().toString().length() == 0) {
                    nombre.setError("Por favor, ingresá un nombre");
//                    Toast.makeText(NuevoTesoro.this, "Ha dejado valores vacios", Toast.LENGTH_SHORT).show();
                }else if(descripcion.getText().toString().length() == 0) {
                    descripcion.setError("Por favor, ingresá una descripción");
                }else if(recompensa.getText().toString().length() == 0) {
                    recompensa.setError("Ingresá una monto");
                }else if(validar(recompensa.getText().toString()) == false){
                    recompensa.setError("Ingresá un numero");
                }
                else{
                    startActivity(intent);
                }

            }

        });

        botonImagen1 = (ImageButton) findViewById(R.id.foto);
        botonImagen2 = (ImageButton) findViewById(R.id.foto2);
        botonImagen3 = (ImageButton) findViewById(R.id.foto3);


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


  /*          //Logica del Spinner (DropDownList)

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //get the spinner from the xml.
            Spinner dropdown = (Spinner)findViewById(R.id.Categoria);
            //create a list of items for the spinner.
            String[] items = new String[]{"Mascota", "Celular", "Mochila"};
            //create an adapter to describe how the items are displayed, adapters are used in several places in android.
            //There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
            //set the spinners adapter to the previously created one.
            dropdown.setAdapter(adapter);*/

        descargarCategorias();

        DialogoAlerta();


    }
    public boolean validar(String cadena) {
        if (cadena.matches("[0-9]*")) {
            return true;
        } else {
            return false;
        }
    }
    private void descargarCategorias() {



        //Creo el llamado asincronico
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TesoroService service = retrofit.create(TesoroService.class);
        final  Call<TesoroCategoria[]> categorias = service.getCategoria();
        categorias.enqueue(new Callback<TesoroCategoria[]>() {
            @Override
            public void onResponse(Call<TesoroCategoria[]> call, Response<TesoroCategoria[]> response) {
                int cont = response.body().length;

                String[] asd = new String[cont];

                mapData = new LinkedHashMap<Integer, String>();


                for (int i = 0; i < response.body().length; i++) {
                    try {

                        asd[i] = response.body()[i].getNombre();

                        mapData.put(response.body()[i].getIdTesoroCategoria(), response.body()[i].getNombre());

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                adapter = new LinkedHashMapAdapter<Integer, String>(NuevoTesoro.this, android.R.layout.simple_spinner_item, mapData);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Spinner categoriaSpinner = (Spinner)findViewById(R.id.Categoria);
                categoriaSpinner.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<TesoroCategoria[]> call, Throwable t) {

                //Toast.makeText(getContext().getApplicationContext(), "Error en la carga de tesoros", Toast.LENGTH_SHORT).show();
            }
        });

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
            imageBitmap = getResizedBitmap(imageBitmap,350,300);

            botonImagen1.setImageBitmap(imageBitmap);

        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = getResizedBitmap(imageBitmap,350,300);

            botonImagen2.setImageBitmap(imageBitmap);
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = getResizedBitmap(imageBitmap,350,300);

            botonImagen3.setImageBitmap(imageBitmap);
        }

        if (requestCode == 4 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = getResizedBitmap(bitmap,350,300);
                botonImagen1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 5 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = getResizedBitmap(bitmap,350,300);
                botonImagen2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 6 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = getResizedBitmap(bitmap,350,300);
                botonImagen3.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void DialogoAlerta() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(NuevoTesoro.this);
        builder1.setMessage("Recordá que para publicar un tesoro perdido deberás abonar la recompensa. La misma será devuelta en caso de no encontrar el objeto sin incluir la comision por el servicio");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

}

}