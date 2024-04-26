package com.example.yahoowallet.activities;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.yahoowallet.R;

public class MainActivity extends BaseActivity {

    private static int SPLASH_SCREEN_DELAY = 3000;

    Animation topAnim, bottomAnim;
    ImageView fullLogo, blob1, blob2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        fullLogo = findViewById(R.id.full_logo);
        blob1 = findViewById(R.id.blob_1);
        blob2 = findViewById(R.id.blob_2);

        fullLogo.setAnimation(topAnim);
        blob1.setAnimation(bottomAnim);
        blob2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}