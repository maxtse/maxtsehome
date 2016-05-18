package com.max.tse.reflect;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.max.tse.db.mybatis.enums.AgeType;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.chrono.AssembledChronology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-21
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */
public class FieldCache {

    private static final LoadingCache<CacheKey, List<Field>> CACHE = CacheBuilder.newBuilder().
            maximumSize(100).
            expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<CacheKey, List<Field>>() {
        @Override
        public List<Field> load(CacheKey key) throws Exception {
            return getAllDeclaredFields(key.getClazz(), key.getFieldFilters());
        }
    });

    private static final Logger logger = LoggerFactory.getLogger(FieldCache.class);





    /**
     * 获取所有定义的字段，包括父类中的
     * 类信息
     * @param clazz
     * 字段过滤器
     * @param fieldFilters
     * */
    public static List<Field> getAllDeclaredFields(Class clazz, List<FieldFilter> fieldFilters) {
        List<Field> fields = Lists.newArrayList();

        boolean doFilter = doFilter(clazz, fieldFilters);

        if (!doFilter) {
          fields.addAll(Lists.newArrayList(clazz.getDeclaredFields()));
        }

        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllDeclaredFields(clazz.getSuperclass(), fieldFilters));
        }
        return fields;
    }




    private static boolean doFilter(Class clazz, List<FieldFilter> fieldFilters) {
        if (clazz == null) {
            return true;
        }
        List<FieldFilter> theFilter = Lists.newArrayList(fieldFilters);
        if (CollectionUtils.isEmpty(fieldFilters)) {
            List<FieldFilter> defaultFilter = Lists.newArrayList();

           theFilter = defaultFilter;
        }


        for (FieldFilter fieldFilter : theFilter) {
            if (fieldFilter.filter(clazz)) {
                return false;
            }
        }
        return true;
    }

    public interface FieldFilter{
        boolean filter(Class clazz);
    }

    public  abstract class AbstractFieldFilter implements FieldFilter {

        @Override
        public boolean filter(Class clazz) {
            if (clazz == null) {
                return true;
            }
            return selfFilter(clazz);
        }

        abstract boolean selfFilter(Class clazz);

    }
    public  class PrimitiveFilter extends AbstractFieldFilter {
        @Override
        boolean selfFilter(Class clazz) {
            return clazz.isPrimitive();
        }
    }

    public  class EnumFilter extends AbstractFieldFilter {
        @Override
        boolean selfFilter(Class clazz) {
            return clazz.isEnum();
        }
    }

    public static class CacheKey {
        private List<FieldFilter> fieldFilters;
        private Class clazz;

        public CacheKey () {}

        public CacheKey(List<FieldFilter> fieldFilters, Class clazz) {
            this.fieldFilters = fieldFilters;
            this.clazz = clazz;
        }

        public List<FieldFilter> getFieldFilters() {
            return fieldFilters;
        }

        public void setFieldFilters(List<FieldFilter> fieldFilters) {
            this.fieldFilters = fieldFilters;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CacheKey)) return false;

            CacheKey cacheKey = (CacheKey) o;

            if (clazz != null ? !clazz.equals(cacheKey.clazz) : cacheKey.clazz != null) return false;
            if (fieldFilters != null ? !fieldFilters.equals(cacheKey.fieldFilters) : cacheKey.fieldFilters != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = fieldFilters != null ? fieldFilters.hashCode() : 0;
            result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
            return result;
        }
    }

    public static class CacheKeyBuilder {
        private List<FieldFilter> fieldFilters;
        private Class clazz;

        public CacheKeyBuilder addFilters(FieldFilter fieldFilter) {
            if (this.fieldFilters == null) {
                this.fieldFilters = Lists.newArrayList();
            }
            this.fieldFilters.add(fieldFilter);
            return this;
        }

        public CacheKeyBuilder addClass(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        public List<FieldFilter> getFieldFilters() {
            return fieldFilters;
        }

        public void setFieldFilters(List<FieldFilter> fieldFilters) {
            this.fieldFilters = fieldFilters;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        CacheKey build() {
           return new CacheKey(getFieldFilters(), getClazz());
        }
    }

    private void test() {
        CacheKey cacheKey = new FieldCache.CacheKeyBuilder().
                addClass(null).
                addFilters(new EnumFilter()).
                addFilters(new PrimitiveFilter()).
                build();
        try {
            CACHE.get(cacheKey);
        } catch (Exception e) {

        }



    }

    public static void main(String[] args) throws Exception{
        System.out.println(EnumFilter.class.getSuperclass());//class com.max.tse.reflect.FieldCache$AbstractFieldFilter
        System.out.println(EnumFilter.class.getSimpleName());//EnumFilter
        System.out.println(EnumFilter.class.getInterfaces());//[Ljava.lang.Class;@603a71ed
        System.out.println(EnumFilter.class.getName());//com.max.tse.reflect.FieldCache$EnumFilter
        System.out.println("dfds");
        System.out.println(EnumFilter.class.getCanonicalName());
        System.out.println(EnumFilter.class.getTypeParameters());
        System.out.println(EnumFilter.class.getSigners());
        System.out.println(EnumFilter.class.getPackage());
        System.out.println(EnumFilter.class.getMethods());
        System.out.println(EnumFilter.class.getModifiers());
        System.out.println(EnumFilter.class.getComponentType());
        System.out.println(EnumFilter.class.getDeclaredClasses());
        System.out.println(EnumFilter.class.getDeclaredFields());
        System.out.println(EnumFilter.class.getDeclaredMethods());
        System.out.println(EnumFilter.class.isMemberClass());
        System.out.println(EnumFilter.class.isAnnotation());
        System.out.println(EnumFilter.class.isArray());
        System.out.println(EnumFilter.class.isAssignableFrom(FieldFilter.class));
        System.out.println(EnumFilter.class.isLocalClass());
        System.out.println(int.class.isPrimitive());
        System.out.println(JSON.toJSONString(EnumFilter.class.getTypeParameters()));
        System.out.println(JSON.toJSONString((ParameterizedType)(EnumFilter.class.getGenericSuperclass())));


    }



}
