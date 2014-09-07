package com.tslamic.traein.xml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/** Uses <a href="http://simple.sourceforge.net/>SimpleXML</a> as its backing engine. */
public final class XmlParser {

    private static final Serializer SERIALIZER = new Persister();

    public static <T> T read(Class<T> clazz, String xml) {
        try {
            return SERIALIZER.read(clazz, xml);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private XmlParser() {}

}