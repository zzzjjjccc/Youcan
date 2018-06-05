package com.jichang.youcan.constant;

import android.os.ParcelUuid;

/**
 * @author jichang
 * @date 2018/1/31
 * email: 2218982471@qq.com
 * describ:
 */

public class BeaconConstants {

    /**
     * Eddystone-UID frame type value
     */
    public static final byte UID_FRAME_TYPE = 0x00;

    /**
     * Minimum expected Tx power (in dBm) in UID and URL frames
     */
    public static final int MIN_EXPECTED_TX_POWER = -100;

    /**
     * Maximum expected Tx power (in dBm) in UID and URL frames
     */
    public static final int MAX_EXPECTED_TX_POWER = 20;

    public static final ParcelUuid SERVICE_UUID =
            ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB");

    // The Eddystone Service UUID, 0xFEAA.
    public static final ParcelUuid EDDYSTONE_SERVICE_UUID =
            ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB");
}
