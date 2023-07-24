package com.example.petfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Diet extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private EditText datePicker,timePicker;

    EditText date_pick;
    EditText time_pick;
    RadioButton btn_lvl_1,btn_lvl_2,btn_lvl_3,btn_lvl_4,btn_lvl_5,btn_lvl_6;
    Button upload;

    DatabaseReference feedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        date_pick = findViewById(R.id.date_pick);
        time_pick = findViewById(R.id.time_pick);
        upload = findViewById(R.id.upload);

        datePicker  = findViewById(R.id.date_pick);
        timePicker = findViewById(R.id.time_pick);
        btn_lvl_1 = findViewById(R.id.lvl_1);
        btn_lvl_2 = findViewById(R.id.lvl_2);
        btn_lvl_3 = findViewById(R.id.lvl_3);
        btn_lvl_4 = findViewById(R.id.lvl_4);
        btn_lvl_5 = findViewById(R.id.lvl_5);
        btn_lvl_6 = findViewById(R.id.lvl_6);
        selectDate();

        timePicker.setOnClickListener(v->selectTime());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });
    }
    private void uploadData() {

        String date = date_pick.getText().toString();
        String time = time_pick.getText().toString();
        String lvl1 = btn_lvl_1.getText().toString();
        String lvl2 = btn_lvl_2.getText().toString();
        String lvl3 = btn_lvl_3.getText().toString();
        String lvl4 = btn_lvl_4.getText().toString();
        String lvl5 = btn_lvl_5.getText().toString();
        String lvl6 = btn_lvl_6.getText().toString();

        Data data = new Data(date,time,lvl1,lvl2,lvl3,lvl4,lvl5,lvl6);
        feedRef = FirebaseDatabase.getInstance().getReference().child("Data");

        feedRef.push().setValue(data);
        Toast.makeText(Diet.this, "Data uploaded", Toast.LENGTH_SHORT).show();
    }

    private void selectDate(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                datePicker.setText(updateDate());
            }
        };
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Diet.this, date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private String updateDate() {
        String myFormat = "yyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        return dateFormat.format(myCalendar.getTime());
    }

    private void selectTime(){

        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minut = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(Diet.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minut) {
                currentTime.set(Calendar.HOUR_OF_DAY,hour);
                currentTime.set(Calendar.MINUTE,minut);

                String myFormat = "HH:mm:ss";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                timePicker.setText(dateFormat.format(currentTime.getTime()));
            }
        },hour,minut, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}