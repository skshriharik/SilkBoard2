package com.example.shrihari.silkboard2;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
public class otherDetails extends AppCompatActivity {
    private EditText race_x , capacity_x,date_x,ddnumber_x,year , other;
    private String race_j="num" , capacity_j ,other_j ,date_j="null",ddnumber_j="null",year_j , radioValue,phone;
    final static String URL_OTHER = "http://192.168.1.100/back/three.php";
    private RadioGroup mRadioGroup ;
    private RadioButton mRadioButton;
    private TextView mdisplayDate;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private Button next3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_details);
        getValues();
        onButtonClicked();
    }

    private void onButtonClicked() {

        Log.d("chk","Date value before setting = "+race_j);
        mdisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =  Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dailog = new DatePickerDialog(otherDetails.this ,
                        R.style.Theme_AppCompat_DayNight_Dialog ,
                        mOnDateSetListener ,
                        year , month ,day);
                dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dailog.show();
            }
        });

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_j = year +"-"+month+"-"+dayOfMonth;
                Log.d("chk","date "+date_j);
                mdisplayDate.setText(date_j);
            }
        };


        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getRadioValues();
                if(date_j.equals("null")){
                    showAlertDailog();
                }
              else callToServer();

            }
        });
    }

    private void showAlertDailog() {
        Log.d("chk","Please Add the date feild ");
        AlertDialog.Builder alert = new AlertDialog.Builder(otherDetails.this);
        alert.setTitle("Alert");
        alert.setMessage("\nPlease Fill out All the feilds.\n");
        alert.setCancelable(true);
        alert.setPositiveButton("OK",null);
        alert.create().show();
    }

    private void callToServer(){
        race_j = this.race_x.getText().toString().trim();
        capacity_j = this.capacity_x.getText().toString().trim();
        ddnumber_j = this.ddnumber_x.getText().toString().trim();
        year_j = this.year.getText().toString().trim();
        other_j = this.year.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_OTHER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("chk","Gor Correct Respoce");




            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("chk ", " " +error.toString());
                    }
                })
        {
            //  Log.d("chk"," hmm ");

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("phone",phone);
                param.put("race",race_j);
                param.put("capacity",capacity_j);
                param.put("ddno",ddnumber_j);
                param.put("year",year_j);
                param.put("other",other_j);
                param.put("owner",radioValue);
                param.put("dd_date",date_j);
                Log.d("chk","getParam Successful");
                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getRadioValues() {
        int id = mRadioGroup.getCheckedRadioButtonId();
        mRadioButton= findViewById(id);
        radioValue = mRadioButton.getText().toString().trim();
        Log.d("chk","Radio Values :"+radioValue);
    }

    private void getValues() {
        Bundle extra = getIntent().getExtras();
        phone = extra.getString("phone");
        race_x = findViewById(R.id.dd_race_x);
        capacity_x = findViewById(R.id.capacity_xm);
        //date_x =findViewById(R.id.);
        ddnumber_x = findViewById(R.id.dd_number_x);
        year = findViewById(R.id.year_x);
        other = findViewById(R.id.other_x);
        next3 = findViewById(R.id.next3);
        mdisplayDate = (TextView) findViewById(R.id.textView8);
        mRadioGroup = findViewById(R.id.owner_radio);
        Log.d("chk","Got all the Values in getValues() " );
    }
}

