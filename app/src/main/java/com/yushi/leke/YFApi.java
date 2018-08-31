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
public interface YFApi {


    /**
     * 初始化
     *
     * @param
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/common/init")
    Call<ResponseBody>
    init(
            @Field("s") String s
    );

    /**
     * 第三方登录
     *
     * @param
     * @param oauthType 授权第三方：1、微信， 2、QQ
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/uc/loginViaOauth")
    Call<ResponseBody>
    loginViaOauth(
            @Field("accessToken") String accessToken,
            @Field("oauthType") String oauthType

    );

    /**
     * 手机号登录
     *
     * @param mobile
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/uc/loginViaPwd")
    Call<ResponseBody>
    loginViaPwd(
            @Field("mobile") String mobile,
            @Field("pwd") String pwd
    );

    /**
     * 重置密码
     *
     * @param mobile
     * @param pwd
     * @param vcode
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/security/resetPwd")
    Call<ResponseBody>
    resetPwd(
            @Field("mobile") String mobile,
            @Field("pwd") String pwd,
            @Field("vcode") String vcode
    );

    @FormUrlEncoded
    @POST("app/{interfVersion}/listTreatureBox")
    Call<ResponseBody>
    listTreatureBox(//获取宝箱列表
                    @Path("interfVersion") String interfVersion,
                    @Field("goodsType") String goodsType
    );

    @FormUrlEncoded
    @POST("app/{interfVersion}/tradeMethod")
    Call<ResponseBody>
    tradeMethod(//获取商品支付方式界面（含ios内购）
                @Path("interfVersion") String interfVersion,
                @Field("isInternalTradeIos") int isInternalTradeIos,
                @Field("goodsType") int goodsType,//商品类型（1-宝箱; 2-内购宝箱;3-购买会员）
                @Field("goodsId") String goodsId
    );

    @FormUrlEncoded
    @POST("app/v1/uc/registerViaVcode")
    Call<ResponseBody>
    registerViaVcode(
            @Field("mobile") String mobile,
            @Field("pwd") String pwd,
            @Field("vcode") String vcode
    );

    @FormUrlEncoded
    @POST("app/v1/uc/sendRegisterVcode")
    Call<ResponseBody>
    sendRegisterVcode(
            @Field("mobile") String mobile,
            @Field("sessionId") String sessionId

    );

    @FormUrlEncoded
    @POST("app/v1/security/sendResetPwdVcode")
    Call<ResponseBody> sendResetPwdVcode(@Field("mobile") String mobile,
                                         @Field("sessionId") String sessionId);


    @FormUrlEncoded
    @POST("app/{interfVersion}/{function}")
    Call<ResponseBody>
    toPay(//发起支付（微信支付 wxPay ／支付宝支付 aliPay）
          @Path("interfVersion") String interfVersion,
          @Path("function") String function,
          @Field("orderTitle") String orderTitle,
          @Field("orderPrice") String orderPrice,
          @Field("tradePrice") String tradePrice,
          @Field("goodsId") String goodsId,
          @Field("tradeApiId") String tradeApiId,
          @Field("ioType") String ioType
    );


    @FormUrlEncoded
    @POST("app/{interfVersion}/haveTradePwd")
    Call<ResponseBody>
    haveTradePwd(//检查是否已经设置了交易密码/
                 @Path("interfVersion") String interfVersion,
                 @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("app/{interfVersion}/setTradePwd")
    Call<ResponseBody>
    setTradePwd(//设置/修改交易密码
                @Path("interfVersion") String interfVersion,
                @Field("userId") String userId,
                @Field("TradePwd") String TradePwd
    );

    @FormUrlEncoded
    @POST("app/{interfVersion}/verifyTradePwd")
    Call<ResponseBody>
    verifyTradePwd(//验证交易密码
                   @Path("interfVersion") String interfVersion,
                   @Field("userId") String userId,
                   @Field("tradePwd") String tradePwd
    );

    @FormUrlEncoded
    @POST("app/{interfVersion}/getAvatarUploadInfo")
    Call<ResponseBody>
    getAvatarUploadInfo(//上传图片
                        @Path("interfVersion") String interfVersion,
                        @Field("fileName") String fileName
    );
}
