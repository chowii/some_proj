package com.soho.sohoapp.network;

/**
 * Created by chowii on 25/7/17.
 */

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.Response;

public class SohoError {

    @SerializedName("error") public String error;
    public boolean relogRequired = false;

    public SohoError() {
        error = "Failed to complete request, please try again.";
    }

    public String getMessage() {
        if(error == null) {
            return "Failed to complete request, please try again.";
        }
        return error;
    }

    public static SohoError fromResponse(Response response) {
        SohoError error = new SohoError();
        try {
            String errorBodyString = response.body().string();
            error = fromJson(errorBodyString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(error == null) {
            error = new SohoError();
        }
        return error;
    }

    public static SohoError fromJson(String json) {
        try {
            return new Gson().fromJson(json, SohoError.class);
        } catch(Exception e) {
            return new SohoError();
        }
    }

}
