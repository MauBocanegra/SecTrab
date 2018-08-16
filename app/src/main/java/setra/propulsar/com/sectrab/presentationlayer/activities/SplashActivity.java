package setra.propulsar.com.sectrab.presentationlayer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.ws.WS;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        WS.getInstance(SplashActivity.this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName),0);
                if (sharedPreferences.getBoolean("loggedIn",false)){
                    Intent intent = new Intent(getApplicationContext(),MainNavigationActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },500);
    }
}
