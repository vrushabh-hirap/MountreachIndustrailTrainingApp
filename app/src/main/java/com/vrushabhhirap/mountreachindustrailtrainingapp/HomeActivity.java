package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.TintableCheckedTextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.NetworkChangeListner;
import com.vrushabhhirap.mountreachindustrailtrainingapp.Common.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends BaseClass implements BottomNavigationView.OnNavigationItemSelectedListener{


    TextView tvName,tvEmail;
    AppCompatButton btnSignOut;

    String strName,strMobileNo,strEmailId,strUsername,strPassword;


    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();

        bottomNavigationView = findViewById(R.id.bototm_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.home_bottom_navigation_home);
//
//        tvName = findViewById(R.id.tv_name);
//        tvEmail = findViewById(R.id.tv_email);
//
//        btnSignOut = findViewById(R.id.btnSignOut);
//
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(HomeActivity.this,googleSignInOptions);
//
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
//

        if (googleSignInAccount != null){
            strName = googleSignInAccount.getDisplayName();
            strEmailId = googleSignInAccount.getEmail();
            strMobileNo = "";
            strUsername = googleSignInAccount.getGivenName();
            strPassword = "";

            editor.putString("username",strUsername);
            editor.putString("password","");

            userRegisterDetails();

        }




    }

    HomeFragment homeFragment = new HomeFragment();
    CategoriesFragment categoriesFragment = new CategoriesFragment();
    MyOrdersFragment myOrdersFragment =  new MyOrdersFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.home_bottom_navigation_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout,homeFragment).commit();
        } else if (item.getItemId() == R.id.home_bottom_navigation_Categories)  {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout,categoriesFragment).commit();
        } else if (item.getItemId() == R.id.home_bottom_navigation_MyOrders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout,myOrdersFragment).commit();
        }
        return true;
    }

    private void userRegisterDetails(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",strName);
        params.put("mobileno",strMobileNo);
        params.put("emailid",strEmailId);
        params.put("username",strUsername);
        params.put("password",strPassword);

        client.post(Urls.registerUserURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("Success");
                    if (status.equals("1")){

                        Toast.makeText(HomeActivity.this,"Registration Successful!!",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(HomeActivity.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(HomeActivity.this,RegistrationActivity.class);
                startActivity(intent1);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.home_menu_my_offers){

        }else if(item.getItemId() == R.id.home_menu_my_cart){

        }else if(item.getItemId() == R.id.home_menu_my_profile){

//            startActivity(new Intent(HomeActivity.this,MyProfileActivity.class));
            Toast.makeText(this, "calling profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this,MyProfileActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Profile called", Toast.LENGTH_SHORT).show();

        }
        return true;
    }
}