package com.daksh.tmdbsample.data.source.remote.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by daksh on 03-Sep-16.
 */
public class LanguageCodeInsertionInterceptor implements Interceptor {

    private final String languageCode;

    public LanguageCodeInsertionInterceptor(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("language", languageCode).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
