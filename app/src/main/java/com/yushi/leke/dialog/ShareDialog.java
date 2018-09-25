package com.yushi.leke.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yushi.leke.R;
import com.yushi.leke.util.QRCodeUtil;
import com.yufan.library.view.HexagonImageView;

public class ShareDialog extends DialogFragment {
    ImageView img_qrcode;
    TextView tv_name,tv_city,tv_introduc;
    Button btn_save;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_qrcode_dialog, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init(view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
    private void init(View view){
        img_qrcode=view.findViewById(R.id.img_qrcode);
        tv_name=view.findViewById(R.id.tv_name);
        tv_city=view.findViewById(R.id.tv_city);
        tv_introduc=view.findViewById(R.id.tv_industry);
        btn_save=view.findViewById(R.id.btn_save);
        Bitmap bitmap = QRCodeUtil.createlogocode("https://www.baidu.com", 130,
                BitmapFactory.decodeResource(getResources(),R.drawable.ic_logo_leke));
        img_qrcode.setImageBitmap(bitmap);
    }
}
