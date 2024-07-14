package com.example.mycake;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycake.db.DBHelperUser;

import java.util.ArrayList;

public class ManageCategoryFragment extends Fragment {

    private EditText etCategoryName;
    private Button btnAddCategory, btnUpdateCategory, btnDeleteCategory;
    private ListView lvCategories;
    private DBHelperUser dbHelperUser;
    private ArrayAdapter<String> categoryAdapter;
    private ArrayList<String> categoryList;
    private int selectedCategoryId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_category, container, false);

        etCategoryName = view.findViewById(R.id.et_category_name);
        btnAddCategory = view.findViewById(R.id.btn_add_category);
        btnUpdateCategory = view.findViewById(R.id.btn_update_category);
        btnDeleteCategory = view.findViewById(R.id.btn_delete_category);
        lvCategories = view.findViewById(R.id.lv_categories);
        dbHelperUser = new DBHelperUser(getContext());

        categoryList = new ArrayList<>();
        categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categoryList);
        lvCategories.setAdapter(categoryAdapter);

        loadCategories();

        btnAddCategory.setOnClickListener(v -> addCategory());
        btnUpdateCategory.setOnClickListener(v -> updateCategory());
        btnDeleteCategory.setOnClickListener(v -> deleteCategory());

        lvCategories.setOnItemClickListener((adapterView, view1, position, id) -> {
            String selectedCategory = categoryList.get(position);
            etCategoryName.setText(selectedCategory);
            selectedCategoryId = position + 1;  // Assume ID starts from 1
        });

        return view;
    }

    private void loadCategories() {
        categoryList.clear();
        Cursor cursor = dbHelperUser.getReadableDatabase().query("category", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                categoryList.add(cursor.getString(1));
            } while (cursor.moveToNext());
            cursor.close();
        }
        categoryAdapter.notifyDataSetChanged();
    }

    private void addCategory() {
        String categoryName = etCategoryName.getText().toString().trim();
        if (categoryName.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a category name", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelperUser.getWritableDatabase().execSQL("INSERT INTO category (category_name) VALUES (?)", new Object[]{categoryName});
        loadCategories();
        etCategoryName.setText("");
        Toast.makeText(getContext(), "Category added", Toast.LENGTH_SHORT).show();
    }

    private void updateCategory() {
        String categoryName = etCategoryName.getText().toString().trim();
        if (categoryName.isEmpty() || selectedCategoryId == -1) {
            Toast.makeText(getContext(), "Please select a category and enter a new name", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelperUser.getWritableDatabase().execSQL("UPDATE category SET category_name = ? WHERE category_id = ?", new Object[]{categoryName, selectedCategoryId});
        loadCategories();
        etCategoryName.setText("");
        selectedCategoryId = -1;
        Toast.makeText(getContext(), "Category updated", Toast.LENGTH_SHORT).show();
    }

    private void deleteCategory() {
        if (selectedCategoryId == -1) {
            Toast.makeText(getContext(), "Please select a category to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelperUser.getWritableDatabase().execSQL("DELETE FROM category WHERE category_id = ?", new Object[]{selectedCategoryId});
        loadCategories();
        etCategoryName.setText("");
        selectedCategoryId = -1;
        Toast.makeText(getContext(), "Category deleted", Toast.LENGTH_SHORT).show();
    }
}
