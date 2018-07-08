package com.example.shrihari.silkboard2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class PhoneNumber extends AppCompatActivity {

    private EditText ph_number ;
    private String server_otp ;
    private static String URL_OTP = "http://192.168.1.100/back/submitphone1.php";
    Button btn_otp ;
    String ph ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        Log.d("chk","In MAIN");
        ph_number = findViewById(R.id.loginPhone);
        btn_otp = findViewById(R.id.btn_otp);
        btn_otp.setEnabled(true);
       // ph_number.addTextChangedListener(loginTextWatcher);

        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("chk","OTP Button Clicked");
                ph = ph_number.getText().toString().trim();
                Log.d("chk","length :"+ph.length());
                if(ph.length() == 10){

                    Log.d("chk","Correct Number");
                    gen_otp();
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {
//                        Log.d("chk","Try Error:"+e.toString());
//                        e.printStackTrace();
//                    }


                }else {
                    Log.d("chk","Wrong Phone Number");
                    AlertDialog.Builder alert = new AlertDialog.Builder(PhoneNumber.this);
                    alert.setTitle("Error!");
                    alert.setMessage("Wrong Phone Number \n\nPlease Re Enter Your Number .");
                    alert.setCancelable(true);
                    alert.setPositiveButton("OK",null);
                    alert.create().show();

                }

            }
        });


    }

    protected void gen_otp() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    server_otp = jsonObject.getString("otp");
                    Toast.makeText(PhoneNumber.this , "OTP Recived "+server_otp,Toast.LENGTH_LONG).show();
                    Log.d("chk","JSON Recived "+server_otp);
                    nextIntent();


                } catch (JSONException e) {
                    Toast.makeText(PhoneNumber.this , "Error: "+e.toString() ,Toast.LENGTH_LONG).show();
                    Log.d("chk","Error----  "+e.toString());
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhoneNumber.this , "Error: "+error.toString() ,Toast.LENGTH_LONG).show();
                        Log.d("chk","Error->* "+error.toString());

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String ,String> param = new HashMap<>();
                param.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                param.put("phone" , ph );
                Log.d("chk","Hash Successful: "+ph);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void nextIntent() {
        Intent intent = new Intent(PhoneNumber.this , verifyOtp2.class);
        intent.putExtra("phoneNumber",ph);
        intent.putExtra("serverotp",server_otp);
        startActivity(intent);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!ph.isEmpty())
                btn_otp.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
