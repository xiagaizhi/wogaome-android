package com.yushi.leke.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;
import com.yushi.leke.util.QRCodeUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import cn.lankton.anyshape.AnyshapeImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ShareDialog extends DialogFragment implements ICallBack {
    RelativeLayout re_scrrent;
    ImageView img_qrcode;
    TextView tv_name,tv_city,tv_introduc;
    Button btn_save;
    ImageView img_all;
    ICallBack mICallBack;
    AnyshapeImageView anyshape_title;
    public void setmICallBack(ICallBack mICallBack) {
        this.mICallBack = mICallBack;
    }
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
        anyshape_title=view.findViewById(R.id.anyshape_title);
        re_scrrent=view.findViewById(R.id.reone);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bmp=convertViewToBitmap(re_scrrent);
                saveBitmap(bmp);
                re_scrrent.destroyDrawingCache(); // 保存过后释放资源
                }
        });
        //加载背景，
        img_all=view.findViewById(R.id.img_all);
        Glide.with(getContext())
                .load(R.drawable.ic_anyshape_text)
                .dontAnimate()
                //.error(R.drawable.no_music_rotate_img)
                // 设置高斯模糊
                .bitmapTransform(new BlurTransformation(getContext(), 14, 3))
                .into(img_all);
    }
    /**
     * 对View进行量测，布局后截图
     *
     * @param view
     * @return
     */
    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
    public void saveBitmap(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"share");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "share" + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
       // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }

    @Override
    public void OnBackResult(Object... s) {
        for (Object o:s){
            Log.d("LOGH",o.toString()+"1");
        }
        if (s!=null){
            anyshape_title.setImageURI((Uri) s[0]);
            tv_introduc.setText((String) s[1]);
            Bitmap bitmap = QRCodeUtil.createlogocode(String.valueOf(s[2]), 130,
                    BitmapFactory.decodeResource(getResources(),R.drawable.ic_logo_leke));
            img_qrcode.setImageBitmap(bitmap);
            tv_name.setText((String) s[3]);
            tv_city.setText((String) s[4]);
        }
    }
}
