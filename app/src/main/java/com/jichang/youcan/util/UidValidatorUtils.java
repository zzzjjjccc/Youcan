package com.jichang.youcan.util;

import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.model.Beacon;

import java.util.Arrays;

/**
 * @author jichang
 * @date 2018/1/31
 * email: 2218982471@qq.com
 * describ:
 */

public class UidValidatorUtils {

    private static final String TAG = UidValidatorUtils.class.getSimpleName();

    public static void validate(String deviceAddress, byte[] serviceData, Beacon beacon) {


        beacon.mHasUidFrame = true;
        int txPower = serviceData[1];
        beacon.mUidStatus.mTxPower = txPower;

        // The namespace and instance bytes should not be all zeroes.
        byte[] uidBytes = Arrays.copyOfRange(serviceData, 2, 18);
        beacon.mUidStatus.mUidValue = toHexString(uidBytes);
        LogUtils.e(TAG, "Service data : " + beacon.mUidStatus.mUidValue);

        if (beacon.mUidServiceData == null) {
            beacon.mUidServiceData = serviceData.clone();
        } else {
            byte[] preUidBytes = Arrays.copyOfRange(beacon.mUidServiceData, 2 , 18);
            if (!Arrays.equals(uidBytes, preUidBytes)) {
                beacon.mUidServiceData = serviceData.clone();
            }
        }


    }


    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    static String toHexString(byte[] bytes) {
        if (bytes.length == 0) {
            return "";
        }
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int c = bytes[i] & 0xFF;
            chars[i * 2] = HEX[c >>> 4];
            chars[i * 2 + 1] = HEX[c & 0x0F];
        }
        return new String(chars).toLowerCase();
    }
}
