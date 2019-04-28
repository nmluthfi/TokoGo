package com.example.hariswedira.todofirebase.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.BarangAdapter;
import com.example.hariswedira.todofirebase.DashboardActivity;
import com.example.hariswedira.todofirebase.Data.Barang;
import com.example.hariswedira.todofirebase.Data.Total;
import com.example.hariswedira.todofirebase.InsertTransaksiActivity;
import com.example.hariswedira.todofirebase.R;
import com.example.hariswedira.todofirebase.SignUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransaksiFragmnet extends Fragment {

    private Button tambah;
    Button total;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Barang> data;
    ArrayList<Total> dataHarga;
    BarangAdapter barangAdapter;
    int totalHarga = 0;

    public TransaksiFragmnet(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_transaksi,container,false);

        recyclerView = view.findViewById(R.id.list_barang_beli);
        total = view.findViewById(R.id.total_btn);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        data = new ArrayList<Barang>();

        total.setText("Total\t: "+0);

        tambah = view.findViewById(R.id.btn_tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InsertTransaksiActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String userUsername = settings.getString(DashboardActivity.USER_PROFILE,"");
        final String kode = settings.getString(InsertTransaksiActivity.KEY_KODE,"");

        reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).child("transaksi").child(kode).child("barang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Barang p =  dataSnapshot1.getValue(Barang.class);
                    data.add(p);
                }
                if (barangAdapter == null) {
                    barangAdapter= new BarangAdapter(getContext(),data);
                }
                recyclerView.setAdapter(barangAdapter);
                barangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"No Data", Toast.LENGTH_SHORT).show();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).child("transaksi").child(kode).child("total");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Total p =  dataSnapshot1.getValue(Total.class);
                    totalHarga += p.getHarga();
                    total.setText("Total\t: "+totalHarga);
                    total.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Checkout", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"No Data", Toast.LENGTH_SHORT).show();
            }
        });
//        if (totalHarga.getHarga()==0){
//            total.setText("Total\t: "+0);
//
//        }else {
//            reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).child("transaksi").child(kode).child("total");
//            reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int p = dataSnapshot.child("totalHarga").getValue(Integer.class);
//                    totalHarga.setHarga(p);
//
//                    if (barangAdapter == null) {
//                        barangAdapter= new BarangAdapter(getContext(),data);
//                    }
//                    recyclerView.setAdapter(barangAdapter);
//                    barangAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(getContext(),"No Data", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }


        return view;
    }
}
