package com.tslamic.traein.web;

import android.content.Context;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * This class provides means to communicate with the server, using Google <a href="https://android.googlesource
 * .com/platform/frameworks/volley/">Volley</a>.
 */
public final class TraeinWebClient {

    /** The default socket timeout in milliseconds */
    public static final int DEFAULT_TIMEOUT = 10 * 1000;
    /** The default number of retries */
    public static final int DEFAULT_RETRY_REQUEST = 3;
    /** The default backoff multiplier */
    public static final float DEFAULT_BACKOFF_MULTIPLIER = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
    /** Default Fusion retry policy. Used as a default StringRequest policy. {@see StringRequest} */
    public static final DefaultRetryPolicy TRAEIN_RETRY_POLICY = new DefaultRetryPolicy(DEFAULT_TIMEOUT,
                                                                                        DEFAULT_RETRY_REQUEST,
                                                                                        DEFAULT_BACKOFF_MULTIPLIER);
    /** RequestQueue for whole app. */
    private static RequestQueue sRequestQueue;

    /** Initialized by the TraeinApplication. {@see TraeinApplication} */
    public static void init(final Context context) {
        if (null == context) throw new IllegalArgumentException("null == context");
        if (null == sRequestQueue) {
            sRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    /** Shuts down the web client. */
    public static void shutdown() {
        if (null != sRequestQueue) {
            sRequestQueue.stop();
            sRequestQueue = null;
        }
    }

    public static RequestQueue getInstance() {
        return sRequestQueue;
    }

}
