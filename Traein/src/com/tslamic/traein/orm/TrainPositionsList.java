package com.tslamic.traein.orm;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ArrayOfObjTrainPositions")
public class TrainPositionsList {

    @ElementList(inline = true, required = false)
    public List<TrainPositions> list;

    @Override
    public String toString() {
        return list.toString();
    }

}
