package com.soho.sohoapp.network;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by williamma on 15/06/2016.
 */
public class AddAuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        String authToken = DEPENDENCIES.getPreferences().getAuthToken();
        Request.Builder requestBuilder = request.newBuilder();
        if(!authToken.equals("")) {
            requestBuilder.addHeader("Authorization", authToken);
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

}
