package com.project.rushabh.jarvis;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Rushabh on 01-Oct-17.
 */

public class HomeRoomList extends AsyncTask <String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        try {

            String link = "http://192.168.43.101:8080/Jarvis/roomlist.php";

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            line = reader.readLine();
            while (line != null && !line.equalsIgnoreCase("")) {
                sb.append(line);
                line = reader.readLine();
            }
            return sb.toString();

        } catch (Exception e) {
            return e.toString();
        }
    }
}