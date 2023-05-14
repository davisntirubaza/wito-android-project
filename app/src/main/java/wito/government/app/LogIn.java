package wito.government.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wito.government.app.classes.DatabaseHelper;

import java.util.HashMap;

public class LogIn extends AppCompatActivity {

    EditText phoneField, passField;
    Button submitBtn, mgeniBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // set status bar color to red
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primaryColor));
        }

        phoneField = findViewById(R.id.editTextText);
        passField = findViewById(R.id.editTextText2);
        submitBtn = findViewById(R.id.button2);
        mgeniBtn = findViewById(R.id.button7);

        mgeniBtn.setOnClickListener(r -> startActivity(new Intent(getApplicationContext(), Register.class)));
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        HashMap<String, String> data = databaseHelper.getTableData("userInfo");
        String savedphone = data.get("phone");
        String savedpassword = data.get("password");

//        showToast("DATA = " + data);

        submitBtn.setOnClickListener(f -> {
            String phone = phoneField.getText().toString();
            String password = passField.getText().toString();

//            showToast("SAVED_PASSWORD = " + savedpassword + "\nSAVED_PHONE = " + savedphone);

            if (phone.isEmpty()) {
                showToast("Ingiza namba yako ya simu!");
                phoneField.requestFocus();
            } else if (password.isEmpty()) {
                showToast("Ingiza nenosiri!");
                passField.requestFocus();
            } else {
                if (phone.equals(savedphone) && password.equals(savedpassword)) {
                    showToast("Umefanikiwa kuingia!");
                    Intent enter = new Intent(getApplicationContext(), Home.class);
                    startActivity(enter);
                    finish();
                } else {
                    showToast("Namba ya simu au nenosiri limekosewa!");
                }

            }
        });
    }

    public void showToast(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
}