package com.nurzainpradana.koperasimasjid.view.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nurzainpradana.koperasimasjid.R;
//import com.nurzainpradana.koperasimasjid.adapter.CartAdapter;
import com.nurzainpradana.koperasimasjid.adapter.CartAdapter;
import com.nurzainpradana.koperasimasjid.model.Cart;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.viewmodel.CartViewModel;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Objects.*;

public class CartFragment extends Fragment implements View.OnClickListener {

    CartViewModel cartViewModel;

    @BindView(R.id.rv_list_cart)
    RecyclerView rvCart;

    @BindView(R.id.tvTotalPrice)
    TextView tvTotal;

    @BindView(R.id.tvTotalPriceText)
    TextView tvTotalText;

    Button btnCheckout;

    public Context mContext;
    //private CartAdapter cartAdapter;

    public SharePref sharePref;
    public int id_user;

    CartAdapter cartAdapter;

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

        btnCheckout = view.findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(CartViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvCart.setLayoutManager(layoutManager);
        rvCart.setItemAnimator(new DefaultItemAnimator());
        getUserCart();
    }


    public void getUserCart() {
        sharePref = new SharePref(requireContext());
        id_user = sharePref.getInt(Const.ID_USER_KEY);
        Log.d("CART", String.valueOf(id_user));
        cartViewModel.getCart(getContext(), id_user);
        cartViewModel.getListCarts().observe(this.getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                int total = 0;
                for(int i=0; i<carts.size(); i++){
                    total = total + Integer.parseInt(carts.get(i).getSubtotal());
                }
                tvTotal.setVisibility(View.VISIBLE);
                tvTotalText.setVisibility(View.VISIBLE);
                btnCheckout.setVisibility(View.VISIBLE);
                tvTotal.setText(String.valueOf(total));
                CartAdapter cartAdapter = new CartAdapter(getContext(), carts, id_user);
                rvCart.setAdapter(cartAdapter);
                onResume();
            }
        });
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_checkout){
            Random random = new Random();
            int id_transaction = random.nextInt(99999);
            cartViewModel.checkout(id_transaction, id_user, Integer.parseInt(tvTotal.getText().toString()));
            cartViewModel.getListCarts().observe(this.getViewLifecycleOwner(), new Observer<List<Cart>>() {
                @Override
                public void onChanged(List<Cart> carts) {
                    for (int i = 0; i <carts.size(); i++){
                        cartViewModel.checkoutItem(id_transaction, Integer.parseInt(carts.get(i).getIdProducts()), Integer.parseInt(carts.get(i).getPrice()), Integer.parseInt(carts.get(i).getQuantity()), Integer.parseInt(carts.get(i).getSubtotal()));
                        cartViewModel.deleteCart(getContext(), Integer.parseInt(carts.get(i).getIdCart()), id_user);
                        Toast.makeText(getContext(), "Berhasil Tambah Transaksi", Toast.LENGTH_SHORT).show();
                        cartViewModel.getListCarts().postValue(carts);
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cartViewModel.getCart(getContext(), id_user);
        cartViewModel.getListCarts().observe(this.getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                int total = 0;
                for(int i=0; i<carts.size(); i++){
                    total = total + Integer.parseInt(carts.get(i).getSubtotal());
                }
                tvTotal.setVisibility(View.VISIBLE);
                tvTotalText.setVisibility(View.VISIBLE);
                btnCheckout.setVisibility(View.VISIBLE);
                tvTotal.setText(String.valueOf(total));
                CartAdapter cartAdapter = new CartAdapter(getContext(), carts, id_user);
                rvCart.setAdapter(cartAdapter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        cartViewModel.getCart(getContext(), id_user);
        cartViewModel.getListCarts().observe(this.getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                int total = 0;
                for (int i = 0; i < carts.size(); i++) {
                    total = total + Integer.parseInt(carts.get(i).getSubtotal());
                }
                tvTotal.setVisibility(View.VISIBLE);
                tvTotalText.setVisibility(View.VISIBLE);
                btnCheckout.setVisibility(View.VISIBLE);
                tvTotal.setText(String.valueOf(total));
                CartAdapter cartAdapter = new CartAdapter(getContext(), carts, id_user);
                rvCart.setAdapter(cartAdapter);
            }
        });
    }
}
