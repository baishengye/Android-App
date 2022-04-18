package com.bo.cloudmusic.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import es.dmoral.toasty.Toasty;

public class ToastUtil {

    /**
     * 短时间错误土司
     */
    public static void errorShortToast(@NonNull Context context, int id) {
        Toasty.error(context, id, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间错误土司
     */
    public static void errorLongToast(@NonNull Context context, int id) {
        Toasty.error(context, id, Toast.LENGTH_LONG).show();
    }
}
