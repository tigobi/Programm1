package com.example.programm1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText userField;
    private Button main_button;
    private TextView result_info;
    private TextView currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userField = findViewById(R.id.userField);
        main_button = findViewById(R.id.main_button);
        result_info = findViewById(R.id.result_info);
        currentPosition = findViewById(R.id.currentPosition);
        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userField.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.noUserInput, Toast.LENGTH_LONG).show();
                } else {
                    String name = userField.getText().toString();
                    String url = "https://pokeapi.co/api/v2/pokemon/" + name.toLowerCase();
                    new GetParsedURLData().execute(url);
                }
            }
        });
    }

    private class GetParsedURLData extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            result_info.setText("Waiting");
            currentPosition.setText("PreExecute");

        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String jsonResponse = null;
            currentPosition.setText("doInBackground");
            try {
                URL url = new URL((strings[0]));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();
                currentPosition.setText("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    currentPosition.setText("YEAH");
                    StringBuilder response = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    for (int i = 0; i < 10; i++) {
                        currentPosition.setText(line);
                        response.append(line);
                    }
                    jsonResponse = response.toString();
                } else {
                    jsonResponse = "Error: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                jsonResponse = "Exception: " + e.getMessage();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                result_info.setText(result);
            } else {
                result_info.setText("Error or empty response");
            }
        }
    }
}
