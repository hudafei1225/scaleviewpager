package com.hu.stackcardview;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

/**
 * 相关的配置
 *
 * Created by hu on 2020/2/19.
 */
public class PageTransformerConfig {

    /**
     * 左层叠
     */
    public static final int LEFT = 1;
    /**
     * 右层叠
     */
    public static final int RIGHT = 2;

    /**
     * 视图类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({LEFT, RIGHT})
    public @interface ViewType {
    }

}
