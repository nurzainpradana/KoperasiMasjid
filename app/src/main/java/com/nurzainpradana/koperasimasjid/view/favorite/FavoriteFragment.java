package com.nurzainpradana.koperasimasjid.view.favorite;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.adapter.BestAdapter;
import com.nurzainpradana.koperasimasjid.adapter.ProductsAdapter;
import com.nurzainpradana.koperasimasjid.api.ApiInterface;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.api.response.JsonRespon;
import com.nurzainpradana.koperasimasjid.model.Product;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class FavoriteFragment extends Fragment {
    public FavoriteFragment() {
        // Required empty public constructor
    }

    private Context mContext;
    private ArrayList<Product> productList = new ArrayList<Product>();
    private RecyclerView rvFavorite;
    private ProductsAdapter adapter;

    public static Fragment newInstance(){
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initalization New Product
        rvFavorite = view.findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new ProductsAdapter(mContext, productList);
        rvFavorite.setAdapter(adapter);
        
        loadDataFavorite();
    }

    private void loadDataFavorite() {
        SharePref sharePref = new SharePref(getContext());
        int id_user = sharePref.getInt(Const.ID_USER_KEY);

        RetroConfig.getApiService(null);
        ApiInterface request = RetroConfig.retrofit.create(ApiInterface.class);
        Call<JsonRespon> call = request.getFavorite(id_user);
        call.enqueue(new Callback<JsonRespon>() {
            @Override
            public void onResponse(Call<JsonRespon> call, Response<JsonRespon> response) {
                JsonRespon jsonResponse = response.body();
                productList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                adapter = new ProductsAdapter(mContext, productList);
                rvFavorite.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonRespon> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }
}
