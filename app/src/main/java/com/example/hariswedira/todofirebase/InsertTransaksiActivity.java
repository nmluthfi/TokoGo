package com.example.hariswedira.todofirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.Barang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class InsertTransaksiActivity extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayout;
    ArrayList<Barang> data;
    BarangTransaksiAdapter barangAdapter;
    public static String KEY_ACTIVITY = "mag_fragment";
    public static String KEY_KODE = "kode trans";
    int a = 0;
    Button back;
    EditText edtSearch;

    private Integer kodeNum = new Random().nextInt();
    private String kodeBarang = "TRX"+kodeNum.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        edtSearch = findViewById(R.id.search_brg);

        recyclerView = findViewById(R.id.transaksi_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        data = new ArrayList<Barang>();
        data = new ArrayList<Barang>();
//        titlePage = findViewById(R.id.tittlePage);
//        linearLayout = findViewById(R.id.linear);
//        scrollView = findViewById(R.id.scrollview);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(InsertTransaksiActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_KODE,kodeBarang);
        editor.commit();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        final String userUsername = settings.getString(DashboardActivity.USER_PROFILE,"");

        reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).child("barang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Barang p = dataSnapshot1.getValue(Barang.class);
                    data.add(p);
                }
                if (barangAdapter ==  null) {
                    barangAdapter = new BarangTransaksiAdapter(InsertTransaksiActivity.this,data,userUsername,kodeBarang);
                }
                recyclerView.setAdapter(barangAdapter);
                barangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(InsertTransaksiActivity.this,"No Data", Toast.LENGTH_SHORT).show();
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertTransaksiActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    search(s.toString(),userUsername);
                }else {
                    search("",userUsername);
                    reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).child("barang");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            data.clear();
                            for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                Barang p = dataSnapshot1.getValue(Barang.class);
                                data.add(p);
                            }
                            if (barangAdapter == null) {
                                barangAdapter = new BarangTransaksiAdapter(InsertTransaksiActivity.this,data,userUsername,kodeBarang);
                            }
                            recyclerView.setAdapter(barangAdapter);
                            barangAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(InsertTransaksiActivity.this,"No Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void search(String s, final String username) {
        Query query = reference.orderByChild("namaBarang")
                .startAt(edtSearch.getText().toString()).endAt(edtSearch.getText().toString()+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    final Barang p = dataSnapshot1.getValue(Barang.class);
                    data.add(p);
                }

                if (barangAdapter == null) {
                    barangAdapter = new BarangTransaksiAdapter(InsertTransaksiActivity.this,data,username,kodeBarang);
                }
                recyclerView.setAdapter(barangAdapter);
                barangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(InsertTransaksiActivity.this,InsertBarangActivity.class);
//                startActivity(intent);
//            }
//        });
}