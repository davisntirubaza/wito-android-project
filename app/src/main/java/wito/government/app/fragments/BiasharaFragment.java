package wito.government.app.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import wito.government.app.R;
import wito.government.app.ViewpagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BiasharaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BiasharaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BiasharaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BiasharaFragment newInstance(String param1, String param2) {
        BiasharaFragment fragment = new BiasharaFragment();
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
        View d = inflater.inflate(R.layout.fragment_biashara, container, false);


        ViewpagerAdapter pagerAdapter = new ViewpagerAdapter(getChildFragmentManager());
        pagerAdapter.add(new TaasisiFragment(), "Taasisi");
        pagerAdapter.add(new BinafsiFragment(), "Binafsi");

        ViewPager viewPager = d.findViewById(R.id.viewPager2);
        viewPager.setAdapter(pagerAdapter);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TabLayout tabLayout = d.findViewById(R.id.biasharaTab);
        tabLayout.setupWithViewPager(viewPager);

        return d;
    }
}