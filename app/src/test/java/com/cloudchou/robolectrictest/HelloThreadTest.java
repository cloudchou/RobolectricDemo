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

import static org.mockito.Mockito.times;

/**
 * date 2016/7/3
 *
 * @author Cloud
 * @version 1.1
 * @since Ver 1.1
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
//必须写如下代码 让PowerMock 忽略Robolectric的所有注入
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
//因为我们是针对类做静态函数的mock，所以必须使用PrepareForTest说明我们要mock的类
@PrepareForTest({SLog.class})
public class HelloThreadTest {

    //不可缺少的代码 表明使用Powermock执行单元测试，虽然我们使用的是RoblectricGradleTestRunner来执行单元测试
    //但是添加了如下代码后RoblectricGradleTestRunner会调用PowerMock的TestRunner去执行单元测试
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setup() {
        PowerMockito.mockStatic(SLog.class);
    }

    @Test
    public void testInit() throws Exception {
        PowerMockito.spy(SLog.class);
        HelloThread.getInstance().init();
        HelloThread.getInstance().waitForInitFinished();
        //因为我们在被测试代码里调用了SLog.e 日志， 所以verifyStatic  必然失败
        PowerMockito.verifyStatic(times(0));
        SLog.e(HelloThread.class.getSimpleName(), "init failed");
    }

}