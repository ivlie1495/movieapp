package com.ivlie7.submission.config;

import com.ivlie7.submission.util.ApiUtils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    private static String language;

    public ApiConfig(String language) {
        ApiConfig.language = language;
    }

    private static OkHttpClient getInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        ApiKeyInterceptor apiKeyInterceptor = new ApiKeyInterceptor(language);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(apiKeyInterceptor)
                .build();
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Api getService() {
        return getRetrofit().create(Api.class);
    }
}
