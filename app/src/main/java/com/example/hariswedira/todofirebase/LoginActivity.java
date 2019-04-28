package com.example.hariswedira.todofirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.User;
import com.example.hariswedira.todofirebase.Utils.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username_l);
        edtPassword = findViewById(R.id.edt_password_l);
        btnLogin = findViewById(R.id.btn_login);
        Button btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        //firebase
        reference = FirebaseDatabase.getInstance().getReference(SignUpActivity.REFERENCE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(edtUsername.getText().toString(),edtPassword.getText().toString());
            }
        });
    }

    private void login(final String username, final String password){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 if (dataSnapshot.child(username).exists()){
                     if (!username.isEmpty()){
                         User login = dataSnapshot.child(username).getValue(User.class);
                         if (login.getPassword().equals(password)){
                             Toast.makeText(LoginActivity.this, "Login Succes", Toast.LENGTH_SHORT).show();
                             UserSession.save(getApplicationContext(),"session","true");
                             Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
//                             User sent = dataSnapshot.child(username).getValue(User.class);
                             SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                             Editor editor = prefs.edit();
                             editor.putString(DashboardActivity.USER_PROFILE,username);
                             editor.putString(DashboardActivity.USER_NAME,login.getNama());
                             editor.putString(DashboardActivity.USER_PASSWORD,login.getPassword());
                             editor.putString(DashboardActivity.USER_NAME_TOKO,login.getNamaToko());
                             editor.putString(DashboardActivity.USER_KODE_UNIK,login.getKodeUnik());
                             editor.commit();
//                             intent.putExtra(DashboardActivity.USER_PROFILE,sent);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(intent);
                             finish();
                         }else {
                             Toast.makeText(LoginActivity.this, "Password Salah\nHint\t: "+login.getKodeUnik(), Toast.LENGTH_LONG).show();
                         }
                     }else {
                         Toast.makeText(LoginActivity.this, "Username is not register", Toast.LENGTH_SHORT).show();
                     }
                 }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
