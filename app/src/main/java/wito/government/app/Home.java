package wito.government.app;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import wito.government.app.activities.About;
import wito.government.app.activities.Settings;
import wito.government.app.fragments.HomeFragment;
import wito.government.app.fragments.MiradiFragment;
import wito.government.app.fragments.SiasaFragment;
import wito.government.app.fragments.WalletFragment;
import wito.government.app.fragments.BiasharaFragment;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    public static String phone;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notification_menu) {
            startActivity(new Intent(getApplicationContext(), Notifications.class));
        } else if (item.getItemId() == R.id.about_menu) {
            startActivity(new Intent(getApplicationContext(), About.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("WITO");
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        getSupportActionBar().setElevation(0);
        phone = getIntent().getStringExtra("phone");

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new BiasharaFragment());
        fragmentList.add(new MiradiFragment());
        fragmentList.add(new SiasaFragment());
        fragmentList.add(new WalletFragment());

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_menu:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.biashara_menu:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.miradi_menu:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.siasa_menu:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.wallet_menu:
                    viewPager.setCurrentItem(4);
                    break;
            }
            return true;
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int id = 0;
                String title = "";
                switch (position) {
                    case 0:
                        id = R.id.home_menu;
                        title = "WITO";
                        break;
                    case 1:
                        id = R.id.biashara_menu;
                        title = "Biashara";
                        break;
                    case 2:
                        id = R.id.miradi_menu;
                        title = "Miradi";
                        break;
                    case 3:
                        id = R.id.siasa_menu;
                        title = "Siasa";
                        break;
                    case 4:
                        id = R.id.wallet_menu;
                        title = "Wallet";
                        break;
                }
                getSupportActionBar().setTitle(title);
                bottomNavigationView.setSelectedItemId(id);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}