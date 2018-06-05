package com.jichang.youcan.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jichang.jichangprojectlibrary.util.IntentUtils;
import com.jichang.youcan.R;
import com.jichang.youcan.activity.UserDetailActivity;
import com.jichang.youcan.constant.SpConstants;
import com.jichang.youcan.db.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class SubscribeRecyclerAdapter extends RecyclerView.Adapter<SubscribeRecyclerAdapter.ViewHolder> {

    private static final String TAG = "DateRecyclerAdapter";

    private List<User> subscribeUsers;

    private List<User> chooseUsers;

    private Context context;

    private View mView;

    private boolean check = false;

    /**
     * Set if show check box
     *
     * @param check If true, show check box, otherwise not
     */
    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<User> getShareUser(){
        return chooseUsers;
    }

    public SubscribeRecyclerAdapter(List<User> users) {
        this.subscribeUsers = users;
        chooseUsers = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        mView = null;
        mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_youcan_contacts, parent, false);
        final ViewHolder holder = new ViewHolder(mView);
        if (check) {
            holder.cbCheck.setVisibility(View.VISIBLE);
        } else {
            holder.cbCheck.setVisibility(View.GONE);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final User user = subscribeUsers.get(position);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = user.getPhone();
                Bundle bundle = new Bundle();
                bundle.putString(SpConstants.USER_PHONE, phone);
                IntentUtils.startActivity(context, UserDetailActivity.class, bundle);
            }
        });

        holder.tvName.setText(user.getName());

        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chooseUsers.add(user);
                } else {
                    if (chooseUsers.contains(user)) {
                        chooseUsers.remove(user);
                    }
                }
            }
        });

        if (chooseUsers.contains(user)) {
            holder.cbCheck.setChecked(true);
        } else {
            holder.cbCheck.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return subscribeUsers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        CheckBox cbCheck;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            cbCheck = itemView.findViewById(R.id.cb_me_check);
        }
    }
}
