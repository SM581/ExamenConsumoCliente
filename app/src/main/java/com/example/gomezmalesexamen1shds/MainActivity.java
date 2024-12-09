package com.example.gomezmalesexamen1shds;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMensaje = findViewById(R.id.txtMensaje);
        Button btnConsumo = findViewById(R.id.btnConsumo);

        btnConsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar a la función que realiza la solicitud HTTP
                new HttpRequestTask().execute("http://10.10.19.191:3000/gomez");
            }
        });
    }

    // Clase para realizar la solicitud HTTP en segundo plano
    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                in.close();
            } catch (Exception e) {
                result = "Error: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // Actualiza el TextView con la respuesta del servidor
            txtMensaje.setText(result);
        }
    }
}