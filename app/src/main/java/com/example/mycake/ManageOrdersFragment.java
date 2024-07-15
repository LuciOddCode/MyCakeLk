// ManageOrdersFragment.java
package com.example.mycake;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mycake.data.model.Order;

import java.util.ArrayList;
import java.util.List;

public class ManageOrdersFragment extends Fragment implements OnOrderActionListener {

    private RecyclerView recyclerViewOrders;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OrdersAdapter ordersAdapter;
    private List<Order> orderList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_orders, container, false);

        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        orderList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(orderList, this);

        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOrders.setAdapter(ordersAdapter);

        swipeRefreshLayout.setOnRefreshListener(this::loadOrders);

        loadOrders();

        return view;
    }

    private void loadOrders() {
        // Load orders from the database and update the orderList
        // For now, let's add some sample orders for demonstration
        orderList.clear();
        orderList.add(new Order(1, 1, 100.00, "2024-07-15", "Pending"));
        orderList.add(new Order(2, 2, 150.00, "2024-07-14", "Pending"));

        ordersAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAcceptOrder(Order order) {
        // Update order status to accepted in the database
        order.setStatus("Accepted");
        ordersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRemoveOrder(Order order) {
        // Remove the order from the database
        orderList.remove(order);
        ordersAdapter.notifyDataSetChanged();
    }
}
