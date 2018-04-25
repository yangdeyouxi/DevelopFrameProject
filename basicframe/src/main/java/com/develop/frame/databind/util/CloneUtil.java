package com.develop.frame.databind.util;

import com.develop.frame.databind.bind.BindModel;

import java.lang.reflect.Field;

/**
 * Created by yangjiahuan on 2018/1/24.
 * 属性克隆工具，用于克隆所有的属性而不改变原有的内存地址
 */

public class CloneUtil {

    public static <T extends BindModel> void clone(T source, T result){
        copyValues(source,result);
    }


    /**
     * 用于对类的字段赋值，无视private,project修饰符,无视set/get方法
     * 这里还有一点，就是自定义类内部如果又是一个自定义类时,
     * @return
     */
    @SuppressWarnings("unchecked")
    private static void copyValues(Object source, Object result) {
        try {
            Class<?> obj = source.getClass();
            Class<?> objResult = result.getClass();
            Field[] fieldsSource = obj.getDeclaredFields();
            Field[] fieldsResult = objResult.getDeclaredFields();
            for (int i = 0; i < fieldsSource.length; i++) {
                fieldsSource[i].setAccessible(true);
                fieldsResult[i].setAccessible(true);
                fieldsResult[i].set(result, fieldsSource[i].get(source));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 用于对类的字段赋值，无视private,project修饰符,无视set/get方法
//     * @param c 要反射的类
//     * @param args 类的字段名和值 每个字段名和值用英文逗号隔开
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public static Object getInstance(Class c, String... args) {
//        try {
//            Object object = Class.forName(c.getName()).newInstance();
//            Class<?> obj = object.getClass();
//            Field[] fields = obj.getDeclaredFields();
//            for (int i = 0; i < fields.length; i++) {
//                fields[i].setAccessible(true);
//                for (int j = 0; j < args.length; j++) {
//                    String str = args[j];
//                    String strs[] = str.split(",");
//                    if (strs[0].equals(fields[i].getName())) {
//                        fields[i].set(object, strs[1]);
//                        break;
//                    }
//                }
//            }
//            return object;
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }



}
