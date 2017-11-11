package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NuevoTesoroFinalizado extends AppCompatActivity {

    Button botonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tesoro_finalizado);

        botonHome = (Button) findViewById(R.id.botonVolverHomeFinalizado);

        botonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }


        });
    }

}
