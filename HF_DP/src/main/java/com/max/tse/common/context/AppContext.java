package com.max.tse.common.context;

import com.max.tse.common.enums.LearnType;
import org.slf4j.MDC;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-26
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates.
 */
public class AppContext {

    private static InheritableThreadLocal<LearnType> learnTypeLocal = new InheritableThreadLocal<>();//学习类型

    public static LearnType getLearnType() {
        return learnTypeLocal.get();
    }

    public static void setLearnType(LearnType learnType) {
        learnTypeLocal.set(learnType);
        if (learnType != null) {
            MDC.put("learnType", learnType.desc);
        }
    }


    /**
     * 清空线程变量
     * */
    public static void releaseAll() {
        learnTypeLocal.remove();
        MDC.clear();
    }
}
