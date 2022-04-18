package com.bo.cloudmusic.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import es.dmoral.toasty.Toasty;

public class ToastUtil {
    /**
     * 上下文
     */
    private static Context context;

    public static void init(Context context) {
        ToastUtil.context=context;
    }

    /**
     * 短时间错误土司
     */
    public static void errorShortToast(int id) {
        Toasty.error(context, id, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间错误土司
     */
    public static void errorLongToast(int id) {
        Toasty.error(context, id, Toast.LENGTH_LONG).show();
    }

}
