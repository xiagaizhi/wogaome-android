package com.yufan.library.widget.highlightview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.yufan.library.R;
import com.yufan.library.util.DensityUtil;
import com.yufan.library.util.PxUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanyangyang on 2018/8/24.
 */

public class HighLightView extends View {
    private Paint paint;
    private int actionBarHeight = 0;
    private Map<Integer, HighLightInfo> lightInfoMap;
    private int count = 0;
    private float p = 0;


    public HighLightView(Context context, final PopupWindow popupWindow) {
        super(context);
        Log.i("heightview", "actionbar_h-" + actionBarHeight);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!next()) {
                    popupWindow.dismiss();
                    lightInfoMap.clear();
                    count = 0;
                }
            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.color_73000000));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Rect rect;
        if ((rect = getRect(count)) != null) {

            if (lightInfoMap.get(count).getHeightLight() == HighLightInfo.HEIGHTLIGHT_RE) {
                canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
            } else if (lightInfoMap.get(count).getHeightLight() == HighLightInfo.HEIGHTLIGHT_CIR) {
                float[] floats = getCR(rect);
                canvas.drawCircle(floats[0], floats[1], floats[2], paint);
            } else if (lightInfoMap.get(count).getHeightLight() == HighLightInfo.HEIGHTLIGHT_ROUNDREC) {
                int padding = PxUtil.convertDIP2PX(getContext(), 4);
                int radian = PxUtil.convertDIP2PX(getContext(), 6);
                RectF rectF = new RectF(rect.left - padding, rect.top - padding, rect.right + padding, rect.bottom + padding);
                canvas.drawRoundRect(rectF, radian, radian, paint);
            }
        }
        paint.setXfermode(null);
        Bitmap bitmap;
        if ((bitmap = getBitmap(count)) != null) {
            float[] floats = getXY(rect, bitmap, lightInfoMap.get(count).getAlign(), lightInfoMap.get(count).getHeightLight());
            canvas.drawBitmap(bitmap, floats[0] + lightInfoMap.get(count).getOfftX(), floats[1] + lightInfoMap.get(count).getOfftY(), paint);
        }

    }

    /*获取相对屏幕的矩阵*/
    private Rect getRect(int index) {
        if (lightInfoMap != null && lightInfoMap.size() != 0) {
            Rect rect;
            View view = lightInfoMap.get(index).getTagView();
            if (view == null) return null;
            rect = getRect(view);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                rect.offset(0, -DensityUtil.getStatusBarHeight(getContext()));
            }
            rect.set(rect.left + view.getPaddingLeft(), rect.top + view.getPaddingTop(), rect.right - view.getPaddingRight(), rect.bottom - view.getPaddingBottom());
            Log.i("heightview", "l" + rect.left + "t" + rect.top + "r" + rect.right + "b" + rect.bottom);
            return rect;
        } else return null;
    }

    private Rect getRect(View view) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        if (view.getParent() != null && view.getParent() instanceof View) {
            if (view.getParent().getParent() != null && view.getParent().getParent() instanceof View) {
                View view_parent = (View) view.getParent();
                Rect rect_parent = getRect(view_parent);
                rect.offset(rect_parent.left, rect_parent.top);
            }
        }
        return rect;
    }

    private Bitmap getBitmap(int index) {
        if (lightInfoMap != null && lightInfoMap.size() != 0) {
            Bitmap bitmap = getBitmapFromDrawable(getResources().getDrawable(lightInfoMap.get(index).getIcon_id()));
            return bitmap;
        }
        return null;
    }

    /**/
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 2);
        int height = Math.max(drawable.getIntrinsicHeight(), 2);
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    /*设置指示数据*/
    public void setHighLightInfos(Map<Integer, HighLightInfo> lightInfoMap) {
        this.lightInfoMap = lightInfoMap;
    }

    public void addHighLightInfo(int index, HighLightInfo highLightInfo) {
        if (this.lightInfoMap == null)
            this.lightInfoMap = new HashMap<>();
        lightInfoMap.put(index, highLightInfo);
    }

    /*下一张*/
    public synchronized boolean next() {
        if (lightInfoMap == null || (lightInfoMap != null && lightInfoMap.size() - 1 == count))
            return false;
        else {
            count++;
            if (lightInfoMap.containsKey(count)) {
                invalidate();
                return true;
            } else
                return next();

        }
    }

    /*获取圆心和半径*/
    private float[] getCR(Rect rect) {
        float[] floats = new float[3];
        float cx = rect.left + (rect.right - rect.left) / 2;
        float cy = rect.top + (rect.bottom - rect.top) / 2;
        float cr = (float) Math.sqrt(Math.pow((rect.right - rect.left) / 2, 2) + Math.pow((rect.bottom - rect.top) / 2, 2));
        floats[0] = cx;
        floats[1] = cy;
        floats[2] = cr;

        return floats;
    }

    /*获取指示图片起始绘制位置*/
    private float[] getXY(Rect rect, Bitmap bitmap, int align, int drawType) {
        float[] floats = new float[2];
        float x = 0;
        float y = 0;
        if (rect == null) {
            float w = DensityUtil.getScreenW(getContext());
            float h = DensityUtil.getScreenH(getContext());
            x = w / 2 - bitmap.getWidth() / 2;
            y = h / 2 - bitmap.getHeight() / 2;
            floats[0] = x;
            floats[1] = y;
            return floats;
        }

        if (drawType == HighLightInfo.HEIGHTLIGHT_CIR) {
            float[] floatscr = getCR(rect);
            rect.set((int) (floatscr[0] - floatscr[2]), (int) (floatscr[1] - floatscr[2]), (int) (floatscr[0] + floatscr[2]), (int) (floatscr[1] + floatscr[2]));
        }

        if (HighLightInfo.ALIGN_RIGHT_UP == align) {
            x = rect.right + p;
            y = rect.top - p - bitmap.getHeight();
        } else if (HighLightInfo.ALIGN_RIGHT_DOWN == align) {
            x = rect.right + p;
            y = rect.bottom + p;
        } else if (HighLightInfo.ALIGN_LEFT_UP == align) {
            x = rect.left - p - bitmap.getWidth();
            y = rect.top - bitmap.getHeight();
        } else if (HighLightInfo.ALIGN_LEFT_DOWN == align) {
            x = rect.left - p - bitmap.getWidth();
            y = rect.bottom + p;
        } else if (HighLightInfo.ALIGN_RIGHT == align) {
            x = rect.right + p;
            y = rect.top;//先这样-(bitmap.getHeight()/2-rect.bottom/2+rect.top/2)
        } else if (HighLightInfo.ALIGN_LEFT == align) {
            x = rect.left - p - bitmap.getWidth();
            y = rect.top;//先这样-(bitmap.getHeight()/2-rect.bottom/2+rect.top/2)
        } else if (HighLightInfo.ALIGN_UP == align) {
            x = rect.left - (bitmap.getWidth() / 2 - rect.right / 2 + rect.left / 2);
            y = rect.top - p - bitmap.getHeight();
        } else if (HighLightInfo.ALIGN_DOWN == align) {
            x = rect.left - (bitmap.getWidth() / 2 - rect.right / 2 + rect.left / 2);
            y = rect.bottom + p;
        }

        floats[0] = x;
        floats[1] = y;
        return floats;
    }

}
