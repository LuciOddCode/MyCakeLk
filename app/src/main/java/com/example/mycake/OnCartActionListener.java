package com.example.mycake;

import com.example.mycake.data.model.CartItem;

public interface OnCartActionListener {
    void onRemoveFromCart(int id);
    void onPlaceOrder(CartItem cartItem);
}
