package com.example.msi.common.annotation;

import com.example.msi.common.enums.FieldTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段最大值或字符最长长度
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BaseMax {

    /**
     * 字段名称
     */
    String fieldName() default "";

    /**
     * 字段类型
     */
    FieldTypeEnum type() default FieldTypeEnum.OTHER;

    /**
     * 最大值或字符最长长度
     */
    long value();
}
