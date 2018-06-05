package com.jichang.youcan.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.jichang.youcan.R;
import com.jichang.youcan.adapter.ContentRecyclerAdapter;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class ScrollDeleteRecyclerView extends RecyclerView {

    private VelocityTracker mVelocityTracker;

    private Scroller mScroller;

    private Context mContext;

    // 删除按钮状态， 0：关闭 1：将要关闭 2：将要打开 3：打开
    private int mDeleteBtnState;

    //    触摸item的位置
    private int mPosition;

    private LinearLayout layout;

    private TextView mDone;
    private int mMaxLength;
    private OnItemClickListener mListener;
    private int mLastX;
    private int mLastY;
    private boolean isItemMoving;
    private boolean isDragging;
    private boolean isStartScroll;
    private TextView mDelete;
    private TextView mEdit;

    public ScrollDeleteRecyclerView(Context context) {
        this(context, null);
    }

    public ScrollDeleteRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollDeleteRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mScroller = new Scroller(context, new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mVelocityTracker.addMovement(e);

        int x = (int) e.getX();
        int y = (int) e.getY();
        View view = findChildViewUnder(x, y);
        if (view == null) {
            return false;
        }
        ContentRecyclerAdapter.ViewHolder viewHolder =
                (ContentRecyclerAdapter.ViewHolder) getChildViewHolder(view);

        layout = viewHolder.linearLayout;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDeleteBtnState == 0) {
//                    View view = findChildViewUnder(x, y);
//                    if (view == null) {
//                        return false;
//                    }
//                    ContentRecyclerAdapter.ViewHolder viewHolder =
//                            (ContentRecyclerAdapter.ViewHolder) getChildViewHolder(view);
//
//                    layout = viewHolder.linearLayout;
                    mPosition = viewHolder.getAdapterPosition();

                    mDone = (TextView) layout.findViewById(R.id.adapter_content_done);
                    mDelete = (TextView) layout.findViewById(R.id.adapter_content_delete);
                    mEdit = (TextView) layout.findViewById(R.id.adapter_content_edit);
                    mMaxLength = mDone.getWidth() * 3;
                    mDone.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onDoneClick(mPosition);
                            layout.scrollTo(0, 0);
                            mDeleteBtnState = 0;
                        }
                    });
                    mDelete.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onDeleteClick(mPosition);
                            layout.scrollTo(0, 0);
                            mDeleteBtnState = 0;
                        }
                    });
                    mEdit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onEditClick(mPosition);
                            layout.scrollTo(0, 0);
                            mDeleteBtnState = 0;
                        }
                    });
                } else if (mDeleteBtnState == 3) {
                    mScroller.startScroll(layout.getScrollX(),
                            0,
                            -mMaxLength,
                            0,
                            200);
                    invalidate();
                    mDeleteBtnState = 0;
                    return false;
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int ex = mLastX - x;
                int ey = mLastY - y;

                int scrollX = layout.getScrollX();
//                左边界检测
                if (Math.abs(ex) > Math.abs(ey)) {
                    isItemMoving = true;
                    if (scrollX + ex <= 0) {
                        layout.scrollTo(0, 0);
                        return true;
                    }
//                    右边界检测
                    else if (scrollX + ex >= mMaxLength) {
                        layout.scrollTo(mMaxLength, 0);
                        return true;
                    }
//                    item跟随手指滑动
                    layout.scrollBy(ex, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null) {
                    mListener.onItemClick(layout, mPosition);
                }
                isItemMoving = false;

//                计算手指滑动的速度
                mVelocityTracker.computeCurrentVelocity(1000);
//                x方向速度，向左为负
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();

                int deltaX = 0;
                int upScrollX = layout.getScrollX();

                if (Math.abs(xVelocity) > 100 &&
                        Math.abs(xVelocity) > Math.abs(yVelocity)) {
//                    左滑速度大于100
                    if (xVelocity <= -100) {
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;

                    } else if (xVelocity > 100) {
                        // 隐藏按钮
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                } else {
                    if (upScrollX >= mMaxLength / 2) {
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    } else if (upScrollX < mMaxLength / 2) {
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                }
//                item自动滑动到指定位置
                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();

                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            layout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mDeleteBtnState == 1) {
                mDeleteBtnState = 0;
            }

            if (mDeleteBtnState == 2) {
                mDeleteBtnState = 3;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
