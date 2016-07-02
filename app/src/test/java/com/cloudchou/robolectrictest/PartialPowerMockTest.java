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

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;

/**
 * Created by Cloud on 2016/6/27.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
//必须写如下代码 让PowerMock 忽略Robolectric的所有注入
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
//因为我们是针对类做静态函数的mock，所以必须使用PrepareForTest说明我们要mock的类
@PrepareForTest({HelloUtils2.class})
public class PartialPowerMockTest {

    //不可缺少的代码 表明使用Powermock执行单元测试，虽然我们使用的是RoblectricGradleTestRunner来执行单元测试
    //但是添加了如下代码后RoblectricGradleTestRunner会调用PowerMock的TestRunner去执行单元测试
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    //因为我们是针对类做静态函数的mock，所以必须在所有测试用例执行之前
    //使用PowerMockito.mockStatic开启对HelloUtils2的静态mock
    @Before
    public void setup() {
        PowerMockito.mockStatic(HelloUtils2.class);
    }

    @Test
    public void testPartialmock() throws Exception {
        //调用spy表明是partial mock
        PowerMockito.spy(HelloUtils2.class);
        //当执行HelloUtils2.test1函数时，让它返回it's partial mocked
        PowerMockito.doReturn("it's partial mocked").when(HelloUtils2.class, "test1");
        //test1 函数的行为已改变 会返回 it's partial mocked
        System.out.println(HelloUtils2.test1());
        System.out.println(HelloUtils2.test2());
    }

}
