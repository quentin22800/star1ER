package fr.istic.starproviderER;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import classes.BusRoute;
import classes.Calendar;
import classes.Stop;
import classes.StopTime;
import classes.Trip;

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

    public void insertStopTime(Collection<StopTime> collecStop)
    {
        Iterator<StopTime> it = collecStop.iterator();
        String query = "INSERT INTO TABLE_STOP_TIMES VALUES";
        int i = 0;
        while(it.hasNext())
        {
            StopTime st = it.next();
            if(i == 0){
                query += "(" + st.getTrip_id() + ",'" + st.getArrival_time() + "','" + st.getDeparture_time() + "'," + st.getStop_id() + "," + st.getStop_sequence() +  ")";
            }else
            {
                query += ",(" + st.getTrip_id() + ",'" + st.getArrival_time() + "','" + st.getDeparture_time() + "'," + st.getStop_id() + "," + st.getStop_sequence() +  ")";
            }
            i++;
        }
        query += ";";
        database.execSQL(query);
    }

    public boolean insertTrip(Trip trip)
    {
        ContentValues values = new ContentValues();
        values.put("trip_id", trip.getTrip_id());
        values.put(StarContract.Trips.TripColumns.BLOCK_ID, trip.getBlock_id());
        values.put(StarContract.Trips.TripColumns.DIRECTION_ID, trip.getDirection_id());
        values.put(StarContract.Trips.TripColumns.HEADSIGN, trip.getHeadsign());
        values.put(StarContract.Trips.TripColumns.ROUTE_ID, trip.getRoute_id());
        values.put(StarContract.Trips.TripColumns.SERVICE_ID, trip.getService_id());
        int IntWheelChair = (trip.isWheelchair_accessible()) ? 1 : 0;
        values.put(StarContract.Trips.TripColumns.WHEELCHAIR_ACCESSIBLE, IntWheelChair);
        long res = database.insert("TABLE_TRIPS",null, values);
        if((int) res != -1){
            return true;
        }
        else
        {
            Log.v("test", "insertion non reussie");
            return false;
        }
    }

    public boolean insertCalendar(Calendar calendar)
    {
        ContentValues values = new ContentValues();
        values.put("service_id", calendar.getService_id());
        values.put(StarContract.Calendar.CalendarColumns.MONDAY, calendar.getMonday());
        values.put(StarContract.Calendar.CalendarColumns.TUESDAY, calendar.getTuesday());
        values.put(StarContract.Calendar.CalendarColumns.WEDNESDAY, calendar.getWednesday());
        values.put(StarContract.Calendar.CalendarColumns.THURSDAY, calendar.getThursday());
        values.put(StarContract.Calendar.CalendarColumns.FRIDAY, calendar.getFriday());
        values.put(StarContract.Calendar.CalendarColumns.SATURDAY, calendar.getSaturday());
        values.put(StarContract.Calendar.CalendarColumns.SUNDAY, calendar.getSunday());
        values.put(StarContract.Calendar.CalendarColumns.START_DATE, calendar.getStart_date());
        values.put(StarContract.Calendar.CalendarColumns.END_DATE, calendar.getEnd_date());

        long res = database.insert("TABLE_CALENDAR",null, values);
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
