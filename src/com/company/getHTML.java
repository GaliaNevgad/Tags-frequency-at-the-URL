package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class getHTML {

    public String getHTML(URL url) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        URLConnection urlConnection;
        BufferedReader inputBufferReader = null;

        try{
            int a;
            urlConnection = url.openConnection();
            inputBufferReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            while((a = inputBufferReader.read()) != -1){
                stringBuilder.append((char)a);
            }
        } finally {
            if(inputBufferReader != null) inputBufferReader.close();
        }
        return stringBuilder.toString();
    }
}
