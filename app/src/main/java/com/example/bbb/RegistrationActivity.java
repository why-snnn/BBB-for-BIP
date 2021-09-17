package com.example.bbb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    private HttpHelper httpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.httpHelper =  HttpHelper.getHttpHelper();

        final EditText nameText = findViewById(R.id.nameText);
        final EditText pass1Text = findViewById(R.id.pass1Text);
        final EditText pass2Text = findViewById(R.id.pass2Text);
        final Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (httpHelper.HttpRegistration("321", "Gig8liLi"))
                            startActivity(new Intent(RegistrationActivity.this, MainMenuActivity.class));
                    }
                }).start();


                /*String name = nameText.getText().toString();
                String pass = pass1Text.getText().toString();
                String pass2 = pass2Text.getText().toString();
                if (pass.equals(pass2)){
                    if (new DataValidation().isCredentialsValid(name, pass)){
                        if (httpHelper.HttpRegistration(name, pass))
                            startActivity(new Intent(RegistrationActivity.this, MainMenuActivity.class));
                        else
                            Toast.makeText(getApplicationContext(), "Регистрация завершилась ошибкой. Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Введенные данные не отвечают правилам", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();*/
            }
        });

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                httpHelper.test("123", "Gig8liLi");
            }
        }).start(); */
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
}