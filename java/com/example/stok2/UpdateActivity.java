package com.example.stok2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    private EditText etProductName, etProductDescription, etProductStock;
    private int productId;
    private DatabaseHelper dbHelper; // Veritabanı işlemleri için dbHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update); // UpdateActivity için layout

        // Veritabanı yardımcı sınıfını başlatıyoruz
        dbHelper = new DatabaseHelper(this); // dbHelper'ı başlatıyoruz

        // EditText bileşenlerini tanımlama
        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductStock = findViewById(R.id.etProductStock);


        // Intent'ten gelen verileri alıyoruz
        Intent intent = getIntent();
        productId = intent.getIntExtra("PRODUCT_ID", -1); // Eğer ID geçilmemişse -1 dönecek
        String productName = intent.getStringExtra("PRODUCT_NAME");
        String productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION");
        int productStock = intent.getIntExtra("PRODUCT_STOCK", 0);

// Null kontrolü ekleyin
        if (productName == null || productDescription == null || productStock == 0) {
            Toast.makeText(this, "Veriler eksik, güncelleme yapılamaz", Toast.LENGTH_SHORT).show();
            return;
        }

// EditText alanlarına verileri yerleştiriyoruz
        etProductName.setText(productName);
        etProductDescription.setText(productDescription);
        etProductStock.setText(String.valueOf(productStock));
    }

    // Güncelleme işlemi için metod
    public void updateProduct(View view) {
        String name = etProductName.getText().toString();
        String description = etProductDescription.getText().toString();
        String stockText = etProductStock.getText().toString();

        if (!name.isEmpty() && !description.isEmpty() && !stockText.isEmpty()) {
            int stock = Integer.parseInt(stockText);

            // Veritabanında güncellemeyi yapacak bir metod çağrılacak
            dbHelper.updateProduct(productId, name, description, stock); // Veritabanı işlemi

            Toast.makeText(this, "Ürün Güncellendi", Toast.LENGTH_SHORT).show();
            finish(); // Güncelleme işlemi tamamlandığında Activity'yi kapatıyoruz
        } else {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
        }
    }



}
