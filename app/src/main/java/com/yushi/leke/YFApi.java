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

    @POST("app/v1/common/init")
    Call<ResponseBody>
    init(

    );

    /**
     * 第三方登录
     *
     * @param
     * @param oauthType 授权第三方：1、微信， 2、QQ
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/uc/registerViaOAuth")
    Call<ResponseBody>
    registerViaOAuth(
            @Field("accessToken") String accessToken,
            @Field("oauthType") String oauthType,
            @Field("openId") String openId

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

    @POST("app/v1/trade/listTreatureBox")
    Call<ResponseBody>
    listTreatureBox(//获取宝箱列表
    );

    @FormUrlEncoded
    @POST("app/v1/trade/tradeMethod")
    Call<ResponseBody>
    tradeMethod(//获取商品支付方式界面（含ios内购）
                @Field("isInternalTradeIos") int isInternalTradeIos,
                @Field("goodsType") int goodsType,//商品类型（1-宝箱; 2-内购宝箱;3-购买会员）
                @Field("goodsId") String goodsId,
                @Field("productId") String productId
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
    @POST("app/v1/trade/{function}")
    Call<ResponseBody>
    toPay(//发起支付（微信支付 wxPay ／支付宝支付 aliPay）
          @Path("function") String function,
          @Field("orderTitle") String orderTitle,
          @Field("orderPrice") String orderPrice,
          @Field("tradePrice") String tradePrice,
          @Field("goodsId") String goodsId,
          @Field("tradeApiId") String tradeApiId,
          @Field("ioType") String ioType
    );


    @POST("app/v1/trade/haveTradePwd")
    Call<ResponseBody>
    haveTradePwd(//检查是否已经设置了交易密码/
    );

    @FormUrlEncoded
    @POST("app/v1/trade/verifyTradePwd")
    Call<ResponseBody>
    verifyTradePwd(//验证交易密码
                   @Field("tradePwd") String tradePwd
    );

    @FormUrlEncoded
    @POST("app/v1/upload/getAvatarUploadInfo")
    Call<ResponseBody>
    getAvatarUploadInfo(//上传图片
                        @Field("fileName") String fileName
    );


    @POST("app/v1/account/getWalletInfo")
    Call<ResponseBody>
    getWalletInfo(//我的钱包
    );

    @FormUrlEncoded
    @POST("app/v1/security/bindMobile")
    Call<ResponseBody>
    bindMobile(//绑定手机
               @Field("mobile") String mobile,
               @Field("vcode") String vcode,
               @Field("pwd") String pwd
    );

    @POST("app/v1/msg/hasUnreadMsg")
    Call<ResponseBody>
    hasUnreadMsg(//用户是否有未读消息
    );

    @POST("app/v1/uc/getMyProfile")
    Call<ResponseBody>
    getMyProfile(//获取用户信息（不可编辑）
    );

    @POST("app/v1/uc/getMyBaseInfo")
    Call<ResponseBody>
    getMyBaseInfo(//获取用户信息（可编辑）
    );

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editavatar(
            @Field("avatar") String avatar);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    edituserName(
            @Field("userName") String userName);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editcompany(
            @Field("company") String company);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editposition(
            @Field("position") String position);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editmotto(
            @Field("motto") String motto);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editemail(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editcity(
            @Field("city") String city);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editaddress(
            @Field("address") String address);

    @FormUrlEncoded
    @POST("app/v1/uc/editMyBaseInfo")
    Call<ResponseBody>
    editgender(
            @Field("gender") String gender);

    @FormUrlEncoded
    @POST("app/v1/trade/setTradePwdWithToken")
    Call<ResponseBody>
    setTradePwdWithToken(//通过绑定手机返回token(初次绑定手机)
                         @Field("token") String token,
                         @Field("tradePwd") String tradePwd
    );

    @FormUrlEncoded
    @POST("app/v1/trade/modifyTradePwd")
    Call<ResponseBody>
    modifyTradePwd(//修改密码
                   @Field("tradeticket") String tradeticket,
                   @Field("newPwd") String newPwd
    );

    @POST("app/v1/uc/getMobile")
    Call<ResponseBody>
    getMobile(//是否绑定手机
    );

    @FormUrlEncoded
    @POST("app/v1/security/sendBindMobileVcode")
    Call<ResponseBody>
    sendBindMobileVcode(
            @Field("mobile") String mobile,
            @Field("sessionId") String sessionId

    );

    @FormUrlEncoded
    @POST("app/v1/security/sendChangeMobileVcode")
    Call<ResponseBody>
    sendChangeMobileVcode(
            @Field("sessionId") String sessionId

    );

    @FormUrlEncoded
    @POST("app/v1/security/validateChangeMobileVcode")
    Call<ResponseBody>
    validateChangeMobileVcode(
            @Field("vcode") String vcode

    );

    @FormUrlEncoded
    @POST("app/v1/security/sendBindNewMobileVcode")
    Call<ResponseBody>
    sendBindNewMobileVcode(
            @Field("sessionId") String sessionId,
            @Field("mobile") String mobile
    );


    @FormUrlEncoded
    @POST("app/v1/security/changeMobile")
    Call<ResponseBody>
    changeMobile(
            @Field("mobile") String mobile,
            @Field("vcode") String vcode,
            @Field("securityTicket") String securityTicket

    );

    @FormUrlEncoded
    @POST("app/v1/security/bindMobileAndForward")
    Call<ResponseBody>
    bindMobileAndForward(//绑定手机
                         @Field("mobile") String mobile,
                         @Field("vcode") String vcode,
                         @Field("pwd") String pwd
    );

    @FormUrlEncoded
    @POST("app/v1/trade/sendmobileVcode")
    Call<ResponseBody>
    sendmobileVcode(
            @Field("mobile") String mobile,
            @Field("sessionId") String vcode
    );

    @FormUrlEncoded
    @POST("app/v1/trade/mobileAndVCode")
    Call<ResponseBody>
    mobileAndVCode(
            @Field("mobile") String mobile,
            @Field("vCode") String vcode
    );

    /**
     * 判断手机号是否存在
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/uc/mobileExist")
    Call<ResponseBody>
    mobileExist(
            @Field("mobile") String mobile

    );

    @FormUrlEncoded
    @POST("app/v1/activity/winlist")
    Call<ResponseBody>
    winlist(//获胜名单
            @Field("activityId") String activityId
    );

    @FormUrlEncoded
    @POST("app/v1/activity/vote")
    Call<ResponseBody>
    vote(//投票页面初始化
         @Field("projectId") String projectId
    );

    /**
     * 发送修改手机密码验证码
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/security/sendChangePwdVcode")
    Call<ResponseBody>
    sendChangePwdVcode(


    );

    /**
     * 修改密码
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/security/changePwdViaVcode")
    Call<ResponseBody>
    changePwdViaVcode(
            @Field("vcode") String vcode,
            @Field("pwd") String pwd

    );

    @POST("app/v1/common/checkAppUpdate")
    Call<ResponseBody>
    checkAppUpdate(
    );

    /**
     * 获取投票中信息
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/activity/detailForVote")
    Call<ResponseBody>
    getvoteingdata(
            @Field("currentPage") int currentPage,
            @Field("activityId") String activityId
    );

    @POST("app/v1/uc/logout")
    Call<ResponseBody>
    logout(
    );

    /**
     * 行业选择列表
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/activity/listIndustryFromProject")
    Call<ResponseBody>
    getindustrylist(
            @Field("activityId") String activityId
    );

    /**
     * 城市选择列表
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/activity/listAddressFromProject")
    Call<ResponseBody>
    getcitylist(
            @Field("activityId") String activityId
    );

    /**
     * 行业城市选择投票中项目
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/activity/allProjectForVote")
    Call<ResponseBody>
    getvoteingallpro(
            @Field("currentPage") int currentPage,
            @Field("activityId") String activityId,
            @Field("industry") String industry,
            @Field("address") String address
    );

    /**
     * 获取投票已结束信息
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/activity/detailForVoteOver")
    Call<ResponseBody>
    getvoteenddata(
            @Field("currentPage") int currentPage,
            @Field("activityId") String activityId
    );

    /**
     * 行业城市选择投票已结束项目
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("app/v1/activity/allProjectForVoteOver")
    Call<ResponseBody>
    getvoteendallpro(
            @Field("currentPage") int currentPage,
            @Field("activityId") String activityId,
            @Field("industry") String industry,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("app/v1/common/getGuideAd")
    Call<ResponseBody>
    getGuideAd(//广告初始化
               @Field("osType") int osType //1:iOS 2:Android
    );

    @FormUrlEncoded
    @POST("app/v1/activity/listActivity")
    Call<ResponseBody>
    listActivity(
            @Field("currentPage") int currentPage
    );

    @FormUrlEncoded
    @POST("app/v1/activity/tradeLKCForVote")
    Call<ResponseBody>
    tradeLKCForVote(
            @Field("token") String token,
            @Field("lkc") String lkc
    );

    @POST("app/v1/activity/playVideoForProject")
    Call<ResponseBody>
    playVideoForProject(
    );

    @FormUrlEncoded
    @POST("app/v1/activity/playVideoCount")
    Call<ResponseBody>
    playVideoCount(
            @Field("projectId") String projectId
    );


    /**
     * 全局搜索
     */
    @FormUrlEncoded
    @POST("app/v1/audioActivitySearch/globalSearch")
    Call<ResponseBody>
    globalSearch(@Field("content") String content);

    /**
     * 音频搜索
     */
    @FormUrlEncoded
    @POST("app/v1/audioActivitySearch/audioSearch")
    Call<ResponseBody>
    audioSearch(@Field("content") String content,
                @Field("currentPage") String currentPage
    );

    /**
     * 活动搜索
     */
    @FormUrlEncoded
    @POST("app/v1/audioActivitySearch/activitySearch")
    Call<ResponseBody>
    activitySearch(@Field("content") String content,
                   @Field("currentPage") String currentPage);

    /**
     * 专辑详情
     */
    @FormUrlEncoded
    @POST("app/v1/albumDetail/getAlbum")
    Call<ResponseBody>
    getAlbum(@Field("albumId") String albumId);

    /**
     * 首页专辑列表
     */
    @FormUrlEncoded
    @POST("app/v1/albumChannelRelation/showAlbum")
    Call<ResponseBody>
    showAlbum(@Field("channelId") long channelId,
              @Field("currentPage") int currentPage);

    /**
     * 专辑播放列表
     */
    @FormUrlEncoded
    @POST("app/v1/albumDetail/getPlayList")
    Call<ResponseBody>
    getPlayList(@Field("albumId") String albumId,
                @Field("currentPage") String currentPage);

    /**
     * 专辑详情
     */
    @FormUrlEncoded
    @POST("app/v1/albumDetail/getAlbum")
    Call<ResponseBody>
    albumdetail(
            @Field("albumId") String albumId
    );

    /**
     *专辑详情播放列表
     *
     */
    @FormUrlEncoded
    @POST("app/v1/albumDetail/getPlayList")
    Call<ResponseBody>
    albummusiclist(
            @Field("albumId")String albumId,
            @Field("currentPage")String currentPage
    );
    /**
     * 首页banner
     */
    @POST("app/v1/banner/showBanners")
    Call<ResponseBody>
    showBanners();
    /**
     *是否订阅
     *
     */
    @FormUrlEncoded
    @POST("app/v1/albumDetail/subscribeCheck")
    Call<ResponseBody>
    substate(
            @Field("albumId")String albumId
    );
    /**
     *订阅专辑
     *
     */
    @FormUrlEncoded
    @POST("app/v1/albumDetail/subscribe")
    Call<ResponseBody>
    registeralbum(
            @Field("albumId")String albumId
    );
    /**
     *取消订阅专辑
     *
     */
    @FormUrlEncoded
    @POST("app/v1/mySubscribe/cancelSubscribe")
    Call<ResponseBody>
    unregisteralbum(
            @Field("albumId")String albumId
    );

    /**
     *我的订阅
     *
     */
    @FormUrlEncoded
    @POST("app/v1/mySubscribe/getMySubscribe")
    Call<ResponseBody>
    getMySubscribe(
            @Field("currentPage")int currentPage
    );


    @POST("app/v1/albumChannelRelation/showChannel")
    Call<ResponseBody>
    showChannel(
    );

    @POST("app/v1/common/getStsAuth")
    Call<ResponseBody>
    getStsAuth(

    );
}
