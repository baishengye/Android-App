package com.bo.cloudmusic.utils;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.animation.AccelerateInterpolator;

/**
 * 动画方式切换图片Drawable的工具类
 */
public class SwitchDrawableUtil {
    private static final int INDEX_BACKGROUND = 0;
    private static final int INDEX_FOREGROUND = 1;

    /**
     * 动画执行时间:毫秒
     */
    private static final int DURATION_ANIMATION = 300;

    /**
     * 多层Drawable
     */
    private final LayerDrawable layerDrawable;


    /**
     * 属性动画
     */
    private ValueAnimator animator;

    /**
     * 构造方法
     * @param backgroundDrawable 旧图片
     * @param foregroundDrawable 新图片
     */
    public SwitchDrawableUtil(Drawable backgroundDrawable, Drawable foregroundDrawable) {
        Drawable[] drawables = new Drawable[2];

        //添加Drawable
        drawables[INDEX_BACKGROUND]=backgroundDrawable;
        drawables[INDEX_FOREGROUND]=foregroundDrawable;

        //创建多层Drawable
        layerDrawable = new LayerDrawable(drawables);

        //初始化动画
        initAnimation();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        //新图片透明度从0到255(透明到不透明)
        animator = ValueAnimator.ofFloat(0.0f, 255.0f);

        //设置执行时间
        animator.setDuration(DURATION_ANIMATION);

        //插值器:类比数字图像处理中的插值
        animator.setInterpolator(new AccelerateInterpolator());

        //添加监听器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /**
             * 每一帧变化回调
             * @param animation
             */
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画值
                int foregroundAlpha = (int) animation.getAnimatedValue();

                //前景慢慢变不透明
                layerDrawable.getDrawable(INDEX_FOREGROUND).setAlpha(foregroundAlpha);
            }
        });

    }

    public Drawable getDrawable() {
        return layerDrawable;
    }

    /**
     * 开始执行动画
     */
    public void start(){
        animator.start();
    }
}
