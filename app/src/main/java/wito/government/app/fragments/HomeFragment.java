package wito.government.app.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import wito.government.app.R;
import wito.government.app.adapters.FeaturedAdapter;
import wito.government.app.adapters.YaliyojiriAdapter;
import wito.government.app.classes.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;


public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    Context context;
    RecyclerView featuredList, yaliyojiriList, miradiList, fedhaList;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View d = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();

        featuredList = d.findViewById(R.id.featuredRecycler);
        yaliyojiriList = d.findViewById(R.id.yaliyojiriRecycler);
        miradiList = d.findViewById(R.id.miradiRecycler);
        fedhaList = d.findViewById(R.id.fedhaRecycler);

        progressBar = d.findViewById(R.id.progressBar2);

        featuredList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        yaliyojiriList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        miradiList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        fedhaList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));

        DatabaseReference featuredRef = FirebaseDatabase.getInstance().getReference("featured");
        DatabaseReference yaliyojiriRef = FirebaseDatabase.getInstance().getReference("yaliyojiri");
        DatabaseReference miradiRef = FirebaseDatabase.getInstance().getReference("miradi");
        DatabaseReference fedhaRef = FirebaseDatabase.getInstance().getReference("fedha");

        TextView featured, yaliyojiri, miradi;
        featured = d.findViewById(R.id.textView5);
        yaliyojiri = d.findViewById(R.id.textView9);
        miradi = d.findViewById(R.id.textView17);

        featuredRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        yaliyojiriRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<HashMap<String, String>> data = new ArrayList<>();
                    for (DataSnapshot items : snapshot.getChildren()) {
                        HashMap<String, String> itemData = new HashMap<>();
                        for (DataSnapshot rows : items.getChildren()) {
                            itemData.put(rows.getKey(), rows.getValue().toString());
                        }
                        data.add(itemData);
                    }
                    YaliyojiriAdapter adapter = new YaliyojiriAdapter(context, data);
                    yaliyojiriList.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        miradiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<HashMap<String, String>> data = new ArrayList<>();
                    ArrayList<HashMap<String, String>> data2 = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        HashMap<String, String> itemData = new HashMap<>();
                        for (DataSnapshot row : item.getChildren()) {
                            if ("picha".equals(row.getKey())) {
                                ArrayList <String> picha = (ArrayList<String>) row.getValue();
                                picha.remove(0);
                                Collections.reverse(picha);
                                itemData.put(row.getKey(), picha.toString());
                            }
                            else itemData.put(row.getKey(), row.getValue().toString());
                        }
                        data.add(itemData);
                        data2.add(itemData);
                    }

                    FeaturedAdapter adapter = new FeaturedAdapter(context, data);
                    featuredList.setAdapter(adapter);
                    Collections.reverse(data2);
                    FeaturedAdapter adapter2 = new FeaturedAdapter(context, data2);
                    miradiList.setAdapter(adapter2);

                    progressBar.setVisibility(View.INVISIBLE);
                    featured.setVisibility(View.VISIBLE);
                    yaliyojiri.setVisibility(View.VISIBLE);
                    miradi.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fedhaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return d;
    }
}