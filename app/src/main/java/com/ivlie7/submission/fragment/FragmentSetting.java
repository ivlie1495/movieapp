package com.ivlie7.submission.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.ivlie7.submission.R;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.presenter.SettingPresenter;
import com.ivlie7.submission.service.reminder.DailyReminder;
import com.ivlie7.submission.service.reminder.UpcomingReminder;
import com.ivlie7.submission.view.SettingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;

public class FragmentSetting extends PreferenceFragmentCompat implements SettingView, Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    @BindString(R.string.setting_locale_key)
    String settingLocaleKey;

    @BindString(R.string.daily_reminder_key)
    String dailyReminderKey;

    @BindString(R.string.upcoming_reminder_key)
    String upcomingReminderKey;

    private SettingPresenter settingPresenter;
    private UpcomingReminder upcomingReminder;
    private DailyReminder dailyReminder;

    private List<Movie> movies = new ArrayList<>();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.setting);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            ButterKnife.bind(this, getActivity());

            upcomingReminder = new UpcomingReminder();
            dailyReminder = new DailyReminder();

            findPreference(dailyReminderKey).setOnPreferenceChangeListener(this);
            findPreference(upcomingReminderKey).setOnPreferenceChangeListener(this);
            findPreference(settingLocaleKey).setOnPreferenceClickListener(this);

            settingPresenter = new SettingPresenter(this, getString(R.string.set_language));
        }
    }

    @Override
    public void setReminder(Movie movie) {
        movies.clear();
        movies.add(movie);
        upcomingReminder.setRepeatReminder(getContext(), movies);
    }

    @Override
    public void cancelReminder() {
        upcomingReminder.cancelReminder(getContext());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();
        boolean isOn = (boolean) o;

        if (key.equals(dailyReminderKey)) {
            if (isOn) {
                dailyReminder.setRepeatReminder(getContext());
            } else {
                dailyReminder.cancelReminder(getContext());
            }
        } else {
            if (isOn) {
                settingPresenter.setReminder();
            } else {
                settingPresenter.cancelReminder();
            }
        }

        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(settingLocaleKey)) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }

        return false;
    }
}
