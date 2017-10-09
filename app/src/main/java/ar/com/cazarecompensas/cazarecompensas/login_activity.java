package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login_activity extends AppCompatActivity {
    //TextView resultadoTextView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Si el usuario ya tiene token, ingresa al activity principal.
        if(AccessToken.getCurrentAccessToken() != null){
            goMainScreen();
        }
        setContentView(R.layout.activity_login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Profile.setCurrentProfile(currentProfile);
                            mProfileTracker.stopTracking();
                            Profile profile = Profile.getCurrentProfile();
                            if(profile != null){
                                registrarPerfilEnBDD();
                                goMainScreen();
                            }else{
                                System.out.println("Error en el login");
                            }
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
                    if(profile != null){
                        registrarPerfilEnBDD();
                        goMainScreen();
                    }else{
                        System.out.println("Error en el login");
                    }
                }

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.cancel_login,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),R.string.cancel_login,Toast.LENGTH_SHORT).show();
            }

        });


        // Ejemplo de invocación a la api rest
        //resultadoTextView = (TextView)findViewById(R.id.resultado);
        //resultadoTextView.setText(new ApiCazarecompensasWebClient().Get("/usuarios"));

    }

    private void registrarPerfilEnBDD(){
        Usuario nuevoUsuario = new Usuario();
        Profile profile = Profile.getCurrentProfile();
        String id = profile.getId();
        nuevoUsuario.setIdFacebook((int) Long.parseLong(id));
        nuevoUsuario.setNombre(profile.getFirstName());
        nuevoUsuario.setApellido(profile.getLastName());
        nuevoUsuario.setEmail("Prueba@gmail.com");
        nuevoUsuario.setUrlFoto(profile.getProfilePictureUri(180,180).toString());

        String nombre = nuevoUsuario.getNombre();
        String urlFoto = nuevoUsuario.getUrlFoto();


        System.out.println("el nombre de facebook es" + nombre + "y la url de foto es : " + urlFoto );

    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
