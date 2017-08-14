package com.soho.sohoapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.soho.sohoapp.Constants;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jovan on 12/4/17.
 */

public class ApiClient {

    private static SohoService service;

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new AddAuthorizationInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(new StatusCodeInterceptor())
            .build();

    public static Retrofit retrofit =
            new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constants.getENDPOINT())
                    .addConverterFactory(buildGson())
                    .client(okHttpClient)
                    .build();

    public static SohoService getService() {
        if (service == null) {
            service = retrofit.create(SohoService.class);
        }
        return service;
    }

    public static GsonConverterFactory buildGson() {
        return GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new DataTypeAdapterFactory()).create());
    }

    public static class DataTypeAdapterFactory implements TypeAdapterFactory {

        public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

            return new TypeAdapter<T>() {

                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                public T read(JsonReader in) throws IOException {

                    JsonElement jsonElement = elementAdapter.read(in);
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.has("data"))
                        if(jsonObject.get("data").isJsonObject()) {
                            jsonElement = jsonObject.get("data");
                        } else if(jsonObject.get("data").isJsonArray() && type.getRawType().isAssignableFrom(List.class)) {
                            jsonElement = jsonObject.get("data");
                        }
                    }

                    return delegate.fromJsonTree(jsonElement);
                }
            }.nullSafe();
        }
    }
}