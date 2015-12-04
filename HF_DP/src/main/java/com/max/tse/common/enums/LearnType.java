package com.max.tse.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-26
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public enum LearnType {
    JAVA("java", null),
    DB("数据库", JAVA),
    elasticSearch("elasticSearch", JAVA),
    guava("guava", JAVA),
    design("设计模式", JAVA),
    reflect("反射", JAVA),
    thread("多线程", JAVA),
    zookeeper("zookeeper", JAVA),
    test("test", JAVA);

    ;
    LearnType(String desc, LearnType parentType) {
        this.desc = desc;
        this.parentType = parentType;
    }
    public final String desc;//描述
    public final LearnType parentType;//父类
}
