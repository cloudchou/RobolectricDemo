package com.cloudchou.robolectrictest;

import android.util.Log;

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
import org.robolectric.shadows.ShadowLog;

import static org.mockito.Mockito.times;

/**
 * Created by Cloud on 2016/6/27.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
//必须写如下代码 让PowerMock 忽略Robolectric的所有注入
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
//因为我们是针对类做静态函数的mock，所以必须使用PrepareForTest说明我们要mock的类
@PrepareForTest({HelloUtils2.class, SLog.class})
public class PowerMockVerifyTest {

    //不可缺少的代码 表明使用Powermock执行单元测试，虽然我们使用的是RoblectricGradleTestRunner来执行单元测试
    //但是添加了如下代码后RoblectricGradleTestRunner会调用PowerMock的TestRunner去执行单元测试
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    //因为我们是针对类做静态函数的mock，所以必须在所有测试用例执行之前
    //使用PowerMockito.mockStatic开启对HelloUtils2的静态mock
    @Before
    public void setup() {
        PowerMockito.mockStatic(HelloUtils2.class);
        PowerMockito.mockStatic(SLog.class);
    }


    @Test
    public void testVerify() throws Exception {
        //先执行逻辑, Powermock会收集执行时的数据，比如函数被调用多次，函数执行时间等信息，
        HelloUtils2.test3();
        HelloUtils2.test3();
        HelloUtils2.test3();
        //然后再对Powermock收集到的数据进行校验 , verifyStatic函数的参数是一个校验模型
        // times(3)表示执行了3次， 但是此时还不知道对哪个函数的执行次数校验3次
        // 必须在后面调用 要校验的 函数， 执行后， Powermock就知道要校验谁了，
        //Powermock此时会执行真正的校验逻辑， 看test3函数是否真的执行了3次
        PowerMockito.verifyStatic(times(3));
        HelloUtils2.test3();
    }

    @Test
    public void testVerifyFailed() throws Exception {
        //先执行逻辑, Powermock会收集执行时的数据，比如函数被调用多次，函数执行时间等信息，
        HelloUtils2.test3();
        HelloUtils2.test3();
//        HelloUtils2.test3();
        //然后再对Powermock收集到的数据进行校验 , verifyStatic函数的参数是一个校验模型
        // times(3)表示执行了3次， 但是此时还不知道对哪个函数的执行次数校验3次
        // 必须在后面调用 要校验的 函数， 执行后， Powermock就知道要校验谁了，
        //Powermock此时会执行真正的校验逻辑， 看test3函数是否真的执行了3次
        PowerMockito.verifyStatic(times(3));
        HelloUtils2.test3();
    }

    @Test
    public void testVerifyStringParam() throws Exception {
        HelloUtils2.testStringParam("test");
        HelloUtils2.testStringParam("test");
        PowerMockito.verifyStatic(times(2));
        HelloUtils2.testStringParam("test");
    }

    @Test
    public void testVerifyStringParamFailed() throws Exception {
        HelloUtils2.testStringParam("test1");
        HelloUtils2.testStringParam("test");
        PowerMockito.verifyStatic(times(2));
        HelloUtils2.testStringParam("test");
    }

    @Test
    public void testVerifyLog() throws Exception {
        //针对HelloUtils2类和SLog类做partial mock
        PowerMockito.spy(HelloUtils2.class);
        PowerMockito.spy(SLog.class);
        HelloUtils2.testLog();
        //因为testLog函数里调用了SLog, 而我们接下来校验时,不允许对SLog调用，所以校验会显示错误
        //使用这种方式能很方便地校验业务逻辑
        PowerMockito.verifyStatic(times(0));
        SLog.e(HelloUtils2.class.getSimpleName(), "Hello world");
    }

}
