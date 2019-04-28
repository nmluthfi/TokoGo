package com.example.hariswedira.todofirebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hariswedira.todofirebase.Data.Hutang;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class HutangAdapter extends RecyclerView.Adapter<HutangAdapter.MyViewHolder>{
    public final static String DATA_HUTANG = "data hutang";
    Context context;
    ArrayList<Hutang> list;

    public HutangAdapter(Context context, ArrayList<Hutang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_hutang,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Hutang isi = list.get(i);

        double harga = Double.parseDouble(String.valueOf(isi.getTotalHutang()));

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        myViewHolder.namaPenghutang.setText(isi.getNamaPenghutang());
        myViewHolder.totalHutang.setText(kursIndonesia.format(harga));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateHutangActivity.class);
                intent.putExtra(DATA_HUTANG,list.get(i));
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namaPenghutang, totalHutang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaPenghutang = itemView.findViewById(R.id.nm_brg);
            totalHutang = itemView.findViewById(R.id.total_hutang);
        }
    }
}
