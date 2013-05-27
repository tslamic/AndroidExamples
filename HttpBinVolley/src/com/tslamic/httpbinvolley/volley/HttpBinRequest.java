package com.tslamic.httpbinvolley.volley;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tslamic.httpbinvolley.Web;
import com.tslamic.httpbinvolley.otto.BusProvider;
import com.tslamic.httpbinvolley.otto.event.ErrorEvent;
import com.tslamic.httpbinvolley.otto.event.ResponseEvent;
import org.json.JSONObject;

public class HttpBinRequest extends JsonObjectRequest {

    private static final String HTTPBIN_URL_POST = "http://httpbin.org/post";
    private static final String HTTPBIN_URL_GET = "http://httpbin.org/get";

    private static final Response.ErrorListener ERROR_LISTENER = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            BusProvider.getInstance().post(new ErrorEvent(error));
        }
    };

    private static final Response.Listener<JSONObject> LISTENER = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            BusProvider.getInstance().post(new ResponseEvent(response));
        }
    };

    public HttpBinRequest(final JSONObject jsonRequest) {
        super(getUrl(jsonRequest), jsonRequest, LISTENER, ERROR_LISTENER);
    }

    private static String getUrl(final JSONObject json) {
        String base = (null == json) ? HTTPBIN_URL_GET : HTTPBIN_URL_POST;
        return base + Web.getArgs();
    }

}
