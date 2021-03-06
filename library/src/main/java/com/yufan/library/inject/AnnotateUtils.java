package com.yufan.library.inject;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.yufan.library.base.BaseVu;
import com.yufan.library.base.Vu;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotateUtils {
    public static void injectViews(BaseVu vu) {
        Class<? extends Vu> object = vu.getClass(); // 获取activity的Class
        Field[] fields = object.getDeclaredFields(); // 通过Class获取activity的所有字段
        for (Field field : fields) { // 遍历所有字段
            // 获取字段的注解，如果没有ViewInject注解，则返回null
            FindView viewInject = field.getAnnotation(FindView.class);
            if (viewInject != null) {
                int viewId = viewInject.value(); // 获取字段注解的参数，这就是我们传进去控件Id
                if (viewId != -1) {
                    try {
                        // 获取类中的findViewById方法，参数为int
                        Method method = object.getMethod("findViewById", int.class);
                        // 执行该方法，返回一个Object类型的View实例
                        Object resView = method.invoke(vu, viewId);
                        field.setAccessible(true);
                        // 把字段的值设置为该View的实例
                        field.set(vu, resView);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static int getLayoutId(BaseVu vu){
        Class<? extends Vu> object = vu.getClass(); // 获取activity的Class
        FindLayout viewInject = object.getAnnotation(FindLayout.class);
        int value=  viewInject.layout();
        if(value==0){
            value= getResId(viewInject.layoutName(),"layout",vu.getContext());
        }
       return value;
    }
    public static int getStateParentId(BaseVu vu){
        Class<? extends Vu>  object = vu.getClass(); // 获取activity的Class
        FindLayout viewInject = object.getAnnotation(FindLayout.class);
        int value=  viewInject.statusLayoutParent();
        if(value==0){
            value=  getResId(viewInject.statusLayoutParentName(),"id",vu.getContext());
        }
        return value;
    }

    public static Class getVu( Object vu){
        Class<? extends Object> object =    vu.getClass();
        VuClass viewInject = object.getAnnotation(VuClass.class);
        return viewInject.value();
    }
    public static String getTitle( BaseVu vu){
        Class<? extends Object> object =    vu.getClass();
        Title viewInject = object.getAnnotation(Title.class);
        return viewInject.value();
    }
    public static int getResId(String name,String type,Context context){
        if(TextUtils.isEmpty(name)){
            return 0;
        }
        Resources r=context.getResources();
        int id = r.getIdentifier(name,type,context.getPackageName());
        return id;
    }

}