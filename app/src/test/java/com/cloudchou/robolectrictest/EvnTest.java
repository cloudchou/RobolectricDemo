package com.cloudchou.robolectrictest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Cloud on 2016/6/22.
 */
@RunWith(RobolectricGradleTestRunner.class) //指定使用RobolectricGradleTestRunner作为单元测试执行者
//配置常量,执行时所使用的AndroidSdk，还可以在这里配置Application类，AndroidManifest文件的路径，Shadow类
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"}) //使得Powermock忽略这些包的注入类
public class EvnTest {

    @Test
    public void testEvn() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Assert.assertNotNull(absolutePath);
        System.out.println("absolute path: " + absolutePath);
        Context application = RuntimeEnvironment.application;
        SharedPreferences sSp = PreferenceManager.getDefaultSharedPreferences(application);
        SharedPreferences.Editor edit = sSp.edit();
        edit.putBoolean("halo", true);
        edit.commit();
        boolean halo = sSp.getBoolean("halo", false);
        Assert.assertTrue(halo);
    }

}
