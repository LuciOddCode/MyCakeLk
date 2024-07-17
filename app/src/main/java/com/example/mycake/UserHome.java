package com.example.mycake;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mycake.data.model.Cake;
import com.example.mycake.db.DBHelperUser;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends Fragment implements OnCakeActionListener {

    EditText search;
    Button btn_graduation, btn_wedding, btn_birthday;
    DBHelperUser db;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CakeAdapter cakeAdapter;
    List<Cake> cakeList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelperUser(getContext());
    }

    private void searchCake(String string) {
        Cursor cursor = db.getReadableDatabase().query("cakes", null, "name LIKE ?", new String[]{"%" + string + "%"}, null, null, null);
        cakeList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                String description = cursor.getString(3);
                String image = cursor.getString(4);
                String category = cursor.getString(5);
                cakeList.add(new Cake(id, name, price, description, image, category));
            } while (cursor.moveToNext());
            cursor.close();
        }
        cakeAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        search = view.findViewById(R.id.et_search);
        btn_graduation = view.findViewById(R.id.btn_graduation);
        btn_wedding = view.findViewById(R.id.btn_wedding);
        btn_birthday = view.findViewById(R.id.btn_birthday);

        recyclerView = view.findViewById(R.id.rv_cakes);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        cakeList = new ArrayList<>();
        cakeAdapter = new CakeAdapter(cakeList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cakeAdapter);

        swipeRefreshLayout.setOnRefreshListener(this::loadCakes);

        search.setOnEditorActionListener((v, actionId, event) -> {
            String query = search.getText().toString().trim();
            if (!query.isEmpty()) {
                searchCake(query);
            }
            return true;
        });

        btn_graduation.setOnClickListener(v -> loadCakesByCategory("Graduation"));
        btn_wedding.setOnClickListener(v -> loadCakesByCategory("Wedding"));
        btn_birthday.setOnClickListener(v -> loadCakesByCategory("Birthday"));

        loadCakes();

        return view;
    }

    private void loadCakes() {
        cakeList.clear();
        Cursor cursor = db.getReadableDatabase().query("product", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                String description = cursor.getString(3);
                String image = cursor.getString(4);
                String category = cursor.getString(5);
                cakeList.add(new Cake(id, name, price, description, image, category));
            } while (cursor.moveToNext());
            cursor.close();
        }
        cakeAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadCakesByCategory(String category) {
        cakeList.clear();
        Cursor cursor = db.getReadableDatabase().query("cakes", null, "category = ?", new String[]{category}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                String description = cursor.getString(3);
                String image = cursor.getString(4);
                String productCategory = cursor.getString(5);
                cakeList.add(new Cake(id, name, price, description, image, productCategory));
            } while (cursor.moveToNext());
            cursor.close();
        }
        cakeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddToCart(Cake cake) {
        SQLiteDatabase writableDatabase = db.getWritableDatabase();

      /*String createTableProductCart = "CREATE TABLE " + TABLE_PRODUCT_CART + " (" +
                COLUMN_PRODUCT_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_ID + " INTEGER, " +
                COLUMN_PRODUCT_CART_QUANTITY + " INTEGER, " +
                COLUMN_PRODUCT_CART_PRICE + " DOUBLE)";*/

        ContentValues values = new ContentValues();
        values.put("product_id", cake.getId());
        values.put("product_cart_quantity", 1);
        values.put("product_cart_price", cake.getPrice());
        writableDatabase.insert("product_cart", null, values);

        new AlertDialog.Builder(getContext())
                .setTitle("Add to Cart")
                .setMessage("Do you want to go to the cart?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    Fragment cartFragment = new ProductCart();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_user_dashboard, cartFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                })
                .setNegativeButton("No", null)
                .show();
    }


}
