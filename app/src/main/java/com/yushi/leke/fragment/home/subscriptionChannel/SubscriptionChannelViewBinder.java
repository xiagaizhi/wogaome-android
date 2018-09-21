package com.yushi.leke.fragment.home.subscriptionChannel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;
import com.yushi.leke.fragment.home.bean.Homeinfo;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 作者：Created by zhanyangyang on 2018/9/20 09:28
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class SubscriptionChannelViewBinder extends ItemViewBinder<Homeinfo, SubscriptionChannelViewBinder.ViewHolder> {
    private ICallBack mICallBack;

    public SubscriptionChannelViewBinder(ICallBack iCallBack) {
        this.mICallBack = iCallBack;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new SubscriptionChannelViewBinder.ViewHolder(layoutInflater.inflate(R.layout.item_mysubscription_layout, viewGroup, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final Homeinfo homeinfo) {
        viewHolder.img_logo.setImageURI(homeinfo.getHorizontalIcon());
        viewHolder.tv_title.setText(homeinfo.getAlbumName());
        viewHolder.tv_name.setText(homeinfo.getCreator());
        viewHolder.tv_industry.setText(homeinfo.getCreatorInfo());
        viewHolder.tv_othertitle.setText(homeinfo.getIntroduction());
        viewHolder.tv_playcount.setText(homeinfo.getViewTimes());
        if (!TextUtils.isEmpty(homeinfo.getTags())) {
            String[] tags = homeinfo.getTags().split(",");
            if (tags.length == 0) {
                viewHolder.ll_tag.setVisibility(View.GONE);
            } else if (tags.length == 1) {
                viewHolder.ll_tag.setVisibility(View.VISIBLE);
                viewHolder.tv_tab1.setText(tags[0]);
                viewHolder.tv_tab1.setVisibility(View.VISIBLE);
                viewHolder.tv_tab2.setVisibility(View.GONE);
                viewHolder.tv_tab3.setVisibility(View.GONE);
            } else if (tags.length == 2) {
                viewHolder.ll_tag.setVisibility(View.VISIBLE);
                viewHolder.tv_tab1.setText(tags[0]);
                viewHolder.tv_tab2.setText(tags[1]);
                viewHolder.tv_tab1.setVisibility(View.VISIBLE);
                viewHolder.tv_tab2.setVisibility(View.VISIBLE);
                viewHolder.tv_tab3.setVisibility(View.GONE);
            } else if (tags.length == 3) {
                viewHolder.ll_tag.setVisibility(View.VISIBLE);
                viewHolder.tv_tab1.setText(tags[0]);
                viewHolder.tv_tab2.setText(tags[1]);
                viewHolder.tv_tab3.setText(tags[2]);
                viewHolder.tv_tab1.setVisibility(View.VISIBLE);
                viewHolder.tv_tab2.setVisibility(View.VISIBLE);
                viewHolder.tv_tab3.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.ll_tag.setVisibility(View.GONE);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mICallBack != null) {
                    mICallBack.OnBackResult(1, homeinfo);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mICallBack != null) {
                    mICallBack.OnBackResult(2, homeinfo);
                }
                return true;
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView img_logo;
        private TextView tv_title;
        private TextView tv_name;
        private TextView tv_industry;
        private TextView tv_othertitle;
        private LinearLayout ll_tag;
        private TextView tv_tab1, tv_tab2, tv_tab3;
        private TextView tv_playcount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img_logo);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_industry = itemView.findViewById(R.id.tv_industry);
            tv_othertitle = itemView.findViewById(R.id.tv_othertitle);
            ll_tag = itemView.findViewById(R.id.ll_tag);
            tv_tab1 = itemView.findViewById(R.id.tv_tab1);
            tv_tab2 = itemView.findViewById(R.id.tv_tab2);
            tv_tab3 = itemView.findViewById(R.id.tv_tab3);
            tv_playcount = itemView.findViewById(R.id.tv_playcount);
        }
    }
}
