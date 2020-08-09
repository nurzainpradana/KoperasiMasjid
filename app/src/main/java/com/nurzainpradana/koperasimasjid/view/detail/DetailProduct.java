package com.nurzainpradana.koperasimasjid.view.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.Utility.AppUtilits;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.Utility.NetworkUtility;
import com.nurzainpradana.koperasimasjid.Utility.SharePreferenceUtils;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.model.AddtoCart;
import com.nurzainpradana.koperasimasjid.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends AppCompatActivity {

    Context context;

    @BindView(R.id.img_detail_product)
    ImageView imgDetail;
    @BindView(R.id.name_detail_product)
    TextView nameDetail;
    @BindView(R.id.price_detail_product)
    TextView priceDetail;
    @BindView(R.id.desc_detail_product)
    TextView descDetail;
    @BindView(R.id.btn_favorite)
    ImageButton btnFavorite;
    @BindView(R.id.btn_add_cart)
    Button btnAddCart;

    private String id_product="";


    ArrayList<Product> listProdut = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        final Intent acc = getIntent();

        id_product = acc.getExtras().getString("id_products");

        nameDetail.setText(acc.getStringExtra("name"));
        priceDetail.setText(acc.getStringExtra("price"));
        descDetail.setText(acc.getStringExtra("description"));

        Picasso.get()
                .load(Const.IMAGE_URL + acc.getStringExtra("image"))
                .error(R.mipmap.ic_launcher)
                .into(imgDetail);

        btnFavorite.setOnClickListener(v -> {
            addtoWishlist();
        });

        btnAddCart.setOnClickListener(v -> {
            addtoCart();
        });




    }

    private void addtoWishlist() {
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_not_connect));
        } else {
            RetroConfig retroConfig = new RetroConfig(null);
            Call<AddtoCart> call = retroConfig.addtoWishlistCall("12345", id_product,
                    SharePreferenceUtils.getInstance().getString(Const.USER_DATA), priceDetail.getText().toString());
            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {
                    Log.e("TAG", "respons is" + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.succes_add_to_wishlist));
                        } else {
                            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.fail_add_to_wishlist));
                        }
                    } else {
                        AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<AddtoCart> call, Throwable t) {
                    AppUtilits.viewMessage(DetailProduct.this, getString(R.string.fail_add_to_wishlist));
                }
            });
        }
    }

    private void addtoCart() {
        if (!NetworkUtility.isNetworkConnected(DetailProduct.this)) {
            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_not_connect));
        } else {
            RetroConfig retroConfig = new RetroConfig(null);
            Call<AddtoCart> call = retroConfig.addtoCartCall("12345", id_product,
                    SharePreferenceUtils.getInstance().getString(Const.USER_DATA), priceDetail.getText().toString());
            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {
                    Log.e("TAG", "respons is" + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            //AppUtilits.viewMessage(DetailProduct.this, getString(R.string.succes_add_to_wishlist));
                            Toast.makeText(DetailProduct.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        } else {
                            AppUtilits.viewMessage(DetailProduct.this, getString(R.string.fail_add_to_wishlist));
                        }
                    } else {
                        AppUtilits.viewMessage(DetailProduct.this, getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<AddtoCart> call, Throwable t) {
                    AppUtilits.viewMessage(DetailProduct.this, getString(R.string.fail_add_to_wishlist));
                }
            });


        }
    }


    //Back
    @OnClick(R.id.img_back)
    void back() {
        finish();
    }


}
