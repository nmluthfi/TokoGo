package com.example.hariswedira.todofirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.Barang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class InsertBarangActivity extends AppCompatActivity {
    private EditText edtNamaBarangC, edtJenisBarangC, edtHargaBarangC;
    private Button btnCreateC, btnCancelC;
    DatabaseReference reference;
    Barang barang = new Barang();
    private Integer kodeNum = new Random().nextInt();
    private String kodeBarang = "BRG_"+kodeNum.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_barang);

        final Barang barang = new Barang();
        edtNamaBarangC = findViewById(R.id.edt_nama_hutang);
        edtJenisBarangC = findViewById(R.id.hrg_brg);
        edtHargaBarangC = findViewById(R.id.edt_harga_barang);
        btnCreateC = findViewById(R.id.btnCreate);
        btnCancelC = findViewById(R.id.btn_cancel);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(InsertBarangActivity.this);
        final String userUsername = settings.getString(DashboardActivity.USER_PROFILE,"");

        btnCreateC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).child("barang");

                barang.setNamaBarang(edtNamaBarangC.getText().toString());
                barang.setJenisBarang(edtJenisBarangC.getText().toString());
                barang.setHargaBarang(Integer.parseInt(edtHargaBarangC.getText().toString()));
                barang.setKodeBarang(kodeBarang);

                reference.child(barang.getKodeBarang()).setValue(barang);
                finish();

//                reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        reference.child(barang.getKodeBarang()).setValue(barang);
//                        Intent intent = new Intent(InsertBarangActivity.this, DashboardActivity.class);
//                        intent.putExtra(LinkFragment.KEY_KODE,barang.getKodeBarang());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }
        });

        btnCancelC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(InsertBarangActivity.this,DashboardActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(),"Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }
}