package com.ivlie7.submission.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.ViewPagerAdapter;
import com.ivlie7.submission.fragment.FragmentFavourite;
import com.ivlie7.submission.fragment.FragmentMovie;
import com.ivlie7.submission.fragment.FragmentTvShow;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.viewPagerBot)
    ViewPager viewPagerBot;

    private MenuItem menuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigationMovie:
                    viewPagerBot.setCurrentItem(0);
                    return true;
                case R.id.navigationTV:
                    viewPagerBot.setCurrentItem(1);
                    return true;
                case R.id.navigationFavorite:
                    viewPagerBot.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.navigationMovie);
        }

        viewPagerBot.addOnPageChangeListener(this);
        setupViewPager(viewPagerBot);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addBotFragment(new FragmentMovie());
        adapter.addBotFragment(new FragmentTvShow());
        adapter.addBotFragment(new FragmentFavourite());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottomNavigation.getMenu().getItem(0).setChecked(false);
        }
        bottomNavigation.getMenu().getItem(i).setChecked(true);
        menuItem = bottomNavigation.getMenu().getItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
