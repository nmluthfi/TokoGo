package com.example.hariswedira.todofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.Barang;
import com.example.hariswedira.todofirebase.Data.User;
import com.example.hariswedira.todofirebase.Utils.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignUpActivity extends AppCompatActivity{
    private EditText edtNama, edtNamaToko, edtUsername, edtPassword, edtKodeAkun, edtConfirmPassword;
    private TextView tvMasuk;
    private Button btnDaftar;
    private DatabaseReference reference;
    public final static String REFERENCE = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        reference = FirebaseDatabase.getInstance().getReference(REFERENCE);

        edtNama = findViewById(R.id.edt_nama);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        edtNamaToko = findViewById(R.id.edt_nama_toko);
        edtPassword = findViewById(R.id.edt_password_l);
        edtUsername = findViewById(R.id.edt_username_l);
        edtKodeAkun = findViewById(R.id.edt_kode_akun);
        btnDaftar = findViewById(R.id.btn_daftar);
        tvMasuk = findViewById(R.id.tv_masuk);

        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNama.getText().toString().matches("") || edtNamaToko.getText().toString().matches("") ||
                        edtPassword.getText().toString().matches("") || edtUsername.getText().toString().matches("") ||
                        edtKodeAkun.getText().toString().matches("")) {
                    Toast.makeText(SignUpActivity.this, "Isian belum lengkap", Toast.LENGTH_SHORT).show();
                }else if (!edtPassword.getText().toString().matches(edtConfirmPassword.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Konfirmasi Password Salah", Toast.LENGTH_SHORT).show();
                }else {
                    final User user = new User(edtNama.getText().toString(),edtNamaToko.getText().toString(),edtUsername.getText().toString()
                            ,edtPassword.getText().toString(),edtKodeAkun.getText().toString());
                    final Barang barang = new Barang("","","",0);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getUsername()).exists()){
                                Toast.makeText(SignUpActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
                            }else {
                                UserSession.save(getApplicationContext(),"session","true");
                                reference.child(user.getUsername()).setValue(user);
                                reference.child(user.getUsername()).child("barang").push();
                                Toast.makeText(SignUpActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                                Intent intentLog = new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(intentLog);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}