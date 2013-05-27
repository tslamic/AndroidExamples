package com.tslamic.httpbinvolley.otto.event;

import org.json.JSONObject;

public class ResponseEvent {

    public final JSONObject jsonObject;

    public ResponseEvent(final JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

}
