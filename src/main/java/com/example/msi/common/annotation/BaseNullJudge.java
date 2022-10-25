package com.example.msi.common.annotation;

import com.example.msi.common.enums.FieldTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段判断
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BaseNullJudge {

    /**
     * 字段名称
     */
    String fieldName() default "";

    /**
     * 字段类型
     */
    FieldTypeEnum type() default FieldTypeEnum.OTHER;
}
