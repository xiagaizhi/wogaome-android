package com.yufan.library.widget.highlightview;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by zhanyangyang on 2018/8/24.
 */

public class HighLightInfo {

    public final static int ALIGN_LEFT=0;
    public final static int ALIGN_LEFT_UP=1;
    public final static int ALIGN_LEFT_DOWN=2;
    public final static int ALIGN_RIGHT=3;
    public final static int ALIGN_RIGHT_UP=4;
    public final static int ALIGN_RIGHT_DOWN=5;
    public final static int ALIGN_UP=6;
    public final static int ALIGN_DOWN=7;

    public final static int HEIGHTLIGHT_RE=0;
    public final static int HEIGHTLIGHT_CIR=1;

    private View tagView;
    private int icon_id;
    private int align;
    private int heightLight;
    private int offtX=0;
    private int offtY=0;

    public HighLightInfo(View tagView, @DrawableRes int icon_id, @NonNull int align, @NonNull int heightLight) {
        this.tagView = tagView;
        this.icon_id = icon_id;
        this.align = align;
        this.heightLight = heightLight;
    }

    public View getTagView() {
        return tagView;
    }

    public void setTagView(View tag) {
        this.tagView = tag;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public int getHeightLight() {
        return heightLight;
    }

    public void setHeightLight(int heightLight) {
        this.heightLight = heightLight;
    }

    public int getOfftX() {
        return offtX;
    }

    public void setOfftX(int offtX) {
        this.offtX = offtX;
    }

    public int getOfftY() {
        return offtY;
    }

    public void setOfftY(int offtY) {
        this.offtY = offtY;
    }
}
