package classes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

    public void parseTrips(File fs)
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
                    String[] Trip = line.split(cvsSplitBy);
                    String newRoute  = Trip[0].replaceFirst("^0*", "");
                    int direction;
                    if(Trip[5] != "")
                    {
                        direction = 1;
                    }
                    else
                    {
                        direction = 0;
                    }
                    Trip trip = new Trip(Integer.valueOf(Trip[2]) , Integer.valueOf(newRoute), Integer.valueOf(Trip[1]), Trip[3], direction, Trip[6], Boolean.valueOf(Trip[7]));
                    boolean retour = ds.insertTrip(trip);
                }
                i++;
            }
            Log.v("test" , "Insertions Trips finis");
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

    public void parseCalendar(File fs)
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
                    String[] Calendar = line.split(cvsSplitBy);

                    Calendar calendar = new Calendar(Integer.valueOf(Calendar[0]) , Integer.valueOf(Calendar[1]), Integer.valueOf(Calendar[2]), Integer.valueOf(Calendar[3]), Integer.valueOf(Calendar[4]), Integer.valueOf(Calendar[5]), Integer.valueOf(Calendar[6]), Integer.valueOf(Calendar[7]),Calendar[8], Calendar[9]);
                    boolean retour = ds.insertCalendar(calendar);
                }
                i++;
            }
            Log.v("test" , "Insertions Calendar finis");
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
            int j = 0;
            Collection<StopTime> collecStop = new ArrayList<StopTime>();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(i != 0)
                {
                    line = line.replace("\"", "");
                    String[] StopTime = line.split(cvsSplitBy);
                    StopTime stopTime = new StopTime(Integer.valueOf(StopTime[0]), StopTime[1], StopTime[2], Integer.valueOf(StopTime[3]), Integer.valueOf(StopTime[4]));
                    collecStop.add(stopTime);
                    if(j == 49)
                    {
                        ds.insertStopTime(collecStop);
                        collecStop.clear();
                        j=-1;
                    }
                    j++;
                }
                i++;
            }
            ds.insertStopTime(collecStop);
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
