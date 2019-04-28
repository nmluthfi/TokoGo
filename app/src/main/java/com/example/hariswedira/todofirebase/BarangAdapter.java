package com.example.hariswedira.todofirebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hariswedira.todofirebase.Data.Barang;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.MyViewHolder>{
    public final static String DATA_BARANG = "data barang";
    Context context;
    ArrayList<Barang> list;

    public BarangAdapter(Context context, ArrayList<Barang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Barang isi = list.get(i);

        double harga = Double.parseDouble(String.valueOf(isi.getHargaBarang()));

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        myViewHolder.namaBarang.setText(isi.getNamaBarang());
        myViewHolder.hargaBarang.setText(kursIndonesia.format(harga));
        myViewHolder.jenisBarang.setText(isi.getJenisBarang());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateBarangActivity.class);
                intent.putExtra(DATA_BARANG,list.get(i));
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
        TextView namaBarang, jenisBarang, hargaBarang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaBarang = itemView.findViewById(R.id.nm_brg);
            jenisBarang = itemView.findViewById(R.id.hrg_brg);
            hargaBarang = itemView.findViewById(R.id.total_hutang);
        }
    }
}
