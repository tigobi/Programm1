package com.example.programm1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText userField;
    private Button main_button;
    private TextView logo;
    private TextView result_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userField = findViewById(R.id.userField);
        main_button = findViewById(R.id.main_button);
        result_info = findViewById(R.id.result_info);
        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userField.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.noUserInput, Toast.LENGTH_LONG).show();
                } else {
                    
                }
            }
        });
    }
}