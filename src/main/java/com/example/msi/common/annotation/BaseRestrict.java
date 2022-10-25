package com.example.msi.common.annotation;

import com.example.msi.common.enums.FieldTypeEnum;
import com.example.msi.common.enums.RestrictPatternEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 值限制
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BaseRestrict {

    /**
     * 字段名称
     */
    String fieldName() default "";

    /**
     * 限制方式，可多种
     */
    RestrictPatternEnum[] restrictPattern();

    /**
     * 字段类型
     */
    FieldTypeEnum type() default FieldTypeEnum.OTHER;

    /**
     * 长度 使用大于判断
     */
    int length() default 255;

    /**
     * 正则表达式
     */
    String regularExpression() default "";

}
