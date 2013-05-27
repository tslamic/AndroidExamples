package com.tslamic.httpbinvolley.fragment;

import com.tslamic.httpbinvolley.Web;

public class ArgsFragment extends ParamFragment {

    @Override
    public void onPause() {
        super.onPause();
        Web.setArgs(getParams());
    }

}
