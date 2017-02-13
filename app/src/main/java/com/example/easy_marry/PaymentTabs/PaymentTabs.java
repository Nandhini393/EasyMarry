package com.example.easy_marry.PaymentTabs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.easy_marry.MessengarTabs.OnlineMemFragment;
import com.example.easy_marry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 14/6/16.
 */
public class PaymentTabs extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView img_menu;
    String str_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        str_index = getIntent().getStringExtra("str_tab_id");

        Log.i("GGG", "str_index : " + str_index);

        img_menu = (ImageView) findViewById(R.id.img_pay_back);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        // tabLayout.getTabAt(Integer.parseInt(str_index));

        TabLayout.Tab tab = tabLayout.getTabAt(Integer.parseInt(str_index));
        tab.select();


        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.i("BG", "str_index : " + tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new CreditCardFragment(), "Credit card");
        adapter.addFragment(new DebitCardFragment(), "Debit card");
        adapter.addFragment(new NetBankingFragment(), "Net banking");
        viewPager.setAdapter(adapter);


    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

