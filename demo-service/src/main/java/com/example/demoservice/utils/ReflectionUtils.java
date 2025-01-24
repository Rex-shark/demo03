package com.example.demoservice.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

    /**
     * 需要修改
     *
     * 將 source 物件的非 null 欄位值設置到 target 物件
     * @param source 源物件
     * @param target 目標物件
     * @throws IllegalAccessException 如果欄位訪問錯誤
     */
    public static void copyNonNullProperties(Object source, Object target) throws IllegalAccessException {
        if (source == null || target == null) {
            return;
        }

        // 獲取 source 類型的所有欄位
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);  // 設置欄位可訪問

            // 獲取源物件對應欄位的值
            Object newValue = field.get(source);

            // 如果新值不為 null，則將其設置到目標物件
            if (newValue != null) {
                field.set(target, newValue);
            }
        }
    }
}
