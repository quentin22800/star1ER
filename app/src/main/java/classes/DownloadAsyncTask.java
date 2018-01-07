package classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    private Activity activity;
    private final TaskListener taskListener;


    public interface TaskListener {
        public void onFinished(byte[] result);
    }

    public DownloadAsyncTask(Activity activity, TaskListener listener) {
        this.dialog = new ProgressDialog(activity);
        this.activity = activity;
        this.taskListener = listener;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Download in progress");
        dialog.setMax(100);
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
        if(this.taskListener != null) {
            this.taskListener.onFinished(result);
        }
    }

    private byte[] downloadUrl(String myurl) {
        DataInputStream open = null;
        try {
            URL url = new URL(myurl);
            Log.v("testlog", "url");
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
