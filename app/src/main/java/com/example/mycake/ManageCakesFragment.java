package com.example.mycake;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mycake.db.DBHelperUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageCakesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageCakesFragment extends Fragment {

    EditText et_cake_name, et_cake_price, et_cake_description;
    ImageView iv_cake_image;
    Spinner sp_cake_category, et_cake_image;

    Button btn_add_cake, btn_update_cake, btn_delete_cake;
    ListView lv_cakes;
    ArrayAdapter<String> cakeAdapter;
    ArrayList<String> cakeList;

    DBHelperUser dbHelperUser;

    int selectedCakeId = -1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManageCakesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageCakesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageCakesFragment newInstance(String param1, String param2) {
        ManageCakesFragment fragment = new ManageCakesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("DiscouragedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_cakes, container, false);

        et_cake_name = view.findViewById(R.id.et_cake_name);
        et_cake_price = view.findViewById(R.id.et_cake_price);
        et_cake_description = view.findViewById(R.id.et_cake_description);
        et_cake_image = view.findViewById(R.id.et_cake_image);
        iv_cake_image = view.findViewById(R.id.iv_cake_image);
        sp_cake_category = view.findViewById(R.id.sp_cake_category);
        btn_add_cake = view.findViewById(R.id.btn_add_cake);
        btn_update_cake = view.findViewById(R.id.btn_update_cake);
        btn_delete_cake = view.findViewById(R.id.btn_delete_cake);
        lv_cakes = view.findViewById(R.id.lv_cakes);

        // Implement the logic for loading the cakes
        dbHelperUser = new DBHelperUser(getContext());
        cakeList = new ArrayList<>();
        cakeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cakeList);

        lv_cakes.setAdapter(cakeAdapter);

        loadCakes();

        loadCategories();

        /*loading images for image spinner*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"cake1", "cake2", "cake3", "cake4", "cake5"});
        adapter.setNotifyOnChange(true);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        et_cake_image.setAdapter(adapter);


        btn_add_cake.setOnClickListener(v -> addCake());
        btn_update_cake.setOnClickListener(v -> updateCake());
        btn_delete_cake.setOnClickListener(v -> deleteCake());

        lv_cakes.setOnItemClickListener((adapterView, view1, position, id) -> {
            String selectedCake = cakeList.get(position);
            et_cake_name.setText(selectedCake);
            selectedCakeId = position + 1;
        });

        /*Add logic to show image when click on the spinner item and spinner does not support setOnItemSelected() method it crashed when I used it before*/




        return view;
    }

    private void loadCategories() {
        // Implement the logic for loading the categories
        Cursor cursor = dbHelperUser.getReadableDatabase().query("category", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                sp_cake_category.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{cursor.getString(1)}));
            } while (cursor.moveToNext());
        }
    }

    private void loadCakes() {
        // Implement the logic for loading the cakes
        cakeList.clear();
        Cursor cursor = dbHelperUser.getReadableDatabase().query("product", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cakeList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
    }

    private void addCake() {

        if (et_cake_name.getText().toString().isEmpty() || et_cake_price.getText().toString().isEmpty() || et_cake_description.getText().toString().isEmpty() || et_cake_image.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String cakeName = et_cake_name.getText().toString();
        double cakePrice = Double.parseDouble(et_cake_price.getText().toString());
        String cakeDescription = et_cake_description.getText().toString();
        String cakeImage = et_cake_image.getSelectedItem().toString();
        String cakeCategory = sp_cake_category.getSelectedItem().toString();


        dbHelperUser.getWritableDatabase().execSQL("INSERT INTO product (product_name, product_price, product_description, product_image, product_category) VALUES (?, ?, ?, ?, ?)", new Object[]{cakeName, cakePrice, cakeDescription, cakeImage, cakeCategory});
        loadCakes();
        clearFields();


        Toast.makeText(getContext(), "Add Cake functionality", Toast.LENGTH_SHORT).show();
    }

    private void updateCake() {

        if (et_cake_name.getText().toString().isEmpty() || et_cake_price.getText().toString().isEmpty() || et_cake_description.getText().toString().isEmpty() || et_cake_image.getSelectedItem().equals("") || selectedCakeId == -1) {
            Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String cakeName = et_cake_name.getText().toString();
        double cakePrice = Double.parseDouble(et_cake_price.getText().toString());
        String cakeDescription = et_cake_description.getText().toString();
        String cakeImage = et_cake_image.getSelectedItem().toString();
        String cakeCategory = sp_cake_category.getSelectedItem().toString();

        dbHelperUser.getWritableDatabase().execSQL("UPDATE product SET product_name = ?, product_price = ?, product_description = ?, product_image = ?, product_category = ? WHERE product_id = ?", new Object[]{cakeName, cakePrice, cakeDescription, cakeImage, cakeCategory, selectedCakeId});
        Toast.makeText(getContext(), "Update Cake functionality", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        et_cake_name.setText("");
        et_cake_price.setText("");
        et_cake_description.setText("");
        selectedCakeId = -1;
    }

    private void deleteCake() {

        if (selectedCakeId == -1) {
            Toast.makeText(getContext(), "Please select a cake to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelperUser.getWritableDatabase().execSQL("DELETE FROM product WHERE product_id = ?", new Object[]{selectedCakeId});
        Toast.makeText(getContext(), "Delete Cake functionality", Toast.LENGTH_SHORT).show();
        clearFields();
    }
}
