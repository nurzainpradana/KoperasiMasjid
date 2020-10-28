package com.nurzainpradana.koperasimasjid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Product;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.view.detail.DetailProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Product> productList;

    public ListProductAdapter(Context mContext, ArrayList<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txt_name.setText(productList.get(i).getName());
        holder.txt_price.setText("Rp " + productList.get(i).getPrice());

        Picasso.get()
                .load(Const.IMAGE_PRODUCT_URL + productList.get(i).getImage())
                .error(R.drawable.ic_atk)
                .fit()
                .into(holder.img_product);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(holder.itemView.getContext(), DetailProduct.class);

                detailIntent.putExtra("id_products", productList.get(i).getId_products());
                detailIntent.putExtra("name", productList.get(i).getName());
                detailIntent.putExtra("price", productList.get(i).getPrice());
                detailIntent.putExtra("description", productList.get(i).getDescription());
                detailIntent.putExtra("image", productList.get(i).getImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_product)
        TextView txt_name;
        @BindView(R.id.price_product)
        TextView txt_price;
        @BindView(R.id.image_product)
        ImageView img_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
