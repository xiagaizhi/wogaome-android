package com.yushi.leke.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yufan.library.Global;
import com.yufan.library.manager.UserManager;
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

public class ShareDialog extends DialogFragment {
    LinearLayout re_scrrent;
    ImageView img_qrcode;
    TextView tv_name, tv_city, tv_introduc;
    Button btn_save;
    ImageView img_cancle;
    AnyshapeImageView anyshape_title;
    private String logo, introduction, shareurl, username, city;

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

    private void init(View view) {
        Bundle bundle=getArguments();
        if (bundle!=null){
            logo = bundle.getString("logo");
            introduction=bundle.getString("introduction");
            shareurl=bundle.getString("shareurl");
            username=bundle.getString("username");
            city=bundle.getString("city");
        }
        img_qrcode = view.findViewById(R.id.img_qrcode);
        tv_name = view.findViewById(R.id.tv_name);
        tv_city = view.findViewById(R.id.tv_city);
        tv_introduc = view.findViewById(R.id.tv_industry);
        btn_save = view.findViewById(R.id.btn_save);
        anyshape_title = view.findViewById(R.id.anyshape_title);
        re_scrrent = view.findViewById(R.id.re_sc);
        img_cancle=view.findViewById(R.id.img_cancle);
        img_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //加载背景，高斯模糊
        LoadImgToBackground(getActivity(), logo, re_scrrent);
        //新建线程加载图片信息，发送到消息队列中
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载图片
                Bitmap bmp = getURLimage(logo);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bmp;
                handle.sendMessage(msg);
            }
        }).start();
        ;
        tv_name.setText(username);
        tv_city.setText(city);
        tv_introduc.setText(introduction);
        //生成二维码
        if (shareurl!=null){
            Bitmap bitmap = QRCodeUtil.createlogocode(shareurl, 150,
                    BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_leke));
            img_qrcode.setImageBitmap(bitmap);
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取线性布局截图
                Bitmap bmp = getLinearLayoutBitmap(re_scrrent);
                saveBitmap(bmp);
                re_scrrent.destroyDrawingCache();
                dismiss();// 保存过后释放资源
            }
        });
    }
    public static void LoadImgToBackground(Activity activity, Object img, final View view){
        if (img==null){
            return;
        }
        Glide.with(activity).load(img).bitmapTransform(new BlurTransformation(activity, 14, 3))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        view.setBackgroundDrawable(resource);
                    }
                });
    }
    /**
     * 对View进行量测，布局后截图
     *
     * @param view
     * @return
     */
    /**
     * 截取LinearLayout
     **/
    public static Bitmap getLinearLayoutBitmap(LinearLayout linearLayout) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            h += linearLayout.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(linearLayout.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        linearLayout.draw(canvas);
        return bitmap;
    }



    public void saveBitmap(Bitmap bitmap) {
        if (bitmap==null){
            return;
        }
        // 首先保存图片
        File appDir = new File(Global.SAVE_SHARE_IMAGE_PATH);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = UserManager.getInstance().getUid() + "_share_" + System.currentTimeMillis() + ".png";
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
        getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(Global.SAVE_SHARE_IMAGE_PATH)));
    }

    //在消息队列中实现对控件的更改
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap bmp = (Bitmap) msg.obj;
                    if (bmp != null && !bmp.isRecycled()) {
                        anyshape_title.setImageBitmap(bmp);
                    }
                    break;
            }
        }

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
