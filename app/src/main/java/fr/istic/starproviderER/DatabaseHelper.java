package fr.istic.starproviderER;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 17009495 on 14/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "star.db";
    private static final int version = 1;
    private static final String TABLE_BUS_ROUTES = StarContract.BusRoutes.CONTENT_PATH;
    private static final String TABLE_TRIPS = StarContract.Trips.CONTENT_PATH;
    private static final String TABLE_STOPS = StarContract.Stops.CONTENT_PATH;
    private static final String TABLE_STOP_TIMES = StarContract.StopTimes.CONTENT_PATH;
    private static final String TABLE_CALENDAR = StarContract.Calendar.CONTENT_PATH;

    private static final String DATABASE_CREATE_TABLE_BUS_ROUTES =
            "CREATE TABLE if not exists TABLE_BUS_ROUTES" +
                    "(route_id integer primary key not null, " +
                    StarContract.BusRoutes.BusRouteColumns.SHORT_NAME + " text, "+
                    StarContract.BusRoutes.BusRouteColumns.LONG_NAME + " text, "+
                    StarContract.BusRoutes.BusRouteColumns.DESCRIPTION + " text, " +
                    StarContract.BusRoutes.BusRouteColumns.TYPE + " text, " +
                    StarContract.BusRoutes.BusRouteColumns.COLOR + " text, "+
                    StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR + " text);";

    private static final String DATABASE_CREATE_TABLE_TRIPS =
            "CREATE TABLE if not exists TABLE_TRIPS" +
                    "(trip_id integer primary key not null, " +
                    "route_id integer, " +
                    StarContract.Trips.TripColumns.SERVICE_ID + " integer, "+
                    StarContract.Trips.TripColumns.HEADSIGN + " text, "+
                    StarContract.Trips.TripColumns.DIRECTION_ID + " integer, " +
                    StarContract.Trips.TripColumns.BLOCK_ID + " integer, " +
                    StarContract.Trips.TripColumns.WHEELCHAIR_ACCESSIBLE + " integer," +
                    "FOREIGN KEY (route_id) REFERENCES TABLE_BUS_ROUTES(route_id));";

    private static final String DATABASE_CREATE_TABLE_STOPS =
            "CREATE TABLE if not exists TABLE_STOPS" +
                    "(stop_id integer primary key not null, " +
                    StarContract.Stops.StopColumns.NAME + " text, "+
                    StarContract.Stops.StopColumns.DESCRIPTION + " text, "+
                    StarContract.Stops.StopColumns.LATITUDE + " numeric, " +
                    StarContract.Stops.StopColumns.LONGITUDE + " numeric, " +
                    StarContract.Stops.StopColumns.WHEELCHAIR_BOARDING + " integer);";

    private static final String DATABASE_CREATE_TABLE_STOP_TIMES =
            "CREATE TABLE if not exists TABLE_STOP_TIMES" +
                    "(" +
                    StarContract.StopTimes.StopTimeColumns.TRIP_ID + " integer primary key not null, "+
                    StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME + " text, "+
                    StarContract.StopTimes.StopTimeColumns.DEPARTURE_TIME + " text, " +
                    StarContract.StopTimes.StopTimeColumns.STOP_ID + " integer, " +
                    StarContract.StopTimes.StopTimeColumns.STOP_SEQUENCE + " integer," +
                    "FOREIGN KEY (stop_id) REFERENCES TABLE_STOPS(stop_id));";

    private static final String DATABASE_CREATE_TABLE_CALENDAR =
            "CREATE TABLE if not exists TABLE_CALENDAR" +
                    "(service_id integer," +
                    StarContract.Calendar.CalendarColumns.MONDAY + " integer, "+
                    StarContract.Calendar.CalendarColumns.TUESDAY + " integer, "+
                    StarContract.Calendar.CalendarColumns.WEDNESDAY + " integer, " +
                    StarContract.Calendar.CalendarColumns.THURSDAY + " integer, " +
                    StarContract.Calendar.CalendarColumns.FRIDAY + " integer, " +
                    StarContract.Calendar.CalendarColumns.SATURDAY + " integer, " +
                    StarContract.Calendar.CalendarColumns.SUNDAY + " integer," +
                    StarContract.Calendar.CalendarColumns.START_DATE + " text," +
                    StarContract.Calendar.CalendarColumns.END_DATE + " text," +
                    "PRIMARY KEY (service_id, start_date, end_date)," +
                    "FOREIGN KEY (service_id) REFERENCES TABLE_TRIPS(service_id));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
        context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE_BUS_ROUTES);
        db.execSQL(DATABASE_CREATE_TABLE_TRIPS);
        db.execSQL(DATABASE_CREATE_TABLE_STOPS);
        db.execSQL(DATABASE_CREATE_TABLE_STOP_TIMES);
        db.execSQL(DATABASE_CREATE_TABLE_CALENDAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUS_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOP_TIMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);
        onCreate(db);
    }
}
