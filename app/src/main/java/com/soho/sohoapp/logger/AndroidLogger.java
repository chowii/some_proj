package com.soho.sohoapp.logger;

import android.util.Log;

import com.soho.sohoapp.BuildConfig;

public class AndroidLogger implements Logger {
    private static final String LOG_TAG = "Soho";
    private static final boolean LOGS_ON = BuildConfig.DEBUG;

    @Override
    public void d(String message) {
        if (LOGS_ON) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();

            String callerClassName = elements[1].getFileName();
            Log.d(LOG_TAG, "[" + callerClassName + "] " + message);
        }
    }

    @Override
    public void w(String message) {
        if (LOGS_ON) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();

            String callerClassName = elements[1].getFileName();
            Log.w(LOG_TAG, "[" + callerClassName + "] " + message);
        }
    }

    @Override
    public void w(String message, Throwable cause) {
        if (LOGS_ON) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();

            String callerClassName = elements[1].getFileName();
            Log.w(LOG_TAG, "[" + callerClassName + "] " + message, cause);
        }
    }

    @Override
    public void e(String message) {
        if (LOGS_ON) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();

            String callerClassName = elements[1].getFileName();
            Log.e(LOG_TAG, "[" + callerClassName + "] " + message);
        }
    }

    @Override
    public void e(String message, Throwable cause) {
        if (LOGS_ON) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();

            String callerClassName = elements[1].getFileName();
            Log.e(LOG_TAG, "[" + callerClassName + "] " + message, cause);
        }
    }

}
