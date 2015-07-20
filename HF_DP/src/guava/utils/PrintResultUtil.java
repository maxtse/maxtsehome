package guava.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-20
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
public final class PrintResultUtil {

    private PrintResultUtil() {}


    public static void printResult(String methodName, Iterable<String> iterable) {
        printResult(methodName, iterable, null);
    }
    public static void printResult(String methodName,Iterable<String> iterable, String iterableName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(methodName), "methodName must not be null or empty");
        Preconditions.checkArgument(iterable != null, "iterable must not be null");

        for (String print : iterable) {
            System.out.println(new StringBuilder(methodName).append(" ").append(Strings.nullToEmpty(iterableName)).
                    append(": [").append(print).append("]").toString());
        }
    }
}
