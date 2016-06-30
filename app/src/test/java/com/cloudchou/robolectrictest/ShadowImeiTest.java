package com.cloudchou.robolectrictest;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Cloud on 2016/6/27.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowTelephoneManager.class})
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class ShadowImeiTest {

    @Test
    public void testEvn2() throws IllegalAccessException, NoSuchFieldException {
        System.out.println(Build.VERSION.RELEASE);
        Context ctx = RuntimeEnvironment.application;
        System.out.println(Build.VERSION.SDK_INT);
        System.out.println(Build.DEVICE);
        System.out.println(Build.FINGERPRINT);
        System.out.println(Build.BRAND);
        System.out.println(Build.BOARD);
        System.out.println(Build.MODEL);
        TelephonyManager systemService = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = systemService.getDeviceId();
        System.out.println(deviceId);
    }

}
