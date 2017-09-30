package com.project.rushabh.jarvis;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Rushabh on 30-Sep-17.
 */

public class UserNameList extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        try {

            String link = "http://192.168.1.36:8080/Jarvis/userlist.php";

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            // Read Server Response
            int i=0;
            line = reader.readLine();
            while (line != null && !line.equalsIgnoreCase("")) {
                sb.append(line);
                //list[i]=sb.toString();
                line = reader.readLine();
                i++;
            }
            return sb.toString();

        } catch (Exception e) {
            return e.toString();
        }
    }
}