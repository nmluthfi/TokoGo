package com.example.hariswedira.todofirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.Hutang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class InsertHutangActivity extends AppCompatActivity {
    private EditText edtNamaHutang, edtTotalGutang;
    private Button btnCreateC, btnCancelC;
    DatabaseReference reference;
    Hutang hutang= new Hutang();
    private Integer kodeNum = new Random().nextInt();
    private String kodeHutang = "HTG"+kodeNum.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_hutang);

        final Hutang hutang = new Hutang();
        edtNamaHutang = findViewById(R.id.edt_nama_hutang);
        edtTotalGutang = findViewById(R.id.edt_total_hutang);
        btnCreateC = findViewById(R.id.btnCreate);
        btnCancelC = findViewById(R.id.btn_cancel);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(InsertHutangActivity.this);
        final String userUsername = settings.getString(DashboardActivity.USER_PROFILE,"");

        btnCreateC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).child("hutang");

                hutang.setNamaPenghutang(edtNamaHutang.getText().toString());
                hutang.setTotalHutang(Integer.parseInt(edtTotalGutang.getText().toString()));
                hutang.setKodeHutang(kodeHutang);

                reference.child(hutang.getKodeHutang()).setValue(hutang);
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