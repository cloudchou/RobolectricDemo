package com.cloudchou.robolectrictest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.Scheduler;

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
//必须写如下代码 让PowerMock 忽略Robolectric的所有注入 这里因为要使用https 所以还需要忽略ssl
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "javax.net.ssl.*"})
//因为我们是针对类做静态函数的mock，所以必须使用PrepareForTest说明我们要mock的类
@PrepareForTest({SLog.class})
public class VolleyRequesterTest {

    //不可缺少的代码 表明使用Powermock执行单元测试，虽然我们使用的是RoblectricGradleTestRunner来执行单元测试
    //但是添加了如下代码后RoblectricGradleTestRunner会调用PowerMock的TestRunner去执行单元测试
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setup() {
        PowerMockito.mockStatic(SLog.class);
    }

    @Test
    public void testRequest() throws Exception {
        PowerMockito.spy(SLog.class);
        VolleyRequester requester = new VolleyRequester();
        //调用请求方法后 volley 会开启后台线程去做真正的请求， 请求完毕后会在主线程上
        //调用Listener.onResponse方法通知请求完毕
        //但是主线程是一个有Handler的线程，Robolectric框架让主线程不轮询消息队列
        //必须在测试方法里主动驱动主线程轮询消息队列，针对消息进行处理
        //否则永远不会在UI线程上通知请求完毕
        requester.request(RuntimeEnvironment.application);
        //获取主线程的消息队列的调度者，通过它可以知道消息队列的情况
        //并驱动主线程主动轮询消息队列
        Scheduler scheduler = Robolectric.getForegroundThreadScheduler();
        //因为调用请求方法后 后台线程请求需要一段时间才能请求完毕，然后才会通知主线程
        // 所以在这里进行等待，直到消息队列里存在消息
        while (scheduler.size() == 0) {
            Thread.sleep(500);
        }
        //轮询消息队列，这样就会在主线程进行通知
        scheduler.runOneTask();
        // 校验 请求是否失败
        PowerMockito.verifyStatic(times(0));
        SLog.e(VolleyRequester.class.getSimpleName(), "request failed");
        //如果没有失败 则打印请求回来的字符串
        String responseString = requester.getResponseString();
        System.out.println("response str:\n" + responseString);
    }


}