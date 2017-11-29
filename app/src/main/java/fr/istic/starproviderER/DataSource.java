package fr.istic.starproviderER;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import classes.BusRoute;
import classes.Calendar;
import classes.Stop;
import classes.StopTime;

/**
 * Created by 17009495 on 21/11/17.
 */

public class DataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void restart() {
        dbHelper.onUpgrade(database, 1, 1);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database.close();
    }

    public boolean insertBusRoute(BusRoute bus)
    {
        ContentValues values = new ContentValues();
        values.put("route_id", bus.getRoute_id());
        values.put(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME, bus.getShort_name());
        values.put(StarContract.BusRoutes.BusRouteColumns.LONG_NAME, bus.getLong_name());
        values.put(StarContract.BusRoutes.BusRouteColumns.COLOR, bus.getColor());
        values.put(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION, bus.getDescription());
        values.put(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR, bus.getText_color());
        values.put(StarContract.BusRoutes.BusRouteColumns.TYPE, bus.getType());
        long res = database.insert("TABLE_BUS_ROUTES",null, values);
        if((int) res != -1){
            return true;
        }
        else
        {
            Log.v("test", "insertion non reussie");
            return false;
        }
    }

    public boolean insertStops(Stop stop)
    {
        ContentValues values = new ContentValues();
        values.put("stop_id", stop.getStop_id());
        values.put(StarContract.Stops.StopColumns.DESCRIPTION, stop.getDescription());
        values.put(StarContract.Stops.StopColumns.LATITUDE, stop.getLatitude());
        values.put(StarContract.Stops.StopColumns.LONGITUDE, stop.getLongitude());
        values.put(StarContract.Stops.StopColumns.NAME, stop.getName());
        int IntWheelChair = (stop.isWheelchair_boarding()) ? 1 : 0;
        values.put(StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING, IntWheelChair);
        long res = database.insert("TABLE_STOPS",null, values);
        if((int) res != -1){
            return true;
        }
        else
        {
            Log.v("test", "insertion non reussie");
            return false;
        }
    }

    public boolean insertStopTime(StopTime stopTime)
    {
        ContentValues values = new ContentValues();
        values.put(StarContract.StopTimes.StopTimeColumns.STOP_ID, stopTime.getStop_id());
        values.put(StarContract.StopTimes.StopTimeColumns.TRIP_ID, stopTime.getTrip_id());
        values.put(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME, stopTime.getArrival_time());
        values.put(StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME, stopTime.getDeparture_time());
        values.put(StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE, stopTime.getStop_sequence());
        long res = database.insert("TABLE_STOP_TIMES",null, values);
        if((int) res != -1){
            return true;
        }
        else
        {
            Log.v("test", "insertion non reussie");
            return false;
        }
    }
}
