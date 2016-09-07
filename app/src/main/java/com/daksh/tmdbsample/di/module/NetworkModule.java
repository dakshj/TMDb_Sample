package com.daksh.tmdbsample.di.module;

import com.daksh.tmdbsample.BuildConfig;
import com.daksh.tmdbsample.data.source.remote.TmdbApi;
import com.daksh.tmdbsample.data.source.remote.interceptor.ApiKeyInsertionInterceptor;
import com.daksh.tmdbsample.data.source.remote.interceptor.LanguageCodeInsertionInterceptor;
import com.daksh.tmdbsample.util.Logger;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daksh on 03-Sep-16.
 */

@Module
public class NetworkModule {

    private static final int TIMEOUT = 60;

    private String baseUrl;
    private String apiKey;

    public NetworkModule(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Provides
    @Singleton
    ApiKeyInsertionInterceptor provideApiKeyInterceptor() {
        return new ApiKeyInsertionInterceptor(apiKey);
    }

    @Provides
    @Singleton
    Locale provideLocale() {
        return Locale.getDefault();
    }

    @Provides
    @Singleton
    LanguageCodeInsertionInterceptor provideLanguageCodeInsertionInterceptor(Locale locale) {
        return new LanguageCodeInsertionInterceptor(locale.getISO3Language());
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.networkLog(message);
            }
        }).setLevel(Level.BODY);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ApiKeyInsertionInterceptor apiKeyInsertionInterceptor,
            LanguageCodeInsertionInterceptor languageCodeInsertionInterceptor,
            HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(apiKeyInsertionInterceptor)
                .addInterceptor(languageCodeInsertionInterceptor);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    TmdbApi provideTmdbApi(Retrofit retrofit) {
        return retrofit.create(TmdbApi.class);
    }
}
