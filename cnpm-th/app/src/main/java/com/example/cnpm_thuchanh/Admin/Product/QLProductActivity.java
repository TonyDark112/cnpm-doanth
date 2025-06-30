package com.example.cnpm_thuchanh.Admin.Product;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm_thuchanh.Adapter.ProductAdapter;
import com.example.cnpm_thuchanh.Dao.CategoryDao;
import com.example.cnpm_thuchanh.Dao.ProductDao;
import com.example.cnpm_thuchanh.Model.Category;
import com.example.cnpm_thuchanh.Model.Product;
import com.example.cnpm_thuchanh.R;

import java.util.List;

public class QLProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText edtName, edtDescription, edtPrice, edtImage;
    Spinner spinnerCategory;
    Button btnAdd, btnUpdate, btnDelete;

    ProductDao productDao;
    CategoryDao categoryDao;
    ProductAdapter adapter;
    List<Product> productList;
    List<Category> categoryList;
    int selectedCateId = -1;
    int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlproduct);

        // Ánh xạ
        recyclerView = findViewById(R.id.recyclerProduct);
        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtImage = findViewById(R.id.edtImage);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // DAO
        productDao = new ProductDao(this);
        categoryDao = new CategoryDao(this);

        loadCategories();
        loadProducts();

        // Thêm
        btnAdd.setOnClickListener(v -> {
            Product p = getProductFromForm();
            productDao.insert(p);
            loadProducts();
            clearForm();
        });

        // Sửa
        btnUpdate.setOnClickListener(v -> {
            if (selectedId != -1) {
                Product p = getProductFromForm();
                p.setId(selectedId);
                productDao.update(p);
                loadProducts();
                clearForm();
            }
        });

        // Xóa
        btnDelete.setOnClickListener(v -> {
            if (selectedId != -1) {
                productDao.delete(selectedId);
                loadProducts();
                clearForm();
            }
        });
    }

    private void loadCategories() {
        categoryList = categoryDao.getAll();
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void loadProducts() {
        productList = productDao.getAll();
        adapter = new ProductAdapter(productList);
        adapter.setOnItemClickListener(product -> {
            selectedId = product.getId();
            edtName.setText(product.getName());
            edtDescription.setText(product.getDescription());
            edtPrice.setText(String.valueOf(product.getPrice()));
            edtImage.setText(product.getImagePath());

            // Chọn đúng category trong Spinner
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == product.getCateId()) {
                    spinnerCategory.setSelection(i);
                    break;
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private Product getProductFromForm() {
        String name = edtName.getText().toString();
        String desc = edtDescription.getText().toString();
        double price = Double.parseDouble(edtPrice.getText().toString());
        String img = edtImage.getText().toString();
        int cateId = ((Category) spinnerCategory.getSelectedItem()).getId();

        return new Product(0, cateId, name, desc, price, img);
    }

    private void clearForm() {
        edtName.setText("");
        edtDescription.setText("");
        edtPrice.setText("");
        edtImage.setText("");
        spinnerCategory.setSelection(0);
        selectedId = -1;
    }
}
