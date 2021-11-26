package net.smallacademy.tabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private TextView birthdayInput;

    final Calendar[] c = new Calendar[1];
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //select birthday
        birthdayInput = (TextView) findViewById(R.id.input_date);
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


    }
}