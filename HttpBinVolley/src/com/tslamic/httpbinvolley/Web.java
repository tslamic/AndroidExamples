package com.tslamic.httpbinvolley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tslamic.httpbinvolley.param.Param;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public final class Web {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private static FutureTask<JSONObject> sFutureJson;
    private static FutureTask<String> sFutureArgs;
    private static RequestQueue sRequestQueue;

    // This is done automatically in WebApplication.
    public static void init(final Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
    }

    // Execute a request with Volley.
    public static void exec(Request<?> request) {
        sRequestQueue.add(request);
    }

    // Cancel all requests with a specific tag.
    public static void cancelAll(final Object tag) {
        sRequestQueue.cancelAll(tag);
    }

    public static String getArgs() {
        return get(sFutureArgs, "");
    }

    public static void setArgs(final List<Param> params) {
        sFutureArgs = getArgsTask(params);
        EXECUTOR_SERVICE.execute(sFutureArgs);
    }

    public static JSONObject getJson() {
        return get(sFutureJson, new JSONObject());
    }

    public static void setJson(final List<Param> params) {
        sFutureJson = getJsonTask(params);
        EXECUTOR_SERVICE.execute(sFutureJson);
    }

    private static <T> T get(FutureTask<T> task, T defaultValue) {
        try {
            return task.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static FutureTask<JSONObject> getJsonTask(final List<Param> params) {
        return new FutureTask<JSONObject>(new Callable<JSONObject>() {
            @Override
            public JSONObject call() {
                final JSONObject object = new JSONObject();
                if (params.isEmpty()) return object;

                for (Param param : params) {
                    if (param.isEmpty()) continue;
                    try {
                        object.put(param.key, param.value);
                    } catch (JSONException e) {
                        // This should not happen, but if it does, do nothing.
                    }
                }

                return object;
            }
        });
    }

    private static FutureTask<String> getArgsTask(final List<Param> params) {
        return new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() {
                if (params.isEmpty()) return "";

                final StringBuilder builder = new StringBuilder();
                builder.append("?");

                String and = "";
                for (Param param : params) {
                    if (param.isEmpty()) continue;
                    builder.append(and);
                    builder.append(param.key);
                    builder.append("=");
                    builder.append(param.value);
                    and = "&";
                }

                return builder.toString();
            }
        });
    }

}
