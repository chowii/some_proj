package com.soho.sohoapp.helper;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by chowii on 25/8/17.
 */

public final class FileWriter {

        public static Uri writeFileToDevice(Context context, Object data, String filename){
            final String DEVICE_INFO_DIR = context.getExternalFilesDir(null) + "/.temp";
            String filenameWithExtension = filename ;
//            + ".json";
            final File devicePath = new File(DEVICE_INFO_DIR);

            Gson gson = new Gson();
            gson.toJson(data);


            if(!devicePath.exists()) devicePath.mkdir();

            final File deviceFile = new File(devicePath, filenameWithExtension);
//            if(deviceFile.exists()) data = appendToExistingFile(context, data, filenameWithExtension);
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
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName().concat(".provider"), deviceFile);

        }

    private static String appendToExistingFile(Context context, String data, String filenameWithExtension) {
        String jsonOld = readFileFromDevice(context, filenameWithExtension).toString();
        StringBuilder builder = new StringBuilder(jsonOld);
        if(data.equalsIgnoreCase("{}")) return builder.toString();
        builder.append(data);
        return builder.toString();
    }

    public static JSONObject readFileFromDevice(Context context, String filename){
            final String DEVICE_INFO_DIR = context.getExternalFilesDir(null) + "/.temp";
            final File devicePath = new File(DEVICE_INFO_DIR);

            String data = "";
            StringBuilder builder = new StringBuilder();
            JSONObject jsonObject = new JSONObject();

            if(devicePath.exists()) {
                String[] fileList = devicePath.list();
                for(String fileName: fileList){
                    if(fileName.equalsIgnoreCase(filename)){
                        try {
                            InputStreamReader fis = new InputStreamReader(new FileInputStream(devicePath.getPath() + "/"+ filename));
                            BufferedReader buffReader = new BufferedReader(fis);
                            while ((data = buffReader.readLine()) != null){
                                builder.append(data);
                            }
                            jsonObject = new JSONObject(builder.toString());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return jsonObject;
        }
}
