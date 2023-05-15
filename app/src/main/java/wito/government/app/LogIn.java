package wito.government.app;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wito.government.app.classes.DatabaseHelper;

import java.util.ArrayList;
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
        /*
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        HashMap<String, String> data = databaseHelper.getTableData("userInfo");
        String savedphone = data.get("phone");
        String savedpassword = data.get("password");

        showToast("DATA = " + data);
*/
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<HashMap<String, String>> users = new ArrayList<>();
                    for (DataSnapshot user : snapshot.getChildren()) {
                        HashMap<String, String> userData = new HashMap<>();
                        for (DataSnapshot rows : user.getChildren()) {
                            String k = rows.getKey(), v = rows.getValue().toString();
                            userData.put(k, v);
                        }
                        users.add(userData);
                    }

//                    showToast("DATA_SIZE = " + users.size());

                    submitBtn.setOnClickListener(f -> {
                        String phone = phoneField.getText().toString();
                        String password = passField.getText().toString();

                        if (phone.isEmpty()) {
                            phoneField.setError("Ingiza namba yako ya simu!");
                            phoneField.requestFocus();
                        } else if (password.isEmpty()) {
                            passField.setError("Ingiza nenosiri!");
                            passField.requestFocus();
                        } else {
                            if (authenticateUser(phone, password, users)) {
//                                showToast("Umefanikiwa kuingia!");
                                Intent enter = new Intent(getApplicationContext(), Home.class);
                                enter.putExtra("phone", phone);
                                startActivity(enter);
                                finish();
                            } else {
                                showToast("Namba ya simu au nenosiri limekosewa!");
                            }

                        }
                    });
                }
            }

            public boolean authenticateUser(String phone, String password, ArrayList <HashMap<String,String>> data) {
                for (HashMap<String,String> user : data) {
                    if (phone.equals(user.get("phone"))) {
                        String savedPassword = user.get("password");
                        if (savedPassword.equals(password)) return true;
                    }
                }
                return false;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void showToast(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
}