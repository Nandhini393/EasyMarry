package com.example.easy_marry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.genericclasses.GeneralData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by android2 on 17/11/16.
 */
public class TestUti extends Activity {
    Button btn_phone,btn_msg;
    Context context;
    public static boolean ASC = true;
    public static boolean DESC = false;
    AnimationDrawable loadingViewAnim;
    TextView loadigText;
    ImageView loadigIcon;
    LinearLayout loadingLayout;
    AlertDialog alertDialog;
    AlertDialog altDialog_load;
    GeneralData gD;
    ArrayList<ListDrawerBean> myList;
    SharedPreferenceOwn ap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_uti);
        context=this;
        gD= new GeneralData(context);

        myList= new ArrayList<ListDrawerBean>();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        ListDrawerBean lb= new ListDrawerBean();
        lb.setStr_list_items("nandhu");
        myList.add(lb);
        String json = gson.toJson(myList);
        Log.e("MK","one-->"+ String.valueOf(json));
        editor.putString("MK", json);
        editor.commit();


        SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson1 = new Gson();
        String json1 = sharedPrefs1.getString("MK", null);
        Type type = new TypeToken<ArrayList<ListDrawerBean>>() {}.getType();
        ArrayList<ListDrawerBean> arrayList = gson1.fromJson(json1, type);

        Log.e("MK", "two_arraylist-->"+ String.valueOf(arrayList));
        Log.e("MK", "two-->"+ String.valueOf(json1));


      /*  ap=new SharedPreferenceOwn();
        ListDrawerBean lb= new ListDrawerBean();
        lb.setStr_list_items("nandhu");
        lb.setStr_list_items("nandhu1");
        lb.setStr_list_items("kkk");
        ap.addFavorite(context,lb);

        myList.add(lb);
        System.out.println(lb);
        Log.e("LY", "adding->"+myList);
        ap.loadFavorites(context);
        Log.e("LY", "load->"+myList);
        ap.storeFavorites(context,myList);
        Log.e("LY", "storing->"+myList);
        ap.removeFavorite(context,lb);
        Log.e("LY", "aftr_removin->"+myList);
        ListDrawerBean lb1= myList.get(0);*/

        Calendar cal = Calendar.getInstance();

        TimeZone tz = cal.getTimeZone();

        Log.d("Time zone","="+tz.getDisplayName());

// Make the two lists
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4);
        List<Integer> list2 = Arrays.asList(2, 3, 4, 6, 7);
// Prepare a union
        List<Integer> union = new ArrayList<Integer>(list1);
        union.addAll(list2);
        Log.e("NN", String.valueOf(union));

// Prepare an intersection
        List<Integer> intersection = new ArrayList<Integer>(list1);
        intersection.retainAll(list2);
        Log.e("NN", String.valueOf(intersection));
// Subtract the intersection from the union
        union.removeAll(intersection);
        Log.e("NN", String.valueOf(union));
// Print the result
        for (Integer n : union) {
            System.out.println(n);
            Log.e("NN", String.valueOf(n));
        }
        //gD.callload(context);


       /* View itemView1;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        itemView1 = LayoutInflater.from(context)
                .inflate(R.layout.custom_image_load, null);
        altDialog_load = builder.create();
        altDialog_load.setView(itemView1);
        altDialog_load.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadingLayout = (LinearLayout) itemView1.findViewById(R.id.LinearLayout1);
        //loadigText = (TextView) itemView1.findViewById(R.id.textView111);
        loadigIcon = (ImageView) itemView1.findViewById(R.id.imageView111);
        loadigIcon.setBackgroundResource(R.drawable.loading_anim);
        loadingViewAnim = (AnimationDrawable) loadigIcon.getBackground();
        //loadigText.setText("Registering..Please wait");
        loadingViewAnim.start();
        altDialog_load.show();*/





       /* context=this;
        HashMap<String, Integer> unsortMap = new HashMap<String, Integer>();
        unsortMap.put("B", 55);
        unsortMap.put("A", 80);
        unsortMap.put("D", 20);
        unsortMap.put("C", 70);

        System.out.println("Before sorting......");
        printMap(unsortMap);

        System.out.println("After sorting ascending order......");
        Map<String, Integer> sortedMapAsc = sortByComparator(unsortMap, ASC);
        printMap(sortedMapAsc);


        System.out.println("After sorting descindeng order......");
        Map<String, Integer> sortedMapDesc = sortByComparator(unsortMap, DESC);
        printMap(sortedMapDesc);*/
    }
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(Map<String, Integer> map)
    {
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
        }
    }

}
