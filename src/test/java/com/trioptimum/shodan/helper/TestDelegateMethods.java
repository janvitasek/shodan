package com.trioptimum.shodan.helper;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 17.12.12
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class TestDelegateMethods {

    public static Method noArgVoidMethod() {
        try {
            return TestDelegate.class.getMethod("noArgVoid");
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }

    public static Method integerArgVoidMethod() {
        try {
            return TestDelegate.class.getMethod("integerArgVoid", Integer.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }
}
