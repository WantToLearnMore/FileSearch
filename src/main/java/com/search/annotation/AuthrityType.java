package com.search.annotation;

/**
 * Created by ASUS on 2017/3/22.
 */
/**
 * 自定义注解的值
 * Validate:登录和操作权限都验证
 * NoAuthrity:不验证权限
 * NoValidate：不验证登录
 */
public enum AuthrityType {
    Validate,
    NoAuthrity,
    NoValidate
}
