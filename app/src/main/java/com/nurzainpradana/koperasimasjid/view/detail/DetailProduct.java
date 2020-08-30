package com.nurzainpradana.koperasimasjid.view.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.util.AppUtilits;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.NetworkUtility;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.viewmodel.CartViewModel;
import com.nurzainpradana.koperasimasjid.viewmodel.FavoriteViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailProduct extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_detail_product)
    ImageView imgDetail;
    @BindView(R.id.name_detail_product)
    TextView nameDetail;
    @BindView(R.id.price_detail_product)
    TextView priceDetail;
    @BindView(R.id.desc_detail_product)
    TextView descDetail;
    @BindView(R.id.btn_add_favorite)
    ImageButton btnFavorite;
    @BindView(R.id.btn_add_cart)
    Button btnAddCart;
    @BindView(R.id.btn_plus)
    Button btnPlus;
    @BindView(R.id.btn_minus)
    Button btnMinus;
    @BindView(R.id.tv_qty)
    TextView tvQty;

    int quantity;
    int id_user;
    int id_product;
    int isFavorite;

    FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        final Intent acc = getIntent();

        String id_products = acc.getExtras().getString("id_products");
        if (id_products != null){
            id_product = Integer.parseInt(id_products);
        }

        SharePref sharePref = new SharePref(this);
        id_user = sharePref.getInt(Const.ID_USER_KEY);

        checkFavorite(id_product, id_user);

        nameDetail.setText(acc.getStringExtra("name"));
        priceDetail.setText(acc.getStringExtra("price"));
        descDetail.setText(acc.getStringExtra("description"));
        //tvIdproducts.setText(acc.getStringExtra("id_products"));

        Picasso.get()
                .load(Const.IMAGE_PRODUCT_URL + acc.getStringExtra("image"))
                .error(R.mipmap.ic_launcher)
                .into(imgDetail);

        btnFavorite.setOnClickListener(this);
        btnAddCart.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
    }

    private void addQuantity(){
        quantity = Integer.parseInt(tvQty.getText().toString());
        quantity = quantity + 1;
        tvQty.setText(String.valueOf(quantity));
        btnMinus.setEnabled(true);
    }
    private void reduceQuantity(){
        quantity = Integer.parseInt(tvQty.getText().toString());
        if (quantity < 0){
            btnMinus.setEnabled(false);
        } else {
            btnMinus.setEnabled(true);
            if (quantity >0){
                quantity = quantity - 1;
                tvQty.setText(String.valueOf(quantity));
            } else {
                btnMinus.setEnabled(false);
            }
        }
    }


    private void checkFavorite(Integer id_product, Integer id_user){
        favoriteViewModel = new FavoriteViewModel();
        favoriteViewModel.checkFavorite(this, id_user, id_product);
        favoriteViewModel.getResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("RES CHECK", integer.toString());
                Toast.makeText(DetailProduct.this, integer.toString(), Toast.LENGTH_SHORT).show();

                if (integer == 1){
                    btnFavorite.setImageResource(R.drawable.ic_favorite);
                    isFavorite = 1;
                } else {
                    btnFavorite.setImageResource(R.drawable.ic_non_favorite);
                    isFavorite = 0;
                }

            }
        });
    }
    private void addToFavorite(int id_product, int id_user) {
        SharePref sharePref = new SharePref(getApplicationContext());
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_not_connect));
        } else {
            FavoriteViewModel favoriteViewModel = new FavoriteViewModel();
            favoriteViewModel.addToFavorite(this, id_product, id_user);
            btnFavorite.setImageResource(R.drawable.ic_favorite);
        }
    }

    private void addtoCart(int id_product, int id_user, int quantity) {
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_not_connect));
        } else {
            if (isFavorite == 0){
                CartViewModel cartViewModel = new CartViewModel();
                cartViewModel.addToCart(this, id_product, id_user, quantity);

            }



            /*

            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {
                    Log.e("TAG", "respons is" + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            //Toast.makeText(DetailProduct.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<AddtoCart> call, Throwable t) {
                    AppUtilits.viewMessage(DetailProduct.this, getString(R.string.fail_add_to_wishlist));
                }
            });

             */
        }
    }


    //Back
    @OnClick(R.id.img_back)
    void back() {
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_plus:
                    addQuantity();
                break;

                case R.id.btn_minus:
                    reduceQuantity();
                break;

            case R.id.btn_add_cart:
                quantity = Integer.parseInt(tvQty.getText().toString());
                addtoCart(id_product, id_user, quantity);
                break;

            case R.id.btn_add_favorite:
                if (isFavorite==0){
                    addToFavorite(id_product, id_user);
                    isFavorite = 1;
                }

                break;
        }
    }
}
