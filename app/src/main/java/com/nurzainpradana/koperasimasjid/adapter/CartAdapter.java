package com.nurzainpradana.koperasimasjid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Cart;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.view.cart.CartFragment;
import com.nurzainpradana.koperasimasjid.viewmodel.CartViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    CartViewModel cartViewModel;


    private Context context;
    public List<Cart> cartItemList;
    int id_user;

    public CartAdapter(Context context, List<Cart> cartItemList, int id_user) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.id_user = id_user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Cart model = cartItemList.get(i);
        holder.txt_name.setText(cartItemList.get(i).getName());
        holder.txt_price.setText("Rp " + cartItemList.get(i).getPrice());
        holder.txt_qty.setText(cartItemList.get(i).getQuantity());

        Picasso.get()
                .load(Const.IMAGE_PRODUCT_URL + cartItemList.get(i).getImage())
                .error(R.drawable.ic_atk)
                .fit()
                .into(holder.img_product);

        //Delete Cart
        holder.btnDelete.setOnClickListener(v -> {
            CartViewModel cartViewModel = new CartViewModel();
            cartViewModel.deleteCart(context, Integer.parseInt(cartItemList.get(i).getIdCart()), Integer.parseInt(cartItemList.get(i).getIdCart()));
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
        @BindView(R.id.tv_qty)
        TextView txt_qty;
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