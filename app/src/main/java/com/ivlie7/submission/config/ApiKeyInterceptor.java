package com.ivlie7.submission.config;

import android.support.annotation.NonNull;

import com.ivlie7.submission.constant.ApiConstants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class ApiKeyInterceptor implements Interceptor {

    private String language;

    ApiKeyInterceptor(String language) {
        this.language = language;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        HttpUrl httpUrl = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", ApiConstants.API_KEY)
                .addQueryParameter("language", language)
                .build();

        Request request = chain.request().newBuilder().url(httpUrl).build();

        return chain.proceed(request);
    }
}
