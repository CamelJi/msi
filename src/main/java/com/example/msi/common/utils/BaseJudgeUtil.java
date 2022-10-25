package com.example.msi.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.msi.common.annotation.BaseNullJudge;
import com.example.msi.common.annotation.BaseMax;
import com.example.msi.common.annotation.BaseRestrict;
import com.example.msi.common.enums.FieldTypeEnum;
import com.example.msi.common.enums.RestrictPatternEnum;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class BaseJudgeUtil {

    /**
     * 判断对象含有 BaseJudge 注解的属性是否为空
     * <br/>如果有一个属性位空则返回 true， 全部不为空则返回 false
     * @param t 需要检查的对象
     * @return boolean
     */
    public static <T> boolean judgeNull(T t) {
        if (null == t) {
            return true;
        }

        List<Field> fieldList = getClassAllFieldList(t);

        // 遍历对象属性
        try {
            for (Field field : fieldList) {
                // 开启访问权限
                field.setAccessible(true);
                // 获取当前对象这个属性的值
                Object o = field.get(t);
                BaseNullJudge annotation = field.getAnnotation(BaseNullJudge.class);
                // 如果没有设置当前注解， 不用校验
                if (annotation == null) {
                    continue;
                }
                // 获取注解接口对象
                if (FieldTypeEnum.STRING == annotation.type()) {
                    if (StrUtil.isBlank((String) o)) {
                        return true;
                    }
                } else if (o == null) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断对象含有 BaseJudge 注解的属性是否为空
     * <br/>如果有一个属性位空则直接抛出异常， 全部不为空则返回 false
     * @param t 需要检查的对象
     * @return boolean
     */
    public static <T> boolean judgeNullE(T t) {
        if (null == t) {
            return true;
        }

        List<Field> fieldList = getClassAllFieldList(t);

        // 遍历对象属性
        try {
            for (Field field : fieldList) {
                // 开启访问权限
                field.setAccessible(true);
                // 获取当前对象这个属性的值
                Object o = field.get(t);
                BaseNullJudge annotation = field.getAnnotation(BaseNullJudge.class);
                // 如果没有设置当前注解， 不用校验
                if (annotation == null) {
                    continue;
                }
                // 获取注解接口对象
                if (FieldTypeEnum.STRING == annotation.type()) {
                    if (StrUtil.isBlank((String) o)) {
                        throw new RuntimeException(annotation.fieldName() + "为空");
                    }
                } else if (o == null) {
                    throw new RuntimeException(annotation.fieldName() + "为空");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断最大值 超过最大值或长度返回true
     */
    public static <T> boolean judgeMax(T t) {
        if (t == null) {
            return true;
        }

        List<Field> fieldList = getClassAllFieldList(t);

        // 遍历对象属性
        try {
            for (Field field : fieldList) {
                // 开启访问权限
                field.setAccessible(true);
                // 获取当前对象这个属性的值
                Object o = field.get(t);
                BaseMax annotation = field.getAnnotation(BaseMax.class);
                // 如果没有设置当前注解， 不用校验
                if (annotation == null) {
                    continue;
                }
                switch (annotation.type()) {
                    case STRING:
                        if (StrUtil.isNotBlank((String) o)) {
                            String oStr = (String) o;
                            if (oStr.length() > annotation.value()) {
                                throw new RuntimeException(annotation.fieldName() + "超长");
                            }
                        }
                        break;
                    case INTEGER:
                        if (o != null) {
                            Integer oInt = (Integer) o;
                            if (oInt > annotation.value()) {
                                throw new RuntimeException(annotation.fieldName() + "数值过大");
                            }
                        }
                        break;
                    case LONG:
                        if (o != null) {
                            Long oLong = (Long) o;
                            if (oLong > annotation.value()) {
                                throw new RuntimeException(annotation.fieldName() + "数值过大");
                            }
                        }
                        break;
                    case DATETIME:
                        if (o != null) {
                            LocalDateTime dt = (LocalDateTime) o;
                            if (LocalDateTime.now().plusMinutes(annotation.value()).isBefore(dt)) {
                                throw new RuntimeException(annotation.fieldName() + "日期时间不在规定范围内");
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 限制判断
     */
    public static <T> boolean judgeRestrict(T t) {
        if (null == t) {
            return true;
        }

        List<Field> fieldList = getClassAllFieldList(t);

        // 遍历对象属性
        try {
            for (Field field : fieldList) {
                // 开启访问权限
                field.setAccessible(true);
                // 获取当前对象这个属性的值
                Object o = field.get(t);
                BaseRestrict annotation = field.getAnnotation(BaseRestrict.class);
                // 如果没有设置当前注解， 不用校验
                if (annotation == null) {
                    continue;
                }

                RestrictPatternEnum[] restrictPatterns = annotation.restrictPattern();
                for (RestrictPatternEnum restrictPattern : restrictPatterns) {
                    String oStr = null;
                    if (!(o instanceof String)) {
                        oStr = o == null ? null : String.valueOf(o);
                    } else {
                        oStr = (String) o;
                    }

                    if (StrUtil.isNotBlank(oStr)) {
                        if (RestrictPatternEnum.LENGTH == restrictPattern) {
                            if (oStr.length() > annotation.length()) {
                                throw new RuntimeException(annotation.fieldName() + "超长");
                            }
                        }
                        if (RestrictPatternEnum.REGULAR_EXPRESSION == restrictPattern) {
                            Pattern pattern = Pattern.compile(annotation.regularExpression());
                            if (!pattern.matcher(oStr).matches()) {
                                throw new RuntimeException(annotation.fieldName() + "数据有误");
                            }

                            if (FieldTypeEnum.DATE == annotation.type() || FieldTypeEnum.DATETIME == annotation.type()) {
                                if (!leapMonthJudgeDay(oStr)) {
                                    throw new RuntimeException(annotation.fieldName() + "日期/时间有误");
                                }
                            }
                        }
                    }
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取对象的所有属性，包括父类的属性
     * @param t 对象
     * @return 对象属性
     */
    private static <T> List<Field> getClassAllFieldList(T t) {
        if (t == null) {
            return new ArrayList<>();
        }

        // 获取 class
        Class<?> clazz = t.getClass();
        // 获取当前对象所有属性
        Field[] declaredFields = clazz.getDeclaredFields();

        // 获取父类
        Class<?> superClazz = clazz.getSuperclass();
        Field[] superClazzDeclaredFields = superClazz.getDeclaredFields();

        List<Field> fieldList = new ArrayList<>();
        List<Field> superFields = Arrays.asList(superClazzDeclaredFields);
        fieldList.addAll(superFields);
        List<Field> fields = Arrays.asList(declaredFields);
        fieldList.addAll(fields);

        return fieldList;
    }

    /**
     * 验证闰年2月日期是否正确
     * 日期格式 yyyy{}MM{}dd 或 yyyy{}MM{}dd HH:mm:ss
     * 闰年计算: (year % 4 == 0 && year % 100 != 0) || year % 400 == 0
     * 能被4整除而不能被100整除；能被400整除
     */
    public static boolean leapMonthJudgeDay(String date) {
        if (StrUtil.isBlank(date)) {
            return true;
        }

        int year = Integer.parseInt(date.substring(0, 4));
        String monthStr = date.substring(5, 7);
        int day = Integer.parseInt(date.substring(8, 10));
        // 判断是否闰年
        if (DateUtil.isLeapYear(year)) {
            if (StrUtil.equals("02", monthStr)) {
                return day <= 29;
            }
        } else {
            if (StrUtil.equals("02", monthStr)) {
                return day <= 28;
            }
        }
        return true;
    }
}
