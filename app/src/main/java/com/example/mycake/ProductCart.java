package com.example.mycake;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.mycake.data.model.CartItem;
import com.example.mycake.db.DBHelperUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductCart extends Fragment implements OnCartActionListener{

    RecyclerView recycler_view;

    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;


    public static ProductCart newInstance(String param1, String param2) {
        ProductCart fragment = new ProductCart();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_cart, container, false);
        recycler_view = view.findViewById(R.id.recycler_view);

        cartItems =new ArrayList<>();
        cartAdapter= new CartAdapter(cartItems,this);

        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setAdapter(cartAdapter);

        loadCartItems();
        return view;
    }

    private void loadCartItems() {
        DBHelperUser dbHelperUser = new DBHelperUser(getContext());
        cartItems.clear();
        cartItems.addAll(dbHelperUser.getCartItems());
        cartAdapter.notifyDataSetChanged();

    }


    @Override
    public void onRemoveFromCart(int id) {
        DBHelperUser dbHelperUser = new DBHelperUser(getContext());
        SQLiteDatabase writableDatabase = dbHelperUser.getWritableDatabase();
        writableDatabase.delete("product_cart","product_cart_id = ?",new String[]{String.valueOf(id)});
        writableDatabase.close();
        loadCartItems();
    }

    @Override
    public void onPlaceOrder(CartItem cartItem) {
        DBHelperUser dbHelperUser = new DBHelperUser(getContext());
        dbHelperUser.placeOrder(cartItem);
        dbHelperUser.removeCartItem(cartItem.getId());
        loadCartItems();

//        notify customer that order has placed
        new AlertDialog.Builder(getContext())
                .setTitle("Order Placed")
                .setMessage("Your order has been placed successfully!")
                .setPositiveButton("OK", null)
                .show();



    }
}