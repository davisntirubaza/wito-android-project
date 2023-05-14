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

import java.util.ArrayList;
import java.util.List;

public class AppointmentsFragment extends Fragment {

    public AppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View d =  inflater.inflate(R.layout.fragment_appointments, container, false);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new BinafsiFragment());
        fragmentList.add(new TaasisiFragment());
        fragmentList.add(new SiasaFragment());

        ViewpagerAdapter pagerAdapter = new ViewpagerAdapter(getChildFragmentManager());
        pagerAdapter.add(new BinafsiFragment(), "Upcoming");
        pagerAdapter.add(new TaasisiFragment(), "Past");
        pagerAdapter.add(new SiasaFragment(), "Doctors");

        ViewPager viewPager = d.findViewById(R.id.viewPager2);
        viewPager.setAdapter(pagerAdapter);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TabLayout tabLayout = d.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return d;
    }
}