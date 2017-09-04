package com.soho.sohoapp.helper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.soho.sohoapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chowii on 25/8/17.
 */

public final class FileWriter<T> {

    public static Uri writeFileToDevice(Context context, Map data, String filename){
        final String DEVICE_INFO_DIR = context.getExternalFilesDir(null) + "/.temp";
        final File devicePath = new File(DEVICE_INFO_DIR);
        if(!devicePath.exists()) devicePath.mkdir();

        Gson gson = new Gson();

        final File deviceFile = new File(devicePath, filename);
        if(deviceFile.exists()) data = appendToExistingFile(context, data, filename);
        else data = getMapList(data);
        try{
            Log.v("LOG_TAG---","createFile(): " + "Json: file created " + deviceFile.createNewFile());
            FileOutputStream fos = new FileOutputStream(deviceFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.append(gson.toJson(data));
            osw.flush();
            osw.close();

        } catch (Exception ioException){
            Log.d("LOG_TAG---", "Could not create debug file");
        }

        return FileProvider.getUriForFile(context, context.getString(R.string.provider_authorities), deviceFile);
    }

    @NonNull
    private static Map getMapList(Map data) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        dataList.add(data);
        Map<String, Object> dataSet = new HashMap<>();
        dataSet.put("data", dataList);
        data = dataSet;
        return data;
    }

    private static Map appendToExistingFile(Context context, Map data, String filename) {
        Map<String, Object> existingData = readFileFromDevice(context, filename);
        List<Map> dataList = ((List<Map>) existingData.get("data"));
        dataList.add(data);
        return existingData;
    }

    public static Map readFileFromDevice(Context context, String filename){
        final String DEVICE_INFO_DIR = context.getExternalFilesDir(null) + "/.temp";
        final File devicePath = new File(DEVICE_INFO_DIR);

        Type t = new TypeToken<Map<String, Object>>(){}.getType();
        JsonReader reader = null;

        try {
            reader = new JsonReader(new FileReader(devicePath.getPath() + "/"+ filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(reader, t);
    }

    public static Object readFileFromDevice(Context context, String filename, Type type){
        final String DEVICE_INFO_DIR = context.getExternalFilesDir(null) + "/.temp";
        final File devicePath = new File(DEVICE_INFO_DIR);


        JsonReader reader = null;

        try {
            reader = new JsonReader(new FileReader(devicePath.getPath() + "/"+ filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(reader, type);
    }
}
