package com.example.stok2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail;  
    private Button btnLogout; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account); 

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        btnLogout = findViewById(R.id.btnLogout);

        String username = "Ali Atlı";  
        String email = "ali@example.com"; 

        tvUsername.setText("Kullanıcı Adı: " + username);
        tvEmail.setText("E-posta: " + email);

        btnLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  
    }
}
