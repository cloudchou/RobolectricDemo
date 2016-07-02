package com.cloudchou.robolectrictest;

/**
 * Created by Cloud on 2016/7/1.
 */
public class HelloUtils2 {
    private static final String TAG = HelloUtils2.class.getSimpleName();

    public static String test1() {
        return "Hello Utils2 test1";
    }

    public static String test2() {
        return "Hello Utils2 test2";
    }

    public static int test3() {
        return 1 + test4();
    }

    public static int test4() {
        return 1;
    }

    public static void testStringParam(String param) {
        System.out.println(param);
    }

    public static void testLog() {
        SLog.e(TAG, "Hello world");
    }

}
