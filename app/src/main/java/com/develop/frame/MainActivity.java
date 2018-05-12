package com.develop.frame;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.develop.frame.demo.MainFragment;

/**
 * Created by yangjh on 2018/4/24.
 */

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameInitCenter.init(getApplication(),"https://api.douban.com/v2/");

        DouBanGetter getter = new DouBanGetter();
        getter.gett();


        setDefaultFragment();
    }

    private void setDefaultFragment()
    {

        mainFragment = new MainFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentLayout, mainFragment).commit();
    }
}
