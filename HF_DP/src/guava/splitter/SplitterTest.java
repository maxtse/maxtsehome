package guava.splitter;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import guava.utils.PrintResultUtil;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-20
 * Time: 上午11:49
 * To change this template use File | Settings | File Templates.
 * Note:Splitter class test
 */
public class SplitterTest{

    /*Splitter.on(char separator)*/
    //弱：只可以使用单个字符进行拆分
    public static void onChar() {
        Iterable<String> iterable = Splitter.on('s').split("apples,splitter");
        PrintResultUtil.printResult("onChar", iterable);
    }

    /* Splitter.on(final CharMatcher separatorMatcher) {*/
    //在符合charMatcher的地方进行拆分 用途是对于一个字符串 有多个拆分规则
    public static void onCharMatcher() {
        CharMatcher charMatcher = CharMatcher.DIGIT.or(CharMatcher.is('s'));//数字或者是s字符
        PrintResultUtil.printResult("onCharMatcher", Splitter.on(charMatcher).omitEmptyStrings().trimResults().splitToList("12as2sad"));
    }

    /*Splitter.on(final String separator) {*/
    //比on(char separator)强的是 我们可以多个字符了
    public static void onString() {
        Iterable<String> iterable = Splitter.on("ha").omitEmptyStrings().trimResults().split("what happened");
        PrintResultUtil.printResult("onString", iterable);
    }

    public static void main(String[] args) {
        //add test method
        onChar();
        onCharMatcher();
        onString();
    }
}
