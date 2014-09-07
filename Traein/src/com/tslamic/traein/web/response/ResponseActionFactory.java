package com.tslamic.traein.web.response;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import com.tslamic.traein.activity.LiveFeedActivity;
import com.tslamic.traein.activity.StationActivity;
import com.tslamic.traein.activity.StationDataShortActivity;
import com.tslamic.traein.fragment.TaskFragment;
import com.tslamic.traein.orm.Station;
import com.tslamic.traein.orm.StationDataList;
import com.tslamic.traein.orm.StationList;
import com.tslamic.traein.orm.TrainPositionsList;
import com.tslamic.traein.util.Util;
import com.tslamic.traein.xml.XmlParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Factory providing common ResponseActions. The actions do XML parsing, so make sure they are run in a worker
 * thread.
 */
public class ResponseActionFactory {

    public static ResponseAction forLiveFeed() {
        return new ResponseAction() {
            @Override
            public void onResponseAction(Context context, TaskFragment parent, Handler handler, String response) {
                final TrainPositionsList positions = XmlParser.read(TrainPositionsList.class, response);
                startIntent(context, LiveFeedActivity.class, getArrayList(positions.list));
                closeDialog(parent, handler);
            }
        };
    }

    public static ResponseAction forStation() {
        return new ResponseAction() {
            @Override
            public void onResponseAction(Context context, final TaskFragment parent, Handler handler, String response) {
                final StationList stations = XmlParser.read(StationList.class, response);
                final ArrayList<Station> list = getArrayList(stations.list);
                Collections.sort(list);
                startIntent(context, StationActivity.class, list);
                closeDialog(parent, handler);
            }
        };
    }

    public static ResponseAction forStationData() {
        return new ResponseAction() {
            @Override
            public void onResponseAction(Context context, final TaskFragment parent, Handler handler, String response) {
                final StationDataList stationData = XmlParser.read(StationDataList.class, response);
                startIntent(context, StationDataShortActivity.class, getArrayList(stationData.list));
                closeDialog(parent, handler);
            }
        };
    }

    /////////////
    // Helpers //
    /////////////

    private static void startIntent(Context context, Class<? extends Activity> clazz,
                                    ArrayList<? extends Parcelable> data) {
        final Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Util.KEY_DATA, data);
        context.startActivity(intent);
    }

    private static void closeDialog(final TaskFragment parent, final Handler handler) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                parent.dismiss();
            }
        });
    }

    private static <T extends Parcelable> ArrayList<T> getArrayList(List<T> list) {
        if (null == list || list.isEmpty()) return new ArrayList<T>(0);
        return new ArrayList<T>(list);
    }

    private ResponseActionFactory() {}

}
