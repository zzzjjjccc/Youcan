package com.jichang.youcan.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.util.ToastUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.activity.ContentActivity;
import com.jichang.youcan.activity.MainActivity;
import com.jichang.youcan.db.model.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class DateRecyclerAdapter extends RecyclerView.Adapter<DateRecyclerAdapter.ViewHolder> {

    private static final String TAG = "DateRecyclerAdapter";

    private List<Date> dates;

    private Context context;

    private List<Date> willBeDeleteDates = new ArrayList<>();

    public DateRecyclerAdapter(List<Date> dates) {
        this.dates = dates;
    }

    private void today() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String today = format.format(new java.util.Date());
        for (Date d : dates) {
            if (today.equals(d.getDate())) {
                return;
            }
        }
        ToastUtils.showToast(BaseApplication.getInstance(), "主人 ~ ~, 你还没有添加今日纪录呢");
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        today();

        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_youcandate_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Date date = dates.get(position);
        holder.tvTime.setText(date.getDate());

        final String queryTime = date.getDate();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (willBeDeleteDates.contains(date)) {
                    willBeDeleteDates.remove(date);
                    setColorForDate(holder, date);
                } else {
                    ContentActivity.actionStart(context, MainActivity.INTENT_KEY_DATE, queryTime);
                }
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                willBeDeleteDates.add(date);
                holder.circleImageView.setVisibility(View.VISIBLE);
                return true;
            }
        });

        setColorForDate(holder, date);

    }

    public List<Date> getDelDates() {
        return willBeDeleteDates;
    }

    /**
     * 为卡片布局设定背景颜色
     * 如果是长按过的就是删除颜色
     * 然后分为时间已过和没过的进行设置
     *
     * @param holder Holder
     * @param date date
     */
    private void setColorForDate(ViewHolder holder, Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new java.util.Date());
        if (willBeDeleteDates.contains(date)) {
            holder.circleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.circleImageView.setVisibility(View.INVISIBLE);
            if (today.compareTo(date.getDate()) != 0) {
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.not_today));
            } else {
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.today));
            }
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;

        CardView cardView;

        CircleImageView circleImageView;

        ViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            cardView = itemView.findViewById(R.id.time_cardview);
            circleImageView = itemView.findViewById(R.id.iv_check);
        }
    }
}
