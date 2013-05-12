package com.tslamic.loader;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DummyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.tslamic.loader.LoaderFramework.provider";

    public static final Uri URI_ITEM = Uri.parse("content://" + AUTHORITY + "/item");
    public static final Uri URI_JSON = Uri.parse("content://" + AUTHORITY + "/json");

    private static final String MIMETYPE_MULTIPLE_CURSOR_ROWS = "vnd.android.cursor.dir/";
    private static final String MIMETYPE_SINGLE_CURSOR_ROW = "vnd.android.cursor.item/";

    private static final int ITEM = 1;
    private static final int ITEM_ID = 2;
    private static final int JSON = 3;
    private static final int JSON_ID = 4;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, "item", ITEM);
        URI_MATCHER.addURI(AUTHORITY, "item/#", ITEM_ID);
        URI_MATCHER.addURI(AUTHORITY, "json", JSON);
        URI_MATCHER.addURI(AUTHORITY, "json/#", JSON_ID);
    }

    private DummyDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mDatabase = DummyDatabase.getInstance(getContext());
        return (null != mDatabase);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DummyDatabase.DB_TABLE_NAME);

        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case ITEM:
            case JSON:
                break;
            case ITEM_ID:
            case JSON_ID:
                builder.appendWhere(generateWhereClause(uri));
                break;
            default:
                break;
        }

        final Cursor cursor = builder
                .query(mDatabase.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case ITEM:
                return MIMETYPE_MULTIPLE_CURSOR_ROWS + AUTHORITY + "/item";
            case ITEM_ID:
                return MIMETYPE_SINGLE_CURSOR_ROW + AUTHORITY + "/item";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final long id = mDatabase.getWritableDatabase().insert(DummyDatabase.DB_TABLE_NAME, null, values);
        if (-1 != id) getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(AUTHORITY + "/item/" + id);       // TODO: WRONG URI RETURNED FIX ASAP
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int id = mDatabase.getWritableDatabase().delete(DummyDatabase.DB_TABLE_NAME, selection, selectionArgs);
        if (-1 != id) getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int id = mDatabase.getWritableDatabase()
                                .update(DummyDatabase.DB_TABLE_NAME, values, selection, selectionArgs);
        if (-1 != id) getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    private static String generateWhereClause(final Uri uri) {
        long id;

        try {
            id = ContentUris.parseId(uri);
        } catch (Exception ex) {
            id = 0;
        }

        return String.format("%s = %ld", DummyDatabase.DB_ID, id);
    }

}
