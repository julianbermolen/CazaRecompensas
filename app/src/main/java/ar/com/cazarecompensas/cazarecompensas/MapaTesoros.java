package ar.com.cazarecompensas.cazarecompensas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;

import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.TesoroAdapter;
import ar.com.cazarecompensas.cazarecompensas.services.TesoroService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.cazarecompensas.cazarecompensas.R.id.listView;
import static ar.com.cazarecompensas.cazarecompensas.R.id.map;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapaTesoros.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapaTesoros} factory method to
 * create an instance of this fragment.
 */
public class MapaTesoros extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    MapView mMapView;
    View mView;
    private Marker marcador;
    private Circle circle;
    double lat = 0.0;
    double lng = 0.0;

    public MapaTesoros() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_mapa_tesoros, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(map);

        if (mMapView != null) {

            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }


        miUbicacion();
        obtenerMarcadores();

        if (lat == 0.0 && lng == 0.0){
            LatLng coordenadas = new LatLng(-37.64903402, -65.47851562);
            CameraUpdate ubicacionDefault = CameraUpdateFactory.newLatLngZoom(coordenadas, 4);
            mMap.animateCamera(ubicacionDefault);

        }
    }

    private void obtenerMarcadores(){

        //Creo el llamado asincronico
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TesoroService service = retrofit.create(TesoroService.class);
        final Call<Tesoro[]> tesoros = service.getTesoros();
        tesoros.enqueue(new Callback<Tesoro[]>() {
            @Override
            public void onResponse(Call<Tesoro[]> call, Response<Tesoro[]> response) {

                for (int i = 0; i < response.body().length; i++) {
                    double lati=Double.parseDouble(response.body()[i].getLatitud());
                    double longLat=Double.parseDouble(response.body()[i].getLongitud());

                    Location locationA = new Location("location A");

                    locationA.setLatitude(longLat / 1E6);
                    locationA.setLongitude(lati  / 1E6);

                    Location locationB = new Location("location B");

                    locationB.setLatitude(lat  / 1E6);
                    locationB.setLongitude(lng  / 1E6);

                    double distance = locationA.distanceTo(locationB);

                    if ( distance < 0.0060 ) {
                        mMap.addMarker(new MarkerOptions().position(
                                new LatLng(longLat,lati))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                                .title(response.body()[i].getNombre())
                        );


                    }


                }



            }

            @Override
            public void onFailure(Call<Tesoro[]> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "Error en la carga de tesoros", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 14);

        if (marcador != null || circle != null) {

            marcador.remove();
            circle.remove();


        }

        //marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi Posición Actual").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_position)));
        mMap.animateCamera(miUbicacion);

        circle = mMap.addCircle(new CircleOptions()
                .center(coordenadas)
                .radius(1000)
                .strokeColor(0XFFe79e55)
                .fillColor(0X26b2ffff));

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

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager locationManager = (LocationManager) getContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locListener);


    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
