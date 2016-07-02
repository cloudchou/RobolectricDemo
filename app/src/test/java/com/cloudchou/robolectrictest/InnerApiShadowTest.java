package com.cloudchou.robolectrictest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Cloud on 2016/6/27.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class InnerApiShadowTest {

    @Test
    @Config(shadows = {ShadowHelloUtils.class})
    public void tesInnerApi() {
        HelloUtils.test();
    }

}
