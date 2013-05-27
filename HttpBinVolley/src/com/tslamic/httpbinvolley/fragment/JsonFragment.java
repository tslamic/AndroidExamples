package com.tslamic.httpbinvolley.fragment;

import com.tslamic.httpbinvolley.Web;

public class JsonFragment extends ParamFragment {

    @Override
    public void onPause() {
        super.onPause();
        Web.setJson(getParams());
    }

}
