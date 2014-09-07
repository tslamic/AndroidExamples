package com.tslamic.traein.orm;

import android.os.Parcel;
import android.os.Parcelable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "objStation")
public class Station implements Comparable<Station>, Parcelable {

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel source) {
            return new Station(source);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };
    @Element(name = "StationDesc")
    public String name;

    @Element(name = "StationAlias", required = false)
    public String alias;

    @Element(name = "StationLatitude")
    public double latitutde;

    @Element(name = "StationLongitude")
    public double longitude;

    @Element(name = "StationCode")
    public String code;

    @Element(name = "StationId")
    public int id;

    public Station() {}

    private Station(Parcel parcel) {
        name = parcel.readString();
        alias = parcel.readString();
        latitutde = parcel.readDouble();
        longitude = parcel.readDouble();
        code = parcel.readString();
        id = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(alias);
        parcel.writeDouble(latitutde);
        parcel.writeDouble(longitude);
        parcel.writeString(code);
        parcel.writeInt(id);
    }

    @Override
    public int compareTo(Station another) {
        if (null == name) return -1;
        return name.compareToIgnoreCase(another.name);
    }

}
