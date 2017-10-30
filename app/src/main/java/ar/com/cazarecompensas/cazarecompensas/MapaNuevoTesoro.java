package ar.com.cazarecompensas.cazarecompensas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import ar.com.cazarecompensas.cazarecompensas.services.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class MapaNuevoTesoro extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    MapView mMapView;
    View mView;
    private Marker marcador;
    private Marker marcadorObtener;
    private Marker marcadorBusqueda;
    double lat = 0.0;
    double lng = 0.0;
    double latObtener = 0.0;
    double lngObtener = 0.0;
    Button botonBuscar;
    Button botonFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_nuevo_tesoro);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        botonBuscar = (Button) findViewById(R.id.buttonBuscarUbicacion);

        botonBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onSearch();
            }

        });


        //Al tocar el boton finalizar guarda el tesoro
        botonFinalizar = (Button) findViewById(R.id.finalizarNuevoTesoro);
        botonFinalizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Se obtienen los datos traidos del intent
                Intent intent = getIntent();
                String nombreTesoro = intent.getStringExtra("nombreTesoro");
                String descripcionTesoro = intent.getStringExtra("descripcionTesoro");
                int categoriaTesoro = intent.getIntExtra("categoriaTesoro",0);
                Integer recompensaTesoro = intent.getIntExtra("recompensaTesoro",0);
                int idEstadoTesoro = intent.getIntExtra("idEstadoTesoro",0);
                byte[] imagen1TesoroCod = intent.getByteArrayExtra("imagen1Tesoro");
                Bitmap imagen1Tesoro = BitmapFactory.decodeByteArray(imagen1TesoroCod, 0, imagen1TesoroCod.length);
                byte[] imagen2TesoroCod = intent.getByteArrayExtra("imagen2Tesoro");
                Bitmap imagen2Tesoro = BitmapFactory.decodeByteArray(imagen2TesoroCod, 0, imagen2TesoroCod.length);
                byte[] imagen3TesoroCod = intent.getByteArrayExtra("imagen3Tesoro");
                Bitmap imagen3Tesoro = BitmapFactory.decodeByteArray(imagen3TesoroCod, 0, imagen3TesoroCod.length);
                guardarTesoro(nombreTesoro,descripcionTesoro,categoriaTesoro,recompensaTesoro,idEstadoTesoro,imagen1Tesoro,imagen2Tesoro,imagen3Tesoro,latObtener,lngObtener);

                //Al finalizar se va hacia el mainActivity
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
            }

        });


    }

    public void guardarTesoro(String nombre, String descripcion, int categoria, Integer Recompensa, int idTesoroEstado, Bitmap img1, Bitmap img2, Bitmap img3,Double lat, Double lng){

        //Creo el tesoro
        final Tesoro tesoro = new Tesoro();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
   UsuarioService service2 = retrofit.create(UsuarioService.class);

        Profile profile = Profile.getCurrentProfile();
        String idFacebook = profile.getId().toString();
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

        String longitud = String.valueOf(lng);
        String latitud = String.valueOf(lat);

        tesoro.setRecompensa(Recompensa);

        String img1String = encodeTobase64(img1);
        String img2String = encodeTobase64(img2);
        String img3String = encodeTobase64(img3);

        //Creo la espera de la llamada y llamo al servicio

        int idUsuario = tesoro.getIdUsuario();

        Call<ModelResponse> modelResponseCall = service.postTesoro(nombre,descripcion,categoria,idUsuario,Recompensa,idTesoroEstado,img1String,img2String,img3String,longitud,latitud);
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (marcadorObtener != null) {

                    marcadorObtener.remove();

                }
                latObtener = latLng.latitude;
                lngObtener = latLng.longitude;
                marcadorObtener =  mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).anchor(0.0f,1.0f).position(latLng));
            }
        });



        miUbicacion();
    }

    public void onSearch(){

        EditText location_tf = (EditText) findViewById(R.id.editTextBuscarUbicacion);

        String location = location_tf.getText().toString();
        List<Address> addressList = null;

        if(location !=null || !location.equals("")){

            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location,1);
            }catch (IOException e){
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());

            if (marcadorBusqueda != null) {

                marcadorBusqueda.remove();

            }
            marcadorBusqueda = mMap.addMarker(new MarkerOptions().position(latLng));
            CameraUpdate ubicacionBuscada = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            mMap.animateCamera(ubicacionBuscada);

        }


    }


    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 11);

        if (marcador != null) {

            marcador.remove();



        }

        marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi Posición Actual").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_position)));
        mMap.animateCamera(miUbicacion);


    }

    private void actualizarUbicacion(Location location) {

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);

        }

    }



    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,45000,0,locListener);


    }

}
