package com.ivlie7.submission.service.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoveViewsFactory(this.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
