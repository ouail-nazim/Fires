package net.smallacademy.tabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {


    private EditText emailInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        String token = preferences.getString("Auth-Token", "");
        if (!token.equalsIgnoreCase("")) {
            super.onStop();
            //already Loged in
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_login);
            loginBtn = (Button) findViewById(R.id.login_btn);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });
            registerBtn = (Button) findViewById(R.id.register_btn);
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    register();
                }
            });
        }
    }

    private void register() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private String authAttempt(String email, String password) {
        //TODO: make api call to make login and return the token
        String token = "Bearer " + UUID.randomUUID().toString();
        if (email.equals("admin") || password.equals("admin")) {
            return token;
        }
        return "";
    }

    private void makeLogin(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Auth-Token", token);
        editor.apply();
        //go to main
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void login() {
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (email.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, R.string.empty_data, Toast.LENGTH_SHORT).show();
        } else {
            String newToken = authAttempt(email, password);
            if (newToken.equalsIgnoreCase("")) {
                Toast.makeText(LoginActivity.this, R.string.invalide_login_data, Toast.LENGTH_SHORT).show();
            } else {
                makeLogin(newToken);
            }
        }
    }
}