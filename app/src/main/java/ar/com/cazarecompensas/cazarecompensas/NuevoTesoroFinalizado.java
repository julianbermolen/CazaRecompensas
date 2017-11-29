package ar.com.cazarecompensas.cazarecompensas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NuevoTesoroFinalizado extends AppCompatActivity {

    Button botonHome;
    TextView NombreTesoroFinalizado;
    TextView DescripcionTesoroFinalizado;
    TextView RecompensaTesoroFinalizado;

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

        NombreTesoroFinalizado = (TextView) findViewById(R.id.textNombreTesoroFinalizado);
        DescripcionTesoroFinalizado = (TextView) findViewById(R.id.textDescripcionTesoroFinalizado);
        RecompensaTesoroFinalizado = (TextView) findViewById(R.id.textRecompensaTesoroFinalizado);

        Intent intent2 = getIntent();
        String nombreTesoro = intent2.getStringExtra("nombreTesoro");
        String descripcionTesoro = intent2.getStringExtra("descripcionTesoro");
        Integer recompensaTesoro = intent2.getIntExtra("recompensaTesoro",0);
        String recompensaTesoroString = recompensaTesoro.toString();

        NombreTesoroFinalizado.setText("Nombre: "+nombreTesoro);
        DescripcionTesoroFinalizado.setText("Descripcion: "+descripcionTesoro);
        RecompensaTesoroFinalizado.setText("Total: $"+recompensaTesoroString);

    }

}
