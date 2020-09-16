package com.nurzainpradana.koperasimasjid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.CartItem;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.view.detail.DetailProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartItem> cartItemList;

    public CartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final CartItem model = cartItemList.get(i);

        holder.txt_name.setText(cartItemList.get(i).getName());
        holder.txt_price.setText("Rp " + cartItemList.get(i).getPrice());

        Picasso.get()
                .load(Const.IMAGE_PRODUCT_URL + cartItemList.get(i).getImage())
                .error(R.drawable.ic_atk)
                .fit()
                .into(holder.img_product);

        //Move to detail
        holder.itemView.setOnClickListener(v -> {
            Intent detailIntent = new Intent(holder.itemView.getContext(), DetailProduct.class);

            detailIntent.putExtra("id_products", cartItemList.get(i).getId_products());
            detailIntent.putExtra("name", cartItemList.get(i).getName());
            detailIntent.putExtra("price", cartItemList.get(i).getPrice());
            detailIntent.putExtra("description", cartItemList.get(i).getDescription());
            detailIntent.putExtra("image", cartItemList.get(i).getImage());

            holder.itemView.getContext().startActivity(detailIntent);
        });

    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_product)
        TextView txt_name;
        @BindView(R.id.price_product)
        TextView txt_price;
        @BindView(R.id.image_product)
        ImageView img_product;
        @BindView(R.id.btn_cart_delete)
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

 */
