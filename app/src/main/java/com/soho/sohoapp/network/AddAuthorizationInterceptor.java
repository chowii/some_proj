package com.soho.sohoapp.network;


import com.soho.sohoapp.helper.SharedPrefsHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by williamma on 15/06/2016.
 */
public class AddAuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        String authToken = SharedPrefsHelper.Companion.getInstance().getAuthToken();
        Request newRequest = request.newBuilder()
                .addHeader("Authorization", "TKCpRQp8fb9Ycg6CbzqgfQqu")
                .build();

        return chain.proceed(newRequest);
    }

}
