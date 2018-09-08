package com.yushi.leke.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.R;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;


/**
 * 作者：Created by zhanyangyang on 2018/9/08
 * 邮箱：zhanyangyang@hzyushi.cn
 */
public class CommonListDialog extends Dialog {
    private TextView tv_title;
    private TextView tv_negative;
    private YFRecyclerView yfRecyclerView;
    private MultiTypeAdapter adapter;
    private Context mContext;


    public CommonListDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CommonListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public CommonListDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_list_common_layout, null);
        setContentView(rootView);
        tv_title = rootView.findViewById(R.id.tv_title);
        tv_negative = rootView.findViewById(R.id.tv_negative);
        yfRecyclerView = rootView.findViewById(R.id.yf_dialog);

    }

    public void bindData(List<String> datas, ICallBack iCallBack) {
        adapter = new MultiTypeAdapter();
        adapter.register(String.class, new ListDialogViewbinder(iCallBack));
//        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_divider_shape));
//        yfRecyclerView.addItemDecoration(divider);
        yfRecyclerView.setAdapter(adapter);
        adapter.setItems(datas);
        yfRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        window.setAttributes(p);
        window.setDimAmount(0.6f);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.dismiss();
    }


    public CommonListDialog setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public CommonListDialog setNegativeName(String negativeName) {
        tv_negative.setText(negativeName);
        return this;
    }

    public void setCommonClickListener(final CommonDialogClick commonClickListener) {
        tv_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonClickListener != null) {
                    commonClickListener.onClick(CommonListDialog.this);
                }
            }
        });
    }

    public interface CommonDialogClick {
        void onClick(CommonListDialog commonListDialog);
    }
}
