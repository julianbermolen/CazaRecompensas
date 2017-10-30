package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import ar.com.cazarecompensas.cazarecompensas.Models.ModelResponse;
import ar.com.cazarecompensas.cazarecompensas.Models.TokenRequest;
import ar.com.cazarecompensas.cazarecompensas.services.LoginApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login_activity extends AppCompatActivity {
    //TextView resultadoTextView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String emailUser;
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
        Profile profile = Profile.getCurrentProfile();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.apiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginApi service = retrofit.create(LoginApi.class);

// METOTODO EN EL MODELO DE USUARIO TOKENREQUEST.
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setApellido(profile.getLastName());
        tokenRequest.setNombre(profile.getFirstName());
        tokenRequest.setIdFacebook(profile.getId().toString());
        tokenRequest.setEmail("Ejemplo");
        tokenRequest.setUrlFoto(profile.getProfilePictureUri(180,180).toString());
// LO PARSEO PARA ENVIARLO COMO QUERY STRING.
        String nombre = tokenRequest.getNombre();
        String apellido = tokenRequest.getApellido();
        String email = tokenRequest.getEmail();
        String idFacebook = tokenRequest.getIdFacebook();
        String urlFoto = tokenRequest.getUrlFoto();
        Call<ModelResponse> modelResponseCall = service.getExito(nombre,apellido,email,idFacebook,urlFoto);
        modelResponseCall.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                int statusCode = response.code();

                ModelResponse modelResponse = response.body();
                Log.d("login_activity","onResponse:" +statusCode);

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Log.d("login_activity","onFailure:" + t.getMessage());
            }
        });
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
