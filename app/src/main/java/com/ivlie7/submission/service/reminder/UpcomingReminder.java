package com.ivlie7.submission.service.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.ivlie7.submission.R;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.presenter.SettingPresenter;
import com.ivlie7.submission.ui.MainActivity;
import com.ivlie7.submission.util.DateUtils;
import com.ivlie7.submission.view.SettingView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpcomingReminder extends BroadcastReceiver implements SettingView {

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "1";
    private static CharSequence NOTIFICATION_CHANNEL_NAME = "Movie Channel";
    private List<Movie> upcomingMovieList = new ArrayList<>();

    public UpcomingReminder() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SettingPresenter settingPresenter = new SettingPresenter(this, context.getString(R.string.set_language));
        settingPresenter.getUpcomingMovie(context);
    }

    private void showReminderNotification(Context context, String title, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri notificationRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_movie)
                .setContentTitle(title)
                .setContentText((String.format(context.getString(R.string.upcoming_reminder_message), title)))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(notificationRingtone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(notificationId, builder.build());
    }

    public void setRepeatReminder(Context context) {
        cancelReminder(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpcomingReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (SDK_INT > Build.VERSION_CODES.KITKAT && SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, UpcomingReminder.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void getUpcomingMovieList(Context context, List<Movie> movies) {
        int notificationId = 1;

        upcomingMovieList.clear();
        upcomingMovieList.addAll(movies);

        for (Movie movie : upcomingMovieList) {
            if (movie.getReleaseDate().equals(DateUtils.getCurrentDate())) {
                showReminderNotification(context, movie.getTitle(), notificationId);
                notificationId++;
            }
        }
    }
}
