package com.example.shrihari.silkboard2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class landDetails extends AppCompatActivity {
    private CheckBox check1 , check2 , check3 , check4 , check5 ;
    private RadioGroup radio1 ,radio2 ,radio3 ,radio4 ;
    private RadioButton radiob1 ,radiob2 ,radiob3 ,radiob4 ;
    private String appType , hostPlant1 ,rearHouse , hostPlant2 ,s_sector ;
    private static String URL_PAGE2 = "http://192.168.1.100/back/two.php";
    private Button next2 ;
    private String phone ;
    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);

        getValues();
        onButtonClicked();


    }

    private void nextIntent() {
        Intent newintent = new Intent(landDetails.this , otherDetails.class);
        newintent.putExtra("phone",phone);
        startActivity(newintent);
    }


    private void onButtonClicked() {
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckBoxValues();
                getAppTypeValues();
                getHostPlant1Values();
                getHostPlant2Values();
                getHouseValue();
                callToServer();
            }
        });
    }



    private void callToServer() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PAGE2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String ret = jsonObject.getString("success");
                    Log.d("chk","Got Respoce :"+ret);

                    if(ret.equals("1")){
                        Toast.makeText(landDetails.this ,"Database Upodated ", Toast.LENGTH_LONG).show();
                        nextIntent();
                    }

                } catch (JSONException e) {
                    Log.d("chk",""+e.toString());
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(landDetails.this ," "+ error.toString() , Toast.LENGTH_SHORT);
                Log.d("chk","Got Error "+error.toString());

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> param = new HashMap<>();
                param.put("phone" , phone);
                param.put("sector",s_sector);
                param.put("app",appType);
                param.put("rear",rearHouse);
                param.put("host1",hostPlant1);
                param.put("host2",hostPlant2);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    private void showDailog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(landDetails.this);
        alert.setTitle("Alert!");
        alert.setMessage("\nPlease Fill all the details  \n");
        alert.setCancelable(true);
        alert.create().show();

    }

    private void getHostPlant2Values() {
        int appTypeSel = radio3.getCheckedRadioButtonId();
        radiob3 = findViewById(appTypeSel);
        hostPlant2 = radiob3.getText().toString();
        Log.d("chk","Host Plant 2 "+hostPlant2);

    }

    private void getHostPlant1Values() {
        int appTypeSel = radio2.getCheckedRadioButtonId();
        radiob2 = findViewById(appTypeSel);
        hostPlant1 = radiob2.getText().toString();
        Log.d("chk","Host Plant 1:"+hostPlant1);

    }
    private void getHouseValue() {
        int appTypeSel = radio4.getCheckedRadioButtonId();
        radiob4 = findViewById(appTypeSel);
        rearHouse = radiob4.getText().toString();
        Log.d("chk","appType Selected "+rearHouse);
    }
    private void getAppTypeValues() {
        int appTypeSel = radio1.getCheckedRadioButtonId();
        radiob1 = findViewById(appTypeSel);
        appType = radiob1.getText().toString();
        if(appType.equals(""))
            Log.d("chk","Sleect app type ");
        Log.d("chk","appType Selected "+appType);
    }

    private void getCheckBoxValues() {

        StringBuffer sector = new StringBuffer();
        StringBuffer empty = new StringBuffer("");

        Log.d("chk","hmm : "+sector);
        if(check1.isChecked())
            sector.append("mulberry ,");
        if(check2.isChecked())
            sector.append("oalk tasar ,");
        if(check3.isChecked())
            sector.append("eri ,");
        if(check4.isChecked())
            sector.append("muga ,");
        if(check5.isChecked())
            sector.append("tasar ,");

        if(sector.toString().equals(empty.toString())){
            AlertDialog.Builder alert = new AlertDialog.Builder(landDetails.this);
            alert.setTitle("Alert!");
            alert.setMessage("\nPlease Select Application Type \n");
            alert.setCancelable(true);
            alert.create().show();
        }
        s_sector = sector.toString();
        Log.d("chk", "checked List :"+s_sector );
    }

    private void getValues() {
        Bundle extra = getIntent().getExtras();
        phone = extra.getString("phone");
        check1 =  findViewById(R.id.mulberry);
        check2 = findViewById(R.id.oalk);
        check4 = findViewById(R.id.muga);
        check3 = findViewById(R.id.eri);
        check5 = findViewById(R.id.tasar);
        next2 = findViewById(R.id.next2);
        radio1 = findViewById(R.id.app_type_r);
        radio2 = findViewById(R.id.host_plant_r);
        radio3 = findViewById(R.id.last_r);
        radio4 = findViewById(R.id.seprg);


    }

}