package com.nurzainpradana.koperasimasjid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import com.nurzainpradana.koperasimasjid.R;
import com.nurzainpradana.koperasimasjid.model.Cart;
import com.nurzainpradana.koperasimasjid.model.Transaction;
import com.nurzainpradana.koperasimasjid.util.Const;
import com.nurzainpradana.koperasimasjid.view.detailtransaction.DetailTransactionAct;
import com.nurzainpradana.koperasimasjid.viewmodel.CartViewModel;
import com.nurzainpradana.koperasimasjid.viewmodel.TransactionViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    TransactionViewModel transactionViewModel;


    private Context context;
    private List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_transaction, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txt_no_trans.setText(transactionList.get(i).getIdTransaction());
        holder.txt_date_order.setText(transactionList.get(i).getDateOrder());
        holder.txt_total_order.setText(transactionList.get(i).getTotalOrder());
        holder.txt_status.setText(transactionList.get(i).getStatus());


        //Intent
        holder.btnDetailTrans.setOnClickListener(v -> {
            Intent gotodetail = new Intent(holder.itemView.getContext(), DetailTransactionAct.class);
            gotodetail.putExtra("id_trans", transactionList.get(i).getIdTransaction());
            gotodetail.putExtra("date_order", transactionList.get(i).getDateOrder());
            gotodetail.putExtra("total_order", transactionList.get(i).getTotalOrder());
            gotodetail.putExtra("status", transactionList.get(i).getStatus());
            holder.itemView.getContext().startActivity(gotodetail);
        });




    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.no_trans)
        TextView txt_no_trans;
        @BindView(R.id.date_order)
        TextView txt_date_order;
        @BindView(R.id.total_order)
        TextView txt_total_order;
        @BindView(R.id.status)
        TextView txt_status;
        @BindView(R.id.btn_detail_trans)
        Button btnDetailTrans;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}