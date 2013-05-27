package com.tslamic.httpbinvolley.otto.event;

import com.android.volley.VolleyError;

public class ErrorEvent {

    public final VolleyError error;

    public ErrorEvent(final VolleyError error) {
        this.error = error;
    }

}
