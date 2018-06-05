package com.jichang.youcan.ui;

import android.view.View;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public interface OnItemClickListener {

    /**
     * item 点击回调
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     * 完成按钮回调
     *
     * @param position
     */
    void onDoneClick(int position);

    /**
     * 删除按钮回调
     *
     * @param position
     */
    void onDeleteClick(int position);

    /**
     * 编辑按钮回调
     *
     * @param position
     */
    void onEditClick(int position);

}
