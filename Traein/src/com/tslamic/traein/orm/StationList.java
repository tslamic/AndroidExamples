package com.tslamic.traein.orm;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ArrayOfObjStation")
public class StationList {

    @ElementList(inline = true)
    public List<Station> list;

    @Override
    public String toString() {
        return list.toString();
    }
}
