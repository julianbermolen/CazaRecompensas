package ar.com.cazarecompensas.cazarecompensas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ar.com.cazarecompensas.cazarecompensas.Models.Comentario;
import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Publicacion;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;
import ar.com.cazarecompensas.cazarecompensas.services.ComentarioService;
import ar.com.cazarecompensas.cazarecompensas.services.PublicacionService;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.cazarecompensas.cazarecompensas.NuevoTesoro.REQUEST_IMAGE_CAPTURE;
import static ar.com.cazarecompensas.cazarecompensas.NuevoTesoro.REQUEST_IMAGE_CAPTURE4;

public class EncontreTesoro extends AppCompatActivity {
    ImageButton botonImagen1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontre_tesoro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Obtengo el Tesoro del intent
        Intent i = getIntent();

        final Tesoro tesoro = i.getParcelableExtra("Tesoro");
        final Usuario usuario = (Usuario) i.getSerializableExtra("Usuario");
        ImageView fotoTesoro = (ImageView) findViewById(R.id.fotoTesoro);
        TextView nombreTesoro = (TextView) findViewById(R.id.nombreTesoro);
        TextView descripcionTesoro = (TextView) findViewById(R.id.descripcionTesoro);
        CircleImageView imageUser = (CircleImageView) findViewById(R.id.imageUser);
        TextView nombreUser = (TextView) findViewById(R.id.nombreUsuario);
        String nombre = usuario.getNombre()+" "+usuario.getApellido();
        nombreUser.setText(nombre);
        //Imagen del Usuario.
        Picasso.with(getApplicationContext()).load(usuario.getUrlFoto()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageUser);
        //Imagen del Tesoro.
        String imagen1 = tesoro.getImagen1();
        Picasso.with(getApplicationContext()).load(imagen1).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(fotoTesoro);
        nombreTesoro.setText(tesoro.getNombre());
        descripcionTesoro.setText(tesoro.getDescripcion());
        botonImagen1 = (ImageButton) findViewById(R.id.foto2);
        //Cuando se clickea el primer boton de imagen (ImageButton)
        botonImagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button enviar = (Button) findViewById(R.id.enviarMensaje);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mensaje = (EditText) findViewById(R.id.mensaje);

                //Creo el llamado asincronico
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.apiUrl))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ComentarioService service = retrofit.create(ComentarioService.class);
                TesoroService service2 = retrofit.create(TesoroService.class);
                UsuarioService service3 = retrofit.create(UsuarioService.class);
                PublicacionService service4 = retrofit.create(PublicacionService.class);

                Comentario comentario = new Comentario();
                comentario.setComentario(mensaje.getText().toString());
 //comentario.setIdUsuario(usuario.getIdUsuario());
                int idTesoro = tesoro.getIdTesoro();
                int idUsuarioReceptor = 0;
                int idPublicacion = 0;
                Call<Publicacion> call2 = service2.getIdPublicacion(idTesoro);
                try {
                    Publicacion publicacion2 = call2.execute().body();
                     idPublicacion = publicacion2.getIdPublicacion();
                    comentario.setIdPublicacion(idPublicacion);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Call<Publicacion> call3 = service4.getPublicacion(idPublicacion);
                try {
                    Publicacion publicacion = call3.execute().body();
                    idUsuarioReceptor = publicacion.getTesoro().getIdUsuario();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                int idUsuario2 = 0;
                Profile profile = Profile.getCurrentProfile();
                String idFacebook = profile.getId().toString();
                Call<ModelResponse> callModel = service3.getUserId(idFacebook);
                try {
                    ModelResponse model = callModel.execute().body();
                    idUsuario2 = model.getValor();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                int idUsuarioEmisor = idUsuario2;
                String detalle = comentario.getComentario();
                String imagen = comentario.getImagen();
                comentario.setMensajeLeido(false);
                boolean mensajeLeido = comentario.getMensajeLeido();


                ImageView imagen1 = (ImageView) findViewById(R.id.foto2);

                //Imagen 1
                Bitmap bitmap = ((BitmapDrawable) imagen1.getDrawable()).getBitmap();
                String imagenCom = encodeTobase64(bitmap);
                ByteArrayOutputStream imageArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,imageArray);
                byte[] imageInByte1 = imageArray.toByteArray();
                Bitmap imagen1Comentario = BitmapFactory.decodeByteArray(imageInByte1, 0, imageInByte1.length);
                String imagenComentario = encodeTobase64(imagen1Comentario);






                Call<ModelResponse> call = service.postMessage(idPublicacion,idUsuarioEmisor,idUsuarioReceptor,detalle,imagenComentario,mensajeLeido);
                call.enqueue(new Callback<ModelResponse>() {
                    @Override
                    public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                        goMainScreen();
                    }

                    @Override
                    public void onFailure(Call<ModelResponse> call, Throwable t) {
                        Toast.makeText(EncontreTesoro.this, "Error en el envío del mensaje", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    //Intentos para poder capturar imagen o buscarla en la galeria -- ImageButton1
    private void dispatchTakePictureIntent() {

        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(EncontreTesoro.this);
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
