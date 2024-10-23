package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.NetworkChangeListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends BaseClass {

    ImageView ivLogo;
    TextView tvRegisterTitle, tvalreadyauserTitle;
    EditText etname,etmobileno,etemailid,etusername,etpassword;
    AppCompatButton btnRegister, btnSignInWithGoggle;

    ProgressDialog progressDialog;
    ProgressDialog progressDialog1 ;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    GoogleSignInOptions googleSignInOptions;// to show the user login accounts when we click on the sign in with goole option
    GoogleSignInClient googleSignInClient; // to store the selected google account (Selected by user )

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        getID();


        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //this above initialization will show the default sign in gamil options and then it will storre in the goole sign in option
        //and then this googlesign in options will give the email to the google sign in client

        //googleSignInOptions=>GoogleSignInClient=>GoogleAccount

        googleSignInClient = GoogleSignIn.getClient(RegistrationActivity.this,googleSignInOptions);




        btnSignInWithGoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog1 = new ProgressDialog(RegistrationActivity.this);
                progressDialog1.setTitle("Please Wait!");
                progressDialog1.setMessage("Logging to account");
                progressDialog1.setCanceledOnTouchOutside(true);
                progressDialog1.show();

                signInWithGoogle();

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etusername.getText().toString().isEmpty()){
                    etusername.setError("Enter Username");
                } else if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("Enter Password");
                } else if (!etemailid.getText().toString().contains("@") && !etemailid.getText().toString().contains(".com") &&etemailid.getText().toString().isEmpty()) {
                    etemailid.setError("Enter valid Email");
                } else if (etusername.getText().toString().length() < 8) {
                    etusername.setError("Your username should be > 8");
                } else if (etpassword.getText().toString().length() < 8) {
                    etpassword.setError("Your Password should be greater than 8");
                } else if (etmobileno.getText().toString().isEmpty()&& etmobileno.getText().toString().length()==10){
                    etmobileno.setError("Enter valid Mobile no");
                } else if (etname.getText().toString().isEmpty()) {
                    etname.setError("Enter Your name ");

                } else {
                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Please Wait!");
                    progressDialog.setMessage("Registration is in Process");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

//                    userRegisterDetails();


//                    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                        @Override
//                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                            progressDialog.dismiss();
//                            Toast.makeText(RegistrationActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onVerificationFailed(@NonNull FirebaseException e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(RegistrationActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onCodeSent(@NonNull String verificationCode,
//                                               @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                            progressDialog.dismiss();
//
//                            // Move to the OTP verification activity with the necessary data
//                            Intent intent = new Intent(RegistrationActivity.this, VerifyOtpActivity.class);
//                            intent.putExtra("verificationCode", verificationCode);
//                            intent.putExtra("name", etname.getText().toString());
//                            intent.putExtra("mobileno", etmobileno.getText().toString());
//                            intent.putExtra("emailid", etemailid.getText().toString());
//                            intent.putExtra("username", etusername.getText().toString());
//                            intent.putExtra("password", etpassword.getText().toString());
//                            startActivity(intent);
//                        }
//                    };
//
//                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//                            .setPhoneNumber("+91" + etmobileno.getText().toString()) // Phone number to verify
//                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                            .setActivity(RegistrationActivity.this) // Activity for callback binding
//                            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
//                            .build();
//
//                    PhoneAuthProvider.verifyPhoneNumber(options);

//
//                    PhoneAuthOptions.Builder builder =
//                            PhoneAuthOptions.newBuilder(mAuth)
//                                    .setPhoneNumber("+91 "+etmobileno.getText().toString())
//                                    .setTimeout(60L,TimeUnit.SECONDS)
//                                    .setActivity(RegistrationActivity.this)
//                                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                        @Override
//                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                            progressDialog.dismiss();
//                                            Toast.makeText(RegistrationActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                        @Override
//                                        public void onVerificationFailed(@NonNull FirebaseException e) {
//                                            progressDialog.dismiss();
//
//                                            Toast.makeText(RegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                        }
//
//                                        @Override
//                                        public void onCodeSent(@NonNull String verificationCode,
//                                                               @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//
//                                            Toast.makeText(RegistrationActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
//
//                                            Intent intent = new Intent(RegistrationActivity.this,VerifyOtpActivity.class);
//                                            intent.putExtra("verificationCode",verificationCode);
//                                            intent.putExtra("name",etname.getText().toString());
//                                            intent.putExtra("mobileno",etmobileno.getText().toString());
//                                            intent.putExtra("emailid",etemailid.getText().toString());
//                                            intent.putExtra("username",etusername.getText().toString());
//                                            intent.putExtra("password",etpassword.getText().toString());
//                                            startActivity(intent);
//
//                                        }
//                                    });
//

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91 " + etmobileno.getText().toString(),
                            60L,TimeUnit.SECONDS,RegistrationActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onCodeSent(@NonNull String verificationCode,
                                                       @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(RegistrationActivity.this,VerifyOtpActivity.class);
                                    intent.putExtra("verificationCode",verificationCode);
                                    intent.putExtra("name",etname.getText().toString());
                                    intent.putExtra("mobileno",etmobileno.getText().toString());
                                    intent.putExtra("emailid",etemailid.getText().toString());
                                    intent.putExtra("username",etusername.getText().toString());
                                    intent.putExtra("password",etpassword.getText().toString());
                                    startActivity(intent);
                                }
                    });




                }
            }
        });

        tvalreadyauserTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void getID() {
        ivLogo = findViewById(R.id.ivRegisterLogo);
        tvRegisterTitle = findViewById(R.id.tvRegisterTitle);
        tvalreadyauserTitle = findViewById(R.id.tvAlreadyAUser);
        etname = findViewById(R.id.etName);
        etmobileno = findViewById(R.id.etmobileno);
        etemailid = findViewById(R.id.etemailid);
        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etPass);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignInWithGoggle = findViewById(R.id.btnSignWithGoogle);
    }

    private void signInWithGoogle() {

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);//it will just store intent not open it



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent userEmail) {
        super.onActivityResult(requestCode, resultCode, userEmail);

        if (requestCode == 999){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(userEmail);

            try{
                task.getResult(ApiException.class);
                Intent intent = new Intent(RegistrationActivity.this,HomeActivity.class);
                startActivity(intent);
                progressDialog1.dismiss();
                finish();
            }catch (ApiException e){
                throw new RuntimeException(e);

            }




        }
    }
}