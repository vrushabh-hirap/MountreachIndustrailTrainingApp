package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SetUpNewPasswordActivity extends BaseClass {

    String strMobileno;

    EditText etNewPass,etConfirmPass;

    AppCompatButton acbtnSetUpPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_up_new_password);

        etNewPass = findViewById(R.id.et_new_password);
        etConfirmPass = findViewById(R.id.et_new_confirm_password);
        acbtnSetUpPassword = findViewById(R.id.btn_set_new_password);

        strMobileno = getIntent().getStringExtra("mobileno");


        acbtnSetUpPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNewPass.getText().toString().isEmpty() || etConfirmPass.getText().toString().isEmpty()){
                    Toast.makeText(SetUpNewPasswordActivity.this, "Please Enter new or confirm password", Toast.LENGTH_SHORT).show();
                } else if (!etNewPass.getText().toString().equals(etConfirmPass.getText().toString())) {

                    etConfirmPass.setError("Pass does not match");
                    Toast.makeText(SetUpNewPasswordActivity.this, "new password and confirm password must be same ", Toast.LENGTH_SHORT).show();

                }else{
                    progressDialog = new ProgressDialog(SetUpNewPasswordActivity.this);
                    progressDialog.setTitle("Updating password");
                    progressDialog.setMessage("Please wait!");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    forgetPassword();
                }

            }
        });
    }

    private void forgetPassword() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("mobileno",strMobileno);
        params.put("password",etConfirmPass.getText().toString());

        client.post(Urls.forgetPasswordUserURL,params,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        Toast.makeText(SetUpNewPasswordActivity.this, "in the forget pass method ", Toast.LENGTH_SHORT).show();

                        try {
                            String status = response.getString("Success");
                            if (status.equals("1")){
                                Toast.makeText(SetUpNewPasswordActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SetUpNewPasswordActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SetUpNewPasswordActivity.this, "Password is not changed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(SetUpNewPasswordActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}