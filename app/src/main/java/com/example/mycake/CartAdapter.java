package com.example.mycake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycake.data.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItem> cartItems;
    private final OnCartActionListener cartActionListener;

    public CartAdapter(List<CartItem> cartItems, OnCartActionListener cartActionListener) {
        this.cartItems = cartItems;
        this.cartActionListener = cartActionListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem, cartActionListener);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image_view;
        private final TextView text_view_price;
        private final Button button_remove;
        private final Button button_order;
        private final TextView text_view_quantity;
        private final Button button_add;
        private final Button button_subtract;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
            text_view_price = itemView.findViewById(R.id.text_view_price);
            button_remove = itemView.findViewById(R.id.button_remove);
            button_order = itemView.findViewById(R.id.button_order);
            text_view_quantity = itemView.findViewById(R.id.text_view_quantity);
            button_add = itemView.findViewById(R.id.button_add);
            button_subtract = itemView.findViewById(R.id.button_subtract);

        }

        public void bind(CartItem cartItem, OnCartActionListener cartActionListener) {
            image_view.setImageResource(R.mipmap.ic_cake1);
            text_view_price.setText(cartItem.getPrice());
            text_view_quantity.setText(String.valueOf(cartItem.getQuantity()));

            button_remove.setOnClickListener(v -> cartActionListener.onRemoveFromCart(cartItem.getId()));
            button_order.setOnClickListener(v -> cartActionListener.onPlaceOrder(cartItem));
            button_add.setOnClickListener(v -> {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                text_view_quantity.setText(String.valueOf(cartItem.getQuantity()));
            });
            button_subtract.setOnClickListener(v -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    text_view_quantity.setText(String.valueOf(cartItem.getQuantity()));
                }
            });


        }



    }
}
