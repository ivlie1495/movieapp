package com.ivlie7.submission.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.ViewPagerAdapter;
import com.ivlie7.submission.ui.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavourite extends Fragment {

    @BindView(R.id.tabLayoutFavourite)
    TabLayout tabLayoutFavourite;

    @BindView(R.id.viewPagerFavourite)
    ViewPager viewPagerFavourite;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tabLayoutFavourite.setupWithViewPager(viewPagerFavourite);
        setupViewPager(viewPagerFavourite);

        setHasOptionsMenu(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addTabFragment(new FragmentFavouriteMovie(), getString(R.string.movie));
        adapter.addTabFragment(new FragmentFavouriteTv(), getString(R.string.tv_show));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.setting_menu, menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_change_settings) {
//            Intent intent = new Intent(getContext(), SettingActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
