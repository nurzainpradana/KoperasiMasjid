package com.nurzainpradana.koperasimasjid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class BerandaFragment extends Fragment {

    public BerandaFragment() {
        // Required empty public constructor
    }

    static Fragment newInstance(){
        return new BerandaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beranda, container, false);
    }
}
