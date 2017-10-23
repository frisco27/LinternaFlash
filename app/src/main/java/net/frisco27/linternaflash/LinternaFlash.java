package net.frisco27.linternaflash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.List;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class LinternaFlash extends AppCompatActivity {

    private ImageButton btControl;
    private int status = 1;//GLOBAL VARIABLE : the status of the Button ( 0 or 1 )
    private Context context;
    private Camera dispCamara;
    Parameters parametrosCamara;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linternaflash);
        context = this;
        btControl = (ImageButton) findViewById(R.id.btLinterna);
        btControl.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                encenderLinternaAndroid ();
            }
        });
        btControl.setBackgroundResource(R.drawable.flashencendido);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //cambiar de evento y vista del boton
    private void cambiarEventos()
    {
        if (status == 1)
        {
            btControl = (ImageButton)findViewById(R.id.btLinterna);
            btControl.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    apagarLinternaAndroid();
                }
            });
            btControl.setBackgroundResource(R.drawable.flashapagado);
            status=0 ; // change the status to 1 so the at the second clic, the else will be executed
        }
        else
        {
            btControl = (ImageButton) findViewById(R.id.btLinterna);
            btControl.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    encenderLinternaAndroid ();
                }
            });
            btControl.setBackgroundResource(R.drawable.flashencendido);
            status =1;//change the status to 0 so the at the second clic, the if will be executed
        }
    }
    //Al cerrar la aplicación apagar el flash
    public void finish()
    {
        if (dispCamara != null)
        {
            dispCamara.release();
            dispCamara = null;
        }
        super.finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            dispCamara = Camera.open();
        }
        catch( Exception e )
        {
            //Toast.makeText(getApplicationContext(),
            //          "No se ha podido acceder a la cámara",
            //          Toast.LENGTH_SHORT).show();
        }
    }

    private void apagarLinternaAndroid ()
    {
        if (dispCamara != null)
        {
            parametrosCamara = dispCamara.getParameters();
            parametrosCamara.setFlashMode(Parameters.FLASH_MODE_OFF);
            dispCamara.setParameters(parametrosCamara);
            cambiarEventos();
        }
        else
        {
            Toast.makeText (getApplicationContext(),
                    "No se ha podido acceder al Flash de la cámara",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void encenderLinternaAndroid ()
    {
        //Toast.makeText(getApplicationContext(),
        //  "Accediendo a la cámara", Toast.LENGTH_SHORT).show();

        if (dispCamara != null)
        {
            //Toast.makeText(getApplicationContext(),
            // "Cámara encontrada", Toast.LENGTH_SHORT).show();

            Parameters parametrosCamara = dispCamara.getParameters();

            //obtener modos de flash de la cámara
            List<String> modosFlash = parametrosCamara.getSupportedFlashModes ();

            if (modosFlash != null && modosFlash.contains(Camera.Parameters.FLASH_MODE_TORCH))
            {
                //establecer parámetro TORCH para el flash de la cámara
                parametrosCamara.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                parametrosCamara.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
                try
                {
                    dispCamara.setParameters(parametrosCamara);
                    dispCamara.startPreview();
                    cambiarEventos();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                            "Error al activar la linterna",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "El dispositivo no tiene el modo de Flash Linterna",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "No se ha podido acceder al Flash de la cámara",
                    Toast.LENGTH_SHORT).show();
        }
    }
    //-----------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.menu_frontal) {
            Toast.makeText(this, "Android FRONTAL is Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_sos) {
            Intent intent = new Intent(LinternaFlash.this,Lanzar.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.menu_settings) {
            try {
                Intent intent = new Intent(context.getApplicationContext(),Settings.class);
                this.startActivity(intent);
            }catch (Exception ex)
            {
                Log.e("Intent Settings",ex.getMessage());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
