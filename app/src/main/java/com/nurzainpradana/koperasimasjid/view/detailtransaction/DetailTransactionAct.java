package com.nurzainpradana.koperasimasjid.view.detailtransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.util.SharePref;
import com.nurzainpradana.koperasimasjid.viewmodel.CartViewModel;
import com.nurzainpradana.koperasimasjid.viewmodel.TransactionViewModel;

public class DetailTransactionAct extends AppCompatActivity {

    RecyclerView rvListTrans;
    TransactionViewModel transactionViewModel;
    SharePref sharePref;
    int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        rvListTrans = findViewById(R.id.rv_list_trans);

        transactionViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TransactionViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvListTrans.setLayoutManager(layoutManager);
        rvListTrans.setItemAnimator(new DefaultItemAnimator());
        sharePref = new SharePref(getApplicationContext());
        id_user = sharePref.getInt(Const.ID_USER_KEY);

        getListTrans();
    }

    private void getListTrans() {

    }
}