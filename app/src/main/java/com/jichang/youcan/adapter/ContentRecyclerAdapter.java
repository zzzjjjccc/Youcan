package com.jichang.youcan.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jichang.youcan.R;
import com.jichang.youcan.db.NoteContentManager;
import com.jichang.youcan.db.model.Content;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author jichang
 * @date 2018/1/18
 * email: 2218982471@qq.com
 * describ:
 */

public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder> {

    private List<Content> contents;

    private ContentViewActivityCallBackInterface callBackInterface;

    private Context context;

    /**
     * 回调接口，回调EditContentActivity中的回调方法。
     */
    public interface ContentViewActivityCallBackInterface {

        /**
         * Call this method to start EditContentActivity
         *
         * @param time    Time of event be created
         * @param content Content of event
         */
        void startToEditContentActivity(String time, String content);

    }

    public ContentRecyclerAdapter(List<Content> contents,
                                  ContentViewActivityCallBackInterface callBackInterface) {
        this.contents = contents;
        this.callBackInterface = callBackInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_youcan_content_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Content content = contents.get(position);
        final String con = content.getContent();
        holder.content.setText(con);

        setColorForContent(holder, content);

    }

    public void deleteContent(NoteContentManager noteContentManager, int position) {
        Content content = contents.get(position);
        noteContentManager.deleteNote(content);
    }

    /**
     * 为卡片布局设定背景颜色
     * 如果是长按过的就是删除颜色
     * 然后分为时间已过和没过的进行设置
     *
     * @param holder Holder
     */
    private void setColorForContent(ViewHolder holder, Content content) {
        if (content.getDone() == 1) {
            holder.circleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.circleImageView.setVisibility(View.GONE);

        }
        switch (content.getPriority()) {
            case 0:
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                break;
            case 1:
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.orange));
                break;
            case 2:
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.red));
                break;
            default:
                break;
        }
    }

    public void changeDone(NoteContentManager noteContentManager, int position) {
        Content content = contents.get(position);
        final String date = content.getDate();
        final String time = content.getTime();
        final int done = 1;
        noteContentManager.updateNote(time, done);
    }

    /**
     * 跳到编辑页面
     *
     * @param position Position
     */
    public void startActivity(int position) {
        Content content = contents.get(position);
        String time = content.getTime();
        String text = content.getContent();
        callBackInterface.startToEditContentActivity(time, text);
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView content;

        // TextView time;

        public LinearLayout linearLayout;

        CardView cardView;

        CircleImageView circleImageView;

        LinearLayout childLinearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.et_content);
            circleImageView = itemView.findViewById(R.id.civ_done);
            cardView = itemView.findViewById(R.id.adapter_content_cardview);
            linearLayout = itemView.findViewById(R.id.adapter_content_linear_layout);
            childLinearLayout = itemView.findViewById(R.id.adapter_content_child_linear_layout);
        }
    }
}
