package com.ivan.boldyrev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    HashMap<Integer, List<Result>> results = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager(), getLifecycle());
        ViewPager2 pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ViewPager2 pager = findViewById(R.id.pager);
                Button button = findViewById(R.id.back_button);
                ButtonState state = new ButtonState();
                state.changeButtonState(button, pager, position);
            }
        });

        for (int i = 0; i < 3; i++){
            results.put(i, new ArrayList<>());
        }

        TabLayout tabLayout = findViewById(R.id.tablayout);
        new TabLayoutMediator(tabLayout, pager, (tab, position) -> tab.setText(getTabName(position))).attach();
    }

    public void clickNext(View view){
        ViewPager2 pager = findViewById(R.id.pager);
        Request request = new Request();
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonNext = findViewById(R.id.next_button);
        results = request.request(this, pager, results, buttonBack, buttonNext);
    }

    public void clickBack(View view){
        ViewPager2 pager = findViewById(R.id.pager);
        Request request = new Request();
        Button buttonBack = findViewById(R.id.back_button);
        request.requestBack(this, pager, results, buttonBack);
    }

    public String getTabName(int position){
        switch (position){
            case 1:
                return "Горячие";
            case 2:
                return "Последние";
            default:
                return "Лучшие";
        }
    }
}

class ViewAdapter extends FragmentStateAdapter {
    ViewAdapter(@NonNull FragmentManager fragment, Lifecycle lifecycle){
        super(fragment, lifecycle);
        this.fragment1 = new Fragment1();
        this.fragment2 = new Fragment2();
        this.fragment3 = new Fragment3();
        hashMap.put(0, this.fragment1);
        hashMap.put(1, this.fragment2);
        hashMap.put(2, this.fragment3);
    }
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    protected HashMap<Integer, Fragment> hashMap = new HashMap<>();
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return fragment2;
            case 2:
                return fragment3;
            default:
                return fragment1;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}