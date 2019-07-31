package com.ivlie7.submission.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.ViewPagerAdapter;

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
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addTabFragment(new FragmentFavouriteMovie(), getString(R.string.movie));
        adapter.addTabFragment(new FragmentFavouriteTv(), getString(R.string.tv_show));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.setting_menu, menu);
    }
}
