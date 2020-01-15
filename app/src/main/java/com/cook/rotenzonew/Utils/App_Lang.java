package com.cook.rotenzonew.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Raghvendra Sahu on 21/11/2019.
 */
public class App_Lang extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "mn"));
    }
}
