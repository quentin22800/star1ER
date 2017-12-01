package classes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.istic.starproviderER.DataSource;

/**
 * Created by GAEL on 28/11/2017.
 */

public class Parses {

    DataSource ds;

    public Parses(DataSource ds){
        this.ds = ds;
    };

    public void parseRoute(File fs)
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            if(ds == null){
                Log.v("test", "ds is null");
            }

            br = new BufferedReader(new FileReader(fs));
            int i = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(i != 0)
                {
                    line = line.replace("\"", "");
                    String[] Route = line.split(cvsSplitBy);
                    String newroute0  = Route[0].replaceFirst("^0*", "");
                    BusRoute busRoute = new BusRoute(Integer.valueOf(newroute0), Route[2], Route[3], Route[4], Route[5], Route[6], Route[7]);
                    boolean retour = ds.insertBusRoute(busRoute);
                }
                i++;
            }
            Log.v("test" , "Insertions Route finis");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void parseStop(File fs)
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            if(ds == null){
                Log.v("test", "ds is null");
            }

            br = new BufferedReader(new FileReader(fs));
            int i = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(i != 0)
                {
                    line = line.replace("\"", "");
                    String[] Stop = line.split(cvsSplitBy);
                    String newStop  = Stop[0].replaceFirst("^0*", "");
                    Stop stop = new Stop(newStop, Stop[2], Stop[3], Double.valueOf(Stop[4]), Double.valueOf(Stop[5]), Boolean.valueOf(Stop[6]));
                    boolean retour = ds.insertStops(stop);
                }
                i++;
            }
            Log.v("test" , "Insertions Stop finis");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void parseStopTime(File fs)
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            if(ds == null){
                Log.v("test", "ds is null");
            }

            br = new BufferedReader(new FileReader(fs));
            int i = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(i != 0)
                {
                    line = line.replace("\"", "");
                    String[] StopTime = line.split(cvsSplitBy);
                    StopTime stopTime = new StopTime(Integer.valueOf(StopTime[0]), StopTime[1], StopTime[2], Integer.valueOf(StopTime[3]), Integer.valueOf(StopTime[4]));
                    boolean retour = ds.insertStopTime(stopTime);
                }
                i++;
            }
            Log.v("test" , "Insertions StopTime finis");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
