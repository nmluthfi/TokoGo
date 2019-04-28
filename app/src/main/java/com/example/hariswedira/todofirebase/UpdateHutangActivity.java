package com.example.hariswedira.todofirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hariswedira.todofirebase.Data.Hutang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateHutangActivity extends AppCompatActivity {

    private EditText edtNamaUpdt, edtHargaUpdt;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hutang);

        edtNamaUpdt = findViewById(R.id.edt_updt_nama);
        edtHargaUpdt = findViewById(R.id.edt_updt_harga);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDeleteUpdt);

        final Hutang hutang = getIntent().getParcelableExtra(HutangAdapter.DATA_HUTANG);

        edtNamaUpdt.setText(hutang.getNamaPenghutang());
        edtHargaUpdt.setText(String.valueOf(hutang.getTotalHutang()));


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(UpdateHutangActivity.this);
        final String userUsername = settings.getString(DashboardActivity.USER_PROFILE,"");

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername).
                child("hutang").child(hutang.getKodeHutang());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hutang.setNamaPenghutang(edtNamaUpdt.getText().toString());
                hutang.setTotalHutang(Integer.parseInt(edtHargaUpdt.getText().toString()));
                if (Integer.parseInt(edtHargaUpdt.getText().toString())==0){
                    reference.removeValue();
                }else {
                    reference.setValue(hutang);
                }

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