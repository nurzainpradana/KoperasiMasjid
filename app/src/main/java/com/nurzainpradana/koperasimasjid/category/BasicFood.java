package com.nurzainpradana.koperasimasjid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.adapter.ListProductAdapter;
import com.nurzainpradana.koperasimasjid.adapter.ProductsAdapter;
import com.nurzainpradana.koperasimasjid.baseurl.Constant;
import com.nurzainpradana.koperasimasjid.baseurl.RequestInterface;
import com.nurzainpradana.koperasimasjid.model.Product;
import com.nurzainpradana.koperasimasjid.model.response.JsonRespon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListProductActivity extends AppCompatActivity {

    //UI
    @BindView(R.id.tv_title)
    TextView tvTitle;

    String idCat = "", idCategory = "";

    private Context mContext;
    private ArrayList<Product> listOfProdut;
    private int loadIndex = 0;
    private boolean doneLoad = false;

    //RecyclerView & Adapter
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    private ListProductAdapter listProductAdapter;
    private ArrayList<Product> productsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        ButterKnife.bind(this);

        listOfProdut = new ArrayList<>();

        //Initilization ctgSBasicFood
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListProductActivity.this, 2);
        rvProduct.setLayoutManager(gridLayoutManager);
        rvProduct.setHasFixedSize(true);
        listProductAdapter = new ListProductAdapter(mContext, productsList);
        rvProduct.setAdapter(listProductAdapter);

        //Intent
        Intent intentData = getIntent();
        if (intentData.hasExtra("category")) {
            tvTitle.setText(intentData.getExtras().getString("category"));
        }

        ctgBasicFood();
        //fetchingData();

    }

    private void ctgBasicFood() {
        tvTitle.setText("Sembako");
        Retrofit retrofit = null;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JsonRespon> call = request.getNew();
            call.enqueue(new Callback<JsonRespon>() {
                @Override
                public void onResponse(Call<JsonRespon> call, Response<JsonRespon> response) {
                    JsonRespon jsonResponse = response.body();
                    productsList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    listProductAdapter = new ListProductAdapter(mContext, productsList);
                    rvProduct.setAdapter(listProductAdapter);
                }

                @Override
                public void onFailure(Call<JsonRespon> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
    }
/*
    private void fetchingData() {
        Retrofit retrofit = null;
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL + idCat + "&id_category=/" + idCategory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RequestInterface request = retrofit.create(RequestInterface.class);
        JSONArray array = new JSONArray();
        Call<JsonRespon> call = request.getWomens();
        call.enqueue(new Callback<JsonRespon>() {
            @Override
            public void onResponse(Call<JsonRespon> call, Response<JsonRespon> response) {
                for (int i=0; i<array.length(); i++) {
                    try {
                        JSONObject data = array.getJSONObject(i);
                        Product product = new Product();
                        product.setName(data.getString("name"));
                        product.setImage(data.getString("image"));
                        product.setPrice(data.getString("price"));
                        product.setUnit(data.getDouble("unit"));
                        product.setId_produts(data.getInt("id_products"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonRespon> call, Throwable t) {

            }
        });
    }


 */

}
