package com.cloudchou.robolectrictest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Cloud on 2016/6/27.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({HelloUtils2.class})
public class PartialPowerMockTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setup() {
        PowerMockito.mockStatic(HelloUtils2.class);
    }

    @Test
    public void tesInnerApi() throws Exception {
        PowerMockito.spy(HelloUtils2.class);
        PowerMockito.doReturn("it's partial mocked").when(HelloUtils2.class, "test1");
        System.out.println(HelloUtils2.test1());
        System.out.println(HelloUtils2.test2());
    }

}
