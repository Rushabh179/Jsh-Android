package com.project.rushabh.jarvis;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Rushabh on 04-Oct-17.
 */

public class ApplianceAdd extends AsyncTask<String,Void,Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String room_name,device_name;
            room_name = params[0];
            device_name = params[1];


            String link = "http://192.168.1.36:8080/Jarvis/adddevice.php";
            String data;
            data = URLEncoder.encode("room_name", "UTF-8") + "=" +
                    URLEncoder.encode(room_name, "UTF-8");
            data += "&" +URLEncoder.encode("device_name", "UTF-8") + "=" +
                    URLEncoder.encode(device_name, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            return reader != null;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}