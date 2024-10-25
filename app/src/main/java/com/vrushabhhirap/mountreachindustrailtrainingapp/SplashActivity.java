package com.vrushabhhirap.mountreachindustrailtrainingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

public class SplashActivity extends BaseClass {
    ImageView ivLogo;
    TextView tvTitle;
    Animation animTranslateForLogo;
    Animation animTranslateForTextView;

    Handler handler;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivLogo = findViewById(R.id.ivSplashLogo);
        tvTitle = findViewById(R.id.tvSplashTitle);

        preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        editor = preferences.edit();
        animTranslateForLogo = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.top_to_bottom_translate);
        ivLogo.startAnimation(animTranslateForLogo);

        animTranslateForTextView = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_to_top_translate);
        tvTitle.startAnimation(animTranslateForTextView);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String username = preferences.getString("username",null);
                String password = preferences.getString("password",null);
                if ((username != null)){
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    editor.putBoolean("isGoogleSignedIn",false).commit();
                    finish();
                }else{
                    Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        },3000);




    }
}