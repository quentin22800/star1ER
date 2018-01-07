package com.example.a17009495.star1er;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import classes.DownloadAsyncTask;
import classes.InsertThread;
import fr.istic.starproviderER.DataSource;

public class MainActivity extends AppCompatActivity {

    private DataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int testDl = 0;
        super.onCreate(savedInstanceState);
        this.deleteDatabase("star.db");
        Intent current = getIntent();
        if(current.getIntExtra("telechargement", 1) == 0)
        {
            testDl = 1;
        }
        else
        {
            Intent intent = new Intent(this, StarService.class);
            startService(intent);
        }
        setContentView(R.layout.activity_main);

        ds = new DataSource(getApplicationContext());
        ds.open();

        File filesDir = getFilesDir();
        File fl = new File(filesDir, "jsonbases.json");
        boolean deleted = fl.delete();

        File flzip = new File(filesDir, "data.zip");
        boolean deletezip = fl.delete();
        if(testDl == 1)
        {
            downloadFileFromWeb("https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=json&timezone=Europe/Berlin");
        }
    }

    public void DownloadZip(String url) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        /*
        * il faut tester d’abord la connexion internet
        */
        if (networkInfo != null && networkInfo.isConnected()) {
            DownloadAsyncTask task = new DownloadAsyncTask(this, new DownloadAsyncTask.TaskListener() {
                @Override
                public void onFinished(byte[] result) {
                    File filesDir = getFilesDir();
                    File fl = new File(filesDir, "data.zip");
                    try {
                        FileOutputStream outputStream = new FileOutputStream (fl);
                        outputStream.write(result);
                        outputStream.flush();
                        outputStream.close();
                        Log.v("test", "Fin écriture zip");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            task.execute(url);
            InsertThread insertionThread = new InsertThread(MainActivity.this,getFilesDir(),ds, new InsertThread.TaskListener() {
                @Override
                public void onFinished(Integer result) {
                    if(result ==1)
                    {
                        Log.v("test", "insertions finies");
                    }
                }
            });
            insertionThread.execute();
        } else {
            Log.e("download", "Connexion réseau indisponible.");
        }
    }
    public void downloadFileFromWeb(String url) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        /*
        * il faut tester d’abord la connexion internet
        */
        if (networkInfo != null && networkInfo.isConnected()) {

                DownloadAsyncTask task = new DownloadAsyncTask(this, new DownloadAsyncTask.TaskListener() {
                    @Override
                    public void onFinished(byte[] result) {
                        Log.v("test", "dl");
                        try {
                            File filesDir = getFilesDir();
                            File fl = new File(filesDir, "jsonbases.json");
                            FileOutputStream outputStream = new FileOutputStream (fl);
                            outputStream.write(result);
                            outputStream.flush();
                            outputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            String res = loadJSON("jsonbases.json");
                            Log.v("test", res);
                            JSONArray js = null;
                            try {
                                js = new JSONArray(res);
                                for (int i = 0; i < js.length(); i++) {
                                    if(i == 0)
                                    {
                                        JSONObject jsonobject = js.getJSONObject(i);
                                        JSONObject secondline = jsonobject.getJSONObject("fields");
                                        String url = secondline.getString("url");
                                        DownloadZip(url);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                task.execute(url);
        } else {
            Log.e("download", "Connexion réseau indisponible.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public String getStringFromFile (String filePath) throws Exception {
        File filesDir = getFilesDir();
        File fl = new File(filesDir, filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public String loadJSON(String filePath) {
        String json = null;
        try {
            File filesDir = getFilesDir();
            File fl = new File(filesDir, filePath);
            InputStream is = new FileInputStream(fl);
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}