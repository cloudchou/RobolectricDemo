package com.cloudchou.robolectrictest;

import android.os.SystemProperties;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Shadow for {@link android.os.SystemProperties}.
 */
@Implements(value = SystemProperties.class, isInAndroidSdk = false)
public class CloudSystemProperties {
    private static final Map<String, Object> VALUES = new HashMap<>();
    private static final Set<String> alreadyWarned = new HashSet<>();

    static {
        VALUES.put("ro.build.version.release", "2.2");
        VALUES.put("ro.build.version.incremental", "0");
        VALUES.put("ro.build.version.sdk", 8);
        VALUES.put("ro.build.date.utc", 1277708400000L);  // Jun 28, 2010
        VALUES.put("ro.debuggable", 0);
        VALUES.put("ro.secure", 1);
        VALUES.put("ro.product.cpu.abilist", "armeabi-v7a");
        VALUES.put("ro.product.cpu.abilist32", "armeabi-v7a,armeabi");
        VALUES.put("ro.product.cpu.abilist64", "armeabi-v7a,armeabi");
        VALUES.put("ro.build.fingerprint", "robolectric");
        VALUES.put("ro.build.version.all_codenames", "REL");
        VALUES.put("log.closeguard.Animation", false);
        VALUES.put("debug.choreographer.vsync", false); // disable vsync for Choreographer
        VALUES.put("debug.choreographer.vsync", false); // disable vsync for Choreographer
        VALUES.put("persist.radio.multisim.config", "DSDS"); // disable vsync for Choreographer
        VALUES.put("ro.product.device", "GT-I9100G");
        VALUES.put("ro.product.board", "t1");
        VALUES.put("ro.build.product", "GT-I9100G");
        VALUES.put("ro.product.brand", "samsung");
        VALUES.put("ro.product.model", "GT-I9100G");
        VALUES.put("ro.build.fingerprint", "samsung/GT-I9100G/GT-I9100G:4.1.2/JZO54K/I9100GXXLSR:user/release-keys");
    }

    @Implementation
    public static String get(String key) {
        Object o = VALUES.get(key);
        if (o == null) {
            warnUnknown(key);
            return "";
        }
        return o.toString();
    }

    @Implementation
    public static String get(String key, String def) {
        Object value = VALUES.get(key);
        return value == null ? def : value.toString();
    }

    @Implementation
    public static int getInt(String key, int def) {
        Object value = VALUES.get(key);
        return value == null ? def : (Integer) value;
    }

    @Implementation
    public static long getLong(String key, long def) {
        Object value = VALUES.get(key);
        return value == null ? def : (Long) value;
    }

    @Implementation
    public static boolean getBoolean(String key, boolean def) {
        Object value = VALUES.get(key);
        return value == null ? def : (Boolean) value;
    }

    synchronized private static void warnUnknown(String key) {
        if (alreadyWarned.add(key)) {
            System.err.println("WARNING: no system properties value for " + key);
        }
    }
}