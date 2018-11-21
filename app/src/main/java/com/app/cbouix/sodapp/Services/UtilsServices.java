package com.app.cbouix.sodapp.Services;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CBouix on 21/04/2017.
 */

public class UtilsServices {

    private static final int TIMEOUT = 10000;

    //Metodo GET
    public static InputStream getStream(String strUrl) throws IOException {
        URL url = new URL(strUrl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(TIMEOUT);

        //TODO chequear y devolver error code
        int responseCode = urlConnection.getResponseCode();

        // Reading data from url
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        return in;
    }
    //Metodo POST con parametros
    public static InputStream getStreamPost(String strUrl, String jsonParam) throws IOException {
        URL url = new URL(strUrl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setConnectTimeout(TIMEOUT);

        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(jsonParam);

        //TODO chequear y devolver error code
        int responseCode = urlConnection.getResponseCode();

        // Reading data from url
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        return in;
    }

    //Metodo POST
    public static InputStream getStreamPost(String strUrl) throws IOException {
        URL url = new URL(strUrl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setConnectTimeout(TIMEOUT);

        //TODO chequear y devolver error code
        int responseCode = urlConnection.getResponseCode();

        // Reading data from url
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        return in;
    }

    //Metodo PUT con parametros
    public static InputStream getStreamPut(String strUrl, String jsonParam) throws IOException {
        URL url = new URL(strUrl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("PUT");
        urlConnection.setDoOutput(true);
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setConnectTimeout(TIMEOUT);

        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(jsonParam);

        //TODO chequear y devolver error code
        int responseCode = urlConnection.getResponseCode();

        // Reading data from url
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        return in;
    }

    //Metodo PUT
    public static InputStream getStreamPut(String strUrl) throws IOException {
        URL url = new URL(strUrl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("PUT");
        urlConnection.setDoOutput(true);
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setConnectTimeout(TIMEOUT);

        //TODO chequear y devolver error code
        int responseCode = urlConnection.getResponseCode();

        // Reading data from url
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        return in;
    }
}
