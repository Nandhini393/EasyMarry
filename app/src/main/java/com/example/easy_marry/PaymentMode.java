package com.example.easy_marry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;

import com.example.easy_marry.PaymentTabs.CreditCardFragment;
import com.example.easy_marry.PaymentTabs.PaymentTabs;

/**
 * Created by android2 on 22/6/16.
 */
public class PaymentMode extends FragmentActivity {
    SlidingDrawer slide_drawer;
    Context context;
    LinearLayout ll_creditCard,ll_debitCard,ll_netBank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_mode);
        context=this;
        ll_creditCard=(LinearLayout)findViewById(R.id.ll_credit_card);
        ll_debitCard=(LinearLayout)findViewById(R.id.ll_debit_card);
        ll_netBank=(LinearLayout)findViewById(R.id.ll_net_banking);
        ll_creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentMode.this, PaymentTabs.class);
                i.putExtra("str_tab_id","0");
                startActivity(i);

               /* Fragment frag = new CreditCardFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.,frag,"Test Fragment");
                transaction.commit();*/
            }
        });
        ll_debitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentMode.this, PaymentTabs.class);
                i.putExtra("str_tab_id","1");
                startActivity(i);

               /* Fragment frag = new CreditCardFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.,frag,"Test Fragment");
                transaction.commit();*/
            }
        });
        ll_netBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentMode.this, PaymentTabs.class);
                i.putExtra("str_tab_id","2");
                startActivity(i);

               /* Fragment frag = new CreditCardFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.,frag,"Test Fragment");
                transaction.commit();*/
            }
        });

        //slide_drawer = (SlidingDrawer) findViewById(R.id.slidingDrawer_chat);
        /*final LinearLayout slider_layout = (LinearLayout) findViewById(R.id.linearLayout);
        slide_drawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                slider_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });*/
    }
}
