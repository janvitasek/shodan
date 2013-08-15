package com.trioptimum.shodan.helper;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 11.12.12
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
public class TestDestination implements TestDelegate {

    public static final int METHOD_COUNT = 6;

    public final LinkedList<Method> calls = new LinkedList<Method>();

    public static Method noArgVoidMethod() {
        try {
            return TestDestination.class.getMethod("noArgVoid");
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }

    public static Method integerArgVoidMethod() {
        try {
            return TestDestination.class.getMethod("integerArgVoid", Integer.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }

    public static Method twoArgsVoidMethod() {
        try {
            return TestDestination.class.getMethod("twoArgsVoid", String.class, Integer.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }

    public static Method twoSwappedArgsVoidMethod() {
        try {
            return TestDestination.class.getMethod("twoSwappedArgsVoid", Integer.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }

    public static Method fourArgsVoidMethod() {
        try {
            return TestDestination.class.getMethod("fourArgsVoid", Double.class, String.class, Float.class, Integer.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }

    public static Method twoArgsStringMethod() {
        try {
            return TestDestination.class.getMethod("twoArgsString", String.class, Integer.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("No such method. Fix it!");
        }
    }

    @Priority(2)
    public void noArgVoid() {
        //calls.add(new Tdcp(noArgVoidMethod(), this));
        calls.add(noArgVoidMethod());
    }

    public void integerArgVoid(Integer param) {
        //calls.add(new Tdcp(integerArgVoidMethod(), this));
        calls.add(integerArgVoidMethod());
    }

    @Priority(1)
    public void twoArgsVoid(String param1, Integer param2) {
        //calls.add(new Tdcp(twoArgsVoidMethod(), this));
        calls.add(twoArgsVoidMethod());
    }

    @Priority(4)
    public void twoSwappedArgsVoid(Integer param1, String param2) {
        //calls.add(new Tdcp(twoSwappedArgsVoidMethod(), this));
        calls.add(twoSwappedArgsVoidMethod());
    }

    public void fourArgsVoid(Double param1, String param2, Float param3, Integer param4) {
        //calls.add(new Tdcp(fourArgsVoidMethod(), this));
        calls.add(fourArgsVoidMethod());
    }

    @Priority(3)
    public String twoArgsString(String param1, Integer param2) {
        //calls.add(new Tdcp(twoArgsStringMethod(), this));
        calls.add(twoArgsStringMethod());
        return null;
    }
}
