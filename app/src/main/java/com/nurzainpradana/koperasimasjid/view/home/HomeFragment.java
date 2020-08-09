package com.nurzainpradana.koperasimasjid.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.adapter.BestAdapter;
import com.nurzainpradana.koperasimasjid.adapter.ProductsAdapter;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.api.response.JsonRespon;
import com.nurzainpradana.koperasimasjid.category.BasicFood;
import com.nurzainpradana.koperasimasjid.category.MensClothing;
import com.nurzainpradana.koperasimasjid.category.StationeryOffice;
import com.nurzainpradana.koperasimasjid.category.WomensClothing;
import com.nurzainpradana.koperasimasjid.model.Product;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class HomeFragment extends Fragment {

    //UI
    @BindView(R.id.ctg_food)
    LinearLayout ctgFood;
    @BindView(R.id.ctg_mens_clothing)
    LinearLayout ctgMensClothing;
    @BindView(R.id.ctg_womens_clothing)
    LinearLayout ctgWomensClothing;

    private Context mContext;
    private ArrayList<Product> productList = new ArrayList<Product>();
    private RecyclerView rvNew, rvBest;
    private ProductsAdapter adapter;
    private BestAdapter bestAdapter;


    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initalization New Product
        rvNew = view.findViewById(R.id.new_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(HORIZONTAL);
        rvNew.setLayoutManager(layoutManager);
        rvNew.setHasFixedSize(true);
        adapter = new ProductsAdapter(mContext, productList);
        rvNew.setAdapter(adapter);

        //Initilization Best Product
        rvBest = view.findViewById(R.id.best_recyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(HORIZONTAL);
        rvBest.setLayoutManager(layoutManager1);
        rvBest.setHasFixedSize(true);
        bestAdapter = new BestAdapter(mContext, productList);
        rvBest.setAdapter(bestAdapter);

        loadNewProduct();
        loadBestProduct();

    }

    private void loadNewProduct() {
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<JsonRespon> call = request.getNew();
        call.enqueue(new Callback<JsonRespon>() {
            @Override
            public void onResponse(Call<JsonRespon> call, Response<JsonRespon> response) {
                JsonRespon jsonResponse = response.body();
                productList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                adapter = new ProductsAdapter(mContext, productList);
                rvNew.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonRespon> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }


    private void loadBestProduct() {
        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<JsonRespon> call = request.getBest();
        call.enqueue(new Callback<JsonRespon>() {
            @Override
            public void onResponse(Call<JsonRespon> call, Response<JsonRespon> response) {
                JsonRespon jsonResponse = response.body();
                productList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                bestAdapter = new BestAdapter(mContext, productList);
                rvBest.setAdapter(bestAdapter);
            }

            @Override
            public void onFailure(Call<JsonRespon> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }

    //GotoViewAll
    @OnClick(R.id.view_all)
    void viewAll() {
        Intent gotoView = new Intent(getContext(), BasicFood.class);
        startActivity(gotoView);

    }

    //GotoViewAll
    @OnClick(R.id.view_all1)
    void viewAll1() {
        Intent gotoView = new Intent(getContext(), MensClothing.class);
        startActivity(gotoView);

    }

    //GotoCategory
    @OnClick(R.id.ctg_food)
    void basicFood() {
        Intent gotoFood = new Intent(getContext(), BasicFood.class);
        startActivity(gotoFood);
    }

    //GotoCategory
    @OnClick(R.id.ctg_mens_clothing)
    void mens() {
        Intent gotoMens = new Intent(getContext(), MensClothing.class);
        startActivity(gotoMens);
    }

    //GotoCategory
    @OnClick(R.id.ctg_womens_clothing)
    void womens() {
        Intent gotoWomens = new Intent(getContext(), WomensClothing.class);
        startActivity(gotoWomens);
    }

    //GotoCategory
    @OnClick(R.id.ctg_stationery)
    void stationery() {
        Intent gotoStationery = new Intent(getContext(), StationeryOffice.class);
        startActivity(gotoStationery);
    }

    //GotoCategory
    @OnClick(R.id.ctg_service)
    void service() {
        Toast.makeText(getContext(), "Maaf Belum Tersedia", Toast.LENGTH_SHORT).show();
    }
}
