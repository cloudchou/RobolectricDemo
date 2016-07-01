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
        AndroidManifest appManifest = super.getAppManifest(config);
        if (config.manifest() != null && !"".equals(config.manifest())) {
            FileFsFile manifestFile = FileFsFile.from(config.manifest());
            try {
                Field androidManifestFile = appManifest.getClass().getSuperclass().getDeclaredField("androidManifestFile");
                androidManifestFile.setAccessible(true);
                androidManifestFile.set(appManifest, manifestFile);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String parent = new File(config.manifest()).getParent();
            FileFsFile assetFile = FileFsFile.from(parent, config.assetDir());
            try {
                Field assetsDirectoryFile = appManifest.getClass().getSuperclass().getDeclaredField("assetsDirectory");
                assetsDirectoryFile.setAccessible(true);
                assetsDirectoryFile.set(appManifest, assetFile);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return appManifest;
    }

}
