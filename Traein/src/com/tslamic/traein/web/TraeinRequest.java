package com.tslamic.traein.web;

import com.android.volley.toolbox.StringRequest;

/** Request class used throughout the app. */
public class TraeinRequest extends StringRequest {

    public TraeinRequest(final String url, final String tag, final VolleyStringResponse response) {
        super(Method.GET, url, response, response);
        setRetryPolicy(TraeinWebClient.TRAEIN_RETRY_POLICY);
        setTag(tag);
    }

}
