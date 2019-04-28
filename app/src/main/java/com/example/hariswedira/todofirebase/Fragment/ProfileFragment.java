package com.example.hariswedira.todofirebase.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.DashboardActivity;
import com.example.hariswedira.todofirebase.Data.Barang;
import com.example.hariswedira.todofirebase.Data.User;
import com.example.hariswedira.todofirebase.LoginActivity;
import com.example.hariswedira.todofirebase.SignUpActivity;
import com.example.hariswedira.todofirebase.Utils.UserSession;
import com.example.hariswedira.todofirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment{
    private EditText edtNama,edtUsername, edtNamaToko, edtPassword, edtKodeUnik;
    private Button logout,update;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String userUsername = settings.getString(DashboardActivity.USER_PROFILE,"");
        String userNama = settings.getString(DashboardActivity.USER_NAME,"");
        String userNameToko = settings.getString(DashboardActivity.USER_NAME_TOKO,"");
        String userPassword = settings.getString(DashboardActivity.USER_PASSWORD,"");
        String userKodeUnik = settings.getString(DashboardActivity.USER_KODE_UNIK,"");

        final String updateChild = userUsername;

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE).child(userUsername);

        edtNama = view.findViewById(R.id.edt_nama_profile);
        edtKodeUnik = view.findViewById(R.id.edt_kode_unik);
        edtNamaToko = view.findViewById(R.id.edt_nama_toko_profile);
        edtUsername = view.findViewById(R.id.edt_username_profile);
        edtPassword = view.findViewById(R.id.edt_password_profile);

        edtNama.setText(userNama);
        edtNamaToko.setText(userNameToko);
        edtUsername.setText(userUsername);
        edtUsername.setVisibility(View.GONE);
        edtPassword.setText(userPassword);
        edtKodeUnik.setText(userKodeUnik);

        logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                UserSession.save(getContext(),"session","false");
                                Intent intentLog = new Intent(getContext(), LoginActivity.class);
                                startActivity(intentLog);
                                getActivity().finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
//                                Intent intentBack = new Intent(getContext(),DashboardActivity.class);
                                Toast.makeText(getActivity().getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
//                                intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intentBack);
//                                getActivity().finish();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Yakin mau keluar ?").setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener).show();
            }
        });

        update = view.findViewById(R.id.btnUpdate3);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child("nama").setValue(edtNama.getText().toString());
                reference.child("username").setValue(edtUsername.getText().toString());
                reference.child("password").setValue(edtPassword.getText().toString());
                reference.child("namaToko").setValue(edtNamaToko.getText().toString());
                reference.child("kodeUnik").setValue(edtKodeUnik.getText().toString());

                SharedPreferences.Editor editor = settings.edit();
                editor.putString(DashboardActivity.USER_NAME,edtNama.getText().toString());
                editor.putString(DashboardActivity.USER_KODE_UNIK,edtKodeUnik.getText().toString());
                editor.putString(DashboardActivity.USER_PASSWORD,edtPassword.getText().toString());
                editor.putString(DashboardActivity.USER_NAME_TOKO,edtNamaToko.getText().toString());
                editor.putString(DashboardActivity.USER_PROFILE,edtUsername.getText().toString());
                editor.apply();

                final DatabaseReference referenceU = FirebaseDatabase.getInstance().getReference().child(SignUpActivity.REFERENCE);
//                referenceU.child(updateChild)

                Toast.makeText(getContext(), "Berhasil ubah profil", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
