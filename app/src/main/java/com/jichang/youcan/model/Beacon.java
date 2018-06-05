package com.jichang.youcan.model;

/**
 * @author jichang
 * @date 2018/1/30
 * email: 2218982471@qq.com
 * describ:
 */

public class Beacon {

    private static final String BULLET = "● ";

    /**
     * The address of given device
     */
    public final String mDeviceAddress;

    public int mRssi;

    public long mTimeStamp = System.currentTimeMillis();

    public long mLastSeenTimeStamp = System.currentTimeMillis();

    public byte[] mUidServiceData;

    public boolean mHasUidFrame;

    public UidStatus mUidStatus = new UidStatus();

    public Beacon(String da, int rssi) {
        this.mDeviceAddress = da;
        this.mRssi = rssi;
    }

    public class UidStatus {

        public String mUidValue;

        public int mTxPower;

        public String mErrTx;

        public String mErrUid;

        public String mErrRfu;

        public String getErrors() {
            StringBuilder sb = new StringBuilder();
            if (mErrTx != null) {
                sb.append(BULLET)
                        .append(mErrTx)
                        .append("\n");
            }
            if (mErrUid != null){
                sb.append(BULLET)
                        .append(mErrUid)
                        .append("\n");
            }
            if (mErrRfu != null) {
                sb.append(BULLET)
                        .append(mErrRfu)
                        .append("\n");
            }
            return sb.toString().trim();
        }
    }

    class FrameStatus {

        public String mNullServiceData;

        public String mTooShortServiceData;

        public String mInvalidFrameType;

        public String getErrors() {

            StringBuilder sb = new StringBuilder();
            if (mNullServiceData != null) {
                sb.append(BULLET)
                        .append(mNullServiceData)
                        .append("\n");
            }
            if (mTooShortServiceData != null){
                sb.append(BULLET)
                        .append(mTooShortServiceData)
                        .append("\n");
            }
            if (mInvalidFrameType != null) {
                sb.append(BULLET)
                        .append(mInvalidFrameType)
                        .append("\n");
            }
            return sb.toString().trim();

        }
    }



    public boolean contains(String s) {
        return s == null ||
                s.isEmpty() ||
                mDeviceAddress.replace(":", "").toLowerCase().contains(s.toLowerCase()) ||
                (mUidStatus.mUidValue != null && mUidStatus.mUidValue.toLowerCase().contains(s.toLowerCase()));
    }

}
