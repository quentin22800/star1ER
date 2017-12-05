package com.example.a17009495.star1er;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import classes.BusRoute;
import classes.DownloadAsyncTask;
import classes.Parses;
import fr.istic.starproviderER.DataSource;

public class MainActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private DataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.deleteDatabase("star.db");
        setContentView(R.layout.activity_main);

        ds = new DataSource(getApplicationContext());
        ds.open();

        File filesDir = getFilesDir();
        File fl = new File(filesDir, "jsonbases.json");
        boolean deleted = fl.delete();

        if(deleted)
        {
            Log.v("test", "fichier delete");
        }

        File flzip = new File(filesDir, "data.zip");
        boolean deletezip = fl.delete();

        if(deletezip)
        {
            Log.v("test", "zip delete");
        }

        downloadFileFromWeb("https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=json&timezone=Europe/Berlin");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

                    unZipIt(fl,filesDir);
                    Parses ps = new Parses(ds);
                    File f1 = new File(filesDir, "jsonbases.json");
                    boolean deleted = f1.delete();
                    File flzip = new File(filesDir, "data.zip");
                    boolean deletezip = flzip.delete();
                    for (final File fileEntry : filesDir.listFiles()) {
                        switch (fileEntry.getName())
                        {
                            case "routes.txt":
                                ps.parseRoute(fileEntry);
                                break;
                            case "stops.txt":
                                ps.parseStop(fileEntry);
                                break;
                            case "trips.txt":
                                ps.parseTrips(fileEntry);
                                break;
                            case "stop_times.txt":
                                ps.parseStopTime(fileEntry);
                                break;
                            case "calendar.txt":
                                ps.parseCalendar(fileEntry);
                        }
                    }
                }
            });
            task.execute(url);
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
                            Log.v("test", "Fin écriture");
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void ok(){
        Log.i("ok","ok");
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


    /**
     * Unzip it
     * @param zipFile input zip file
     * @param outputFolder zip file output folder
     */
    public void unZipIt(File zipFile, File outputFolder){
        List<String> fileList;
        byte[] buffer = new byte[1024];

        try{

            //create output directory is not exists
            File folder = outputFolder;
            if(!folder.exists()){
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                newFile.delete();
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
            Log.v("test","Unzip fini");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}


