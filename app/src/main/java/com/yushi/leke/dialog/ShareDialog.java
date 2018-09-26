package com.yushi.leke.dialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.yushi.leke.R;
import com.yushi.leke.util.QRCodeUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.lankton.anyshape.AnyshapeImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ShareDialog extends DialogFragment{
    RelativeLayout re_scrrent;
    ImageView img_qrcode;
    TextView tv_name,tv_city,tv_introduc;
    Button btn_save;
    ImageView img_all;
    AnyshapeImageView anyshape_title;
    private String logo,introduction,shareurl,username,city;
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
        logo=getArguments().getString("logo");
        introduction=getArguments().getString("introduction");
        username=getArguments().getString("username");
        shareurl=getArguments().getString("shareurl");
        city=getArguments().getString("city");
        img_qrcode=view.findViewById(R.id.img_qrcode);
        tv_name=view.findViewById(R.id.tv_name);
        tv_city=view.findViewById(R.id.tv_city);
        tv_introduc=view.findViewById(R.id.tv_industry);
        btn_save=view.findViewById(R.id.btn_save);
        anyshape_title=view.findViewById(R.id.anyshape_title);
        re_scrrent=view.findViewById(R.id.re_sc);
        //加载背景，
        img_all=view.findViewById(R.id.img_all);
        Glide.with(getContext())
                .load(logo)
                .dontAnimate()
                //.error(R.drawable.no_music_rotate_img)
                // 设置高斯模糊
                .bitmapTransform(new BlurTransformation(getContext(), 14, 3))
                .into(img_all);
        Log.d("LOGH", String.valueOf(Uri.parse(logo)));
        //新建线程加载图片信息，发送到消息队列中
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = getURLimage(logo);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bmp;
                handle.sendMessage(msg);
            }
        }).start();  ;
        tv_name.setText(username);
        tv_city.setText(city);
        tv_introduc.setText(introduction);
        Bitmap bitmap = QRCodeUtil.createlogocode(shareurl, 130,
                BitmapFactory.decodeResource(getResources(),R.drawable.ic_logo_leke));
        img_qrcode.setImageBitmap(bitmap);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bmp=convertViewToBitmap(re_scrrent);
                saveBitmap(bmp);
                re_scrrent.destroyDrawingCache();
                dismiss();// 保存过后释放资源
            }
        });
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
       getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }
    //在消息队列中实现对控件的更改
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap bmp=(Bitmap)msg.obj;
                    anyshape_title.setImageBitmap(bmp);
                    break;
            }
        };

    };
    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
