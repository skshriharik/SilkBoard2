package com.example.shrihari.silkboard2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PersonalDetails extends AppCompatActivity {
    private EditText fullName , fatherName , landLine ,addr_doorno_x, area , taluk , distrit_x ;
    private Button next ;
    private String phone ;
    private static final String URL_PAGE = "http://192.168.1.100/back/one.php";
    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        getValues();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

    }

    private void nextIntent() {
        Intent intent =  new Intent(PersonalDetails.this, landDetails.class);
        intent.putExtra("phone" ,phone);
        startActivity(intent);
    }

    private void sendData() {
        final String first_name , middle_name , land_line ,addr_doorno ,addr_area , addr_taluk ,district ,state ;
        first_name = this.fullName.getText().toString().trim();
        middle_name = this.fatherName.getText().toString().trim();
        land_line = this.landLine.getText().toString().trim();
        addr_area = (PersonalDetails.this).area.getText().toString().trim();
        addr_taluk = this.taluk.getText().toString().trim();
        district = this.distrit_x.getText().toString().trim();
        addr_doorno = this.addr_doorno_x.getText().toString().trim();
        state = "Karnataka";
        if(first_name.equals("") || addr_area.equals("") || addr_taluk.equals("") || addr_doorno.equals("")){
            Log.d("chk","Wrong input");
            AlertDialog.Builder alert = new AlertDialog.Builder(PersonalDetails.this);
            alert.setTitle("Alert");
            alert.setMessage("\nPlease Fill All the nessary Details.\n");
            alert.setCancelable(true);
            alert.setPositiveButton("OK",null);
            alert.create().show();
        }else {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PAGE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("success");
                        if (result.equals("1")) {
                            Log.d("chk", "DataBase Updated");
                            Log.d("chk", "responce :" + jsonObject.getString("message"));
                            Toast.makeText(PersonalDetails.this, "Message:" + jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            nextIntent();


                        } else {
                            Log.d("chk", "responce :" + jsonObject.getString("message"));
                            Toast.makeText(PersonalDetails.this, "Responce:" + jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("chk", "JSON Excepton " + e.toString());
                        Toast.makeText(PersonalDetails.this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d("chk", "Volley Error" + error.toString());

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("phone", phone);
                    param.put("first_name", first_name);
                    param.put("middle_name", middle_name);
                    param.put("land_line", land_line);
                    param.put("addr_doorno", addr_doorno);
                    param.put("addr_area", addr_area);
                    param.put("addr_taluk", addr_taluk);
                    param.put("district", district);
                    param.put("state", state);
                    return param;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    private void getValues() {
        fullName = findViewById(R.id.name_x);
        fatherName = findViewById(R.id.fname_x);
        landLine = findViewById(R.id.landline_x);
        area = findViewById(R.id.area_x);
        taluk = findViewById(R.id.taluk_x);
        distrit_x = findViewById(R.id.dist_x);
        addr_doorno_x = findViewById(R.id.doorno_x);
        next = findViewById(R.id.next_1);
        Bundle extra = getIntent().getExtras();
        phone = extra.getString("phone");
        Log.d("chk"," "+phone);

    }
}

