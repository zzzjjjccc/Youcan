package com.jichang.youcan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.jichang.jichangprojectlibrary.util.IntentUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.activity.UserDetailActivity;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.model.Beacon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class NearByRecyclerAdapter extends RecyclerView.Adapter<NearByRecyclerAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "DateRecyclerAdapter";

    private List<Beacon> mAllBeacons;

    private List<Beacon> mFilteredBeacons;

    private Context context;

    private View mView;

    public NearByRecyclerAdapter(Context context, List<Beacon> mAllBeacons) {
        this.mAllBeacons = mAllBeacons;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        mView = null;
        mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_youcan_beacon, parent, false);
        final ViewHolder holder = new ViewHolder(mView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Beacon beacon = mAllBeacons.get(position);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(SpConstants.USER_PHONE, beacon.mUidStatus.mUidValue.substring(21, 32));
                IntentUtils.startActivity(context, UserDetailActivity.class, bundle);
            }
        });

        holder.tvDeviceAddress.setText(beacon.mDeviceAddress);

        if (beacon.mHasUidFrame) {
            holder.tvDistance.setText(String.format("%.2f m", distanceFromRssi(beacon.mRssi, beacon.mUidStatus.mTxPower)));
        } else {
            holder.tvDistance.setText(R.string.unKnown);
        }

    }

    private double distanceFromRssi(int rssi, int txPowerOm) {
        int pathLoss = txPowerOm - rssi;
        return Math.pow(10, (pathLoss - 41) / 20.0);
    }

    @Override
    public int getItemCount() {
        return mAllBeacons.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Beacon> filteredBeacons;
                if (constraint != null && constraint.length() != 0) {
                    filteredBeacons = new ArrayList<>();
                    for (Beacon b : mAllBeacons) {
                        if (b.contains(constraint.toString())) {
                            filteredBeacons.add(b);
                        }
                    }
                } else {
                    filteredBeacons = mAllBeacons;
                }
                results.count = filteredBeacons.size();
                results.values = filteredBeacons;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredBeacons.clear();
                mFilteredBeacons.addAll((List<Beacon>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDeviceAddress;

        TextView tvDistance;

        ViewHolder(View itemView) {
            super(itemView);
            tvDeviceAddress = itemView.findViewById(R.id.tv_beacon_device_address);
            tvDistance = itemView.findViewById(R.id.tv_beacon_distance);
        }
    }
}
