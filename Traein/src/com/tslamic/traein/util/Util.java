package com.tslamic.traein.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Util {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    public static List<String> getFavorites(Context context) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        final Map<String, ?> map = preferences.getAll();
        final List<String> favorites = new ArrayList<String>(map.size());

        for (Map.Entry<String, ?> entry : map.entrySet()) {
            favorites.add(entry.getKey());
        }

        return favorites;
    }

    public static void setFavorites(Context context, List<String> favorites) {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        final SharedPreferences.Editor editor = pref.edit();
        editor.clear();

        for (String favorite : favorites) {
            editor.putString(favorite, favorite);
        }

        editor.commit();
    }
    public static final String KEY_DATA = "com.tslamic.traein.KEY_DATA";
    public static final String URL_BASE = "http://api.irishrail.ie/realtime/realtime.asmx";

    public static final String URL_LIVE_FEED = URL_BASE + "/getCurrentTrainsXML";
    public static final String URL_STATIONS_ALL = getStationTypeUrl("A");
    public static final String URL_STATIONS_DART = getStationTypeUrl("D");
    public static final String URL_STATIONS_MAINLINE = getStationTypeUrl("M");
    public static final String URL_STATIONS_SUBURBAN = getStationTypeUrl("S");

    public static String getStationDataByNameUrl(String station) {
        return URL_BASE + "/getStationDataByNameXML?StationDesc=" + station;
    }

    private static String getStationTypeUrl(final String type) {
        return URL_BASE + "/getAllStationsXML_WithStationType?StationType=" + type;
    }

    private Util() {}

}
