package guava.splitter;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

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
    public static void onCharTest() {
        Iterable<String> iterable = Splitter.on('s').split("apples,splitter");
        printResult("onCharTest", iterable);
    }

    /* Splitter.on(final CharMatcher separatorMatcher) {*/
    public static  void onCharMatcher() {
        CharMatcher charMatcher = CharMatcher.DIGIT.and(CharMatcher.is('s'));//数字或者是s字符

    }


    private static void printResult(String methodName, Iterable<String> iterable) {
        printResult(methodName, iterable, null);
    }
    private static void printResult(String methodName,Iterable<String> iterable, String iterableName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(methodName), "methodName must not be null or empty");
        Preconditions.checkArgument(iterable != null, "iterable must not be null");
        for (String print : iterable) {
            System.out.println(methodName + " " + Strings.nullToEmpty(iterableName) + ": [" + print + "]");
        }
    }

    public static void main(String[] args) {
        //add test method
        onCharTest();
    }
}
