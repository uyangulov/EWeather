package com.example.eweather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadData {

    //method for connection and receiving data
    public static String executeGet(String targetURL) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();


            connection.setDoInput(true);
            InputStream in;
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)
                in = connection.getErrorStream();
            else
                in = connection.getInputStream();

            //read data stream from server
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error ";
        } finally {
            if(connection != null) {
                connection.disconnect();

            }

        }

    }
}
