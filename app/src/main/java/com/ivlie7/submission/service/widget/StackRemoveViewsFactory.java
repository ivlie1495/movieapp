package com.ivlie7.submission.service.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ivlie7.submission.R;
import com.ivlie7.submission.model.Movie;
import com.ivlie7.submission.constant.ApiConstants;

import java.util.concurrent.ExecutionException;

import static com.ivlie7.submission.config.DatabaseProvider.URI_MOVIE;

class StackRemoveViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;

    StackRemoveViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }

        long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(URI_MOVIE, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(position)) {
            return null;
        }

        Movie movie = getItem(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;

        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(ApiConstants.API_POSTER + movie.getPosterPath())
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.imageViewWidget, bitmap);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private Movie getItem(int i) {
        if (!cursor.moveToPosition(i)) {
            throw new IllegalStateException("No Position Found!");
        }

        return new Movie(cursor);
    }
}
