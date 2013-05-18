package com.tslamic.loader.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DummyDatabase extends SQLiteOpenHelper {

    private static final Random RANDOM = new Random();

    private static final String DB_NAME = "dummy.db";
    private static final int DB_VERSION = 1;

    private static final String[] RANDOM_STRINGS = {
            "Teleporters are the machines of the evasive honor",
            "The kingdom is full of booda-hood ",
            "To the delicious ginger add spinach, oysters, rice vinegar and packaged chickpeas",
            "Resist bravely like a crazy processor. spacecrafts warp with core at the brave cabin",
            "Masts are the skiffs of the big endurance. ah, courage"
    };

    public static final class Field {

        public static final String TABLE_NAME = "dummy";
        public static final String ID = "_id";
        public static final String INFO = "info";
        public static final String JSON = "json";

        private Field() { throw new AssertionError(); } // Instantiation disabled.

    }

    private static DummyDatabase sDatabaseInstance;

    public static DummyDatabase getInstance(final Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sDatabaseInstance == null) {
            sDatabaseInstance = new DummyDatabase(context.getApplicationContext());
        }
        return sDatabaseInstance;
    }

    // Private constructor. Get instance from a static factory.
    private DummyDatabase(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static String createDatabaseSqlCommand() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE ");
        builder.append(Field.TABLE_NAME);
        builder.append(" ( ");

        builder.append(Field.ID);
        builder.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        builder.append(Field.INFO);
        builder.append(" TEXT, ");

        builder.append(Field.JSON);
        builder.append(" TEXT ");
        builder.append(");");

        return builder.toString();
    }

    private static String getRandomValue(final String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    public static ContentValues getRandomlyPopulatedContentValues() {
        final ContentValues values = new ContentValues(2);

        values.put(Field.INFO, getRandomValue(RANDOM_STRINGS));
        values.put(Field.JSON, generateJsonValue());

        return values;
    }

    private static String generateJsonValue() {
        final int random = RANDOM.nextInt();
        return String.format("{ \"%s\":\"%d\" }", "integer", random);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDatabaseSqlCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String dropCommand = String.format("DROP TABLE IF EXISTS %s;", Field.TABLE_NAME);
        db.execSQL(dropCommand);
        onCreate(db);
    }

}
