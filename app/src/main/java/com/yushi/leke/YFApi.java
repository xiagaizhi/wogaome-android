package com.yushi.leke;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mengfantao on 18/2/27.
 */
public interface YFApi  {

    /**
     * 手机号登录
     * @param mobile
     * @param pwd
     * @return
     */
    @FormUrlEncoded
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
    @FormUrlEncoded
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
    @POST("/app/{interfVersion}/tradeMethod")
    Call<ResponseBody>
    tradeMethod(//获取商品支付方式界面（含ios内购）
              @Path("interfVersion") String interfVersion,
              @Field("arg1") int isInternalPayIos,
              @Field("arg2") int goodsType,
              @Field("arg3") String goodsId
    );
    @FormUrlEncoded
    @POST("/app/v1/uc/registerViaVcode")
    Call<ResponseBody>
    registerViaVcode(
            @Field("mobile") String mobile,
            @Field("pwd") String pwd,
            @Field("vcode") String vcode
    );
    @FormUrlEncoded
    @POST("/app/v1/uc/sendRegisterVcode")
    Call<ResponseBody>
    sendRegisterVcode(
            @Field("mobile") String mobile,
            @Field("sessionId") String sessionId

    );
    @FormUrlEncoded
    @POST("/app/v1/security/sendResetPwdVcode")
    Call<ResponseBody> sendResetPwdVcode( @Field("mobile") String mobile,
                                          @Field("sessionId") String sessionId);


    @FormUrlEncoded
    @POST("/app/{interfVersion}/{function}")
    Call<ResponseBody>
    toPay(//发起支付（微信支付 wxPay ／支付宝支付 aliPay）
          @Path("interfVersion") String interfVersion,
          @Path("function") String function,
          @Field("arg1") String orderTitle,
          @Field("arg2") String orderPrice,
          @Field("arg3") String payPrice,
          @Field("arg4") String goodsId,
          @Field("arg5") String payApiId,
          @Field("arg6") String ioType
    );


    @FormUrlEncoded
    @POST("/app/{interfVersion}/haveTradePwd")
    Call<ResponseBody>
    haveTradePwd(//检查是否已经设置了交易密码/
               @Path("interfVersion") String interfVersion,
               @Field("arg1") String userId
    );
    @FormUrlEncoded
    @POST("/app/{interfVersion}/setPayPwd")
    Call<ResponseBody>
    setPayPwd(//设置/修改交易密码
              @Path("interfVersion") String interfVersion,
              @Field("arg1") String userId,
              @Field("arg2") String payPwd
    );

    @FormUrlEncoded
    @POST("/app/{interfVersion}/verifyPayPwd")
    Call<ResponseBody>
    verifyPayPwd(//验证交易密码
                 @Path("interfVersion") String interfVersion,
                 @Field("arg1") String userId,
                 @Field("arg2") String payPwd
    );
}
