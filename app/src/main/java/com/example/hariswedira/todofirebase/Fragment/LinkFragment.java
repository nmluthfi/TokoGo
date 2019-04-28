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

import com.example.hariswedira.todofirebase.BarangAdapter;
import com.example.hariswedira.todofirebase.DashboardActivity;
import com.example.hariswedira.todofirebase.Data.Barang;
import com.example.hariswedira.todofirebase.InsertBarangActivity;
import com.example.hariswedira.todofirebase.R;
import com.example.hariswedira.todofirebase.SignUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LinkFragment extends Fragment{

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Barang> data;
    BarangAdapter barangAdapter;
    Button btnAdd;
    TextView titlePage;
    LinearLayout linearLayout;
    ScrollView scrollView;
    EditText edtSearch;
    public static String KEY_ACTIVITY = "mag_fragment";
    public static String KEY_KODE = "kode barang";
    int a = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_link,container,false);

        btnAdd = view.findViewById(R.id.btnAdd);
        edtSearch = view.findViewById(R.id.edt_search_hutang);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        data = new ArrayList<Barang>();
        titlePage = view.findViewById(R.id.tittlePage);
        linearLayout = view.findViewById(R.id.linear);
        scrollView = view.findViewById(R.id.scrollview);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),InsertBarangActivity.class);
                startActivity(intent);
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
                    search(s.toString());
                }else {
                    search("");
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
                }
            }
        });

        return view;
    }

    private void search(String s) {
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
                    barangAdapter= new BarangAdapter(getContext(),data);
                }
                recyclerView.setAdapter(barangAdapter);
                barangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
