package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.NetworkChangeListner;
import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvTitle, tvNewUser;
    EditText etUserName, etPassword;
    AppCompatButton btnLogin;
    CheckBox cbShowPass;

    ProgressDialog progressDialog;

    TextView tvForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getID();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Please Wait!");
                progressDialog.setMessage("Login Under Process");
                progressDialog.show();

                userLogin();
            }


        });
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ConfirmRegiserMobilenoActivity.class);
                startActivity(intent);
            }
        });



    }

    private void getID() {
        ivLogo = findViewById(R.id.ivLoginLogo);
        tvTitle = findViewById(R.id.tvLoginTitle);
        tvNewUser = findViewById(R.id.tvNewUserTitle);
        etUserName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        cbShowPass = findViewById(R.id.cbLoginShowAndHidePassword);
        tvForgetPassword = findViewById(R.id.tv_forget_password);
    }

    private void userLogin() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etUserName.getText().toString());
        params.put("password",etPassword.getText().toString());


        client.post(Urls.loginUserURL , params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("Success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login Successful!!",Toast.LENGTH_SHORT).show();

//                        Toast.makeText(LoginActivity.this,"hello",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Wrong Credentials!",Toast.LENGTH_LONG).show();
//                        Intent intent1 = new Intent(LoginActivity.this,LoginActivity.class);
//                        startActivity(intent1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,"Server Error!!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                throwable.printStackTrace();
                Toast.makeText(LoginActivity.this, "Log in failed"+responseString, Toast.LENGTH_SHORT).show();
            }
        });


    }


}