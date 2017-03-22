package com.search.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ASUS on 2017/3/22.
 */

/**
 * privilege :权限值  0：每个人都可以操作  1：管理员权限
 * authrity ：验证方式
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authrity {
    int privilege()default 0;
    AuthrityType authrity()default AuthrityType.Validate;
}
