package com.example.hariswedira.todofirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.Barang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class UpdateBarangActivity extends AppCompatActivity {

    private EditText edtNamaUpdt, edtJenisUpdt, edtHargaUpdt;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);

        edtNamaUpdt = findViewById(R.id.edt_updt_nama);
        edtJenisUpdt = findViewById(R.id.edt_updt_jenis);
        edtHargaUpdt = findViewById(R.id.edt_updt_harga);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDeleteUpdt);

        final Barang barang = getIntent().getParcelableExtra(BarangAdapter.DATA_BARANG);

        edtNamaUpdt.setText(barang.getNamaBarang());
        edtJenisUpdt.setText(barang.getJenisBarang());
        edtHargaUpdt.setText(String.valueOf(barang.getHargaBarang()));


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(UpdateBarangActivity.this);
        final String userUsername = settings.getString(DashboardActivity.USER_PROFILE,"");

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).
                child("barang").child(barang.getKodeBarang());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barang.setNamaBarang(edtNamaUpdt.getText().toString());
                barang.setJenisBarang(edtJenisUpdt.getText().toString());
                barang.setHargaBarang(Integer.parseInt(edtHargaUpdt.getText().toString()));
                reference.setValue(barang);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue();
                finish();
            }
        });
    }
}