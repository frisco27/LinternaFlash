package net.frisco27.linternaflash;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    ListView simpleList;
    String[] questions;
    Button save;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
// get the string array from string.xml file
        questions = getResources().getStringArray(R.array.questions);
// get the reference of ListView and Button
        simpleList = (ListView) findViewById(R.id.simpleListView);
        save = (Button) findViewById(R.id.submit);
// set the adapter to fill the data in the ListView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), questions);
        simpleList.setAdapter(customAdapter);
// perform setOnClickListerner event on Button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
// get the value of selected answers from custom adapter
                for (int i = 0; i < CustomAdapter.selectedAnswers.size(); i++) {
                    message = message + "\n" + (i + 1) + " " + CustomAdapter.selectedAnswers.get(i);
                }
// display the message on screen with the help of Toast.
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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
            Intent intent = new Intent(context.getApplicationContext(),Lanzar.class);
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