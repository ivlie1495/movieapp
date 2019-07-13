package com.ivlie7.submission.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivlie7.submission.R;
import com.ivlie7.submission.adapter.ViewPagerFavouriteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavourite extends Fragment {

    @BindView(R.id.tabLayoutFavourite)
    TabLayout tabLayoutFavourite;

    @BindView(R.id.viewPagerFavourite)
    ViewPager viewPagerFavourite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        ViewPagerFavouriteAdapter adapter = new ViewPagerFavouriteAdapter(getFragmentManager());
        adapter.addTabFragment(new FragmentFavouriteMovie(), getString(R.string.movie));
        adapter.addTabFragment(new FragmentFavouriteTv(), getString(R.string.tv_show));
        viewPager.setAdapter(adapter);
    }
}
