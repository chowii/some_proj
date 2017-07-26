package com.soho.sohoapp.dev.network;


import com.soho.sohoapp.dev.helper.SharedPrefsHelper;

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
                .addHeader("X-User-Auth-Token", authToken)
                .build();

        return chain.proceed(newRequest);
    }

}
