package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class ForgetPasswordVerifyOTPActivity extends BaseClass {

    TextView TvMobileNo,TvResendOTP;
    AppCompatButton AppCompatBtnVerify;
    EditText EtOTP1,EtOTP2,EtOTP3,EtOTP4,EtOTP5,EtOTP6;

    ProgressDialog progressDialog;

    private String strVerificationCode,strMobileNo;

    String OTPCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_verify_otpactivity);

        TvMobileNo = findViewById(R.id.verify_otp_mobileno_TV);
        TvResendOTP = findViewById(R.id.tv_resend_otp);
        AppCompatBtnVerify = findViewById(R.id.material_btn_verify);

        EtOTP1 = findViewById(R.id.et_otp_1);
        EtOTP2 = findViewById(R.id.et_otp_2);
        EtOTP3 = findViewById(R.id.et_otp_3);
        EtOTP4 = findViewById(R.id.et_otp_4);
        EtOTP5 = findViewById(R.id.et_otp_5);
        EtOTP6 = findViewById(R.id.et_otp_6);

        setUpInputOTP();

        strVerificationCode = getIntent().getStringExtra("verificationCode");
        strMobileNo = getIntent().getStringExtra("mobileno");

        TvMobileNo.setText(strMobileNo);

        AppCompatBtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( EtOTP1.getText().toString().trim().isEmpty()
                        || EtOTP2.getText().toString().trim().isEmpty()
                        || EtOTP3.getText().toString().trim().isEmpty()
                        || EtOTP4.getText().toString().trim().isEmpty()
                        || EtOTP5.getText().toString().trim().isEmpty()
                        || EtOTP6.getText().toString().trim().isEmpty() ){

                    Toast.makeText(ForgetPasswordVerifyOTPActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();

                }

                OTPCode = EtOTP1.getText().toString() + EtOTP2.getText().toString()
                        + EtOTP3.getText().toString() + EtOTP4.getText().toString()
                        + EtOTP5.getText().toString() + EtOTP6.getText().toString();

                if(strVerificationCode!=null){
                    progressDialog = new ProgressDialog(ForgetPasswordVerifyOTPActivity.this);
                    progressDialog.setTitle("Verifying OTP");
                    progressDialog.setMessage("Please Wait!");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(strVerificationCode,OTPCode);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                progressDialog.dismiss();
                                Intent intent = new Intent(ForgetPasswordVerifyOTPActivity.this,SetUpNewPasswordActivity.class);
                                intent.putExtra("mobileno",strMobileNo);
                                startActivity(intent);



                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(ForgetPasswordVerifyOTPActivity.this, "OTP Verification Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        TvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91 " + strMobileNo,
                        60L, TimeUnit.SECONDS,ForgetPasswordVerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                Toast.makeText(ForgetPasswordVerifyOTPActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(ForgetPasswordVerifyOTPActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String newVerificationCode,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                strVerificationCode = newVerificationCode;

                            }
                        });
            }
        });



    }



    private void setUpInputOTP() {
        EtOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    EtOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EtOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    EtOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EtOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    EtOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EtOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    EtOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EtOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    EtOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}