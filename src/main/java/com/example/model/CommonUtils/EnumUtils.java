package com.example.model.CommonUtils;

import com.example.model.dict.CommonEnum;
import com.example.model.dict.OrderStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnumUtils {

    private static final Map<Class<?>, Map<Object, ?>> CODE_ENUM_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Map<String, ?>> DESC_ENUM_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Map<String, ?>> DESC_IGNORE_CASE_CACHE = new ConcurrentHashMap<>();

    // 根据code获取desc
    public static <E extends Enum<E> & CommonEnum> String getDescByCode(
            Class<E> enumClass, Object code) {
        E enumValue = getEnumByCode(enumClass, code);
        return enumValue != null ? enumValue.getDesc() : null;
    }

    // 根据code获取desc（带默认值）
    public static <E extends Enum<E> & CommonEnum> String getDescByCode(
            Class<E> enumClass, Object code, String defaultValue) {
        String result = getDescByCode(enumClass, code);
        return result != null ? result : defaultValue;
    }

    // 根据code获取枚举值
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E> & CommonEnum> E getEnumByCode(
            Class<E> enumClass, Object code) {
        Map<Object, E> codeMap = (Map<Object, E>) CODE_ENUM_CACHE
                .computeIfAbsent(enumClass, clazz -> createCodeMap((Class<E>) clazz));
        return codeMap.get(code);
    }

    // 根据desc获取枚举值
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E> & CommonEnum> E getEnumByDesc(
            Class<E> enumClass, String desc) {
        Map<String, E> descMap = (Map<String, E>) DESC_ENUM_CACHE
                .computeIfAbsent(enumClass, clazz -> createDescMap((Class<E>) clazz));
        return descMap.get(desc);
    }

    // 根据desc获取枚举值（带默认值）
    public static <E extends Enum<E> & CommonEnum> E getEnumByDesc(
            Class<E> enumClass, String desc, E defaultValue) {
        E result = getEnumByDesc(enumClass, desc);
        return result != null ? result : defaultValue;
    }

    // 根据desc获取枚举值（忽略大小写）
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E> & CommonEnum> E getEnumByDescIgnoreCase(
            Class<E> enumClass, String desc) {
        Map<String, E> descMap = (Map<String, E>) DESC_IGNORE_CASE_CACHE
                .computeIfAbsent(enumClass, clazz -> createDescIgnoreCaseMap((Class<E>) clazz));
        return descMap.get(desc.toLowerCase());
    }

    // 创建code->enum映射
    private static <E extends Enum<E> & CommonEnum> Map<Object, E> createCodeMap(
            Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(
                        CommonEnum::getCode,
                        Function.identity(),
                        (existing, replacement) -> existing));
    }

    // 创建desc->enum映射
    private static <E extends Enum<E> & CommonEnum> Map<String, E> createDescMap(
            Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(
                        CommonEnum::getDesc,
                        Function.identity(),
                        (existing, replacement) -> existing));
    }

    // 创建小写desc->enum映射
    private static <E extends Enum<E> & CommonEnum> Map<String, E> createDescIgnoreCaseMap(
            Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(
                        e -> e.getDesc().toLowerCase(),
                        Function.identity(),
                        (existing, replacement) -> existing));
    }

    public static void main(String[] args) {
        System.out.println(EnumUtils.getEnumByCode(OrderStatus.class,"1"));
        System.out.println(EnumUtils.getEnumByDesc(OrderStatus.class,"待确认"));
    }
}