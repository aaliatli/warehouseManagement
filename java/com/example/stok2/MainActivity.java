package com.example.stok2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<Product> productList;
    EditText etProductName, etProductDescription, etStockAmount;
    Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar'ı ayarlıyoruz
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Veritabanı ve UI elemanlarını tanımlıyoruz
        dbHelper = new DatabaseHelper(this);
        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        etStockAmount = findViewById(R.id.etStockAmount);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        recyclerView = findViewById(R.id.recyclerView);

        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList,
                position -> {
                    // Ürün silme işlemi
                    Product product = productList.get(position);
                    dbHelper.deleteProduct(product.getId());
                    loadProducts();
                    Toast.makeText(this, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                },
                position -> {
                    // Ürün güncelleme işlemi
                    Product product = productList.get(position);
                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                    intent.putExtra("PRODUCT_ID", product.getId());
                    intent.putExtra("PRODUCT_NAME", product.getName());
                    intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
                    intent.putExtra("PRODUCT_STOCK", product.getStockAmount());
                    startActivity(intent);
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadProducts(); // Ürünleri listele

        // Ürün ekleme butonuna tıklama olayı
        btnAddProduct.setOnClickListener(v -> {
            String name = etProductName.getText().toString();
            String description = etProductDescription.getText().toString();
            String stockText = etStockAmount.getText().toString();

            if (!name.isEmpty() && !description.isEmpty() && !stockText.isEmpty()) {
                int stock = Integer.parseInt(stockText);
                dbHelper.addProduct(name, description, stock);
                loadProducts();
                Toast.makeText(this, "Ürün Eklendi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Ürünleri veritabanından yükleyip RecyclerView'e aktarır
    private void loadProducts() {
        productList.clear();
        productList.addAll(dbHelper.getAllProducts());
        adapter.notifyDataSetChanged();
    }

    // Menü oluşturma
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Menüdeki butonlara tıklama olayları
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_account) {
            // Hesap sayfasına git
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Aktivite yeniden görünür olduğunda ürünleri yeniden yükler
    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();  // MainActivity yeniden görünür olduğunda verileri tekrar yükleyin
    }
}
