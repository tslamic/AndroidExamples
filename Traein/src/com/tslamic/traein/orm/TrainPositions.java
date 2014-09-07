package com.tslamic.traein.orm;

import android.os.Parcel;
import android.os.Parcelable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "objTrainPositions")
public class TrainPositions implements Parcelable {

    public static final Creator<TrainPositions> CREATOR = new Creator<TrainPositions>() {
        @Override
        public TrainPositions createFromParcel(Parcel source) {
            return new TrainPositions(source);
        }

        @Override
        public TrainPositions[] newArray(int size) {
            return new TrainPositions[size];
        }
    };

    @Element(name = "TrainStatus")
    public String trainStatus;

    @Element(name = "TrainLatitude")
    public double trainLatitude;

    @Element(name = "TrainLongitude")
    public double trainLongitude;

    @Element(name = "TrainCode")
    public String trainCode;

    @Element(name = "TrainDate")
    public String trainDate;

    @Element(name = "PublicMessage")
    public String publicMessage;

    @Element(name = "Direction")
    public String direction;

    public TrainPositions() {}

    private TrainPositions(Parcel parcel) {
        publicMessage = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(publicMessage);
    }

}
