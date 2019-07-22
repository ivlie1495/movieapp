package com.ivlie7.favourite;

import android.database.Cursor;

interface MovieCallback {
    void postExecute(Cursor cursor);
}
