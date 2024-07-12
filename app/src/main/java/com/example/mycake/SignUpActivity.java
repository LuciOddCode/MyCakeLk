package com.example.mycake;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycake.db.DBHelperUser;

public class SignUpActivity extends AppCompatActivity {

    RecyclerView recyclerViewSignUp;
    Button btnSignUp;
    EditText txtUserName, txtEmail, txtPassword, txtPasswordRe,txtAddAdminPassword;
    CheckBox cbxRole;

    private static final String PREFS_NAME = "UserPref";
    private static final String IS_ADMIN_KEY = "isAdmin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewSignUp = findViewById(R.id.recyclerViewSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtUserName = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtPasswordRe = findViewById(R.id.txtPasswordRe);
        txtAddAdminPassword = findViewById(R.id.txtAddAdminPassword);
        cbxRole = findViewById(R.id.cbxAdmin);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isAdmin = settings.getBoolean(IS_ADMIN_KEY, false);

        DBHelperUser dbHelperUser = new DBHelperUser(this);
        SQLiteDatabase db = dbHelperUser.getWritableDatabase();

        cbxRole.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                txtAddAdminPassword.setEnabled(true);
            } else {
                txtAddAdminPassword.setEnabled(false);
            }
        });

        /*sign up*/
        btnSignUp.setOnClickListener(v -> {
            String username = txtUserName.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            String passwordRe = txtPasswordRe.getText().toString();
            String role = cbxRole.isChecked() ? "ADMIN" : "USER";

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.equals(passwordRe)) {

                if (role.equals("ADMIN")) {
                    if (!txtAddAdminPassword.getText().toString().equals("admin")) {
                        Toast.makeText(this, "Invalid admin password", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        String query = "INSERT INTO user (user_name, user_email, user_password, user_role) VALUES ('" + username + "', '" + email + "', '" + password + "', '" + role + "')";
                        db.execSQL(query);
                        Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // user
                    String query = "INSERT INTO user (user_name, user_email, user_password, user_role) VALUES ('" + username + "', '" + email + "', '" + password + "', '" + role + "')";
                    db.execSQL(query);
                    Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setAdmin(boolean isAdmin) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IS_ADMIN_KEY, isAdmin);
        editor.apply();
    }
}