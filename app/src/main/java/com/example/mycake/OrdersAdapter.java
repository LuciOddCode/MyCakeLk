// OrdersAdapter.java
package com.example.mycake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycake.data.model.Order;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private final List<Order> orders;
    private final OnOrderActionListener orderActionListener;

    public OrdersAdapter(List<Order> orders, OnOrderActionListener orderActionListener) {
        this.orders = orders;
        this.orderActionListener = orderActionListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order, orderActionListener);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvOrderID;
        private final TextView tvOrderDate;
        private final TextView tvOrderTotalPrice;
        private final TextView tvOrderStatus;
        private final Button btnAcceptOrder;
        private final Button btnRemoveOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderID = itemView.findViewById(R.id.tvOrderID);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderTotalPrice = itemView.findViewById(R.id.tvOrderTotalPrice);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            btnAcceptOrder = itemView.findViewById(R.id.btnAcceptOrder);
            btnRemoveOrder = itemView.findViewById(R.id.btnRemoveOrder);
        }

        public void bind(Order order, OnOrderActionListener orderActionListener) {
            tvOrderID.setText("Order ID: #" + order.getOrderId());
            tvOrderDate.setText("Date: " + order.getDate());
            tvOrderTotalPrice.setText("Total Price: $" + order.getTotalPrice());
            tvOrderStatus.setText("Status: " + order.getStatus());

            btnAcceptOrder.setOnClickListener(v -> orderActionListener.onAcceptOrder(order));
            btnRemoveOrder.setOnClickListener(v -> orderActionListener.onRemoveOrder(order));
        }
    }
}
