package com.nurzainpradana.koperasimasjid.view.cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.nurzainpradana.koperasimasjid.R;
//import com.nurzainpradana.koperasimasjid.adapter.CartAdapter;
import com.nurzainpradana.koperasimasjid.api.RetroConfig;
import com.nurzainpradana.koperasimasjid.model.CartDetail;
import com.nurzainpradana.koperasimasjid.model.CartItem;
import com.nurzainpradana.koperasimasjid.util.AppUtilits;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.NetworkUtility;
import com.nurzainpradana.koperasimasjid.util.SharePreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    @BindView(R.id.rv_list_cart)
    RecyclerView rvCart;

    private Context mContext;
    //private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems = new ArrayList<>();


    public CartFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new CartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvCart.setLayoutManager(layoutManager);
        rvCart.setItemAnimator(new DefaultItemAnimator());
        //cartAdapter = new CartAdapter(mContext, cartItems);
        //rvCart.setAdapter(cartAdapter);

        //getUserCart();

    }
/*
    private void getUserCart() {
        if (!NetworkUtility.isNetworkConnected(getContext())) {
            AppUtilits.viewMessage(getContext(), getString(R.string.network_not_connect));
        } else {
            RetroConfig retroConfig = new RetroConfig(null);
            Call<CartDetail> call = retroConfig.cartDetailCall("12345",
                    SharePreferenceUtils.getInstance().getString(Const.QUOTE_ID),
                    SharePreferenceUtils.getInstance().getString(Const.QUOTE_ID));

            call.enqueue(new Callback<CartDetail>() {
                @Override
                public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                    Log.e("TAG", "respons is" + response.body() + new Gson().toJson(response.body()));
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            cartItems.clear();
                            for (int i = 0; i < response.body().getInformation().size(); i++) {
                                cartItems.add(new CartItem(response.body().getInformation().get(i).getIdProducts(),
                                        response.body().getInformation().get(i).getName(),
                                        response.body().getInformation().get(i).getDescription(),
                                        response.body().getInformation().get(i).getImage(),
                                        response.body().getInformation().get(i).getPrice(),
                                        response.body().getInformation().get(i).getUnit()));
                            }

                            cartAdapter.notifyDataSetChanged();

                        } else {
                            AppUtilits.viewMessage(getContext(), response.body().getMsg());
                        }
                    } else {
                        AppUtilits.viewMessage(getContext(), getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<CartDetail> call, Throwable t) {
                    AppUtilits.viewMessage(getContext(), getString(R.string.fail_CartDetail));
                }
            });
        }
    }

 */
}
