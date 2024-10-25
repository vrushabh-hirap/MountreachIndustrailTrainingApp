package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MyProfileActivity extends AppCompatActivity {

    AppCompatButton btnSignOut, btnEditPhoto;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    String strUsername, password;

    ProgressDialog progressDialog;

    TextView tvMyProfileName, tvMyProfileEmail, tvMyProfileUsername, tvMyProfileMobileNo;
    ImageView ivMyProfilePhoto;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        preferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
        editor = preferences.edit();
        btnSignOut = findViewById(R.id.btnSignOut);

        strUsername = preferences.getString("username",null);
        tvMyProfileName = findViewById(R.id.tv_my_profile_my_name);
        tvMyProfileEmail = findViewById(R.id.tv_my_profile_my_email);
        tvMyProfileUsername = findViewById(R.id.tv_my_profile_my_username);
        Toast.makeText(this, "in the profile lalalalalallalall", Toast.LENGTH_SHORT).show();
        tvMyProfileMobileNo = findViewById(R.id.tv_my_profile_my_mobile_no);
        btnEditPhoto = findViewById(R.id.acbtn_my_profile_edit);

        boolean a = preferences.getBoolean("isGoogleSignedIn",false);
        if (a){
            googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            googleSignInClient = GoogleSignIn.getClient(MyProfileActivity.this,googleSignInOptions);
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

            if (googleSignInAccount!= null){
                btnSignOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(MyProfileActivity.this,LoginActivity.class);
                                startActivity(intent);
                                editor.putString("username",null);
                                editor.putString("password",null);


                                tvMyProfileName.setText(null);
                                tvMyProfileEmail.setText(null);
                                tvMyProfileMobileNo.setText(null);
                                tvMyProfileUsername.setText(null);

                                finish();
                            }
                        });

                    }
                });
            }


        } else if (a == false) {

            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyProfileActivity.this,LoginActivity.class);
                    startActivity(intent);
                    editor.putString("username",null);
                    editor.putString("password",null);

                    tvMyProfileName.setText(null);
                    tvMyProfileEmail.setText(null);
                    tvMyProfileMobileNo.setText(null);
                    tvMyProfileUsername.setText(null);
                    finish();
                }
            });

        }


    }

    @Override
    protected void onStart() {

        Toast.makeText(this, "in onstart", Toast.LENGTH_SHORT).show();
        super.onStart();
        System.out.println("hello");
        progressDialog = new ProgressDialog(MyProfileActivity.this);
        progressDialog.setTitle("My Profile");
        progressDialog.setMessage("Please Wait ! Data Fetching");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        getMyDetails();
    }

    private void getMyDetails() {
        Toast.makeText(this, "in getMyDetails", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strUsername);

        client.post(Urls.myDetailsURL,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("getMyDetails");

                    Toast.makeText(MyProfileActivity.this, "get my details success", Toast.LENGTH_SHORT).show();
                    for(int i =0; i < jsonArray.length() ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("emailed");
                        String mobileNo = jsonObject.getString("mobileno");
                        String username = jsonObject.getString("username");
                        String password = jsonObject.getString("password");


                        tvMyProfileName.setText(name);
                        tvMyProfileEmail.setText(email);
                        tvMyProfileMobileNo.setText(mobileNo);
                        tvMyProfileUsername.setText(username);

                        progressDialog.dismiss();


                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Toast.makeText(MyProfileActivity.this, "get my detaisl on fail", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Toast.makeText(MyProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}















