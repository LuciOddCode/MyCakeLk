package com.example.mycake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycake.data.model.Cake;

import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {

    private final List<Cake> cakes;
    private final OnCakeActionListener cakeActionListener;

    public CakeAdapter(List<Cake> cakes, OnCakeActionListener cakeActionListener) {
        this.cakes = cakes;
        this.cakeActionListener = cakeActionListener;
    }

    @NonNull
    @Override
    public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeViewHolder holder, int position) {
        Cake cake = cakes.get(position);
        holder.bind(cake, cakeActionListener);
    }

    @Override
    public int getItemCount() {
        return cakes.size();
    }

    static class CakeViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCakeName;
        private final TextView tvCakePrice;
        private final TextView tvCakeDescription;
        private final ImageView ivCakeImage;
        private final TextView tvCakeCategory;
        private final Button btnAddToCart;


        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCakeName = itemView.findViewById(R.id.tv_cake_name);
            tvCakePrice = itemView.findViewById(R.id.tv_cake_price);
            tvCakeDescription = itemView.findViewById(R.id.tv_cake_description);
            ivCakeImage = itemView.findViewById(R.id.iv_cake_image);
            tvCakeCategory = itemView.findViewById(R.id.tv_cake_category);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }

        public void bind(Cake cake, OnCakeActionListener cakeActionListener) {
            tvCakeName.setText("Name : "+cake.getName());
            tvCakePrice.setText("Price Rs: "+cake.getPrice());
            tvCakeDescription.setText(cake.getDescription());
            ivCakeImage.setImageResource(Integer.parseInt(cake.getImage()));
            tvCakeCategory.setText("Category : "+cake.getCategory());
            btnAddToCart.setOnClickListener(v -> cakeActionListener.onAddToCart(cake));

        }
    }

}
