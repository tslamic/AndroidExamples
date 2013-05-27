package com.tslamic.httpbinvolley.param;

import android.text.TextUtils;

public class Param {

    public String key;
    public String value;

    public boolean isEmpty() {
        return TextUtils.isEmpty(key) && TextUtils.isEmpty(value);
    }

}
