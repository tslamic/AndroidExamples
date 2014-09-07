package com.tslamic.traein.orm;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "objTrainMovements")
public class TrainMovements {

    @Element(name = "TrainCode")
    public String trainCode;

    @Element(name = "TrainDate")
    public String trainDate;

    @Element(name = "LocationCode")
    public String locationCode;

    @Element(name = "LocationFullName")
    public String locationFullName;

    @Element(name = "LocationOrder")
    public int locationOrder;

    @Element(name = "LocationType")
    public int locationType;

    @Element(name = "TrainOrigin")
    public String trainOrigin;

    @Element(name = "TrainDestination")
    public String trainDestination;

    @Element(name = "ScheduledArrival")
    public String scheduledArrival;

    @Element(name = "ScheduledDeparture")
    public String scheduledDeparture;

    @Element(name = "ExpectedArrival")
    public String expectedArrival;

    @Element(name = "ExpectedDeparture")
    public String expectedDeparture;

    @Element(name = "Arrival")
    public String arrival;

    @Element(name = "Departure")
    public String departure;

    @Element(name = "AutoArrival")
    public int autoArrival;

    @Element(name = "AutoDepart")
    public int autoDepart;

    @Element(name = "StopType")
    public String stopType;

}
