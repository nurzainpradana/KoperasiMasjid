package com.nurzainpradana.koperasimasjid;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.nurzainpradana.koperasimasjid.Adapter.ProductsAdapter;
import com.nurzainpradana.koperasimasjid.Api.RequestInterface;
import com.nurzainpradana.koperasimasjid.Model.Product;
import com.nurzainpradana.koperasimasjid.Model.Response.JsonRespon;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class BerandaFragment extends Fragment {

    //List store all product
    private Context mContext;
    private ArrayList<Product> productList = new ArrayList<Product>();
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;

    ProgressDialog pd;

    public static Fragment newInstance() {
        return new BerandaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvProduct);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ProductsAdapter(productList, mContext);
        recyclerView.setAdapter(adapter);

        loadProduct();

    }

    private void loadProduct() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.83/koperasimasjid/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JsonRespon> call = request.getJSON();
        call.enqueue(new Callback<JsonRespon>() {
            @Override
            public void onResponse(Call<JsonRespon> call, Response<JsonRespon> response) {
                JsonRespon jsonResponse = response.body();
                productList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                adapter = new ProductsAdapter(productList, mContext);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonRespon> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }


}
