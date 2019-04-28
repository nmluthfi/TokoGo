package com.example.hariswedira.todofirebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.Barang;
import com.example.hariswedira.todofirebase.Data.Total;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Random;

public class BarangTransaksiAdapter extends RecyclerView.Adapter<BarangTransaksiAdapter.MyViewHolder>{
    public final static String DATA_BARANG = "data barang";
    Context context;
    ArrayList<Barang> list;
    DatabaseReference reference;
    String username;
    int totalHarga = 0;
    String kodeBarang;

    public BarangTransaksiAdapter(Context context, ArrayList<Barang> list, String username, String koderBarang) {
        this.context = context;
        this.list = list;
        this.username = username;
        this.kodeBarang = koderBarang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.trans_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final Barang isi = list.get(i);

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
        myViewHolder.jenisBarang.setVisibility(View.GONE);
        myViewHolder.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Barang barang = new Barang();
                Total total = new Total();
                barang.setNamaBarang(isi.getNamaBarang().toString());
                barang.setHargaBarang(isi.getHargaBarang());
                barang.setJenisBarang(isi.getJenisBarang());
                barang.setKodeBarang(isi.getKodeBarang());
                totalHarga += isi.getHargaBarang();
                total.setHarga(totalHarga);
                reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(username).child("transaksi").child(kodeBarang).child("barang");
                reference.child(isi.getKodeBarang()).setValue(barang);
                reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(username).child("transaksi").child(kodeBarang).child("total");
                reference.child("totalHarga").setValue(total);
                Toast.makeText(context, "Ditambah kekeranjang", Toast.LENGTH_SHORT).show();
            }
        });
        myViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Total total = new Total();
                totalHarga -= isi.getHargaBarang();
                total.setHarga(totalHarga);
                reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(username).child("transaksi").child(kodeBarang).child("barang").child(isi.getKodeBarang());
                reference.removeValue();
                reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(username).child("transaksi").child(kodeBarang).child("total");
                reference.child("totalHarga").setValue(total);
                Toast.makeText(context, "Dikurangi dari kekeranjang", Toast.LENGTH_SHORT).show();
            }
        });

//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,UpdateBarangActivity.class);
//                intent.putExtra(DATA_BARANG,list.get(i));
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namaBarang, jenisBarang, hargaBarang;
         Button insert, remove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            insert = itemView.findViewById(R.id.button_plus);
            remove = itemView.findViewById(R.id.button_min);
            namaBarang = itemView.findViewById(R.id.nm_brg);
            jenisBarang = itemView.findViewById(R.id.jns_brg);
            hargaBarang = itemView.findViewById(R.id.hrg_brg);
        }
    }
}
