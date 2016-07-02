package com.cloudchou.robolectrictest;

import android.util.Log;

/**
 * Created by Cloud on 2016/7/2.
 */
public class SLog {
    private static boolean sLog = true;
    private static String sTAG = "ShuameMobile";

    private SLog() {

    }

    public static void setGlobalTag(String tag) {
        sTAG = tag;
    }

    public static void v(String tag, String msg) {
        if (sLog) {
            msg = genMsg(tag, msg);
            Log.v(sTAG, msg);
        }
    }

    private static String genMsg(String tag, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(tag).append("::").append(msg);
        return sb.toString();
    }

    public static void d(String tag, String msg) {
        if (sLog) {
            msg = genMsg(tag, msg);
            Log.d(sTAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (sLog) {
            msg = genMsg(tag, msg);
            Log.i(sTAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (sLog) {
            msg = genMsg(tag, msg);
            Log.w(sTAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (sLog) {
            msg = genMsg(tag, msg);
            Log.e(sTAG, msg);
        }
    }

    public static void e(String tag, Throwable e) {
        if (sLog) {
            Log.e(sTAG, genMsg(tag, ""), e);
        }
    }
}
