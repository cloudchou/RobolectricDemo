package com.cloudchou.robolectrictest;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowSystemProperties;

import java.io.IOException;

/**
 * Created by Cloud on 2016/6/27.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class ShadowTest {

    @Config(shadows = {ShadowSystemProperties.class})
    @Test
    public void testEvn() throws IllegalAccessException, NoSuchFieldException {
        System.out.println(Build.VERSION.RELEASE);
        System.out.println(Build.VERSION.SDK_INT);
        System.out.println(Build.DEVICE);
        System.out.println(Build.FINGERPRINT);
        System.out.println(Build.BRAND);
        System.out.println(Build.BOARD);
        System.out.println(Build.MODEL);
    }

    @Config(shadows = {CloudSystemProperties.class})
    @Test
    public void testEvn2() {
        System.out.println(Build.VERSION.RELEASE);
        System.out.println(Build.VERSION.SDK_INT);
        System.out.println(Build.DEVICE);
        System.out.println(Build.FINGERPRINT);
        System.out.println(Build.BRAND);
        System.out.println(Build.BOARD);
        System.out.println(Build.MODEL);
    }

    @Config(shadows = {CloudSystemProperties2.class})
    @Test
    public void testShadow2() {
        System.out.println(Build.VERSION.RELEASE);
        System.out.println(Build.VERSION.SDK_INT);
        System.out.println(Build.DEVICE);
        System.out.println(Build.FINGERPRINT);
        System.out.println(Build.BRAND);
        System.out.println(Build.BOARD);
        System.out.println(Build.MODEL);
    }

}
