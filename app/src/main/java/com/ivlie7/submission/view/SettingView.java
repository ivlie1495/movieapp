package com.ivlie7.submission.view;

import com.ivlie7.submission.model.Movie;

public interface SettingView {
    void setReminder(Movie movie);
    void cancelReminder();
}
