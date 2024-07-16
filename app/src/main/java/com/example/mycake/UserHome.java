package com.example.mycake;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
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
    }

    private void searchCake(String string) {
//                get similar cake name product list from db and load it to recycler view
        Cursor cursor = db.getReadableDatabase().query("cakes", null, "name like ?", new String[]{"%" + string + "%"}, null, null, null);
        if (cursor.getCount() > 0) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        recyclerView = view.findViewById(R.id.rv_cakes);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        cakeList=new ArrayList<>();
        cakeAdapter = new CakeAdapter(cakeList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cakeAdapter);

        swipeRefreshLayout.setOnRefreshListener(this::loadCakes);

        loadCakes();


        return view;
    }

    private void loadCakes() {

        cakeList.clear();
        cakeList.add(new Cake(1, "Chocolate Cake", "1000", "Chocolate Cake", "cake1", "Birthday"));
        cakeList.add(new Cake(2, "Vanilla Cake", "1500", "Vanilla Cake", "cake2", "Birthday"));
        cakeAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAddToCart(Cake cake) {
        // Add the cake to the cart

    }
}