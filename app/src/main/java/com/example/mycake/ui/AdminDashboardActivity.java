package com.example.mycake.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mycake.HomeFragment;
import com.example.mycake.ManageCakesFragment;
import com.example.mycake.ManageCategoryFragment;
import com.example.mycake.ManageOrdersFragment;
import com.example.mycake.R;
import com.google.android.material.navigation.NavigationView;

public class AdminDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl_admin_main;
    NavigationView nv_admin_main;
    Toolbar mtb_admin_main;

    public static final int NAV_HOME = R.id.nav_home;
    public static final int NAV_MANAGE_ORDERS = R.id.nav_manage_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dl_admin_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dl_admin_main = findViewById(R.id.dl_admin_main);
        nv_admin_main = findViewById(R.id.nv_admin_main);

        mtb_admin_main = findViewById(R.id.mtb_admin_main);
        setSupportActionBar(mtb_admin_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl_admin_main, mtb_admin_main, R.string.navigation_open, R.string.navigation_close);
        dl_admin_main.addDrawerListener(toggle);
        toggle.syncState();

        nv_admin_main.setNavigationItemSelectedListener(this);

        // Load the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_admin_dashboard,
                    new HomeFragment()).commit();
            nv_admin_main.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();
        Fragment selectedFragment = null;
        if (item.getItemId() == NAV_HOME) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            selectedFragment = new HomeFragment();
        } else if (item.getItemId() == NAV_MANAGE_ORDERS) {
            Toast.makeText(this, "Manage Orders", Toast.LENGTH_SHORT).show();
            selectedFragment = new ManageOrdersFragment();
        } else if (item.getItemId() == R.id.nav_manage_cakes) {
            selectedFragment = new ManageCakesFragment();
        } else if (item.getItemId() == R.id.nav_manage_category) {
            selectedFragment = new ManageCategoryFragment();
        }else if (item.getItemId() == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        }

        if (selectedFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_admin_dashboard, selectedFragment);
            transaction.commit();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();

        }
        dl_admin_main.closeDrawer(GravityCompat.START);

        return true;
    }



}
