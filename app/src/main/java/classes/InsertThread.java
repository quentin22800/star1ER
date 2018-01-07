package classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import fr.istic.starproviderER.DataSource;

/**
 * Created by GAEL on 07/01/2018.
 */

public class InsertThread extends AsyncTask<String, Integer, Integer> {
        private File filesDir;

        private final TaskListener taskListener;

        private ProgressDialog mProgressDialog;

        private DataSource ds;

        private Context cont;

        public InsertThread(Context c, File fild, DataSource d,TaskListener listener) {
            this.mProgressDialog = new ProgressDialog(c);
            this.cont = c;
            this.taskListener = listener;
            this.ds =d;
            this.filesDir = fild;
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.setTitle("Insertions en cours");
            // Set your progress dialog Message
            mProgressDialog.setMessage("Veuillez patientez!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show progress dialog
            mProgressDialog.show();
        }

        public interface TaskListener {
            public void onFinished(Integer result);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if(this.taskListener != null) {
                this.taskListener.onFinished(result);
            }
        }

        public Integer insertions()
        {
            File fl = new File(filesDir, "data.zip");

            unZipIt(fl,filesDir);
            Parses ps = new Parses(ds);
            File f1 = new File(filesDir, "jsonbases.json");
            boolean deleted = f1.delete();
            File flzip = new File(filesDir, "data.zip");
            boolean deletezip = flzip.delete();
            int i = 1;
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
                        break;
                }
                mProgressDialog.setProgress(i*20);
                i++;
            }
            Log.e("test", "Insertions finis");
            return 1;
        }

    /**
     * Unzip le fichier zippé
     * @param zipFile zip d entree
     * @param outputFolder dossier de sortie
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


    protected Integer doInBackground(String... strings) {
        return insertions();
    }
}
