package com.example.shrihari.silkboard2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class verifyOtp2 extends AppCompatActivity {

    protected EditText user_otp  ;
    private String user_otp_string ;
    private String  server_otp ,ph="" ;
    private static String URL_OTP = "http://192.168.1.100/back/submitphone1.php";
    private Button btn_submit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp2);

        getValues();
        onSubmitClick();


        //  checkOTP();
//        Log.d("chk","ph_no:\n"+ph);
//        Log.d("chk","Intent 2 otp "+ server_otp);

    }

    private void nextIntent() {
        Intent intent = new Intent(verifyOtp2.this , PersonalDetails.class);
        intent.putExtra("phone",ph);
        startActivity(intent);
    }

    private void onSubmitClick() {

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserOtp();
                checkOTP();
            }
        });

    }

    private void getUserOtp() {
        user_otp_string = user_otp.getText().toString().trim();
        Log.d("chk","user OTP "+user_otp_string);

    }

    private void getValues() {
        Bundle extras = getIntent().getExtras();
        ph = extras.getString("phoneNumber");
        server_otp = extras.getString("serverotp");
        btn_submit = findViewById(R.id.otpSubmit);
        user_otp = findViewById(R.id.user_otp_x);


    }

    private void  checkOTP(){

        if (user_otp_string.equals(server_otp)) {
            Log.d("chk", "Correct OTP "+user_otp_string);
            Toast.makeText(verifyOtp2.this, "Correct OTP "+user_otp_string, Toast.LENGTH_SHORT).show();
            nextIntent();

        }
        else {
            Log.d("chk","Wrong OTP "+user_otp_string);
            Toast.makeText(verifyOtp2.this , "Wrong OTP "+user_otp_string,Toast.LENGTH_SHORT).show();
        }
    }
}
