package com.example.coffestoreapp.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.coffestoreapp.CustomAdapter.ViewPagerAdapter;
import com.example.coffestoreapp.Fragments.DisplayCategoryFragment;
import com.example.coffestoreapp.Fragments.DisplayHomeFragment;
//import com.example.coffestoreapp.Fragments.DisplayStaffFragment;
//import com.example.coffestoreapp.Fragments.DisplayStatisticFragment;
import com.example.coffestoreapp.Fragments.DisplayTableFragment;
import com.example.coffestoreapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MenuItem selectedFeature, selectedManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    TextView TXT_menu_userName;
    int accessId = 0;
    SharedPreferences sharedPreferences;

    private int[] tabIcons = {
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_home_24,
    };
    public int getAccessId(){
        return accessId;
    }

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view_home);
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        TXT_menu_userName = (TextView) view. findViewById(R.id.txt_menu_userName);


        setSupportActionBar(toolbar); // tao toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opentoggle, R.string.closetoggle){
           @Override
           public void onDrawerOpened(View drawerView){
               super.onDrawerOpened(drawerView);
           }
           @Override
           public void onDrawerClosed(View drawerView){
               super.onDrawerClosed(drawerView);
           }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Tụ động gán tên nv đăng nhập qua Extras
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        TXT_menu_userName.setText("Xin chào " + userName + " !!");

        //lấy file share prefer
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        accessId = sharedPreferences.getInt("maquyen", 0);

        //hiện thị fragment home mặc định
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
        DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
        tranDisplayHome.replace(R.id.contentView, displayHomeFragment);
        tranDisplayHome.commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
                DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
                tranDisplayHome.replace(R.id.contentView, displayHomeFragment);
                tranDisplayHome.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();

                break;

            case R.id.nav_statistic:
                FragmentTransaction tranDisplayStatistic = fragmentManager.beginTransaction();
                //DisplayStatisticFragment displayStatisticFragment = new DisplayStatisticFragment();
                //tranDisplayStatistic.replace(R.id.contentView,displayStatisticFragment);
                tranDisplayStatistic.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();

                break;

            case R.id.nav_table:
                FragmentTransaction tranDisplayTable = fragmentManager.beginTransaction();
                DisplayTableFragment displayTableFragment = new DisplayTableFragment();
                tranDisplayTable.replace(R.id.contentView,displayTableFragment);
                tranDisplayTable.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();

                break;

            case R.id.nav_category:
                FragmentTransaction tranDisplayMenu = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();
                tranDisplayMenu.replace(R.id.contentView, displayCategoryFragment);
                tranDisplayMenu.commit();
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();

                break;

            case R.id.nav_staff:
//                if(accessId == 1){
//                    FragmentTransaction tranDisplayStaff = fragmentManager.beginTransaction();
//                    //DisplayStaffFragment displayStaffFragment = new DisplayStaffFragment();
//                    tranDisplayStaff.replace(R.id.contentView,displayStaffFragment);
//                    tranDisplayStaff.commit();
//                    navigationView.setCheckedItem(item.getItemId());
//                    drawerLayout.closeDrawers();
//                }else {
//                    Toast.makeText(getApplicationContext(),"Bạn không có quyền truy cập",Toast.LENGTH_SHORT).show();
//                }

                break;

            case R.id.nav_logout:
                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
