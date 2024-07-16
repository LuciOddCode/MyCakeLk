package com.example.mycake.ui;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.mycake.ProductCart;
import com.example.mycake.R;
import com.example.mycake.UserAccount;
import com.example.mycake.UserHome;
import com.google.android.material.navigation.NavigationView;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl_user_main;
    NavigationView nv_user_main;
    Toolbar tb_user_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dl_user_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dl_user_main = findViewById(R.id.dl_user_main);
        nv_user_main = findViewById(R.id.nv_user_view);
        tb_user_main = findViewById(R.id.tb_user_main);

        setSupportActionBar(tb_user_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl_user_main, tb_user_main, R.string.navigation_open, R.string.navigation_close);

        dl_user_main.addDrawerListener(toggle);
        toggle.syncState();

        nv_user_main.setNavigationItemSelectedListener(this);

        // Load the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_user_dashboard, new UserHome()).commit();
            nv_user_main.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            selectedFragment = new UserHome();
        }else if (itemId == R.id.nav_account) {
            selectedFragment = new UserAccount();
        }else if (itemId == R.id.nav_cart){
            selectedFragment = new ProductCart();
        }else if (itemId == R.id.nav_logout){
            finish();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_user_dashboard, selectedFragment).commit();
            nv_user_main.setCheckedItem(itemId);
        }

        dl_user_main.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}