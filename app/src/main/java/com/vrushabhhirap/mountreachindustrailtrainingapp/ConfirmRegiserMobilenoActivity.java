package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ConfirmRegiserMobilenoActivity extends BaseClass {

    EditText confirmMobileno;
    AppCompatButton acbtnVerify;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_regiser_mobileno);

        confirmMobileno = findViewById(R.id.etConfirmRegisterMobileno);
        acbtnVerify = findViewById(R.id.btn_verify_mobile_no_for_set_new_password);

        acbtnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmMobileno.getText().toString().isEmpty()){
                    confirmMobileno.setError("Please enter valid no");
                } else if (confirmMobileno.getText().toString().length()!=10) {
                    confirmMobileno.setError("Please enter valid no");
                }else {


                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91 " + confirmMobileno.getText().toString(),
                            60L, TimeUnit.SECONDS,ConfirmRegiserMobilenoActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ConfirmRegiserMobilenoActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ConfirmRegiserMobilenoActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onCodeSent(@NonNull String verificationCode,
                                                       @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(ConfirmRegiserMobilenoActivity.this,ForgetPasswordVerifyOTPActivity.class);
                                    intent.putExtra("verificationCode",verificationCode);
                                    intent.putExtra("mobileno",confirmMobileno.getText().toString());
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
    }
}