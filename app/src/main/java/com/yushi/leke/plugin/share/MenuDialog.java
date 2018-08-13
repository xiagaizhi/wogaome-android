package com.yushi.leke.plugin.share;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yufan.library.R;
import com.yufan.library.view.recycler.anim.FadeInUpAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 17/8/29.
 * 底部交互dialog
 */

public abstract class MenuDialog extends Dialog {

    private boolean isAnim;
    private final View content;
    private RecyclerView recyclerView;
    private ImageView iv_cancel;
    private int shareRowCount = 4;
    private MenuType[] types;
    private ShareAdapter mShareAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (types.length > mShareAdapter.getItemCount()) {
                        MenuType menu = types[mShareAdapter.getItemCount()];
                        mShareAdapter.add(menu, mShareAdapter.getItemCount());
                        sendEmptyMessage(0);
                    } else if (types.length == mShareAdapter.getItemCount()) {
                        ViewCompat.animate(iv_cancel)
                                .rotation(360)
                                .setDuration(400)

                                .start();
                    }
                    break;
                //无动画
                case 1:
                    for (int i=0;i<types.length;i++){
                        MenuType menu = types[i];
                        mShareAdapter.add(menu,mShareAdapter.getItemCount());
                    }
                    break;
            }
        }
    };
    public MenuDialog(Context context, MenuType[] types, boolean isItemAnim) {
        super(context, R.style.dialog_bottom_v2);
        this.types=types;
        this.isAnim=isItemAnim;
        if(types.length>4){
            shareRowCount=4;
        }else {
            shareRowCount=types.length;
        }
        content = LayoutInflater.from(context).inflate(
                R.layout.share_layout, null);
        initView( content);
        setCanceledOnTouchOutside(true);
        setContentView(content);

    }





    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);

    }
    private void initView(View content) {

        recyclerView = (RecyclerView) content.findViewById(R.id.rc_share);
        iv_cancel = (ImageView) content.findViewById(R.id.iv_cancel);
        content.findViewById(R.id.view_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), shareRowCount));
        if(isAnim){
            recyclerView.setItemAnimator(new FadeInUpAnimator());
            recyclerView.getItemAnimator().setAddDuration(370);
            recyclerView.getItemAnimator().setRemoveDuration(370);
        }
        if (types.length > 4) {
            recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getContext().getResources().getDimension(R.dimen.px550)));
        } else {
            recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getContext().getResources().getDimension(R.dimen.px350)));
        }
        //当按钮个数少于4个，加padding算法，个数越少padding越多
        if(types.length<4&&types.length>0){
            WindowManager m = getWindow().getWindowManager();
            Display d = m.getDefaultDisplay();
            int padd=(d.getWidth()/5)/types.length;
            recyclerView.setPadding(padd,0,padd,0);
        }
        mShareAdapter = new ShareAdapter(new ArrayList<MenuType>(), new OnClickListener() {
            @Override
            public void onItemClick(int position) {
                MenuType shareItem = mShareAdapter.getDatas().get(position);
                dispatchClick(shareItem);

            }
        });
        recyclerView.setAdapter(mShareAdapter);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if(isAnim){
                    handler.sendEmptyMessageDelayed(0, 10);
                }else {
                    handler.sendEmptyMessage(1);
                }

            }
        });
    }



    /**
     * 按钮adapter
     */
    private class ShareAdapter extends RecyclerView.Adapter<ShareViewHolder> {

        private List<MenuType> list;
        private OnClickListener listener;

        public void remove(int position) {
            list.remove(position);
            notifyItemRemoved(position);
        }

        public List<MenuType> getDatas() {
            return list;
        }

        public void add(MenuType text, int position) {
            list.add(position, text);
            notifyItemInserted(position);
        }

        public ShareAdapter(List<MenuType> list, OnClickListener listener) {
            this.list = list;
            this.listener = listener;
        }

        @Override
        public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.share_item, null);
            return new ShareViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(ShareViewHolder holder, int position) {
            MenuType item = list.get(position);
            holder.iv_share_icon.setImageResource(item.getIcon());
            holder.tv_share_name.setText(item.getName());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    private class ShareViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_share_icon;
        public TextView tv_share_name;
        private OnClickListener itemListener;

        public ShareViewHolder(View itemView, OnClickListener listener) {
            super(itemView);
            this.itemListener = listener;
            iv_share_icon = (ImageView) itemView.findViewById(R.id.iv_share_icon);
            tv_share_name = (TextView) itemView.findViewById(R.id.tv_share_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MenuDialog.ShareViewHolder.this.itemListener != null) {
                        MenuDialog.ShareViewHolder.this.itemListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }

    }




    protected abstract void dispatchClick(MenuType shareItem);
    private interface OnClickListener {
        void onItemClick(int position);
    }
}
