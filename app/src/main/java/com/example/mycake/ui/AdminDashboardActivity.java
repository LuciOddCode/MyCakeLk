package com.example.mycake.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.mycake.HomeFragment;
import com.example.mycake.MainActivity;
import com.example.mycake.ManageCakesFragment;
import com.example.mycake.ManageCategoryFragment;
import com.example.mycake.ManageOrdersFragment;
import com.example.mycake.R;
import com.google.android.material.navigation.NavigationView;

public class AdminDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl_admin_main;
    NavigationView nv_admin_main;
    Toolbar mtb_admin_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_admin_dashboard, new HomeFragment()).commit();
            nv_admin_main.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("Navigation", "Item selected: " + item.getItemId());
        Fragment selectedFragment = null;

        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
            Log.d("Navigation", "Selected Home Fragment");
        } else if (itemId == R.id.nav_manage_orders) {
            selectedFragment = new ManageOrdersFragment();
            Log.d("Navigation", "Selected Manage Orders Fragment");
        } else if (itemId == R.id.nav_manage_cakes) {
            selectedFragment = new ManageCakesFragment();
            Log.d("Navigation", "Selected Manage Cakes Fragment");
        } else if (itemId == R.id.nav_manage_category) {
            selectedFragment = new ManageCategoryFragment();
            Log.d("Navigation", "Selected Manage Category Fragment");
        } else if (itemId == R.id.nav_logout) {
            // Handle logout functionality
            logout();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            Log.d("Navigation", "Logged out");
        } else {
            Log.e("Navigation", "Unknown navigation item selected");
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_admin_dashboard, selectedFragment)
                    .commit();
            nv_admin_main.setCheckedItem(item.getItemId());
        } else {
            Log.e("Navigation", "Selected fragment is null");
        }

        dl_admin_main.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        // Implement logout functionality
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
    }
}
