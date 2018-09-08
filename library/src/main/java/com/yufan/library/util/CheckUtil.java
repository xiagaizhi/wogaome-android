package com.yufan.library.util;

import android.text.TextUtils;
import android.widget.EditText;

import com.yufan.library.manager.DialogManager;

/**
 * Created by mengfantao on 18/8/23.
 */

public class CheckUtil {

    public static boolean checkInputState(EditText phone,EditText password,EditText code,boolean toast){
        if(phone!=null){
            if(TextUtils.isEmpty(phone.getText())){
                if(toast){
                    DialogManager.getInstance().toast("手机号不能为空");
                }
                return false;
            }
            if(phone.getText().length()!=11){
                if(toast){
                    DialogManager.getInstance().toast("手机号格式不正确");
                }
                return false;
            }
        }
        if(password!=null){
            if(TextUtils.isEmpty(password.getText())){
                if(toast) {
                    DialogManager.getInstance().toast("密码不能为空");
                }
                return false;
            }
            if(password.getText().length()<6||password.getText().length()>18){
                if(toast) {
                    DialogManager.getInstance().toast("密码长度需大于6位，小于18位");
                }
                return false;
            }
        }
        if(code!=null){
            if(TextUtils.isEmpty(code.getText())){
                if(toast) {
                    DialogManager.getInstance().toast("验证码不能为空");
                }
                return false;
            }

        }
        return true;

    }

    public static boolean checkInputEmpty(EditText phone,EditText password,EditText code,boolean toast){
        if(phone!=null){
            if(TextUtils.isEmpty(phone.getText())){
                if(toast){
                    DialogManager.getInstance().toast("手机号不能为空");
                }
                return false;
            }

        }
        if(password!=null){
            if(TextUtils.isEmpty(password.getText())){
                if(toast) {
                    DialogManager.getInstance().toast("密码不能为空");
                }
                return false;
            }

        }
        if(code!=null){
            if(TextUtils.isEmpty(code.getText())){
                if(toast) {
                    DialogManager.getInstance().toast("验证码不能为空");
                }
                return false;
            }

        }
        return true;

    }

    public static boolean checkPhone(String phone){
        if(TextUtils.isEmpty(phone)){
            DialogManager.getInstance().toast("手机号不能为空");
            return false;
        }
        if(phone.length()!=11){
            DialogManager.getInstance().toast("手机号格式不正确");
            return false;
        }
        return true;
    }
}
