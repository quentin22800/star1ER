package classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by 17009495 on 14/11/17.
 */

public class DownloadAsyncTask extends AsyncTask<String, Integer, byte[]> {
    private ProgressDialog dialog;
    private String file;
    private Activity activity;

    public DownloadAsyncTask(Activity activity,
                             String fileName) {
        this.dialog = new ProgressDialog(activity);
        this.file = fileName;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Download in progress");
        dialog.show();
    }

    @Override
    protected byte[] doInBackground(String... params) {
        return downloadUrl(params[0]);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPostExecute(byte[] result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        try {
            FileOutputStream outputStream =
                    activity.openFileOutput(file, Context.MODE_PRIVATE);
            outputStream.write(result);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
        }

    }

    private byte[] downloadUrl(String myurl) {
        DataInputStream open = null;
        try {
            URL url = new URL(myurl);
            open = new DataInputStream(url.openStream());
            return readIt(open);
        } catch (Exception e) {
        } finally {
            if (open != null) { //open.close(); }
            }
        }
        return null;
    }

    private byte[] readIt(DataInputStream is)
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }


}
