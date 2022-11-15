package com.handset.sdktool.util;

import android.os.Looper;
import android.util.Log;

import androidx.annotation.IntRange;

//import android.support.annotation.IntRange;


/**
 * 日志打印工具
 *
 * @author wr
 */

public class DebugLog {

    /**
     * 控制是否打印日志
     **/
    public static boolean isDebug = true;
    /**
     * 类名
     **/
    private static String className;
    /**
     * 方法名
     **/
    private static String methodName;

    private DebugLog() {
        /* Protect from instantiations */
    }

    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
    }

    public static void e(String message) {
        if (!isDebug)
            return;
        e(message, 3);
    }

    public static void e(String message, int stackLevel) {
        if (!isDebug)
            return;

        printCallStatck(4, stackLevel);
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebug)
            return;
        printCallStatck(2);
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebug)
            return;
        printCallStatck(1);
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebug)
            return;
        printCallStatck(0);
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebug)
            return;
        printCallStatck(3);
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebug)
            return;
        printCallStatck(5);
        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

    /**
     * 打印堆栈信息
     */
    public static void printCallStatck(@IntRange(from = 0, to = 6) int level) {
//        if (true) {
//            return;
//        }
        printCallStatck(level, 2);
    }

    /**
     * 打印堆栈信息
     */
    public static void printCallStatck(@IntRange(from = 0, to = 6) int level, int stackLeve) {
//        if (true) {
//            return;
//        }
        printInfo("----", " ", level);
        printThreadInfo(level);
        Throwable ex = new Throwable();
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements == null || stackElements.length <= stackLeve) {
            return;
        }
        sb.append("at " + stackElements[stackLeve].getClassName() + ".");
        sb.append(stackElements[stackLeve].getMethodName());
        sb.append("(" +
                stackElements[stackLeve].getFileName() +
                ":"
                + stackElements[stackLeve].getLineNumber() +
                ")");
        sb.append("\n");
        printInfo("CallStatckInfo", sb.toString(), level);
    }

    /**
     * 打印线程信息
     *
     * @param level
     */
    private static void printThreadInfo(@IntRange(from = 0, to = 6) int level) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            printInfo("ThreadInfo    ", "MainThread", level);
        } else {
            printInfo("ThreadInfo    ", "ThreadGroup:" + Thread.currentThread().getThreadGroup()
                    + "ThreadName:" + Thread.currentThread().getName()
                    + "ThreadID:" + Thread.currentThread().getId(), level);
        }
    }

    /**
     * 根据级别打印信息
     *
     * @param tag
     * @param msg
     * @param level
     */
    private static void printInfo(String tag, String msg, @IntRange(from = 0, to = 6) int level) {
        switch (level) {
            case 0:
                Log.v(tag, msg);
                break;
            case 1:
                Log.d(tag, msg);
                break;
            case 2:
                Log.i(tag, msg);
                break;
            case 3:
                Log.w(tag, msg);
                break;
            case 4:
                Log.e(tag, msg);
                break;
            case 5:
                Log.wtf(tag, msg);
                break;
        }
    }

//    public static void eLogHttpRequest(OkHttpRequest okHttpRequest, String response) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("url:" + okHttpRequest.getUrl() + "\n");
//        if (okHttpRequest.getTag() != null) {
//            sb.append("tag:" + okHttpRequest.getTag().toString() + "\n");
//        }
//        Map<String, String> params = okHttpRequest.getParams();
//        if (params != null) {
//            sb.append("params:\n");
//            for (String key : params.keySet()) {
//                sb.append(key + ":" + params.get(key) + "\n");
//            }
//        }
//        sb.append("response:" + response);
//        e(sb.toString(), 3);
//    }

}
