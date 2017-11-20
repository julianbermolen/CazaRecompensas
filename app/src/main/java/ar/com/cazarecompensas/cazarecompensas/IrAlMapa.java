package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import ar.com.cazarecompensas.cazarecompensas.Models.Tesoro;
import ar.com.cazarecompensas.cazarecompensas.Models.Usuario;

import static java.security.AccessController.getContext;

public class IrAlMapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir_al_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Volver = (Button) findViewById(R.id.buttonVolverHomeIrAMapa);

        Volver.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {

                                                     volverAlHome();

                                                 }
                                             }
        );
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

        Intent intent = getIntent();
        final Tesoro tesoro = intent.getParcelableExtra("Tesoro");
        // Add a marker in Sydney and move the camera
        double latitud = Double.parseDouble(tesoro.getLongitud());
        double longitud = Double.parseDouble(tesoro.getLatitud());

        Geocoder geoCoder = new Geocoder(this);
        List<Address> matches = null;
        try {
            matches = geoCoder.getFromLocation(latitud, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
        String address = matches.get(0).getAddressLine(0);

        LatLng ubicacionTesoro = new LatLng(latitud,longitud);
        mMap.addMarker(new MarkerOptions().position(ubicacionTesoro).title(tesoro.getNombre()).snippet(address).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionTesoro,14));


    }

    private void volverAlHome(){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
