// OnOrderActionListener.java
package com.example.mycake;

import com.example.mycake.data.model.Order;

public interface OnOrderActionListener {
    void onAcceptOrder(Order order);
    void onRemoveOrder(Order order);
}
