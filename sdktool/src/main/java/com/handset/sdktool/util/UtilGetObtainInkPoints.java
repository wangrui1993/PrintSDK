package com.handset.sdktool.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.regex.Pattern;

/**
 * 获取打印墨点数
 */

public class UtilGetObtainInkPoints {
    public static int getObtainInkPoints(String deviceModel) {
        return (deviceModel.equals("TSC") || deviceModel.equals("Nmark") || deviceModel.equals(Device.DEVICEDOT_12)) ? 12 : 8;
    }

}
