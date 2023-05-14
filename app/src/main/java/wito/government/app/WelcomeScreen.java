package wito.government.app;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;



public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);

        // set status bar color to red
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primaryColor));
        }

        Button go = findViewById(R.id.button);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button reg = findViewById(R.id.button14);

        go.setOnClickListener(g -> {
            startActivity(new Intent(getApplicationContext(), LogIn.class));
            finish();
        });

        reg.setOnClickListener(goo -> {
            startActivity(new Intent(getApplicationContext(), Register.class));
        });
    }
}