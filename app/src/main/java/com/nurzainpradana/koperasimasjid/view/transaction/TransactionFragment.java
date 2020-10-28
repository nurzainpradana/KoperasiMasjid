package com.nurzainpradana.koperasimasjid.view.transaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.adapter.CartAdapter;
import com.nurzainpradana.koperasimasjid.adapter.TransactionAdapter;
import com.nurzainpradana.koperasimasjid.model.Cart;
import com.nurzainpradana.koperasimasjid.model.Transaction;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.viewmodel.TransactionViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionFragment extends Fragment {

    TransactionViewModel transactionViewModel;

    @BindView(R.id.rv_list_trans)
    RecyclerView rv_list_trans;

    public SharePref sharePref;
    public int id_user;


    public TransactionFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new TransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TransactionViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_list_trans.setLayoutManager(layoutManager);
        rv_list_trans.setItemAnimator(new DefaultItemAnimator());
        sharePref = new SharePref(getContext());
        id_user = sharePref.getInt(Const.ID_USER_KEY);

        getTrans();
    }

    private void getTrans() {
        transactionViewModel.setTransaction(getContext(), id_user);
        transactionViewModel.getListTransaction().observe(this.getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                TransactionAdapter transactionAdapter = new TransactionAdapter(getContext(), transactions);
                rv_list_trans.setAdapter(transactionAdapter);
            }
        });
    }
}
