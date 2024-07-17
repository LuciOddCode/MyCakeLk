package com.example.mycake;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycake.db.DBHelperUser;
import com.example.mycake.ui.AdminDashboardActivity;
import com.example.mycake.ui.UserDashboard;
import com.example.mycake.ui.UserMain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static String CURRENT_USER="";

    RecyclerView recyclerView;
    Button btnLogin, btnSignUp;
    EditText txtEmail, txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);


        DBHelperUser dbHelperUser = new DBHelperUser(this);
        SQLiteDatabase db = dbHelperUser.getWritableDatabase();

        dbHelperUser.addSampleData();

        /*login*/
        btnLogin.setOnClickListener(v -> {
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();

            String query = "SELECT * FROM user WHERE user_email = '" + email + "' AND user_password = '" + password + "'";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                String role = cursor.getString(4);
                if (role.equals("ADMIN")) {
                    // admin
                    CURRENT_USER = cursor.getString(2);
                    Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AdminDashboardActivity.class);
                    startActivity(intent);
                } else {
                    // user
                    CURRENT_USER = cursor.getString(2);
                    Toast.makeText(this, "Welcome User", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, UserDashboard.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        /*sign up*/
        btnSignUp.setOnClickListener(v -> {
            //navigate to sign up activity
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

    }
}