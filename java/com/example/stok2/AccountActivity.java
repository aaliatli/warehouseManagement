package com.example.stok2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail;  // Kullanıcı adı ve e-posta için TextView'ler
    private Button btnLogout;  // Çıkış yap butonu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account); // activity_account layout dosyasını kullanıyoruz

        // UI bileşenlerini bağlama
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        btnLogout = findViewById(R.id.btnLogout);

        // Burada kullanıcı bilgilerini alacağız (örneğin, SharedPreferences veya veri tabanından)
        // Örnek olarak statik veri kullanıyoruz:
        String username = "Ali Atlı";  // Kullanıcı adı
        String email = "ali@example.com";  // Kullanıcı e-posta

        // Kullanıcı bilgilerini ekrana yerleştirme
        tvUsername.setText("Kullanıcı Adı: " + username);
        tvEmail.setText("E-posta: " + email);

        // Çıkış yap butonuna tıklama olayı
        btnLogout.setOnClickListener(v -> logout());
    }

    // Kullanıcıyı çıkış yaptıracak metod
    private void logout() {
        // Örneğin, oturum açma bilgilerini temizleyebilirsiniz (SharedPreferences, Session vb.)
        // Bu örnekte, sadece MainActivity'ye yönlendiriyoruz
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // AccountActivity'yi kapatıyoruz
    }
}
