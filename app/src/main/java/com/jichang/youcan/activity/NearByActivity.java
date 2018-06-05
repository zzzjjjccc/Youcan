package com.jichang.youcan.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jichang.jichangprojectlibrary.activity.AppManager;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.adapter.NearByRecyclerAdapter;
import com.jichang.youcan.constant.BeaconConstants;
import com.jichang.youcan.model.Beacon;
import com.jichang.youcan.util.UidValidatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.jichang.youcan.constant.BeaconConstants.EDDYSTONE_SERVICE_UUID;

/**
 * @author jichang
 * @date 2018/1/26
 * email: 2218982471@qq.com
 * describ:
 */

public class NearByActivity extends AbstractAppBaseActivity
        implements View.OnClickListener {

    private static final String TAG = NearByActivity.class.getSimpleName();

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 2;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;

    private ArrayList<Beacon> mBeacons = new ArrayList<>();

    private NearByRecyclerAdapter mAdapter;

    private List<ScanFilter> mScanFilters;

    private ScanCallback mCallback;

    private BluetoothLeScanner mScanner;

    // An aggressive scan for nearby devices that reports immediately.
    private static final ScanSettings SCAN_SETTINGS =
            new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(0)
                    .build();

    private static final Handler mHandler = new Handler(Looper.getMainLooper());


    private Map<String /* device address */, Beacon> deviceToBeaconMap = new HashMap<>();

    private int onLostTimeoutMillis;

    private BluetoothAdapter mBtAdapter;


    /**
     * Enabled is false, and if idol's phone number not be subscribed, it will change to enable
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youcan_near_by);
        initVariables();
        initViews();
        initEvents();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.removeCallbacksAndMessages(null);
        onLostTimeoutMillis = 5 * 1000;
        setOnLostRunnable();
        if (mScanner != null) {
            mScanner.startScan(mScanFilters, SCAN_SETTINGS, mCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScanner != null && mBtAdapter.isEnabled()) {
            mScanner.stopScan(mCallback);
        }
    }

    @Override
    public void initVariables() {
        init();
        mScanFilters = new ArrayList<>();
        mScanFilters.add(new ScanFilter.Builder().setServiceUuid(EDDYSTONE_SERVICE_UUID).build());
        mCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                ScanRecord scanRecord = result.getScanRecord();
                if (scanRecord == null) {
                    return;
                }

                String deviceAddress = result.getDevice().getAddress();
                Beacon beacon;
                if (!deviceToBeaconMap.containsKey(deviceAddress)) {
                    beacon = new Beacon(deviceAddress, result.getRssi());
                    deviceToBeaconMap.put(deviceAddress, beacon);
                    mBeacons.add(beacon);
                } else {
                    deviceToBeaconMap.get(deviceAddress).mLastSeenTimeStamp = System.currentTimeMillis();
                    deviceToBeaconMap.get(deviceAddress).mRssi = result.getRssi();

                }

                byte[] serviceData = scanRecord.getServiceData(EDDYSTONE_SERVICE_UUID);
                validateServiceData(deviceAddress, serviceData);
            }

            @Override
            public void onScanFailed(int errorCode) {
                ToastUtils.showToast(NearByActivity.this, "ScanFailed");
            }
        };

        onLostTimeoutMillis = 5 * 1000;
    }

    @Override
    public void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_beacon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(NearByActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new NearByRecyclerAdapter(NearByActivity.this, mBeacons);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvents() {
    }

    @Override
    public void loadData() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle("搜索附近");
        }


    }

    @Override
    public void onClick(View v) {
        final int i = v.getId();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppManager.getAppManager().finishActivity();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                init();
            } else {
                AppManager.getAppManager().finishActivity();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    ToastUtils.showToast(this, "Coarse location access is required App will close since the permission was denied");
                }
            }
        }
    }

    private void setOnLostRunnable() {
        Runnable removeLostDevices = new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                Iterator<Map.Entry<String, Beacon>> itr = deviceToBeaconMap.entrySet().iterator();
                while (itr.hasNext()) {
                    Beacon beacon = itr.next().getValue();
                    if ((time - beacon.mLastSeenTimeStamp) > onLostTimeoutMillis) {
                        itr.remove();
                        mBeacons.remove(beacon);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                mHandler.postDelayed(this, onLostTimeoutMillis);
            }
        };
        mHandler.postDelayed(removeLostDevices, onLostTimeoutMillis);
    }

    // Checks the frame type and hands off the service data to the validation module.
    private void validateServiceData(String deviceAddress, byte[] serviceData) {
        Beacon beacon = deviceToBeaconMap.get(deviceAddress);

        switch (serviceData[0]) {
            case BeaconConstants.UID_FRAME_TYPE:
                UidValidatorUtils.validate(deviceAddress, serviceData, beacon);
                break;
            default:
                break;
        }
        if (!deviceToBeaconMap.containsKey(deviceAddress)) {
            deviceToBeaconMap.put(deviceAddress, beacon);
            mBeacons.add(beacon);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (NearByActivity.this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs coarse location access");
                builder.setMessage("Please grant coarse location access so this app can scan for beacons");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });

                builder.show();
            }
        }
        BluetoothManager manager = (BluetoothManager) getApplicationContext()
                .getSystemService(Context.BLUETOOTH_SERVICE);
        mBtAdapter = manager.getAdapter();
        if (mBtAdapter == null) {
            ToastUtils.showToast(NearByActivity.this, "Bluetooth Error Bluetooth not detected on device");
        } else if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
        } else {
            mScanner = mBtAdapter.getBluetoothLeScanner();
        }
    }
}
