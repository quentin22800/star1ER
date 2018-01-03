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
    private static final UriMatcher URI_MATCHER =
            new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(StarContract.AUTHORITY,
                StarContract.BusRoutes.CONTENT_PATH, QUERY_BUSROUTES);
    }
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
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case QUERY_BUSROUTES:
                return StarContract.BusRoutes.CONTENT_TYPE;
            default: throw new IllegalArgumentException("URI non supportée : " + uri);
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
