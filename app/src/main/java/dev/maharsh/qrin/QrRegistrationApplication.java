package dev.maharsh.qrin;

import android.app.Application;

public class QrRegistrationApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DayNightSettings.setDefaultMode(this);
    }
}