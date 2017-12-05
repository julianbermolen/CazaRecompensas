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
                String bitmapComentario = "iVBORw0KGgoAAAANSUhEUgAAAKgAAACoCAIAAAD7KTLjAAAAA3NCSVQICAjb4U/gAAAgAElEQVR4\n" +
                        "nO1963cc1ZXv3vtUq9UPPSxbtmzLtvxSbIzBgGEIDK8AgQmwsobJZNZk1v2/btb9lrXunTt5rDvJ\n" +
                        "DZDMhHjCJZPAGAMGYlvGsmVjI1uyHpa6W62us/f9sKuqT79k9VPI7t9iNeVS1amqs88++3n2Aeii\n" +
                        "iy666KKLLrrooosuuuiiiy666KKLLrrooosuuuiiiy666KKNwNa3iOgeiEjLH3E/o1X96dV9B6E+\n" +
                        "HwBQAAAqX8TEPGbWv+r1wZUIAEAMAMAEZedrHXevZxBhrtLRIYiImWv+uRrqJ7wIYPk8QUR9fX0D\n" +
                        "AwPpdDqRSAChiBCgvjRsdMdt6utFBA1ls9nscmZpaWlxcdFaW9b/DUwDjU71ep8AIm7ZOrRv375t\n" +
                        "27cnk8l4LOZ5HnhGRFCAmbFilHSxTohIxMrW2kKhkMlkbs3OXL58eXluocjiCABgBADAHREhiaqj\n" +
                        "CcIjAsv4+Pjhb42n02nyPABAEQCwCIioo7XB9rsI4apKiFiwfi6Xu/jFuYmJCUQUkcYIbxp8HUIQ\n" +
                        "OXz0yIlHH0kkkwKgLyEALIKAawikLtYJZiYiay0RiQgiIiIh9sRiO3aOFKw/P3M7ulhq07gqGiU8\n" +
                        "4sFDhx597DFPGV2HXvCX7tzeYrhdql3ted62bdu44M/dnmuMxRok/MDQ0COPPppIJABAEATEAKCy\n" +
                        "OyI5x+5dggAYTkE1jrvXR9cIBFysPYyA0YTPIEiUSqVmbs/mc7moKVy38Kb1XVZ6D9HY2FgqlYr0\n" +
                        "izLbvfK4i8ag0zsAEFF0Rn+ZOZVK7d27l7ARItZvzgGk0+nhHdtNzFOFIi8MAD3OUKtlURop/hWl\n" +
                        "+vnu9dH10aRgBECEHUWPAdAzMTLDw8OpLQN35ucrlbu10chgSafTyWRS1Y1I6WignS4agFrI0TTQ\n" +
                        "29vb19fXQDv1czxCbzIRi8X0JQCA1q1d1JoJuufXf94gMjMiIYCAxONx1bTq89s1wPGqU3ieFyka\n" +
                        "0HXIdxDq1RFR2xk8z0skEkB10/EuN0SqRPEGIn02EQmiFUEBakOwp4uqYEIfxCChgCAKIhEBogGk\n" +
                        "avSqhfpGSq12uxzfSbRkrr2LjK9sF9U555xnEKSuq65DMAIowBB4at1BAPWMg0a0+jK4brsuOoBK\n" +
                        "Xm+g/+/C8ZVEZZAgJF/2Kms+WpABAMUDAEAfAKyPIjK8dSAWi6mbP1BYDAGACK6srCwvZdtrLiID\n" +
                        "AAiVHzfTTpvBoU8+ErsEQOH59TNhIw6clkBdUa+++urhw4eZfQAIKE8IANbKxx9//Pt3/0Njz91J\n" +
                        "peVomPDKJT6AlMiLGmMfgzMMABJcz8w8PLx1dHQXYAEAOLhVRIQLcGXyIpIAc9hasQUSAgBGhmIi\n" +
                        "AwGAoO88C6K/1vKU6ZuE4tG9i4vtrIebO8LrrcWGcbzahHqss5YxxMxKB0QgImV0Cti9azG2Eg0T\n" +
                        "PuA8EbEuRWqM/YCHAKDIScjMgVwXFfIWEUmImS34FqywAfEYfCIS9RLqs50nhklL5bzu/tU66U2V\n" +
                        "f61ESTt1cHNxTvrmY8PmKKV61ZOV57sCvuVogOMZIMigZWRZQ8Zjcey78lLcGQJFgDVnV6nLCIyA\n" +
                        "jMgoYgFYgHTuBwB2+MlNUzTsQSj13XcoTWXUe6nsDNa4spaMlxrzx2bhdcWGyXgIg82VxlpldP+u\n" +
                        "5mIX9aJuwqMQCoXachUZLyKIaoARhBq4paIMDiS9hnQFUdBlH8MkIgUsMDIAAZAHggIWHBkfvAkA\n" +
                        "gAgCAJOPiCgkItE7FK9xNP+yb3GOi+eNWg16HmJhKqEgGgAwmv6Mm4m/K9FKjkdEqEi3ct37brqR\n" +
                        "hNM7IroeCXBmgvCkOC2A007xN4xZVc8FCiyF9X+I8xumHUDUPjqO0s2bP1434QUhYtHIiwQOgSOK\n" +
                        "qs/NalYIIwBI6XwtIoLCwITCzEFXkzAziYb5WcT6yAio3BlmmBQzTRBBRBAM2+A4atx5UvRT/Yug\n" +
                        "lONLkpSJmRnBiIi2yoj6IVIxyjcRWsbxAb2dHkck7axat2DoqnWX/+hx0YMbNV5bsUdEa2005tQ9\n" +
                        "sP4lRVLyv3Jw2HLkIY/eSlc7bFLaN0j4yCMmIX/ovOeurhIoCEildh3o1Xp9MIEEeeNRJrkQchR9\n" +
                        "EkRECYyBomauxxFVmFllsDg2wvo+JvqpCo9ZiIIs96hZY4yeKTu/WdBijncRJIpwUcCGg0JZp1yW\n" +
                        "uzm7wo5cD64pl+uu1EZEIsJgALVyEg7bFPdlJJRkdQ+ybwwatOOV29jJtwumZmRAGB7emk6n0QkY\n" +
                        "K5EIBEKeFSFmTqbigDbi4GiGJ6L+/v6xvbustULK1gBhfp+6EdS08IUXFxcXFzJKciLaPToci8VE\n" +
                        "OLpGxx65XB2cFwAwggBgyQIAsSm2H30tszHGWqtcvrycmZ+f58CYIUSonIe++WgZxwf8TcLMTz/9\n" +
                        "9COPPCLiyr9ywlsLIrJjeDAiajQBGGMQ4dixY/t2jwEAB9yGUI3wudX8n/70p/f+8J8QTvs//OEP\n" +
                        "+/r6MNAUmyW8DfWGaEqfn1946623vrx4RUSIVDxtvuBhA4QnAFIdm0BlvJLSiAhbYIb+dGrXyA4R\n" +
                        "C+uYBsu0MAlX3qd6E6mRRK2bwjeBfMH29aVZfN/6hB4zj+4a7uvrYx+h6CVsnAs5NDVEhKhHcwjo\n" +
                        "te/+8//+1Y0bN6wtiAS+SyJVNep9QuU80YmZY/PFEzcK0exljBkfH//Rj340ODgYndx0+l1ThOeK\n" +
                        "YRnxa5vXSFPVNw9VLQQgQRaM1JHq168fGkHQ2AEjCOH44X0/+se/G9o6CMhqTwKhz+tfyhICfU1J\n" +
                        "cp/WAUXhHuT41hrWroHgtoyIx44de/3117dv3w6hddfC57YbdRNeR6Oa37Ww9l9rjWjlqvWhFgdH\n" +
                        "+WdlXN44DxlAsExCmnwAAKFXEYwxTzx64rvfeX54eDio+cP1T3JC6wz5NztrVbR276BNTrRazRpj\n" +
                        "4vH4448//swzzwwMDLTj0e1DA0tvmIBViis0XlfnQ6tcH/nnK7iz8owPEMpFZEBWvlEZXPuuRhBq\n" +
                        "Kixi3TdHAfatoN+bjH37yYf+6okHU6mU+zVr9m0j3NtayX9PcXw7UD3cBwBhUAARBwYGXnzxxRMn\n" +
                        "TvT09HT6/RpFg3Z8ia8eAdTRgah+rLfe/t0f3vuze0+VCBgXAOAf/+EHhw8f1sxaZPXZxUTEWvvx\n" +
                        "xx//7vd/8H2fsAcAAH1EBCczX0UjMywtLQEyGSCMafKW4+JtFZO4tjUBgBXNMEAAEjGDg9t+8Hev\n" +
                        "vfD8k6GGpxOS5x6LCICPiADEzAsLCzMzM5cmpqamphZzmUKh0INxEbHEIhIDD0rzGFqLVvrqo3jJ\n" +
                        "3Nzc3NxcdB6qEZ7FB4B8Pu+2EP0y89LS0rVr13zfB/EQkWWViJTwAgWAgPCIJvLza6yMmZm53ZOZ\n" +
                        "OhndCE0ymUwmkxooqiS8iAEARKsJHdHHvvAsXL58+Y//9cHnn3++spSPWm63QdcywntEIsIV82Ew\n" +
                        "QwZZtm7GixERAkJBFKtBD5EgDoYoAIxgQBhREMFwDCwAWgDgIF6n/7PC4WGVkEkbvWDhK1t3kKm/\n" +
                        "Mpxv3ONotgjDGoiIGE/S4SP7d42N7Nmz49S7/zk3N6ctMGpu4N3XLDSGlrGF2+luHH0NRJzqkkqX\n" +
                        "zkTXVB6XPTTqwSheXnlZO9DAg4qDFdEYE7VjjEmn088999z3v//9VCrVmQB/66b6gF+D3ITI7xFy\n" +
                        "vIHS5KdwnkRnLY5odDXKbgh+SRBh/9ieeDyufzUeMTMCiQhSUcoAgIh4MQJkEO3Z1vJ6eR4AkcfM\n" +
                        "RgO1a9LL7RMIpZKIECBbFoPGmEcfOTr99eNv//Y/mG21olitFF6tzsAppfcag7fMI+Yyd9XrX3nl\n" +
                        "ld27dwedZVBEIsVBSpM4tDRIB1BXPN6dtKIbo3u1K+Lx+FNPPXXm0/M3btxo43sDQAsJr6O+Vly6\n" +
                        "Mgs94BJAgfLcac23QTRqowsTEm0ZGty6bSisx6HJb8UuEyxqhYY8ay22JQvWWSkQEg8RWZeD3Y38\n" +
                        "lSwhIlbT99hHRGCzZWDo+CPHrn095Ul74/ytlPHuwI+Ebl0tRAcuT0STpK6mi1rWf+qv+0Q92ZKP\n" +
                        "WvttozdcT2jOffPoTFTNJvpGz/PGx8fj8XgbXx0AWp5eHXi5AMqGf+WaFQYLGKbFOxwAbp+qPw4t\n" +
                        "ETH7zL72FDNGvOKLBafyls72ZS8WxtT1H4KIteSxO+B0RhF0BVa4di9K9Cume+DaPFk5MiRM8RAR\n" +
                        "ZERAIRbkbX39SeMtBxYBQ3tskg3z3JVZAS4DVcrOxrT0EirWeHoZosV7az+xtVZDNIz01xjTATWl\n" +
                        "/Uuo3HVoekKz59ATESSSMLFJLRz9fmMMIgIhEEYTqg5TVyuEalX2dI2LrrqtnISDurBqZQSB8OJq\n" +
                        "7ZKLtR1UfwNK6FMzVRTuxnkSw9xw90wHRNVG+uqjKvyRdRud14NIZjfGYRHVy7SqyMeu0NJt0bH7\n" +
                        "JpVzT8uN7DLrprWNr4H2c7wj10tqtSpnExr13ZWX8mFE0Qg3EtTgAFfnLYKkmLdP5IkIMmO4jscY\n" +
                        "IyKIukibtGUJqwZGL0ZEoWNVRIKyP1SS2w8QrqNb24K/ew+J6Fu5w7Td2LDVsp7n+b5fydxQ6gww\n" +
                        "xkCpYbbOfikzDdzzEq7vQSeZHxzyV2qa4ZzUlsS6MrO+HY+oREdlfLDCVDwAWF1dFZH//j9+Ykxg\n" +
                        "0gtYHfvq1SoUCrm8E7QI82qcubE8Ylbl4boqz5CIkICIFKz4vu/7dmpqanr61tzc3MLi4vz8fGZp\n" +
                        "pbe3d7A/OTQ0NLBl67Zt2/bv251KpUwcACBGMWYWYoFAxosTfGoSrqTrgHRXbGTVKxFZWVmx1oZF\n" +
                        "kazL64joCOK6O7hMujNzoeDPz89f//rm+fPnz5+/cOvWLWslWvaF4omIQSsiQJ7neYP9yfHx8UNH\n" +
                        "9u/bt2/blm3xeBydllsId54rUynahw7J+JLjIJoeyEuioN5VQCSIAQCALyIYOOREADRepy+MAsKC\n" +
                        "RideDwB0Ta6BYkjU8+Kii3EBfV9mZm5PXLj84YcfXrt2PZ/Pa0iNEMkUHw0AQbU1ZMuFuYXlP394\n" +
                        "5sxHnw8ODj7+7ccefPDB3Tu2JxIJ9nxElFUAAAaLofuCoal1W2WKfcPtrBMbXBHDPRBl8KjsFYAb\n" +
                        "1nEtXQ3nuGzh8oquawkK5AnPzs5+du78Rx99dOXy9UKhAECV0rpc+XdW8/u+PzMz884775w5c+bR\n" +
                        "h44//PDDu/aNGGNQHDeiSqrOqeQtQAcIX6xA51aqQTTM/K1DYwMDAwyMiFr7qmSfDSd/I5nsB/BU\n" +
                        "ZgtRsNcVgK64C2WDjZbI65VTV746derU5+cuLi8vS+ApExE24QQCji6p621FgjBhkGkDPiCA5evX\n" +
                        "r9+enb84efU7Lz5z9OjR3h4SYY3OIQE0R/cy50QHsJEcDwBPP/30kSNHgpJGlWk6JYRPQoXVqzq5\n" +
                        "zrUR5+l5a/0LFy789p1/n5yc9MVEsbvoRmf+AHDipM4gQMfEAgDI5/MXL15cWJx56aWXnnziZDwe\n" +
                        "Dy9jLM3G+eajA4Qvat3s1qMSK8LJVKp/YADJLX1WPRYVGvclS5QjtcigRgc8IhQp+L5/9rNLv/rV\n" +
                        "r2ZmbltrYgaRyGeOiiYKgISr28PGo8yfSN7rKDEAQFoXg4hFbk7P/ev/eWtxbuG1116LeSDCUJqL\n" +
                        "0M7ObBk2mOPBiaqVui/Ku89Vd93rwwN19JK1VoQvXLjwL//yi/n5ec/rAQjK57nsGOkE6MT33Cud\n" +
                        "ZJCSABozE2Amk3n77bcB4LXvvaI77zVJ9c4Pl44Rvsj3On8HpQ9AQIK4CLNf1eUSIeLysmu0LJEI\n" +
                        "KGFuXJ/9xc9/fefOHd0/BcNd2jCc5EUEkOO9PX3JRDKZHBgYSKVS6b7EyspKZnl1YWFhObecyWTy\n" +
                        "+QIza3AurLNFBgnJAoAUYu/+7v9t3br1iSee6IlrJY7I31A37iMZ79a6ib448qlVwrG4yrM2XOE6\n" +
                        "Nzf305/+9MaNG+T1qIBmZnWKqG+EiBKJxO7Rnfv37z9y+NCBAwfi8TgiqrhB6CkUClevX/3yyy8n\n" +
                        "J69cuXJl6U4mSt4lR+qLQC6Xe+edd1Kp1IPHjxpjsKH93zYKHSJ8lehckEuj/RXo+WsP/BqTgfrb\n" +
                        "GREzudw7v/3txJeTaGKhOAguYmYU6enpGd0zfPLkyePHj23ZssWYOCKKaO1sg0gCq8aTA2N7xvbu\n" +
                        "zvzVyfPnz5/99IvPPvssuyKIZLlARCxIRF4MAGDm9uK/v/uHoS2De/fuBRIiqkgGWF//3LtTfbvg\n" +
                        "Tv7nzp379NNPXR87gGa5WGNMzJjHHnvsmWefGB0d9bxiFK5Mq9DzRNTX1/fYY48d2H94z549v377\n" +
                        "31ZWVqI5AxydY2pq6pNPPtm+fXtvMr5ZVHrY8CVUregpn3kVTWx+cemDP5/JZgqIRvP1BCyiMPs9\n" +
                        "HnkE333lhdffeGVsbMzzPKIeEQPgixREtKRWcW1a9FZEtGW4/5kXnvpv//QPQ4Np1oQhFmBhICtI\n" +
                        "AIV8/uwXE9enb2tEceP6oT5sMOFbMsWpgX727NnJyUnf90NVMTDcPc+z1j777LMvvfTS0NAQImoM\n" +
                        "NAqHRLzrqvruu8Xj8ZMnT7755pua9O7YfsGccf369S+++CKbzW6iJfIbzfG1dLl1A9GI4J07d86e\n" +
                        "Pbu8vAwAqPF9RutLj+cB84PHj/7g7/82kYhrfI9ZlzIVgno+YVhMx0rx3UQAwEDMQIypcOLk8Vdf\n" +
                        "eTHmAYR1sEDrfqHx7erHn3w0MzMv0ui27B3HZlJE18D09PTMzIybw6TsyMxbt2598803o33uXR+A\n" +
                        "6wkoC465cy8ze55njHn88ccffvjhqrPU7du3b9y40TDHd1652/SER0Rr7fT0zMLCUmToR/1oDD75\n" +
                        "5BNbtw5YG6zODNfQk4b2ocKGLvUjgUgBwAcLBszAYPqhh4/19/djEJLjoDWh/Erh8uSVwmpZNZv1\n" +
                        "4r6T8S3B0tLSV199lcvlyvxxIrJ79+6HHnooFouViW1FdHJthov+6nne4cOHDxw44CYORWV0L126\n" +
                        "tLS01I4PbAfuBcJnMplrV69rCECndGa2VkTw8OGD27YNGRNT/g6XT5fXyRGR6Lh8cIgnlixYC9YT\n" +
                        "05/o27t/lxcHsIRsMCjHaBDN7dvzN2/ONPYJ3am+biBiNptdWFiIfOnKwSLS29u7Z8+e3t5eaCJV\n" +
                        "N7o3ygEfGxtLp9NlOgEArKyszM/Pt/DT2oqNJvxd1piupwFZXV3N5/ORdA9VMx4Y6BsaGooSWMO1\n" +
                        "7C6vc8V/lWDdHgt0PRDJ6MjOwXSf1tcOxoQACTDywtJCw1/RRB80gk1vx/u+n8lkomVTbg/29vYq\n" +
                        "uzfzICnNAACAWCyWTqfdBqPjhYUGCd95bHo7XgSz2RV3YQaE+loikYj3emQimw3XrI5XHSUygoUA\n" +
                        "jTGpVMoYBOBg3kCt3hFbWsg09zWdw0ZP9U1DRHzfdzk+ssKNMVFkthkBH7UcmYuxWKzsJIRCp8mn\n" +
                        "dAybnvAEkoj3mJgnCICMFNrx6K/kl1fzVrgYU2mgfxkEDYXHKEjMnM1mOSiHj1bEoCXwGTnZl2zs\n" +
                        "K+47Gd88jDHJZNJdARMhn8+vrKyAIwIa698yR561NpPJRDtluh6CTVTfcqOVu8ocq3pBGE/09sSM\n" +
                        "IWALuhMKIgqblZyfyQRCN3K13eV9pLx4hxLYQzIQhGsXM8tLuawBMaF+4jP4DLqrRmMf0Z3q6wYi\n" +
                        "xuPxZDIppVl1ALC4uDg9Pa3xOmhi+bEbkWPmr776Sj10kciPruxy/HrRCtnG6XRy69YtGFZBErAC\n" +
                        "lsjzfZ6amlpaWiLydKnrXZ0GldcEa6pBc+rE91evTF7NLq8wgoVi0SURSSd6t28dauwb7jsZ3/wU\n" +
                        "JyJ9fX179+51/ecRi1+8ePHrr7+21lZqAA28pIjcunVramqqUChEgh/DLP2dO3fu2LGjyc/pGDaa\n" +
                        "41sRj08kUrt27UwmE+ozF0aEYKnM4mLm9OlPstllHRUN0D4QH4gsUljlc3+ZuH79euQl1A10BQEN\n" +
                        "HTx0oDfR9qJFrcKml/EAQEQjIyPbtm1z4+t6wMxnzpz5/PPPlekbaDyy0Zn56tWrZ86cUYWxrLV0\n" +
                        "On3gwAENETWArnJXN9RrNrJteN/u0Vi8RzDwqIPShiiTy739zqnLV6YrCe/uiVFrfww1FFBoYW7x\n" +
                        "9+/98cpXXyN5Evr5rSADIfC+vaMjI9sbXtx+38n4ViGRSJw4cWJoaMgNl0Va9/T09M9+9rPp6Sq0\n" +
                        "Xydyudy777579uzZKKcPnCSfdDp94sSJwcHBjpU1aB6b5kVrQTdxEo8PHT34wPj+uGFm9n3fgCEh\n" +
                        "g+gREdHU1NT/+p8/m7x0DRy7LkZGrXMo7o/h1GJAJKIYePnllX/++b++96f/KuR9FCJAjwxaQAsi\n" +
                        "1hg8uH/vkfGDsVisUCg0+BXdqb5eROZ7T0/Ps88+OzQ0pGvhovQ39cBYay9duvSTn/zk/fffz2az\n" +
                        "vr/Wcq0otJ/L5S5duvTjH//4gw8+UE0ewjVAEan6+/tPnDgxPDwMG0G/hrHRCyqaj8cjc2hP7x4d\n" +
                        "+etnvv3LX/9W96MGgKCaRrCU2tycuf3zX7x1YeLqt586uWPHji2pfs/zmLSahkbcPWb22V9ZWZmZ\n" +
                        "vXP69OnTp08vLCwod4sYRCQPmS2DQcQeI8cfGD927KiOomj5bf3dcN+snVM0/7WBIAdk5pgXe+65\n" +
                        "52bmlt5//332NSGuvI5eJpP58MMPz1/47ODBg0cOHB4ZGelNx3t6euJezPf9fIFXVlZuzdy8cuXK\n" +
                        "l5eu3bx5010oryurg19BIhofP/jCCy9s2bKlyRhg57HBhFc7vhniB0PHgoceM/T09L726surueWP\n" +
                        "zvwln88rMUxYakU5kpkXF7IfnT579tMLfX19vb09yWSyp6enUCjk84VsNru8fCfaMyVI4AGPhQkF\n" +
                        "gAlRvXijo7u+971XRkd3RttNbyLlbqOn+qahrBgtmgGAwcHBl19+Ob9KZ8+eDTf9DapmuFsgaILs\n" +
                        "/Py8rnsKHXC6cLNoFLh+fgK9FwBgx46RN95448CBA4hIprg9Q2Nf0Xnl4B4hfGiFM7MFQ9t3bn/j\n" +
                        "tZdTCfPh6U/y+bwWsgzLHrkFSwQxWFEbZmlq7aTygos6UiBU+w8cOPA3r7z0rW8dJM+ICIWXibMy\n" +
                        "q96vuL9kfEuAYd3jgPVFPM/btWvX66+/3pvs/+Mf/6hRea1tEVE04k6Xxu6vIgz8BFcS0cMnjr/6\n" +
                        "6qu7d27Xfe4jb8EmWjgHHSN8UMUIWrmPkiIglShjMgAQEIgA2oHB9Pff+JutW/pOnTo1OztrfQsA\n" +
                        "EFbOdG8Hl7lL4ftqvAkRpQdSJ0+efPnFv96yZYvWxgmJTboeu4mv0CJclUvv2rWL1r3A8bWAiLGY\n" +
                        "9/zzzz/wwAOnTp364osL8/PzBWvL6hVXktw9iYie56XTyfHx8aeefvLQoUPx2Gbi7FroWEUM/X8F\n" +
                        "TzRtx5dyQ0l1W0REEmbeuWv7D/7+bx959PLp06enrn01Pz+fya4UCgXDRIi+MAAYTc3TPS/RiAAZ\n" +
                        "TiYS/f2pHTt2nHjkwePHj/clU4jgCyFqIRywpKl3Tb6/6glrVOhuPTa9Hb9Gy5G2JSLxePzIkSNj\n" +
                        "Y2M3Z2avX79+4+ub165dW15YyuVyK37B931ZZUQ0PcbzvN7e5MDAwMjObaOjo6OjO4eHh/v6EwCA\n" +
                        "oEtqNs1a6DXQdsLrMLYVtQspNOIlkP0N2/NU9Vgk8NepRNeljb29Pfv37N67a2TVz8/NzeUyK7lc\n" +
                        "LpdfzWazq6urnuclEvFEIhGPx1Op1MDAQDKZ9DyPmQWi4keAwV41AgCke90GfLlWHe01318YGTCq\n" +
                        "xdsJ3MsyXpwaWq5hRkTxeHznzp0GPWa2AtZaHR/GYLArSoWJRURsmzLW18A9aMeHFeKqnK/gfwsg\n" +
                        "1TTbep8GAFBc8FYaqLWi4RkEAF8sIhICkZHQ1nCTqYsOnOJniN4bkoqhuMtVE1o9IxExaZ+4q/ja\n" +
                        "pdVvGhdjA3DpF/nqJVgsXV4Uo9KCj86XmfUAVay+TYeOyXgGACMERXkvhkj92wI+IjCry6yxJ5Tz\n" +
                        "RKWlHv7TlJ0vM+QgnJ/cmsrhMSGAbhLnoqmdaRgRkI0VEt2/zmcQjKa9dmn1G8zxvu9vLodX+6A8\n" +
                        "oL3h+w2WVFk/OibjCcpGL5EVWVxchKBylQgxANafdVs5btbWrp0doXUGCmzx4nmXd8MC+sXzurN7\n" +
                        "aLuzc00jCPbgZDRkmIHIy6/mVvLZhhtcJzaY469cudJw/uu9gSjNSxWRQqFw8+ZNDS60FZ0iPHK0\n" +
                        "ixgGW0gjAExd/mr+9h09bwCrbeK4TpRpwmt8lw/gazRPkAU5zLar1UJ5pYwoO28dz7o7gn2FCRmB\n" +
                        "QIDt+fOThQK29ilVntva5urF7Ozs6dOnox1dN/ZlNgSRiqM9cP369bNnz3agKzpEeAESIB23ymco\n" +
                        "YhCt8J8//GBi4ks1uJr44PXzBAFQKT9VolY9HIDaGfjNQOf5bDb/y1/+emkp43yLW6unldgwz10U\n" +
                        "w56dnf3Nb37T09Ozd+8odHDHvW8IMNg7AZaWlt79zbsTExMAKNL2Pa06u0OFerbVz4WEiBatzzxx\n" +
                        "cer//vrfvvPi82NjY+mk7gET1BZrMqWpHQjXZjZyr+tM1GMr6LOdnp5+7733znzwiWUiEiITbB/v\n" +
                        "Mrqzo0/z2EiOFxEJdgaCc+fOzc3PHjt27NCBPbt3704kErFYrGw76M2OKBNXv6tQKORyuVuzc5cu\n" +
                        "Xfri3F+uXr0KhaBqi4hQmwM2bSe8uD47UusZAICREZFQtw8SZp7++tbszNyZM6nh4eF0Oh2Pxz3T\n" +
                        "IyICBUTU2YKgZMd3KPrJAUKfoKk4dq/p5PWhKhDMdiKCVEzszOfz2bcop6kAAARWSURBVGx2Yf7O\n" +
                        "7Ozsam5VBMWzAkKiBbV0P852Cb4N43is2N6NmQuFwszMzO3bt/UCQzFmDnIeNjnhhQ0iCgQb3xVD\n" +
                        "BkGOJyKiDfL2O5F42XbC6+6Syuvu/vHufl3iVDExFAsDIWDFikjYfxYAbCBay0W+uxEM1zi2NY7b\n" +
                        "eb0OUA3/iOOcVr43IhIjj5mtYQAx0AMCut9ZFV4vif03iw1ToV3h7Uq+spDJN0qtawaVHyIOXBYv\n" +
                        "iwe2CW3neEs+hDI+dN1pVJTKvjb4fiomQaBsvgBo6T6pmquDAEBQ/C43FszoA2ncL0jkE6mxTym2\n" +
                        "UupvsFZfSXs93sT6PEY/JWdUclfJ29dLnFU7UNEz7UDdhK/6QiI1M+aqyHghADC6VzP6AIDiAYAI\n" +
                        "658BQJRX6n25bwIk+glAoWZTlY81a88SAxZ320ZEjRYyOhK9pRr+XdqqKpkqDzYfX96jWL9kbKpY\n" +
                        "T7A/bDBFV6e92qNuPF4tezdOfxdNtaUeq3ajci90zT4KvrTiWxhLfBtQ2j9V2he3zxtHfb1Zxu5d\n" +
                        "Rt8QVK7TUz24rkbWxfFlg6tyrEUrSavdXG59Yr28u0l4XVGZT1zyvev4lrX7J/B/UEn+510JVKWd\n" +
                        "u15RBrY2l8tp8acodaTeRrpoGC6Z1de5vLzM9W9lvC7Cl03puVyuUCise55vfSz5PoebQFwoFKLi\n" +
                        "HXVhXYQv4+mlpSXdzLMr6TcQ2vl37tzRhNV6Ub/4FMgsLc/emlldyVsRW0F7jbXSveVw3RBU7T03\n" +
                        "OTOfy83fvp1dzqjfQy2rdXJhI0FfEVlZzQ8PD/f29hIFKUzoVo5w3rFL+4ZRq/cQERBEZGFubmJi\n" +
                        "Ipsp7oC0/rm3fjseAQAWb89dujBx7PiD/f39ErghndcN9PyuIGgWbua1a8F7SIsLi5cuXYo2PKse\n" +
                        "tayNxn31k5OTaOjo0aO96RQRGV1UUOHX6xK+MUhpVY7on5qRPDs3NzExceXy5YbbbzS/hxAAFubm\n" +
                        "/UKhNx43iOR5ltmEUYhgY4dofRoCYB0T0f12vYIAUNM3EDw11d2STCIgspLP37p16/Lk5OXJSQpL\n" +
                        "CzXwrEbZMZxZEHFwaMvIyMjQtm3pdDrZ22uMQWPKXQoVjsy1cb9dr3CLSBgJyq5rukqhUCjk89ls\n" +
                        "9ubMzI0bN+4sLAhzlcWd635Ws/MwhqXGUqlUIpFIpJLJZDKIQAeVI5zX6h7XcywiBCgiaCiTyeRz\n" +
                        "K5lMZjmbYWu1KEtNogDA3QZB3YS/S51eQiICIigmWgl8Azpxkx4r4VlNY8sAVaiqCqBLlLYQvvJm\n" +
                        "KT2DgMEHNLHH3/2MkkEQ7nJVrM0n34BMhUp3wfodCF3Uwtp92KoeblSrr9Aku2ZbY3AJqcdRMlat\n" +
                        "68tvq9HO2mhZxLM7pbcWm7s/u9NAw3DTMiu7sfkJ/14o0ngf4pvET129ruNopss3U1ZTF1100UUX\n" +
                        "XXTRRRdddNFFF1100cV9if8PEWGO/Qf89Q8AAAAASUVORK5CYII=\n";

                //La imagen es null cuando el bitmap es el de la imagen por default.
                if(imagenComentario.equals(bitmapComentario)){
                    imagenComentario = null;
                }

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
