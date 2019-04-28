package com.example.hariswedira.todofirebase.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hariswedira.todofirebase.R;

public class HomeFragment extends Fragment{

//    private TabLayout tabLayout;
//    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        Button intentApp = view.findViewById(R.id.btn_intent);
        intentApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.user.piecharttutorial");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });
//        tabLayout = view.findViewById(R.id.tab_layout);
//        viewPager = view.findViewById(R.id.view_pager);
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
//        adapter.addFragment(new ReportMainFragment(),"Laporan");
//        adapter.addFragment(new ReportChildOneFragment(),"Laporan Mingguan");
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
