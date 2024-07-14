package com.example.mycake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageCakesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageCakesFragment extends Fragment {

    EditText et_cake_name, et_cake_price, et_cake_description, et_cake_image;
    ImageView iv_cake_image;
    Spinner sp_cake_category;

    Button btn_add_cake, btn_update_cake, btn_delete_cake;



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

        btn_add_cake.setOnClickListener(v -> addCake());
        btn_update_cake.setOnClickListener(v -> updateCake());
        btn_delete_cake.setOnClickListener(v -> deleteCake());


        return inflater.inflate(R.layout.fragment_manage_cakes, container, false);
    }

    private void deleteCake() {

    }

    private void updateCake() {

    }

    private void addCake() {

    }

    private void loadCakes() {

    }
}