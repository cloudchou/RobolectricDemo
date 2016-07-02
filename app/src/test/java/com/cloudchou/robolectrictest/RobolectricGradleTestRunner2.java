package com.cloudchou.robolectrictest;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Cloud on 2016/6/29.
 */
//可以修改AndroidManifest和asset的TestRunner
public class RobolectricGradleTestRunner2 extends RobolectricGradleTestRunner {

    public RobolectricGradleTestRunner2(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected AndroidManifest getAppManifest(final Config config) {
        //使用父类的方法创建AndroidManifest对象,
        //因为我们只修改AndroidManifest对象的AndroidManifest文件的位置
        // 和assets文件夹的位置
        AndroidManifest appManifest = super.getAppManifest(config);
        //只有当我们在测试脚本里设置了manifest时，自定义TestRunner才处理
        if (config.manifest() != null && !"".equals(config.manifest())) {
            //使用反射修改AndroidManifest对象的androidManifestFile字段的值，
            //使其指向我们设置的AndroidManifest文件的位置
            FileFsFile manifestFile = FileFsFile.from(config.manifest());
            Class<? extends AndroidManifest> appManifestClass = null;
            try {
                appManifestClass = appManifest.getClass();
                Field androidManifestFileField;
                try {
                    androidManifestFileField = appManifestClass.getDeclaredField("androidManifestFile");
                } catch (NoSuchFieldException e) {
                    androidManifestFileField = appManifestClass.getSuperclass().getDeclaredField("androidManifestFile");
                }
                androidManifestFileField.setAccessible(true);
                androidManifestFileField.set(appManifest, manifestFile);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //使用反射修改AndroidManifest对象的assetsDirectory字段的值，
            //使其指向我们设置的assets文件夹的位置
            String parent = new File(config.manifest()).getParent();
            FileFsFile assetFile = FileFsFile.from(parent, config.assetDir());
            try {
                Field assetsDirectoryField = appManifestClass.getDeclaredField("assetsDirectory");
                assetsDirectoryField.setAccessible(true);
                assetsDirectoryField.set(appManifest, assetFile);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return appManifest;
    }

}
