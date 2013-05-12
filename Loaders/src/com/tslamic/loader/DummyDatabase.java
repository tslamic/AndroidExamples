package com.tslamic.loader;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DummyDatabase extends SQLiteOpenHelper {

    private static final String[] RANDOM_STRINGS = {"lorem", "ipsum", "dolor", "sit", "amet"};
    private static final Random RANDOM = new Random();

    private static final int DB_VERSION = 2;

    public static final String DB_TABLE_NAME = "dummy";
    public static final String DB_ID = "_id";
    public static final String DB_INFO = "info";
    public static final String DB_JSON = "json";

    private static DummyDatabase sDatabaseInstance;

    // Private constructor - avoiding instantiation.
    private DummyDatabase(final Context context) {
        super(context, DB_TABLE_NAME, null, DB_VERSION);
    }

    public static DummyDatabase getInstance(final Context context) {
        if (null == sDatabaseInstance) {
            sDatabaseInstance = new DummyDatabase(context);
        }
        return sDatabaseInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDatabaseSqlCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String dropCommand = String.format("DROP TABLE IF EXISTS %s;", DB_TABLE_NAME);
        db.execSQL(dropCommand);
        onCreate(db);
    }

    private static String createDatabaseSqlCommand() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE ");
        builder.append(DB_TABLE_NAME);
        builder.append(" ( ");

        builder.append(DB_ID);
        builder.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        builder.append(DB_INFO);
        builder.append(" TEXT, ");

        builder.append(DB_JSON);
        builder.append(" TEXT ");
        builder.append(");");

        return builder.toString();
    }

    private static String getRandomValue(final String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    public static ContentValues getRandomlyPopulatedContentValues() {
        final ContentValues values = new ContentValues(2);

        values.put(DB_INFO, getRandomValue(RANDOM_STRINGS));
        values.put(DB_JSON, generateJsonValue());

        return values;
    }

    private static String generateJsonValue() {
        final int random = RANDOM.nextInt();
        return String.format("{ \"integer\" : %d }", random);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
