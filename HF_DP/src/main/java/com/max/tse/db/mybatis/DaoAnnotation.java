package com.max.tse.db.mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-9
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 * Note:注解 用于注入mapper
 * 注解相关文章参考：http://www.cnblogs.com/peida/archive/2013/04/24/3036689.html
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoAnnotation {
}
