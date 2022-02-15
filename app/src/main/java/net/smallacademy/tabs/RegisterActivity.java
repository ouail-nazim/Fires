package net.smallacademy.tabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private TextView birthdayInput;

    final Calendar[] c = new Calendar[1];
    private DatePickerDialog dpd;
    Button submit;
    EditText input_first_name;
    EditText input_last_name;
    EditText input_phone_number;
    EditText input_email;
    EditText input_password;
    EditText input_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //select birthday
        birthdayInput = (TextView) findViewById(R.id.input_date);
        input_first_name = (EditText) findViewById(R.id.input_first_name);
        input_last_name = (EditText) findViewById(R.id.input_last_name);
        input_phone_number = (EditText) findViewById(R.id.input_phone_number);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        input_password = (EditText) findViewById(R.id.input_password);
        submit = (Button) findViewById(R.id.button_sign_up);

        birthdayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c[0] = Calendar.getInstance();
                int day = c[0].get(Calendar.DAY_OF_MONTH);
                int month = c[0].get(Calendar.MONTH);
                int year = c[0].get(Calendar.YEAR);
                dpd = new DatePickerDialog(RegisterActivity.this, R.style.MyDatePickerStyle, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthdayInput.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = String.valueOf(input_first_name.getText());
                String last_name = String.valueOf(input_last_name.getText());
                String phone = String.valueOf(input_phone_number.getText());
                String email = String.valueOf(input_email.getText());
                String password = String.valueOf(input_password.getText());
                String dob = String.valueOf(birthdayInput.getText());
                submit_form(first_name, last_name, phone, email, password, dob);
            }
        });
    }

    private void submit_form(String first_name, String last_name, String phone, String email, String password, String dob) {
        Boolean valid = isValidData(first_name, last_name, phone, email, password, dob);
        if (!valid) {
            Toast.makeText(RegisterActivity.this, R.string.empty_data, Toast.LENGTH_SHORT).show();
        } else {
            makeRegister(first_name, last_name, phone, email, password, dob);
        }

    }

    private void makeRegister(String first_name, String last_name, String phone, String email, String password, String dob) {
        String url = "http://192.168.1.8:8080/api/auth/register?email=" + email + "&password=" + password + "&first_name=" + first_name + "&last_name=" + last_name + "&phone=" + phone + "&dob=" + dob;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    if (res.length() == 3) {
                        String token = (String) res.get("token");
                        JSONObject client = (JSONObject) res.get("client");
                        if (token.equalsIgnoreCase("")) {
                            Toast.makeText(RegisterActivity.this, R.string.invalide_login_data, Toast.LENGTH_SHORT).show();
                        } else {
                            makeLogin(token,client);
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, R.string.invalide_login_data, Toast.LENGTH_SHORT).show();
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

    private Boolean isValidData(String first_name, String last_name, String phone, String email, String password, String dob) {
        return true;
    }

    private void makeLogin(String token,JSONObject client) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Auth-Token", token);
        editor.putString("Auth", client.toString());
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}