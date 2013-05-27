package com.tslamic.httpbinvolley.otto;

public class BusProvider {

    private static final AndroidBus BUS = new AndroidBus();

    public static AndroidBus getInstance() {
        return BUS;
    }

    private BusProvider() { } // Instantiation disabled.

}
