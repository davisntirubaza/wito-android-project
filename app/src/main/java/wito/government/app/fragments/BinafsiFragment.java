package wito.government.app.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import wito.government.app.R;
import wito.government.app.adapters.MiradiAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BinafsiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BinafsiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;

    public BinafsiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingAppointmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BinafsiFragment newInstance(String param1, String param2) {
        BinafsiFragment fragment = new BinafsiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View d = inflater.inflate(R.layout.fragment_biashara_binafsi, container, false);

        RecyclerView recyclerView = d.findViewById(R.id.binafsiList);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ProgressBar progressBar = d.findViewById(R.id.progressBar2b);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DatabaseReference miradiRef = FirebaseDatabase.getInstance().getReference("biashara/binafsi");
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

                    MiradiAdapter adapter = new MiradiAdapter(context, data);
                    recyclerView.setAdapter(adapter);
                    Collections.reverse(data2);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return d;
    }
}