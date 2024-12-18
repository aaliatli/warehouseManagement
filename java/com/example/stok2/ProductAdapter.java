package com.example.stok2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.stok2.Product;
import com.example.stok2.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private OnProductDeleteListener deleteListener;
    private OnProductClickListener clickListener;

    // Silme işlemi için listener
    public interface OnProductDeleteListener {
        void onDelete(int position);
    }

    // Ürün tıklama işlemi için listener
    public interface OnProductClickListener {
        void onClick(int position);
    }

    public ProductAdapter(List<Product> productList, OnProductDeleteListener deleteListener, OnProductClickListener clickListener) {
        this.productList = productList;
        this.deleteListener = deleteListener;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productStock.setText("Stok: " + product.getStock());

        // Silme butonuna tıklama
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(position));

        // Ürün tıklama işlemi
        holder.itemView.setOnClickListener(v -> clickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productStock;
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productStock = itemView.findViewById(R.id.productStock);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
