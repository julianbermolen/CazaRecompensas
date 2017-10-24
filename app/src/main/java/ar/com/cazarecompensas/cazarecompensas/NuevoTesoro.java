package ar.com.cazarecompensas.cazarecompensas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.Profile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.services.LoginApi;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tesoro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        siguiente = (Button) findViewById(R.id.siguientes);

        siguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                long categoria2 = categoria.getSelectedItemId();
                int categoria3 = (int) categoria2;
                Integer Recompensa = Integer.parseInt(String.valueOf(recompensa.getText()));
                int idTesoroEstado = 1;


                    guardarTesoro(nombre2,descripcion2,categoria3,Recompensa,idTesoroEstado,bitmap,bitmap2,bitmap3);

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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


            //Logica del Spinner (DropDownList)

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //get the spinner from the xml.
            Spinner dropdown = (Spinner)findViewById(R.id.Categoria);
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

public void guardarTesoro(String nombre, String descripcion, int categoria, Integer Recompensa, int idTesoroEstado, Bitmap img1, Bitmap img2, Bitmap img3){

    //Creo el tesoro
    final Tesoro tesoro = new Tesoro();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(getString(R.string.apiUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UsuarioService service2 = retrofit.create(UsuarioService.class);

    Profile profile = Profile.getCurrentProfile();
    long idFacebook = Long.parseLong(profile.getId());
    Call<ModelResponse> callModel = service2.getUserId(idFacebook);
    try {
        ModelResponse model = callModel.execute().body();
        int idUsuario = model.getValor();
        tesoro.setIdUsuario(idUsuario);
    } catch (IOException e) {
        e.printStackTrace();
    }

    TesoroService service = retrofit.create(TesoroService.class);


    //Seteo los valores
    tesoro.setNombre(nombre);
    tesoro.setDescripcion(descripcion);
    tesoro.setIdTesoroCategoria(categoria);

    tesoro.setRecompensa(Recompensa);

    String img1String = encodeTobase64(img1);
    String img2String = encodeTobase64(img2);
    String img3String = encodeTobase64(img3);

    //Creo la espera de la llamada y llamo al servicio
    int idUsuario = tesoro.getIdUsuario();
    Call<ModelResponse> modelResponseCall = service.postTesoro(nombre,descripcion,categoria,idUsuario,Recompensa,idTesoroEstado,img1String,img2String,img3String);
    modelResponseCall.enqueue(new Callback<ModelResponse>() {
        @Override
        public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
            int statusCode = response.code();

            ModelResponse modelResponse = response.body();
            Log.d("NuevoTesorox","onResponse:" +statusCode);
        }

        @Override
        public void onFailure(Call<ModelResponse> call, Throwable t) {

        }
    });
}


    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

}