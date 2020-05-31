package com.nurzainpradana.koperasimasjid.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context mContext;
    private List<Product> favoriteLists;

    public FavoriteAdapter(List<Product> favoriteLists, Context mContext) {
        this.favoriteLists = favoriteLists;
        this.mContext = mContext;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Product pl = favoriteLists.get(i);

        holder.nameProduct.setText(pl.getName());
        holder.priceProduct.setText(pl.getPrice());
        Picasso.with(mContext)
                .load(pl.getImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return (favoriteLists == null) ? 0 : favoriteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_product)
        ImageView imageView;
        @BindView(R.id.name_product)
        TextView nameProduct;
        @BindView(R.id.price_product)
        TextView priceProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
