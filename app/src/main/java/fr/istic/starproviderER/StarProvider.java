package fr.istic.starproviderER;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by GAEL on 02/01/2018.
 */

public class StarProvider extends ContentProvider {
    private static final int QUERY_BUSROUTES = 1;
    private static final int QUERY_STOPS = 2;
    private static final int QUERY_STOP_TIMES = 3;

    private static UriMatcher makeUriMatcher() {

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        // For the record
        matcher.addURI(StarContract.AUTHORITY,StarContract.BusRoutes.CONTENT_PATH, QUERY_BUSROUTES);
        matcher.addURI(StarContract.AUTHORITY,StarContract.Stops.CONTENT_PATH, QUERY_STOPS);
        matcher.addURI(StarContract.AUTHORITY,StarContract.StopTimes.CONTENT_PATH, QUERY_STOP_TIMES);
        return matcher;
    }

    /*private static final UriMatcher URI_MATCHER =
            new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(StarContract.AUTHORITY,String.valueOf(StarContract.BusRoutes.CONTENT_URI), QUERY_BUSROUTES);
    }*/

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (getType(uri)){
            case StarContract.BusRoutes.CONTENT_TYPE :
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables("TABLE_BUS_ROUTES");
                Cursor cursor = queryBuilder.query(DataSource.database, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor;

            case StarContract.Trips.CONTENT_TYPE :
                SQLiteQueryBuilder queryBuilder4 = new SQLiteQueryBuilder();
                queryBuilder4.setTables("TABLE_TRIPS");
                Cursor cursor4 = queryBuilder4.query(DataSource.database, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor4;

            case StarContract.Stops.CONTENT_TYPE:
                SQLiteQueryBuilder queryBuilder2 = new SQLiteQueryBuilder();
                queryBuilder2.setTables("TABLE_STOPS");
                Cursor cursor2 = queryBuilder2.query(DataSource.database, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor2;
            case StarContract.StopTimes.CONTENT_TYPE:
                SQLiteQueryBuilder queryBuilder3 = new SQLiteQueryBuilder();
                queryBuilder3.setTables("TABLE_STOP_TIMES");
                queryBuilder3.setTables("TABLE_TRIPS");
                queryBuilder3.setTables("TABLE_CALENDAR");
                Cursor cursor3= queryBuilder3.query(DataSource.database, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor3;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (makeUriMatcher().match(uri)) {
            case QUERY_BUSROUTES:
                return StarContract.BusRoutes.CONTENT_TYPE;
            case QUERY_STOPS:
                return StarContract.Stops.CONTENT_TYPE;
            case QUERY_STOP_TIMES:
                return StarContract.StopTimes.CONTENT_TYPE;
            default: throw new IllegalArgumentException("URI non supportée lol : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return  null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
