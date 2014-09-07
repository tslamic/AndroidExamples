package com.tslamic.traein.orm;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tslamic.traein.R;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;

@Root(name = "objStationData")
public class StationData implements Parcelable {

    public static final Creator<StationData> CREATOR = new Creator<StationData>() {
        @Override
        public StationData createFromParcel(Parcel source) {
            return new StationData(source);
        }

        @Override
        public StationData[] newArray(int size) {
            return new StationData[size];
        }
    };

    @Element(name = "Servertime")
    public String serverTime;

    @Element(name = "Traincode")
    public String trainCode;

    @Element(name = "Stationfullname")
    public String stationFullName;

    @Element(name = "Stationcode")
    public String stationCode;

    @Element(name = "Querytime")
    public String quertyTime;

    @Element(name = "Traindate")
    public String trainDate;

    @Element(name = "Origin")
    public String origin;

    @Element(name = "Destination")
    public String destination;

    @Element(name = "Status")
    public String status;

    @Element(name = "Origintime")
    public String originTime;

    @Element(name = "Destinationtime")
    public String destinationTime;

    @Element(name = "Lastlocation", required = false)
    public String lastLocation;

    @Element(name = "Duein")
    public int dueIn;

    @Element(name = "Late")
    public int late;

    @Element(name = "Exparrival")
    public String expectedArrival;

    @Element(name = "Expdepart")
    public String expectedDeparture;

    @Element(name = "Scharrival")
    public String scheduledArrival;

    @Element(name = "Schdepart")
    public String scheduledDeparture;

    @Element(name = "Direction")
    public String direction;

    @Element(name = "Traintype")
    public String trainType;

    @Element(name = "Locationtype")
    public String locationType;

    public StationData() {}

    private StationData(Parcel parcel) {
        serverTime = parcel.readString();
        trainCode = parcel.readString();
        stationFullName = parcel.readString();
        stationCode = parcel.readString();
        quertyTime = parcel.readString();
        trainDate = parcel.readString();
        origin = parcel.readString();
        destination = parcel.readString();
        status = parcel.readString();
        originTime = parcel.readString();
        destinationTime = parcel.readString();
        lastLocation = parcel.readString();
        dueIn = parcel.readInt();
        late = parcel.readInt();
        expectedArrival = parcel.readString();
        expectedDeparture = parcel.readString();
        scheduledArrival = parcel.readString();
        scheduledDeparture = parcel.readString();
        direction = parcel.readString();
        trainType = parcel.readString();
        locationType = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(serverTime);
        parcel.writeString(trainCode);
        parcel.writeString(stationFullName);
        parcel.writeString(stationCode);
        parcel.writeString(quertyTime);
        parcel.writeString(trainDate);
        parcel.writeString(origin);
        parcel.writeString(destination);
        parcel.writeString(status);
        parcel.writeString(originTime);
        parcel.writeString(destinationTime);
        parcel.writeString(lastLocation);
        parcel.writeInt(dueIn);
        parcel.writeInt(late);
        parcel.writeString(expectedArrival);
        parcel.writeString(expectedDeparture);
        parcel.writeString(scheduledArrival);
        parcel.writeString(scheduledDeparture);
        parcel.writeString(direction);
        parcel.writeString(trainType);
        parcel.writeString(locationType);
    }

    public String getTitle() {
        return origin + " to " + destination;
    }

    public String getTimeDescription() {
        return originTime + " - " + destinationTime;
    }

    public int getIconResource() {
        if ("Northbound".equals(direction)) {
            return R.drawable.ic_up;
        }

        if ("Southbound".equals(direction)) {
            return R.drawable.ic_down;
        }

        return R.drawable.ic_right;
    }

    public Map<String, String> toMap() {
        final Map<String, String> map = new HashMap<String, String>();

        map.put("Train Code", trainCode);
        map.put("Train Type", trainType);
        map.put("Direction", direction);
        map.put("Due in", dueIn + " min");

        if (!"No Information".equals(status)) {
            map.put("Status", status);
        }

        if (!TextUtils.isEmpty(lastLocation)) {
            map.put("Last Location", lastLocation);
        }

        if (0 != late) {
            map.put("Late", late + " min");
        }

        return map;
    }

}
