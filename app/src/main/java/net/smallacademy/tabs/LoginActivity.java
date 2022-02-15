package net.smallacademy.tabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
        String client = preferences.getString("Auth", "");
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

    private void authAttempt(String email, String password) {
        String url = "http://192.168.1.8:8080/api/auth/login?email="+email+"&password="+password;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    if (res.length() == 2) {
                        String token = (String) res.get("token");
                        JSONObject client = (JSONObject) res.get("client");
                        if (token.equalsIgnoreCase("")) {
                            Toast.makeText(LoginActivity.this, R.string.invalide_login_data, Toast.LENGTH_SHORT).show();
                        } else {
                            makeLogin(token,client);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.invalide_login_data, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    private void makeLogin(String token,JSONObject client) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Auth-Token", token);
        editor.putString("Auth", client.toString());
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
            authAttempt(email, password);
        }
    }
}