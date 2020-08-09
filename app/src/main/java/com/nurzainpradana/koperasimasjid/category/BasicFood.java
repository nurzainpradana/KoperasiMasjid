package com.nurzainpradana.koperasimasjid.category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.adapter.ListProductAdapter;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.api.response.JsonRespon;
import com.nurzainpradana.koperasimasjid.model.Product;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicFood extends AppCompatActivity {

    //UI
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Context mContext;

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

        //Initilization ctgSBasicFood
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BasicFood.this, 2);
        rvProduct.setLayoutManager(gridLayoutManager);
        rvProduct.setHasFixedSize(true);
        listProductAdapter = new ListProductAdapter(mContext, productsList);
        rvProduct.setAdapter(listProductAdapter);

        ctgBasicFood();

    }

    public void ctgBasicFood() {
        tvTitle.setText("Sembako");
        RetroConfig.getApiService(null);

        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
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


}
