package com.paxw.phonesafe.utils;

import android.util.Log;

/**
 * log 工具类
 * Created by lichuang on 2015/12/15.
 */
public class Logs {
    public static boolean openLog = true;
    public static final String ApplicationTag = "MYAPPLICATION";
    public static final String defMsg = "-----------------------";

    public static void e(String msg) {
        e(null, msg);

    }

    public static void e() {
        e(null, null);
    }

    public static void e(String tag, String msg) {

        if (openLog) {
            if (null == tag) {
                if (null == msg) {

                    Log.e(ApplicationTag, defMsg);
                } else {
                    Log.e(ApplicationTag, msg);

                }
            } else {
                Log.e(tag, msg);
            }

        }
    }

    public static void i(String msg) {
        i(null, msg);

    }

    public static void i() {
        i(null, null);
    }

    public static void i(String tag, String msg) {

        if (openLog) {
            if (null == tag) {
                if (null == msg) {

                    Log.i(ApplicationTag, defMsg);
                } else {
                    Log.i(ApplicationTag, msg);

                }
            } else {
                Log.i(tag, msg);
            }

        }
    }

    public static void v(String msg) {
        v(null, msg);

    }

    public static void v() {
        v(null, null);
    }

    public static void v(String tag, String msg) {

        if (openLog) {
            if (null == tag) {
                if (null == msg) {

                    Log.v(ApplicationTag, defMsg);
                } else {
                    Log.v(ApplicationTag, msg);

                }
            } else {
                Log.v(tag, msg);
            }

        }
    }


    public static void d(String msg) {
        d(null, msg);

    }

    public static void d() {
        d(null, null);
    }

    public static void d(String tag, String msg) {

        if (openLog) {
            if (null == tag) {
                if (null == msg) {

                    Log.d(ApplicationTag, defMsg);
                } else {
                    Log.d(ApplicationTag, msg);

                }
            } else {
                Log.d(tag, msg);
            }

        }
    }

    public static void w(String msg) {
        w(null, msg);

    }

    public static void w() {
        w(null, null);
    }

    public static void w(String tag, String msg) {

        if (openLog) {
            if (null == tag) {
                if (null == msg) {

                    Log.w(ApplicationTag, defMsg);
                } else {
                    Log.w(ApplicationTag, msg);

                }
            } else {
                Log.w(tag, msg);
            }

        }
    }


}
