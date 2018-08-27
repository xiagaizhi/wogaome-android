package com.yushi.leke;


import com.yufan.library.api.remote.BaseApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mengfantao on 18/2/27.
 */
public interface YFApi extends BaseApi {

    /**
     * 手机号登录
     * @param mobile
     * @param pwd
     * @return
     */
    @POST("/app/v1/uc/loginViaPwd")
    Call<ResponseBody>
    loginViaPwd(
            @Field("mobile") String mobile,
            @Field("pwd") String pwd
    );
    /**
     * 重置密码
     * @param mobile
     * @param pwd
     * @param vcode
     * @return
     */
    @POST("/app/v1/security/resetPwd")
    Call<ResponseBody>
    resetPwd(
            @Field("mobile") String mobile,
            @Field("pwd") String pwd,
            @Field("vcode") String vcode
    );

    @FormUrlEncoded
    @POST("/app/{interfVersion}/listTreatureBox")
    Call<ResponseBody>
    listTreatureBox(//获取宝箱列表
                    @Path("interfVersion") String interfVersion,
                    @Field("arg1") String goodsType
    );

    @FormUrlEncoded
    @POST("/app/{interfVersion}/payMethod")
    Call<ResponseBody>
    payMethod(//获取商品支付方式界面（含ios内购）
              @Path("interfVersion") String interfVersion,
              @Field("arg1") int isInternalPayIos,
              @Field("arg2") int goodsType,
              @Field("arg3") String goodsId
    );

    @POST("/app/v1/uc/registerViaVcode")
    Call<ResponseBody>
    registerViaVcode(
            @Field("mobile") String mobile,
            @Field("pwd") String pwd,
            @Field("vcode") String vcode
    );

    @POST("/app/v1/uc/sendRegisterVcode")
    Call<ResponseBody>
    sendRegisterVcode(
            @Field("mobile") String mobile,
            @Field("sessionId") String sessionId

    );

    @POST("/app/v1/security/sendResetPwdVcode")
    Call<ResponseBody> sendResetPwdVcode( @Field("mobile") String mobile,
                                          @Field("sessionId") String sessionId);
}
