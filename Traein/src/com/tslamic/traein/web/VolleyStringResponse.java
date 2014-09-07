package com.tslamic.traein.web;

import com.android.volley.Response;

/** Helper interface combining two separate interfaces. */
public interface VolleyStringResponse extends Response.Listener<String>, Response.ErrorListener {}
