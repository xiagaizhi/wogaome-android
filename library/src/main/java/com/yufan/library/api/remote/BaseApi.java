package com.yufan.library.api.remote;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mengfantao on 18/2/27.
 */
public interface BaseApi {




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
    @POST("/app/{interfVersion}/havePayPwd")
    Call<ResponseBody>
    havePayPwd(//检查是否已经设置了交易密码
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
