package com.cloudchou.robolectrictest;

import android.os.SystemProperties;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Shadow for {@link SystemProperties}.
 */
@Implements(value = SystemProperties.class, isInAndroidSdk = false)
public class CloudSystemProperties2 {

    @Implementation
    public static String get(String key) {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = CloudSystemProperties2.class.getResourceAsStream("i9100g.prop");
            prop.load(is);
            return prop.getProperty(key);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    @Implementation
    public static String get(String key, String def) {
        Object value = get(key);
        return value == null ? def : value.toString();
    }

    @Implementation
    public static int getInt(String key, int def) {
        String value = get(key);
        return value == null ? def : Integer.parseInt(value);
    }

    @Implementation
    public static long getLong(String key, long def) {
        String value = get(key);
        return value == null ? def : (Long) Long.parseLong(value);
    }

    @Implementation
    public static boolean getBoolean(String key, boolean def) {
        String value = get(key);
        return value == null ? def : Boolean.parseBoolean(value);
    }

}