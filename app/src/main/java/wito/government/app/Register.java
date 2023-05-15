package wito.government.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import wito.government.app.classes.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText fnameField, lnameField, phoneField, passField, confirmpassfield, nidaField;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // set status bar color to red
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primaryColor));
        }

        fnameField = findViewById(R.id.editTextText3);
        lnameField = findViewById(R.id.editTextText7);
        phoneField = findViewById(R.id.editTextText6);
        passField = findViewById(R.id.editTextText4);
        nidaField = findViewById(R.id.editTextText8);
        confirmpassfield = findViewById(R.id.editTextText9);
        registerBtn = findViewById(R.id.button3);


        registerBtn.setOnClickListener(r -> {
            String fname, lname, phone, password, cpassword, nida;
            fname = fnameField.getText().toString();
            lname = lnameField.getText().toString();
            phone = phoneField.getText().toString();
            password = passField.getText().toString();
            cpassword = confirmpassfield.getText().toString();
            nida = nidaField.getText().toString();

            if (fname.isEmpty() || lname.isEmpty() || phone.isEmpty() || password.isEmpty() || nida.isEmpty()) {
                showToast("Tafadhali jaza kwenye nafasi zote!");
            } else if (!cpassword.equals(password)) {
                showToast("Nenosiri hazifanani!");
                passField.requestFocus();
            } else if (nida.length() != 20) {
                showToast("Namba ya NIDA si sahihi!");
                nidaField.requestFocus();
            }else {
//                showToast("Tafadhali subiri...");

                HashMap<String, Integer> wallet = new HashMap<>();
                wallet.put("salioKuu", 0);
                wallet.put("thamaniHisa", 0);
                wallet.put("idadiMiadi", 0);
                wallet.put("hisaKusubiri", 0);

                String userId = System.currentTimeMillis() + "";
                HashMap<String,String> cv = new HashMap<>();
                cv.put("firstName", fname);
                cv.put("lastName", lname);
                cv.put("phone", phone);
                cv.put("password", password);
                cv.put("nida", nida);
//                cv.put("id", userId);

                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users/" + phone);
                userReference.setValue(cv).addOnSuccessListener(unused -> {
                    userReference.child("wallet").setValue(wallet);
                    showToast("Registered successfully!");
                    Intent goHome = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(goHome);
                    finish();
                }).addOnFailureListener(e -> {
                    showToast("ERROR:" + e.getMessage());
                });
            }
        });
    }

    public void showToast(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
}