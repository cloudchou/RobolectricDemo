package com.cloudchou.robolectrictest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Cloud on 2016/6/27.
 */
//必须指定使用自定义的TestRunner才能指定AndroidManifest文件的位置进行加载
@RunWith(RobolectricGradleTestRunner2.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class ManifestConfigTest {

    //指定使用test目录下的AndroidManifest.xml RobolectricGradleTestRunner2会自动将assets目录调整到
    //和AndroidMnifeset.xml同一级目录下的assets文件夹
    @Test
    @Config(manifest = "src/test/AndroidManifest.xml")
    public void testManifestConfig() throws PackageManager.NameNotFoundException, IOException {
        String packageName = RuntimeEnvironment.application.getPackageName();
        PackageManager packageManager = RuntimeEnvironment.application.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        System.out.println(packageInfo.activities);
        System.out.println(packageName);
        System.out.println(packageInfo.versionCode);
        System.out.println(packageInfo.versionName);
        InputStream fileInputStream = RuntimeEnvironment.application.getAssets().open("test.prop");
        Properties props = new Properties();
        props.load(fileInputStream);
        String property = props.getProperty("ro.product.cpu.abilist");
        System.out.println(property);
    }
}
