package com.yushi.leke.util;

import android.text.TextUtils;

/**
 * Created by james on 2018/9/30.
 */

public class FormatImageUtil {

    /**
     * 处理图片 1、转换成webp
     *
     * @param originalUrl
     * @return
     */
    public static String converImageUrl(String originalUrl) {
        originalUrl = converImageUrl(originalUrl, 0, 0, 0, 0, 0);
        return originalUrl;
    }

    /**
     * 处理图片 1、转换成webp 2、压缩图片质量
     *
     * @param originalUrl
     * @param quality
     * @return
     */
    public static String converImageUrl(String originalUrl, int quality) {
        originalUrl = converImageUrl(originalUrl, quality, 0, 0, 0, 0);
        return originalUrl;
    }

    /**
     * 处理图片 1、转换成webp 2、指定图片宽高
     *
     * @param originalUrl
     * @param w
     * @param h
     * @return
     */
    public static String converImageUrl(String originalUrl, int w, int h) {
        originalUrl = converImageUrl(originalUrl, 0, w, h, 0, 0);
        return originalUrl;
    }

    /**
     * 处理图片 1、转换成webp 2、压缩图片质量 3、指定图片宽高
     *
     * @param originalUrl
     * @param quality     (取值范围:1-100)   决定图片的绝对质量，把原图质量压到Q%，如果原图质量小于指定数字，则不压缩。如果原图质量是100%，使用”90Q”会得到质量90％的图片；如果原图质量是95%，使用“90Q”还会得到质量90%的图片；如果原图质量是80%，使用“90Q”不会压缩，返回质量80%的原图。
     * @param w
     * @param h
     * @param blurR      模糊半径  [1,50]r 越大图片越模糊。
     * @param blurS       正态分布的标准差 [1,50]s 越大图片越模糊。
     * @return
     */
    public static String converImageUrl(String originalUrl, int quality, int w, int h, int blurR, int blurS) {
        if (TextUtils.isEmpty(originalUrl)) {
            return "";
        }
        if (originalUrl.startsWith("http://lelian")) {//阿里云图片地址
            if (!originalUrl.contains("webp")) {
                if (!originalUrl.contains("x-oss-process=image")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(originalUrl);
                    sb.append("?x-oss-process=image");
                    if (w > 0 || h > 0) {
                        sb.append("/resize");
                        if (w > 0) {
                            sb.append(",w_");
                            sb.append(w);
                        }
                        if (h > 0) {
                            sb.append(",h_");
                            sb.append(h);
                        }
                    }
                    if (blurR > 0 && blurS > 0) {
                        if (blurR > 50) {
                            blurR = 50;
                        }
                        if (blurS > 50) {
                            blurS = 50;
                        }
                        sb.append("/blur,r_");
                        sb.append(blurR);
                        sb.append(",s_");
                        sb.append(blurS);
                    }
                    if (quality > 0) {
                        sb.append("/quality,Q_");
                        sb.append(quality);
                    }
                    sb.append("/format,webp");
                    originalUrl = sb.toString();
                }
            }
        }
        return originalUrl;
    }

    /**
     * 高斯模糊
     *
     * @param originalUrl
     * @param blurR      模糊半径 [1,50]r 越大图片越模糊。
     * @param blurS      正态分布的标准差 [1,50]s 越大图片越模糊。
     * @return
     */
    public static String blurImage(String originalUrl, int blurR, int blurS) {
        return blurImage(originalUrl, 0, 0, 0, blurR, blurS);
    }

    public static String blurImage(String originalUrl, int quality, int blurR, int blurS) {
        return blurImage(originalUrl, quality, 0, 0, blurR, blurS);
    }

    public static String blurImage(String originalUrl, int w, int h, int blurR, int blurS) {
        return blurImage(originalUrl, 0, w, h, blurR, blurS);
    }

    public static String blurImage(String originalUrl, int quality, int w, int h, int blurR, int blurS) {
        return converImageUrl(originalUrl, quality, w, h, blurR, blurS);
    }

}
