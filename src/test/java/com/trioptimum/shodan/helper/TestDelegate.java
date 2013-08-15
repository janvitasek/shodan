package com.trioptimum.shodan.helper;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 2.12.12
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public interface TestDelegate {

    void noArgVoid();

    void integerArgVoid(Integer param);

    void twoArgsVoid(String param1, Integer param2);

    void fourArgsVoid(Double param1, String param2, Float param3, Integer param4);

    String twoArgsString(String param1, Integer param2);
}
