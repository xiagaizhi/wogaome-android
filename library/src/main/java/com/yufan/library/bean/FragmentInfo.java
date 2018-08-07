package com.yufan.library.bean;

import android.os.Bundle;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by mengfantao on 18/7/30.
 */

public class FragmentInfo {
    private SupportFragment fragment;
    private Bundle bundle;
    public FragmentInfo(Class clz) {
        try {
            fragment= (SupportFragment) clz.newInstance();
            bundle=new Bundle();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public FragmentInfo put(String key,String value){
        bundle.putString(key,value);
        return this;
    }
    public FragmentInfo put(String key,int value){
        bundle.putInt(key,value);
        return this;
    }
    public FragmentInfo put(String key,boolean value){
        bundle.putBoolean(key,value);
        return this;
    }
    public FragmentInfo put(String key,float value){
        bundle.putFloat(key,value);
        return this;
    }
    public FragmentInfo put(String key,double value){
        bundle.putDouble(key,value);
        return this;
    }
    public SupportFragment build(){
        fragment.setArguments(bundle);
        return fragment;
    }
}
