package com.tslamic.traein.web.response;

import android.content.Context;
import android.os.Handler;
import com.tslamic.traein.fragment.TaskFragment;

/** Specifies the action that should be performed after a successful web request. */
public interface ResponseAction {

    void onResponseAction(Context context, TaskFragment parent, Handler handler, String response);

}