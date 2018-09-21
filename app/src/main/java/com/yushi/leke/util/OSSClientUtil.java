package com.yushi.leke.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yushi.leke.YFApi;
import com.yushi.leke.bean.OssICredentialInfo;

/**
 * 作者：Created by zhanyangyang on 2018/8/31 19:44
 * 邮箱：zhanyangyang@hzyushi.cn
 * <p>
 * 阿里云图片上传
 */

public class OSSClientUtil {
    private static OSSClientUtil instance;
    private ClientConfiguration mConf;

    private OSSClientUtil() {
        mConf = new ClientConfiguration();
        mConf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        mConf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        mConf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        mConf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
    }

    public static OSSClientUtil getInstance() {
        if (instance == null) {
            instance = new OSSClientUtil();
        }
        return instance;
    }


    public void uploadImgToOss(final Context context, String fileName, final String filePath, final UploadImageInterf uploadImageInterf) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getAvatarUploadInfo(fileName))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onResponse(ApiBean mApiBean) {
                        try {
                            if (ApiBean.checkOK(mApiBean.getCode())) {
                                OssICredentialInfo ossICredentialInfo = JSON.parseObject(mApiBean.getData(), OssICredentialInfo.class);
                                if (ossICredentialInfo != null) {
                                    String accessKeyId = ossICredentialInfo.getAccessKeyId();
                                    String secretKeyId = ossICredentialInfo.getAccessKeySecret();
                                    String securityToken = ossICredentialInfo.getSecurityToken();
                                    String bucketName = ossICredentialInfo.getBucket();
                                    String objectKey = ossICredentialInfo.getKey();
                                    String endpoint = ossICredentialInfo.getEndPoint();
                                    final String imgUrl = ossICredentialInfo.getDownloadPath();
                                    OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);
                                    OSS oss = new OSSClient(context, endpoint, credentialProvider, mConf);
                                    // 构造上传请求
                                    PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, filePath);
                                    // 异步上传时可以设置进度回调
                                    put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                                        @Override
                                        public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                                            Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                                        }
                                    });
                                    OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                                        @Override
                                        public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                            Log.d("PutObject", "UploadSuccess");
                                            Log.d("ETag", result.getETag());
                                            Log.d("RequestId", result.getRequestId());
                                            uploadImageInterf.onSuccess(imgUrl);
                                        }

                                        @Override
                                        public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                                            uploadImageInterf.onFail();
                                            // 请求异常
                                            if (clientExcepion != null) {
                                                // 本地异常如网络异常等
                                                clientExcepion.printStackTrace();
                                            }
                                            if (serviceException != null) {
                                                // 服务异常
                                                Log.e("ErrorCode", serviceException.getErrorCode());
                                                Log.e("RequestId", serviceException.getRequestId());
                                                Log.e("HostId", serviceException.getHostId());
                                                Log.e("RawMessage", serviceException.getRawMessage());
                                            }
                                        }
                                    });
                                    task.waitUntilFinished();
                                }
                            } else {
                                uploadImageInterf.onFail();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            uploadImageInterf.onFail();
                        }

                    }

                    @Override
                    public void onFailure(int id, Exception e) {
                        super.onFailure(id, e);
                        uploadImageInterf.onFail();
                    }

                    @Override
                    public void onSuccess(ApiBean mApiBean) {

                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public interface UploadImageInterf {
        void onSuccess(String url);

        void onFail();
    }
}
