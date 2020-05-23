package com.nurzainpradana.koperasimasjid.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nurzainpradana.koperasimasjid.R;

public class CartFragment extends Fragment {
    public CartFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new CartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keranjang, container, false);
    }
}
