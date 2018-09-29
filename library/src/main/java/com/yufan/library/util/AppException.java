package com.yufan.library.util;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.yufan.library.Global;
import com.yufan.library.base.BaseApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 应用程序异常：用于捕获异常和提示错误信息
 *
 * @author
 * @author
 * @created
 */
public class AppException extends Exception implements UncaughtExceptionHandler {

    /**
     * 定义异常类型
     */
    public final static byte TYPE_NETWORK = 0x01;
    public final static byte TYPE_SOCKET = 0x02;
    public final static byte TYPE_HTTP_CODE = 0x03;
    public final static byte TYPE_HTTP_ERROR = 0x04;
    public final static byte TYPE_XML = 0x05;
    public final static byte TYPE_IO = 0x06;
    public final static byte TYPE_RUN = 0x07;
    public final static byte TYPE_JSON = 0x08;
    public final static byte TYPE_FILENOTFOUND = 0x09;
    private byte type;// 异常的类型
    // 异常的状态码，这里一般是网络请求的状态码
    private int code;

    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    public AppException() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    private AppException(byte type, int code, Exception excp) {
        super(excp);
        this.type = type;
        this.code = code;

    }


    public AppException http(int code) {
        return new AppException(TYPE_HTTP_CODE, code, null);
    }

    public AppException http(Exception e) {
        return new AppException(TYPE_HTTP_ERROR, 0, e);
    }

    public AppException socket(Exception e) {
        return new AppException(TYPE_SOCKET, 0, e);
    }

    public AppException file(Exception e) {
        return new AppException(TYPE_FILENOTFOUND, 0, e);
    }

    // io异常
    public AppException io(Exception e) {
        return io(e, 0);
    }

    // io异常
    public AppException io(Exception e, int code) {
        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            return new AppException(TYPE_NETWORK, code, e);
        } else if (e instanceof IOException) {
            return new AppException(TYPE_IO, code, e);
        }
        return run(e);
    }

    public AppException xml(Exception e) {
        return new AppException(TYPE_XML, 0, e);
    }

    public AppException json(Exception e) {
        return new AppException(TYPE_JSON, 0, e);
    }

    // 网络请求异常
    public AppException network(Exception e) {
        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            return new AppException(TYPE_NETWORK, 0, e);
//        } else if (e instanceof HttpException) {
//            return http(e);
        } else if (e instanceof SocketException) {
            return socket(e);
        }
        return http(e);
    }

    public AppException run(Exception e) {
        return new AppException(TYPE_RUN, 0, e);
    }

    public int getCode() {
        return this.code;
    }

    public int getType() {
        return this.type;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            saveToSDCard(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }


    private void saveToSDCard(Throwable ex) throws Exception {
        if (FileUtil.checkFilePathExists(Global.SAVE_LOG_PATH)) {
            List<File> list = FileUtil.listPathFiles(Global.SAVE_LOG_PATH);
            if (list.size() > 10) {
                long tempTime = list.get(0).lastModified();
                File tempF = list.get(0);
                for (File f : list) {
                    if (f.lastModified() < tempTime) {
                        tempTime = f.lastModified();
                        tempF = f;
                    }
                }
                tempF.delete();
            }
        }
        File file = FileUtil.createFile(Global.SAVE_LOG_PATH, StringUtil.getDataTime("MM_dd_HH_mm_ss") + ".txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        // 导出发生异常的时间
        pw.println(StringUtil.getDataTime("yyyy_MM_dd_HH_mm_ss"));
        // 导出手机信息
        dumpPhoneInfo(pw);
        pw.println();
        // 导出异常的调用栈信息
        ex.printStackTrace(pw);
        pw.println();
        pw.close();
    }

    private void dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
        pw.print("App Version: ");
        pw.print(DeviceUtil.VersionName(BaseApplication.getInstance()));
        pw.print('_');
        pw.println(DeviceUtil.VersionCode(BaseApplication.getInstance()));
        pw.println();

        // android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.println();

        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
        pw.println();
    }


}
