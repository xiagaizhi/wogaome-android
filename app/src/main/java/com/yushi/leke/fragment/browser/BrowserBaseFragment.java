package com.yushi.leke.fragment.browser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.utils.TbsLog;
import com.yufan.library.Global;
import com.yufan.library.api.ApiManager;
import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.manager.UserManager;
import com.yufan.library.util.DeviceUtil;
import com.yufan.library.util.ImageUtil;
import com.yufan.library.util.SoftInputUtil;
import com.yufan.library.view.ptr.PtrDefaultHandler;
import com.yufan.library.view.ptr.PtrFrameLayout;
import com.yufan.library.view.ptr.PtrHandler;
import com.yufan.library.webview.WVJBWebViewClient;
import com.yufan.share.ShareModel;
import com.yushi.leke.App;
import com.yushi.leke.UIHelper;
import com.yushi.leke.dialog.CommonDialog;
import com.yushi.leke.dialog.ShareDialog;
import com.yushi.leke.dialog.recharge.PayDialog;
import com.yushi.leke.fragment.album.AlbumDetailFragment;
import com.yushi.leke.fragment.exhibition.detail.ExhibitionDetailFragment;
import com.yushi.leke.fragment.login.LoginFragment;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.share.ShareMenuActivity;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;


/**
 * Created by mengfantao on 18/7/26.
 */
@VuClass(BrowserVu.class)
public class BrowserBaseFragment extends BaseFragment<BrowserContract.View> implements BrowserContract.Presenter {
    private String TAG = "BrowserActivity";
    private ValueCallback<Uri> uploadFile;
    private String mIntentUrl;
    private WVJBWebViewClient wVJBWebViewClient;
    private WebSettings webSetting;

    private WVJBWebViewClient.WVJBResponseCallback payWVJBResponseCallback;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Global.BROADCAST_PAY_RESUIL_ACTION:
                    boolean ispaySuccess = intent.getBooleanExtra(Global.INTENT_PAY_RESUIL_DATA, false);
                    if (ispaySuccess) {
                        if (payWVJBResponseCallback != null) {
                            payWVJBResponseCallback.callback("");
                        }
                        new CommonDialog(_mActivity).setTitle("恭喜您，充值成功！")
                                .setPositiveName("确定")
                                .setHaveNegative(false)
                                .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                                    @Override
                                    public void onClick(CommonDialog commonDialog, int actionType) {
                                        commonDialog.dismiss();
                                    }
                                }).show();
                    } else {
                        new CommonDialog(_mActivity).setTitle("本次充值失败，请重新充值！")
                                .setPositiveName("确定")
                                .setHaveNegative(false)
                                .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                                    @Override
                                    public void onClick(CommonDialog commonDialog, int actionType) {
                                        commonDialog.dismiss();
                                    }
                                }).show();
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Global.BROADCAST_PAY_RESUIL_ACTION);
        LocalBroadcastManager.getInstance(_mActivity).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initbrowser(vu.getWebView());
    }

    @Override
    public void getBundleDate() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mIntentUrl = bundle.getString(Global.BUNDLE_KEY_BROWSER_URL);
        }
    }


    @Override
    public boolean isPtrEnable() {
        return false;
    }

    @Override
    public void openPage(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("rbaction")) {//schame协议跳转
                try {
                    Uri uri = Uri.parse(url);
//                    Uri uri = Uri.parse("rbaction://sns_share?title=标题&digest=内容&shareUrl=http%3A%2F%2Fweb.dev.leke.com%2F%23%2Fplay%2Fdetail%2F1&icon=http://lelian-dev.oss-cn-shanghai.aliyuncs.com/img/QQ%E6%88%AA%E5%9B%BE20180921173007.png");
                    String auth = uri.getAuthority();
                    if (TextUtils.equals(auth, "doSnsShare")) {//分享
                        String title = uri.getQueryParameter("title");
                        String digest = uri.getQueryParameter("digest");
                        String shareUrl = uri.getQueryParameter("shareUrl");
                        String icon = uri.getQueryParameter("icon");
                        openShare(title, digest, icon, shareUrl);
                    }
                } catch (Exception e) {
                }
            } else {//跳转h5
                start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, url).build());
            }
        }
    }


    protected WVJBWebViewClient getWebViewClient() {
        return new BrowserWebViewClient(vu.getWebView());
    }

    private void initbrowser(final WebView webView) {
        wVJBWebViewClient = getWebViewClient();
        webView.setWebViewClient(wVJBWebViewClient);
        vu.getPtr().setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (vu.getWebView() != null) {
                    vu.getWebView().reload();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, webView.getView(), header) && isPtrEnable();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                vu.onProgressChanged(webView, i);

            }


            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            CustomViewCallback callback;
            //

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                vu.onShowCustomView(view, customViewCallback);
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                vu.onHideCustomView();
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsAlert(null, "www.baidu.com", "aa", arg3);
            }

            /**
             * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
             */


            @Override
            public void onReceivedTitle(WebView view, final String arg1) {
                super.onReceivedTitle(view, arg1);
                Log.i(TAG, "webpage title is " + arg1);
                vu.onReceivedTitle(view, arg1);
            }

            private void openFileChooseProcess() {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"),
                        0);
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.i("test", "openFileChooser 3");
                BrowserBaseFragment.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }


        });

        webView.setDownloadListener(new DownloadListener() {
            private boolean isDownloadAction;

            @Override
            public void onDownloadStart(final String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                TbsLog.d(TAG, "url: " + arg0);
                if (isDownloadAction == true) {
                    isDownloadAction = false;
                    return;
                }
                isDownloadAction = true;
                Uri uri = Uri.parse(arg0);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Bundle data = new Bundle();
        data.putBoolean("standardFullScreen", false);
        //true表示标准全屏，false表示X5全屏；不设置默认false，
        //data.putBoolean("supportLiteWnd", false);
        //false：关闭小窗；true：开启小窗；不设置默认true，
        data.putInt("DefaultVideoScreen", 2);
        //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
        //
        webView.setVerticalScrollBarEnabled(false);
        if (webView.getX5WebViewExtension() != null) {
            webView.getX5WebViewExtension().setScrollBarFadingEnabled(false);
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
        webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getContext().getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getContext().getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getContext().getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        String ua = webSetting.getUserAgentString();
        if (!(ua != null && ua.contains("LekeApp"))) {
            webSetting.setUserAgentString(ua + "/LekeApp");
        }
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        if (mIntentUrl == null) {
            throw new IllegalArgumentException("intent data can not null");
        } else {
            Log.d("browser", "load:" + mIntentUrl);
            webView.loadUrl(mIntentUrl);
        }
        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        loadCookie(CookieManager.getInstance());
    }


    protected void loadCookie(CookieManager cookie) {
        CookieSyncManager.createInstance(getActivity());
        cookie.setAcceptCookie(true);
        Map<String, String> cookies = ApiManager.getInstance().getApiHeader(getContext());
        for (String key : cookies.keySet()) {
            cookie.setCookie(mIntentUrl, key + "=" + cookies.get(key));
        }
        cookie.setCookie(mIntentUrl, "User-Agent=" + webSetting.getUserAgentString());
        cookie.setCookie(mIntentUrl, "token=" + UserManager.getInstance().getToken());
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public boolean onBackPressedSupport() {
        SoftInputUtil.closeKeybordForActivity(getActivity());
        if (vu.getWebView().canGoBack()) {
            vu.getWebView().goBack();
            return true;
        }
        return super.onBackPressedSupport();

    }

    @Override
    public void onBackPressed() {
        SoftInputUtil.closeKeybordForActivity(getActivity());
        if (vu.getWebView().canGoBack()) {
            vu.getWebView().goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }

    }
//
//    private void upPhoto() {
//        // 修改头像
//        List<String> nameList = new ArrayList<>();
//        nameList.add( "选择照片");
//        final BottomMenu dialog = new BottomMenu(getActivity());
//        dialog.setAdapter(new CommonDialogAdapter(nameList, new BaseRecycleAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(View view, int postion) {
//                switch (postion) {
//                    case 0:
//                        Matisse.from(BaseBrowserFragment.this)
//                                .choose(MimeType.allOf())
//                                .countable(true)
//                                .maxSelectable(1)
//                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                .thumbnailScale(0.8f)
//                                .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
//                                        new CaptureStrategy(true, "com.youximao.anew.myapplication.fileprovider"))
//                                .imageEngine(new GlideEngine())
//                                .forResult(REQUEST_CODE_CHOOSE);
//                        break;
//                    case 1:
//
//                        break;
//                    case -1:
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, int postion) {
//                return false;
//            }
//        }));
//        dialog.show();
//    }


    @Override
    public void onDestroy() {
        if (vu.getWebView() != null)
            vu.getWebView().destroy();
        super.onDestroy();
        LocalBroadcastManager.getInstance(_mActivity).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onLoadMore(int index) {

    }

    @Override
    public void onRefresh() {
        if (vu.getWebView() != null) {
            vu.getWebView().reload();
        }
    }


    class BrowserWebViewClient extends WVJBWebViewClient {
        private boolean isError = false;

        /**
         * @param webView
         */
        public BrowserWebViewClient(WebView webView) {

            super(webView, new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {

                }
            });


            //返回
            registerHandler("web_backPrevious", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    if (getActivity() != null) {
                        onBackPressed();
                    }
                }
            });


            registerHandler("web_zfGoods", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    if (data != null) {
                        JSONObject mJSONObject = (JSONObject) data;
                        String goodsId = mJSONObject.optString("goodsId");
                        if (!TextUtils.isEmpty(goodsId)) {
                            PayDialog payDialog = new PayDialog(_mActivity, goodsId, 3, true);
                            payDialog.show();
                            payWVJBResponseCallback = callback;
                        }
                    }
                }
            });
            registerHandler("web_doPoster", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    if (data != null) {
                        JSONObject mJSONObject = (JSONObject) data;
                        String logo = mJSONObject.optString("avatar");
                        String introduction = mJSONObject.optString("motto");
                        String shareUrl = mJSONObject.optString("shareUrl");
                        String userName = mJSONObject.optString("userName");
                        String city = mJSONObject.optString("city");
                        ShareDialog shareDialog = new ShareDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("logo", logo);
                        bundle.putString("introduction", introduction);
                        bundle.putString("shareurl", shareUrl);
                        bundle.putString("username", userName);
                        bundle.putString("city", city);
                        shareDialog.setArguments(bundle);
                        shareDialog.show(getFragmentManager(), "ShareDialog");
                    }
                }
            });

            registerHandler("web_setNaviBar", new WVJBHandler() {//设置自定义头部
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    if (data != null) {
                        NaviBarInfoList naviBarInfoList = JSON.parseObject(data.toString(), NaviBarInfoList.class);
                        if (naviBarInfoList != null) {
                            getVu().setNaviBar(naviBarInfoList);
                        }
                    }
                }
            });

            registerHandler("web_tokenExpired", new WVJBHandler() {//token失效
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    UserManager.getInstance().setToken("");
                    UserManager.getInstance().setUid("");
                    App.getApp().bindUid("*");
                    getRootFragment().startWithPopTo(UIHelper.creat(LoginFragment.class).build(), MainFragment.class, true);
                }
            });

            registerHandler("web_copyText", new WVJBHandler() {//复制
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    DeviceUtil.copyTextToBoard(data.toString(), true);
                }
            });

            registerHandler("web_doShare", new WVJBHandler() {//调用原生分享
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    JSONObject jsonObject = (JSONObject) data;
                    String digest = jsonObject.optString("digest");
                    String icon = jsonObject.optString("icon");
                    String shareUrl = jsonObject.optString("shareUrl");
                    String title = jsonObject.optString("title");
                    openShare(title, digest, icon, shareUrl);
                }
            });

            registerHandler("web_jumpModule", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    JSONObject jsonObject = (JSONObject) data;
                    int type = jsonObject.optInt("type");//type: 要跳转的类型
                    switch (type) {
                        case 1://专辑详情页
                            String albumId = jsonObject.optString("sid");
                            start(UIHelper.creat(AlbumDetailFragment.class).put(Global.BUNDLE_KEY_ALBUMID, albumId).build());
                            break;
                        case 2://活动详情页
                            String activityId = jsonObject.optString("sid");
                            int activityProgress = jsonObject.optInt("status");//活动进度（0--未开始，1--报名中，2--投票中，3--已结束）
                            if (activityProgress == 0 || activityProgress == 1) {//h5详情页面
                                getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getExhibitionDetail(activityId)).build());
                            } else {//原生详情页面
                                getRootFragment().start(UIHelper.creat(ExhibitionDetailFragment.class)
                                        .put(Global.BUNDLE_KEY_EXHIBITION_TYE, activityProgress)
                                        .put(Global.BUNDLE_KEY_ACTIVITYID, activityId)
                                        .build());
                            }
                            break;
                        case 3://我的投票
                            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyVote()).build());
                            break;
                        case 4://我的邀请
                            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyInvite()).build());
                            break;
                        case 5://我的路演
                            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyRoadShow()).build());
                            break;
                    }
                }
            });


            registerHandler("web_openNewPage", new WVJBHandler() {//新窗口打开页面
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    JSONObject jsonObject = (JSONObject) data;
                    String url = jsonObject.optString("url");
                    getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, url).build());
                }
            });


            /**
             * 屏幕截图
             */
            registerHandler("web_screenshots", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBWebViewClient.WVJBResponseCallback callback) {
                    ImageUtil.saveImageFile(ImageUtil.captureWebView(getVu().getWebView()), _mActivity, Global.SAVE_SCREEN_CAPTURE_IMAGE_PATH);
                    DialogManager.getInstance().toast("截图成功");
                    if (callback != null) {
                        callback.callback("{\"code\" :\"000\"}");
                    }
                }
            });


            /**
             *下载图片保存到相册
             */
            registerHandler("web_downloadImage", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBWebViewClient.WVJBResponseCallback callback) {
                    //{"url":"XXX"}

                    JSONObject mJSONObject = (JSONObject) data;
                    String url = mJSONObject.optString("url");
                    Glide.with(_mActivity).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ImageUtil.saveImageFile(resource, _mActivity, Global.SAVE_DOWNLOAD_IMAGE_IMAGE_PATH);
                        }
                    });
                }
            });


            /**
             * 关闭当前 Web 窗口
             */
            registerHandler("web_closeWindow", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBWebViewClient.WVJBResponseCallback callback) {
                    pop();
                }
            });


        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            isError = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (isError) {
                vu.setStateError();
            } else {
                vu.setStateGone();
            }
            vu.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();  // 接受所有网站的证书
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            isError = true;
            vu.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * 去分享
     *
     * @param title
     * @param digest
     * @param icon
     * @param shareUrl
     */
    private void openShare(String title, String digest, String icon, String shareUrl) {
        try {
            title = URLDecoder.decode(title, "UTF-8");
            digest = URLDecoder.decode(digest, "UTF-8");
            icon = URLDecoder.decode(icon, "UTF-8");
            shareUrl = URLDecoder.decode(shareUrl, "UTF-8");
            ShareModel shareModel = new ShareModel();
            shareModel.setContent(digest);
            shareModel.setTitle(title);
            shareModel.setIcon(icon);
            shareModel.setTargetUrl(shareUrl);
            ShareMenuActivity.startShare(BrowserBaseFragment.this, shareModel);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


}
