package com.example.hariswedira.todofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hariswedira.todofirebase.Data.User;
import com.example.hariswedira.todofirebase.Fragment.HomeFragment;
import com.example.hariswedira.todofirebase.Fragment.HutangFragment;
import com.example.hariswedira.todofirebase.Fragment.LinkFragment;
import com.example.hariswedira.todofirebase.Fragment.ProfileFragment;
import com.example.hariswedira.todofirebase.Fragment.TransaksiFragmnet;
import com.example.hariswedira.todofirebase.Utils.UserSession;

public class DashboardActivity extends AppCompatActivity {
    public final static String USER_PROFILE = "urser profile";
    public final static String USER_NAME = "urser nama";
    public final static String USER_NAME_TOKO = "urser nama toko";
    public final static String USER_PASSWORD = "password";
    public final static String USER_KODE_UNIK = "kode unik";

    boolean session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottonNav = findViewById(R.id.bottom_nav);
        bottonNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        sessions();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            final User userData = getIntent().getParcelableExtra(USER_PROFILE);

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_tran:
                    selectedFragment = new TransaksiFragmnet();
                    break;
                case R.id.nav_link:
                    selectedFragment = new LinkFragment();
                    break;
                case R.id.nav_hutang:
                    selectedFragment = new HutangFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };

    public void sessions(){
        session = Boolean.valueOf(UserSession.read(getApplicationContext(),"session","false"));
        if (!session){
            Intent intentSession = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intentSession);
            finish();
        }else {
//            Toast.makeText(this, "You Is Logged In", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
