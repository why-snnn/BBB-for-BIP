package com.example.bbb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EntryActivity extends AppCompatActivity {
    private HttpHelper httpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        this.httpHelper = new HttpHelper();

        EditText nameText = findViewById(R.id.Name);
        EditText passwordText = findViewById(R.id.Password);
        Button regButton = findViewById(R.id.regButton);
        Button entryButton = findViewById(R.id.entryButton);

        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String name = nameText.getText().toString();
                String password = passwordText.getText().toString();*/

                String name = "snnn";
                String password = "Gig8liLi";

                if (!name.equals("") && !password.equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (httpHelper.HttpLogin(name, password))
                                startActivity(new Intent(EntryActivity.this, MainMenuActivity.class));
                        }
                    }).start();
                }
                else
                    Toast.makeText(getApplicationContext(), "Поле ввода пусто!", Toast.LENGTH_SHORT).show();
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryActivity.this, RegistrationActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}