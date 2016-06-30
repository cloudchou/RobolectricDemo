package com.cloudchou.robolectrictest;

import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by Cloud on 2016/6/25.
 */
@Implements(value = TelephonyManager.class, isInAndroidSdk = false)
public class ShadowTelephoneManager {

    @Implementation
    public String getDeviceId() {
        return "863396020118228";
    }

}
