package com.tslamic.traein.orm;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ArrayOfObjStationData")
public class StationDataList {

    @ElementList(inline = true, required = false)
    public List<StationData> list;

    @Override
    public String toString() {
        return list.toString();
    }

}
