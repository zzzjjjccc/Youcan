package com.jichang.youcan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jichang.youcan.R;
import com.jichang.youcan.data.bean.res.DiscoverInfo;

import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class DiscoverRecyclerAdapter extends RecyclerView.Adapter<DiscoverRecyclerAdapter.ViewHolder> {

    private static final String TAG = "DiscoverRecyclerAdapter";

    private List<DiscoverInfo> mDiscoverInfos;

    private Context context;

    public DiscoverRecyclerAdapter(List<DiscoverInfo> discoverInfos) {
        this.mDiscoverInfos = discoverInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_youcan_discover, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        DiscoverInfo discoverInfo = mDiscoverInfos.get(position);



        holder.tvName.setText(discoverInfo.getName());
        holder.tvContent.setText(discoverInfo.getContent());
        holder.tvDate.setText(discoverInfo.getDate());
    }

    @Override
    public int getItemCount() {
        return mDiscoverInfos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        TextView tvContent;

        TextView tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_discover_name);
            tvContent = itemView.findViewById(R.id.tv_discover_content);
            tvDate = itemView.findViewById(R.id.tv_discover_date);
        }
    }
}
