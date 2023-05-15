package wito.government.app.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wito.government.app.Home;
import wito.government.app.R;
import wito.government.app.activities.ArticleViewer;
import wito.government.app.activities.About;
import wito.government.app.activities.ItemViewer;
import wito.government.app.activities.Settings;
import wito.government.app.classes.DatabaseHelper;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyHealthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View d = inflater.inflate(R.layout.fragment_wallet, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        HashMap<String, String> userInfo = databaseHelper.getTableData("userInfo");
        HashMap<String, String> walletInfo = databaseHelper.getTableData("wallet");

        TextView name, salioKuu, thamaniHisa, idadiMiadi, hisaKusubiri, jumlaKuu;
        name = d.findViewById(R.id.textView15);
        salioKuu = d.findViewById(R.id.textView47);
        thamaniHisa = d.findViewById(R.id.textView49);
        idadiMiadi = d.findViewById(R.id.textView51);
        hisaKusubiri = d.findViewById(R.id.textView53);
        jumlaKuu = d.findViewById(R.id.textView3);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/" + Home.phone);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, String> data = new HashMap<>();

                    for (DataSnapshot row : snapshot.getChildren()) {
                        data.put(row.getKey(), row.getValue().toString());
                    }

                    name.setText(data.get("firstName") + " " + data.get("lastName"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference walletRef = dbRef.child("wallet");
        walletRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, String> data = new HashMap<>();
                    for (DataSnapshot row : snapshot.getChildren()) {
                        data.put(row.getKey(), row.getValue().toString());
                    }

                    int nSalioKuu = Integer.parseInt(data.get("salioKuu"));
                    int nThamaniHisa = Integer.parseInt((data.get("thamaniHisa")));
                    int nIdadiMiadi = Integer.parseInt(data.get("idadiMiadi"));
                    int nHisaKusubiri = Integer.parseInt(data.get("hisaKusubiri"));

                    salioKuu.setText(nSalioKuu + "");
                    thamaniHisa.setText(nThamaniHisa + "");
                    idadiMiadi.setText(nIdadiMiadi + "");
                    hisaKusubiri.setText(nHisaKusubiri + "");

                    int nJumlaKuu = nSalioKuu + nThamaniHisa + nHisaKusubiri;
                    jumlaKuu.setText(nJumlaKuu + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        
        name.setText(userInfo.get("firstName") + " " + userInfo.get("lastName"));


        Button nunuaHisaBtn, uzaHisaBtn, ongezaSalioBtn, tumaSalioBtn;
        nunuaHisaBtn = d.findViewById(R.id.button8);
        uzaHisaBtn = d.findViewById(R.id.button9);
        ongezaSalioBtn = d.findViewById(R.id.button10);
        tumaSalioBtn = d.findViewById(R.id.button11);

        View.OnClickListener listener = view -> doTransacton();
        nunuaHisaBtn.setOnClickListener(listener);
        uzaHisaBtn.setOnClickListener(listener);
        ongezaSalioBtn.setOnClickListener(listener);
        tumaSalioBtn.setOnClickListener(listener);

        return d;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void doTransacton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Nunua hisa");

        View view = getLayoutInflater().inflate(R.layout.dialog_transaction, null);
        builder.setView(view);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText edt_kiasi = view.findViewById(R.id.edt_kiasi_trans);
        EditText edt_neno_siri = view.findViewById(R.id.edt_neno_siri_trans);
        TextView cancel_button = view.findViewById(R.id.cancel_button_trans);

        Button btnTransation = view.findViewById(R.id.btn_trans);

        final AlertDialog dialog = builder.create();

        btnTransation.setOnClickListener(view1 -> {
            if (edt_kiasi.getText().toString().isEmpty()) {
                edt_kiasi.setError("Tafadhali weka kiasi!");
                edt_kiasi.requestFocus();
            } else if (edt_neno_siri.getText().toString().trim().isEmpty()) {
                edt_neno_siri.setError("Tafadhali ingiza neno siri!");
                edt_neno_siri.requestFocus();
            } else {
                Toast.makeText(context, "Muamala wako unashughulikiwa!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        cancel_button.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();
    }

    public void showToast(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
}