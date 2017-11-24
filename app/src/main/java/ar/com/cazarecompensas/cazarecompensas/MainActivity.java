package ar.com.cazarecompensas.cazarecompensas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, listaTesoros.OnFragmentInteractionListener,MapaTesoros.OnFragmentInteractionListener{
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "ar.com.cazarecompensas.cazarecompensas",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        //Chequeo si el usuario está ya logueado
        verificarSesion();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Renderizo el perfil del usuario
        renderFacebookProfile();



        //Manejo de Fragments
        listaTesoros fr1 = new listaTesoros();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, fr1).commit();

        Button frag1 = (Button) findViewById(R.id.frag1);
        Button frag2 = (Button) findViewById(R.id.frag2);

        frag1.setOnClickListener(this);
        frag2.setOnClickListener(this);

    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,login_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void logout(View view){
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
    public String getUserName(){
        Profile profile = Profile.getCurrentProfile();
        String nombre = Profile.getCurrentProfile().getFirstName();
        String apellido = Profile.getCurrentProfile().getLastName();
        String nombreCompleto = nombre+" "+apellido;
        return nombreCompleto;
    }
    public void verificarSesion(){
        if(AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }
    }
    public void renderFacebookProfile(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String valor = getUserName();
        View header=navigationView.getHeaderView(0);
        TextView nombreUsuario = (TextView) header.findViewById(R.id.nombreHader);
        nombreUsuario.setText(valor);

        URL imageUrl = null;
        HttpURLConnection conn = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            imageUrl =  new URL(""+Profile.getCurrentProfile().getProfilePictureUri(180,180));
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream());
            ImageView img = (ImageView) header.findViewById(R.id.imageView);
            img.setImageBitmap(imagen);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent Options = new Intent(this,
                    MisRecompensas.class);
            this.startActivity(Options);
            return true;

        } else if (id == R.id.nav_add) {

            Intent Options = new Intent(this,
                    NuevoTesoro.class);
            this.startActivity(Options);
            return true;

        } else if (id == R.id.nav_bandejaEntrada) {
            goBandejaDeEntrada();

        } else if (id == R.id.nav_misBusquedas) {

            Intent Options = new Intent(this,
                    MisPeticiones.class);
            this.startActivity(Options);
            return true;

        } else if (id == R.id.nav_cerrarSesion) {
            LoginManager.getInstance().logOut();
            goLoginScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goBandejaDeEntrada(){
        Intent intent = new Intent(this,BandejaEntrada.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag1:
                listaTesoros fragmento1 = new listaTesoros();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, fragmento1);
                transaction.commit();
                break;
            case R.id.frag2:
                MapaTesoros fragmento2 = new MapaTesoros();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.contenedor, fragmento2);
                transaction2.commit();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
