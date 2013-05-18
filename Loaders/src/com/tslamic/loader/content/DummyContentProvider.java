package com.tslamic.loader.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DummyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.tslamic.loader.provider";

    public static final Uri URI_ITEM = Uri.parse("content://" + AUTHORITY + "/item");
    public static final Uri URI_JSON = Uri.parse("content://" + AUTHORITY + "/json");

    private static final UriMatcher URI_MATCHER = getUriMatcher();

    // Matcher IDs
    private static final int ITEM = 1;
    private static final int ITEM_ID = 2;
    private static final int JSON = 3;
    private static final int JSON_ID = 4;
    private DummyDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mDatabase = DummyDatabase.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = URI_MATCHER.match(uri);

        if (ITEM_ID == match || JSON_ID == match) {
            selection = appendWhereIfNecessary(uri, selection);
        }

        final SQLiteDatabase db = mDatabase.getReadableDatabase();
        final Cursor cursor = db.query(DummyDatabase.Field.TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case ITEM:
                return "vnd.android.cursor.dir/" + AUTHORITY + "/item";
            case ITEM_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + "/item";
            default:
                throw new IllegalArgumentException("Uri not recognized: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDatabase.getWritableDatabase();

        final long id = db.insert(DummyDatabase.Field.TABLE_NAME, null, values);
        if (-1 == id) throw new SQLException("Could not insert for: " + uri);

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDatabase.getWritableDatabase();

        final int deleted = db.delete(DummyDatabase.Field.TABLE_NAME, selection, selectionArgs);
        if (0 != deleted) getContext().getContentResolver().notifyChange(uri, null);

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDatabase.getWritableDatabase();

        final int updated = db.update(DummyDatabase.Field.TABLE_NAME, values, selection, selectionArgs);
        if (0 < updated) getContext().getContentResolver().notifyChange(uri, null);

        return updated;
    }

    private static String appendWhereIfNecessary(final Uri uri, final String selection) {
        if (null != selection) return selection;

        final long id = getIdFromUri(uri, 0);
        return String.format("%s = %d", DummyDatabase.Field.ID, id);
    }

    private static long getIdFromUri(final Uri uri, final int defaultId) {
        try {
            return ContentUris.parseId(uri);
        } catch (Exception ex) {
            return defaultId;
        }
    }

    private static UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, "item", ITEM);
        matcher.addURI(AUTHORITY, "item/#", ITEM_ID);
        matcher.addURI(AUTHORITY, "json", JSON);
        matcher.addURI(AUTHORITY, "json/#", JSON_ID);

        return matcher;
    }

}
